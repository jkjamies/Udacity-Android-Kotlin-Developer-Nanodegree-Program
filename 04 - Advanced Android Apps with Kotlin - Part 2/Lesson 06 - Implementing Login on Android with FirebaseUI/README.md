# udacity.android.kotlin.developer.nanodegree.firebaseui_login
Firebase Login Application from Udacity Android Kotlin Developer Nanodegree program.
\
Firebase Authentication: https://firebase.google.com/docs/auth \
FirebaseUI for Auth: https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md \
Firebase Console: https://console.firebase.google.com/ \
Obtain SHA1 debug signing certificate: https://developers.google.com/android/guides/client-auth \
Download the google-services.json file and add it to the project under app directory.\
Firebase documentation: https://firebase.google.com/docs/guides?authuser=0 \
Firebase sample apps: https://firebase.google.com/docs/samples?authuser=0 \
\
In firebase console, select project, develop, authentication (left side panel).\
Go to sign in method (top navigation bar, may have to choose get started first), click email/password and toggle 'enabled' switch, and click save.\
Click on Google row, toggle the enabled switch, enter a project support email, click save.\
Can go to users tab (top navigation bar) and see registered users\
\
Firebase login, logged in, and logout states included in the observeAuthenticationState function.\
\
Conditional Navigation for a settings screen.\
\
\
\
Popping the Back Stack\
As the user progresses through an app, Android keeps track of the screens they visit. When the user presses the back button, the app goes back through the visited screens in order.\
\
When you pop a screen off the backstack, you are basically telling Android to skip that screen when the user presses the back button.\
\
In this case, you are removing the current screen from the back stack so that when the user presses the back button, the app skips this screen and goes back to the screen that the user visited before visiting this one.\
\
In other words, when the user presses the back button, the app does not come back to the login screen, instead it goes to the screen that the user visited before coming to the login screen.\
\
\
\
Android developer documentation\
Android's Autofill Framework: https://developer.android.com/guide/topics/text/autofill \
Smart Lock for Passwords on Android: https://developers.google.com/identity/smartlock-passwords/android/ \
\
Videos\
Optimizing User Flows through Login: https://www.youtube.com/watch?v=dbMxvTG0KEU