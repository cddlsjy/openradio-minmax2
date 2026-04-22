package com.yuriy.openradio.mobile.dependencies

import android.content.Context
import com.yuriy.openradio.mobile.view.activity.MainActivity
import com.yuriy.openradio.shared.dependencies.DependencyRegistryCommon
import com.yuriy.openradio.shared.dependencies.FavoritesStorageDependency
import com.yuriy.openradio.shared.dependencies.LatestRadioStationStorageDependency
import com.yuriy.openradio.shared.dependencies.MediaPresenterDependency
import com.yuriy.openradio.shared.player.RadioPlayerService
import com.yuriy.openradio.shared.presenter.MediaPresenter
import com.yuriy.openradio.shared.storage.FavoritesStorage
import com.yuriy.openradio.shared.storage.LatestRadioStationStorage
import java.lang.ref.WeakReference

/**
 * Mobile-specific dependency injection registry.
 * Provides dependencies for the mobile application module.
 */
object DependencyRegistry : FavoritesStorageDependency, LatestRadioStationStorageDependency, MediaPresenterDependency {

    private var mFavoritesStorage: FavoritesStorage? = null
    private var mLatestRadioStationStorage: LatestRadioStationStorage? = null

    /**
     * Injects dependencies into the MainActivity.
     */
    fun inject(activity: MainActivity) {
        injectFavoritesStorage()
        injectLatestRadioStationStorage()
        // Initialize common dependencies
        DependencyRegistryCommon.init(activity.applicationContext)
    }

    /**
     * Injects favorites storage dependency.
     */
    override fun injectFavoritesStorage() {
        // Already handled via DependencyRegistryCommon
    }

    /**
     * Injects latest radio station storage dependency.
     */
    override fun injectLatestRadioStationStorage() {
        // Already handled via DependencyRegistryCommon
    }

    /**
     * Configures the media presenter with dependencies.
     */
    override fun configureWith(mediaPresenter: MediaPresenter) {
        DependencyRegistryCommon.configureWith(mediaPresenter)
    }

    /**
     * Configures with favorites storage.
     */
    fun configureWith(favoritesStorage: FavoritesStorage) {
        mFavoritesStorage = favoritesStorage
    }

    /**
     * Configures with latest radio station storage.
     */
    fun configureWith(latestRadioStationStorage: LatestRadioStationStorage) {
        mLatestRadioStationStorage = latestRadioStationStorage
    }

    /**
     * Gets the favorites storage instance.
     */
    fun getFavoritesStorage(): FavoritesStorage? = mFavoritesStorage

    /**
     * Gets the latest radio station storage instance.
     */
    fun getLatestRadioStationStorage(): LatestRadioStationStorage? = mLatestRadioStationStorage
}
