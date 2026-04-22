package com.yuriy.openradio.shared.storage

import android.content.Context

interface LatestRadioStationStorage {
    fun saveLatestStation(mediaId: String)
    fun getLatestStation(): String?
}

class LatestRadioStationStorageImpl(context: Context) : LatestRadioStationStorage {
    private val prefs = context.getSharedPreferences("latest_station", Context.MODE_PRIVATE)

    override fun saveLatestStation(mediaId: String) {
        prefs.edit().putString("latest", mediaId).apply()
    }

    override fun getLatestStation(): String? {
        return prefs.getString("latest", null)
    }
}
