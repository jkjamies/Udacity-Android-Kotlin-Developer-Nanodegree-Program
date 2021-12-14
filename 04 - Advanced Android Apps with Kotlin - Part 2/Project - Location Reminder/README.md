In this project, you will create a TODO list app with location reminders that remind the user to do something when the user is at a specific location. The app will require the user to create an account and login to set and access reminders.

#Getting Started
The first step is to clone the starter project to your local machine and open the project using Android Studio.

##Dependencies
There are some dependencies that are needed to complete your project, here are the step-by-step instructions to get a dev environment running:

1. To enable Firebase Authentication:
    - Go to the authentication tab at the Firebase console and enable Email/Password and Google Sign-in methods.
    - Download google-services.json and add it to the app.
2. To enable Google Maps:
    - Go to APIs & Services at the Google console.
    - Select your project and go to APIs & Credentials.
    - Create a new api key and restrict it for android apps.
    - Add your package name and SHA-1 signing-certificate fingerprint.
    - Enable Maps SDK for Android from API restrictions and Save.
    - Copy the api key to the google_maps_api.xml
3. Run the app on your mobile phone or emulator with Google Play Services in it.

#Project Instructions
1. Create a Login screen to ask users to login using an email address or a Google account. Upon successful login, navigate the user to the Reminders screen. If there is no account, the app should navigate to a Register screen.
2. Create a Register screen to allow a user to register using an email address or a Google account.
3. Create a screen that displays the reminders retrieved from local storage. If there are no reminders, display a "No Data" indicator. If there are any errors, display an error message.
4. Create a screen that shows a map with the user's current location and asks the user to select a point of interest to create a reminder.
5. Create a screen to add a reminder when a user reaches the selected location. Each reminder should include
    - title
    - description
    - selected location
6. Reminder data should be saved to local storage.
7. For each reminder, create a geofencing request in the background that fires up a notification when the user enters the geofencing area.
8. Provide testing for the ViewModels, Coroutines and LiveData objects.
9. Create a FakeDataSource to replace the Data Layer and test the app in isolation.
10. Use Espresso and Mockito to test each screen of the app:
    - Test DAO (Data Access Object) and Repository classes.
    - Add testing for the error messages.
    - Add End-To-End testing for the Fragments navigation.

###Student Deliverables
- APK file of the final project.
- Git Repository or zip file with the code.