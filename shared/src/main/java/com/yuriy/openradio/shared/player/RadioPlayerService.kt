/*
 * Copyright 2021 The "Open Radio" Project. Author: Chernyshov Yuriy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yuriy.openradio.shared.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

/**
 * Radio player service that handles media playback in the background.
 * Uses Media3 ExoPlayer for audio streaming and provides media session support.
 */
@UnstableApi
class RadioPlayerService : MediaSessionService() {

    private var mMediaSession: MediaSession? = null
    private var mExoPlayer: ExoPlayer? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        initializePlayer()
        initializeMediaSession()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Radio playback controls"
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun initializePlayer() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .build()

        mExoPlayer = ExoPlayer.Builder(this)
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .build()
            .apply {
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        when (playbackState) {
                            Player.STATE_BUFFERING -> {
                                updateNotification("Buffering...")
                            }
                            Player.STATE_READY -> {
                                updateNotification(if (isPlaying) "Playing" else "Paused")
                            }
                            Player.STATE_ENDED -> {
                                updateNotification("Stopped")
                            }
                            Player.STATE_IDLE -> {
                                updateNotification("Idle")
                            }
                        }
                    }

                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        if (isPlaying) {
                            updateNotification("Playing")
                        }
                    }

                    override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                        updateNotification("Error: ${error.message}")
                    }
                })
            }
    }

    private fun initializeMediaSession() {
        mExoPlayer?.let { player ->
            mMediaSession = MediaSession.Builder(this, player).build()
        }
    }

    private fun updateNotification(status: String) {
        val notification = createNotification(status)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun createNotification(status: String): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, Class.forName("com.yuriy.openradio.mobile.view.activity.MainActivity")),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("OpenRadio")
            .setContentText(status)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        return builder.build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mMediaSession
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mMediaSession?.player
        if (player != null && !player.isPlaying) {
            stopSelf()
        }
    }

    override fun onDestroy() {
        mMediaSession?.run {
            player.release()
            release()
            mMediaSession = null
        }
        mExoPlayer = null
        super.onDestroy()
    }

    companion object {
        private const val CHANNEL_ID = "open_radio_playback"
        private const val CHANNEL_NAME = "Radio Playback"
        private const val NOTIFICATION_ID = 1
    }
}