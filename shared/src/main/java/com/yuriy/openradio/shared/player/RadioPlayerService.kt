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
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

/**
 * Radio player service that handles media playback in the background.
 * Uses Media3 ExoPlayer for audio streaming and provides media session support.
 */
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
        mExoPlayer = ExoPlayer.Builder(this)
            .setHandleAudioBecomingNoisy(true)
            .build()
            .apply {
                // Add player listener for playback state changes
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        // Handle playback state changes
                        when (playbackState) {
                            Player.STATE_BUFFERING -> {
                                // Show buffering notification
                            }
                            Player.STATE_READY -> {
                                // Playback ready
                            }
                            Player.STATE_ENDED -> {
                                // Playback ended
                            }
                            Player.STATE_IDLE -> {
                                // Player idle
                            }
                        }
                    }

                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        // Update notification based on playing state
                    }

                    override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                        // Handle playback error
                    }
                })
            }
    }

    private fun initializeMediaSession() {
        mExoPlayer?.let { player ->
            mMediaSession = MediaSession.Builder(this, player)
                .setCallback(MediaSessionCallback())
                .build()
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mMediaSession
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mMediaSession?.player
        if (player != null && !player.playWhenReady) {
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

    private inner class MediaSessionCallback : MediaSession.Callback {
        // Custom callback implementation if needed
    }

    companion object {
        private const val CHANNEL_ID = "open_radio_playback"
        private const val CHANNEL_NAME = "Radio Playback"
        private const val NOTIFICATION_ID = 1
    }
}