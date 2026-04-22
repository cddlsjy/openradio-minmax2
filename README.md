---
AIGC:
    ContentProducer: Minimax Agent AI
    ContentPropagator: Minimax Agent AI
    Label: AIGC
    ProduceID: "00000000000000000000000000000000"
    PropagateID: "00000000000000000000000000000000"
    ReservedCode1: 3046022100e8cefcdda71b6162aec141ee0205808a33c7f2da262e69fb6d426cf4e292d9920221008426163f9a9b2ce39976f454a42e92eefc6a0e0a12aeae6ea7d53f77290cd9cc
    ReservedCode2: 3045022046f38f72527d504c50b5d39a09a6b219fa917b9cf1d36b42b4496bc0b0de497b02210099b7650bbc714e7ab2cc29ab42ce286491b4408a2504c53c0ce061d5bb834694
---

# Open Radio #

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/I2I1LGFPH)

### What is this ? ###

* **Open Radio** is the project which is use media framework of Android to provide live streaming of the world wide Radio Stations. Open Radio supported on Mobile, Android Auto, Android TV and Android Automotive.
* This project is use [Community Radio Browser's API](http://www.radio-browser.info) and [Web Radio](https://jcorporation.github.io/webradiodb) - services that provide a list of radio stations broadcasting their live stream on the Internet.
* Graphics are provided by [Free Iconset: Beautiful Flat Mono Color Icons by Elegantthemes](http://www.iconarchive.com/show/beautiful-flat-one-color-icons-by-elegantthemes.html)
* Playlist parser is provided by [William Seemann](https://github.com/wseemann/JavaPlaylistParser)
* Playback powered by [Exo Player](https://github.com/google/ExoPlayer)
* Offline countries boundaries are provided by [Tobias Zwick](https://github.com/westnordost/countryboundaries)
* Android requirements : Android 4.2 (API level 17) (new APIs for implementing audio playback that is compatible with Auto) or newer.

### Permissions used ###

* INTERNET - To access internet connection.
* ACCESS_NETWORK_STATE - To monitor Internet connection state, detect connect and reconnect states.
* WAKE_LOCK - To keep screen on while playing Radio Station.
* ACCESS_COARSE_LOCATION - On user's demand only - to select Country for user based on Location. This helps to navigate local Radio Stations.
* READ_EXTERNAL_STORAGE (Android 12 and older), READ_MEDIA_IMAGES (Android 13 and newer, on user's demand only) to read image from phone's memory when set it as image for Local Radio Station.
* FOREGROUND_SERVICE - To keep service active while playing stream.
* BLUETOOTH, BLUETOOTH_ADMIN, BLUETOOTH_CONNECT - On user's demand only - to handle connection with a Bluetooth device.
* RECORD_AUDIO - On user's demand only - to use voice search engine on Android TV.

### Delivery files ###

* [Google Play](https://play.google.com/store/apps/details?id=com.yuriy.openradio) - this application is suitable now for the Android Media Browser simulator as well as for the Android Auto.

**Application is fully compatible with vehicle's system.**

In order to run application just like it does on vehicle it is necessary to install [Android Auto for Mobile](https://play.google.com/store/apps/details?id=com.google.android.projection.gearhead&hl=en).

**Enjoy!**
