# udacity.android.kotlin.developer.nanodegree.treasureHunt
Geo-fence Treasure Hunt Application from Udacity Android Kotlin Developer Nanodegree program.
\
Geofence permissions in the Android Manifest include ACCESS_FINE_LOCATION and if Android Q or higher, also ACCESS_BACKGROUND_LOCATION\
\
Geofence should first check permissions and have additional permission in check for Android Q or higher\
\
After checking permissions should also check for location being on\
\
Resolvable Api Exception used for addOnFailureListener which will request to turn on device location\
https://developers.google.com/android/reference/com/google/android/gms/common/api/ResolvableApiException.html \
\
Adding a geofence uses a pending intent with the geofence client under the location services\
https://developer.android.com/reference/android/app/PendingIntent \
https://developers.google.com/android/reference/com/google/android/gms/location/GeofencingClient \
\
Geofence events are caught via a broadcast receiver (only 1 geofence can be active at a time)\
https://developer.android.com/guide/components/broadcasts \
https://developer.android.com/reference/android/content/BroadcastReceiver \
\
When you no longer need geofences it's best practice to remove them in order to save battery and cpu cycles\
Also should remove them in OnDestroy of the app\
\
Maximum number of geofences is 100\
\
General documentation: https://developer.android.com/training/location/geofencing \
\
General API tutorial for geofencing: https://www.raywenderlich.com/7372-geofencing-api-tutorial-for-android