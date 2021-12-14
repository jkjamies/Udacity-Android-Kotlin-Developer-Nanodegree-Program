# udacity.android.kotlin.developer.nanodegree.eggtimernotifications
Egg Timer Notifications Application from Udacity Android Kotlin Developer Nanodegree program.

Highlights:

Notifications\
NotificationManager\
Notification Importance\
Notification Channel (API 26)\
AlarmManager\
Notification Update/Cancel/CancelAll\
Notification Styling (BigTextStyle, BigPictureStyle, InboxStyle, MediaStyle, MessagingStyle)\
Notification Actions (Buttons on Notifications)\
Notification Importance and Priority\
Notification Badges and Removal\
Firebase Cloud Messaging (push notifications)\
Message Composing (comes with Firebase, Manifest, create channel, service [for token/etc])\
Data Messages (Firebase)\
\
Notification - Foreground: onMessageReceived(), Background: System Tray\
Data - Foreground: onMessageReceived(), Background: onMessageReceived()\
Notification + Data - Foreground: onMessageReceived(), Background: Notification - System Tray and Data - onMessageReceived()\
\
Note: can make a notification internally from onMessageReceived() if foregrounded