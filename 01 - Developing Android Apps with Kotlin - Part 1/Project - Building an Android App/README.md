#Introduction to the project
In this project, students will apply their skills in building layouts and navigation files by building a Shoe Store app. They will build an app with four screens:

1. Login
2. Onboarding
3. Shoe listing
4. Shoe detail page

While the login and onboarding pages will not do anything, the user will need to save the login state so that the login and onboarding pages do not show again.

The shoe listing page will show the list of shoes and show a button for adding a new shoe, while the detail page will be for adding a shoe entry. All entries will be saved only as long as the app is running.

##The Shoe Store
This project will consist of five screens. You don't have to create a shoe store, you can use any other item as long as you create the following screens. You will be creating:

1. Login screen: Email and password fields and labels plus create and login buttons
2. Welcome onboarding screen
3. Instructions onboarding screen
4. Shoe Listing screen
5. Shoe Detail screen for adding a new shoe

##Getting Started
Steps
1. Open the starter project from the github repo in Android Studio
2. Add the navigation libraries to the app build.gradle file
3. Add the safe-arg plugin to the main and app build.gradle file
4. Set DataBindingUtil in build.gradle
5. Use DataBindingUtil to inflate every layout
6. Create a new navigation xml file
7. Create a new Login destination.
    - Include email and password labels
    - Include email and password fields
    - Create buttons for creating a new login and logging in with an existing account
    - Clicking either button should navigate to the Welcome Screen.
8. Create a new Welcome screen destination that includes:
    - A new layout
    - At least 2 textviews and use any text you would like
    - A navigation button with actions to navigate to the instructions screen
9. Create a new Instruction destination that includes:
    - A new layout
    - At least 2 textviews with appropriate information
    - A navigation button with actions to navigate to the shoe list screen
10. Create a class that extends ViewModel
    - Use a LiveData field that returns the list of shoes
11. Create a new Shoe List destination that includes:
    - A new layout
    - A ScrollView
    - A LinearLayout inside the ScrollView for Shoe Items
    - A FloatingActionButton with an action to navigate to the shoe detail screen
12. In MainActivity, setup the nav controller with the toolbar and an AppBarConfiguration
13. In MainActivity, add a Logout menu to return to the login screen
14. Create a new Shoe Detail destination that includes:
    - A new layout
    - A TextView label and EditView for the Shoe Name, Company, Shoe Size and Description
    - A Cancel button with an action to navigate back to the shoe list screen
    - A Save button with an action to navigate back to the shoe list screen and add a new Shoe to the Shoe View Model.

_**Hint:** In the Shoe List destination, to add a child view programmatically to a ViewGroup, such as LinearLayout, use the addView() method. Refer to a sample implementation in the StackOverflow discussion here._

15. Make sure you can’t go back to onboarding screens In the Shoe List screen:
    - use an Activity level ViewModel to hold a list of Shoes (use by activityViewModels)
    - Observe the shoes variable from the ViewModel
    - Add a new layout item into the scrollview for each shoe.