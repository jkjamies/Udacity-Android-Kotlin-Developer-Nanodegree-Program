#Add your API KEY to the constants.

#Project Overview
Asteroid Radar is an app to view the asteroids detected by NASA that pass near Earth, you can view all the detected asteroids given a period of time with data such as the size, velocity, distance to earth and if they are potentially hazardous. In this project, you will apply the skills such as fetching data from the internet, saving data to a database, and display the data in a clear, compelling UI.

You will need the NEoWs API which is a free, open source API provided by NASA JPL Asteroid team, as they explain it: “Is a RESTful web service for near earth Asteroid information. With NeoWs a user can: search for Asteroids based on their closest approach date to Earth, lookup a specific Asteroid with its NASA JPL small body id, as well as browse the overall data-set.”

The resulting output of the project will be two screens: a Main screen with a list of all the detected asteroids and a Details screen that is going to display the data of that asteroid once it´s selected in the Main screen list. The main screen will also show the NASA image of the day to make the app more striking.

##Getting Started
The first step is to get an API Key from NASA. Follow the instructions as listed.

1. Go to the following URL - https://api.nasa.gov/ and scroll down a little 
   
Fill the required fields, click the Signup button and you will get a API key (the API Key is also going to be sent to your email ).

2. You will use the API key to send requests to NASA servers to get data about asteroids - to view how it works, scroll down a little more until you see the NeoWs data.

3. You are now ready to build a query string with the API like the following, using the API_KEY, START_DATE and END_DATE parameters. https://api.nasa.gov/neo/rest/v1/feed?start_date=START_DATE&end_date=END_DATE&api_key=YOUR_API_KEY. For eg., the following is the demo key that you can try out https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=DEMO_KEY

In the above JSON object, the asteroids are inside near_earth_objects key and all the asteroids for a given date are all listed there.

For this project you will use the following fields:
- id (Not for displaying but for using in db)
- absolute_magnitude
- estimated_diameter_max (Kilometers)
- is_potentially_hazardous_asteroid
- close_approach_data -> relative_velocity -> kilometers_per_second
- close_approach_data -> miss_distance -> astronomical

##NASA image of the day
Finally, to make the app more look more interesting, we are going to display the NASA’s image of the day in Main Screen top, this is an simple image we can get from the next link: https://api.nasa.gov/planetary/apod?api_key=YOUR_API_KEY

This is also going to return a simple JSON object of which you just need the “url” key, example:

- url: https://apod.nasa.gov/apod/image/2001/STSCI-H-p2006a-h-1024x614.jpg
- media_type: The image of the day could be an image or a video, we are using only the image, to know what media type is you have to check the media_type field, if this value is “image” you are going to download and use the image, if it’s video you are going to ignore it.
- title: The title of the picture, this is going to be used as content description of the image for Talk back.

##Project Instructions
You are provided with a starter code in our github repo, which includes the necessary dependencies and plugins that you need to complete this project.

Included in the starter project are:

- Retrofit library to download the data from the Internet.
- Moshi to convert the JSON data we are downloading to usable data in the form of custom classes.
- Picasso library to download and cache images (You could also use Glide, but we found it has some issues downloading images from this particular API).
- RecyclerView to display the asteroids in a list.

We recommend you following the guidelines seen in the courses, as well as using the components from the Jetpack library:

- ViewModel
- Room
- LiveData
- Data Binding
- Navigation

To start follow the next basic steps:

- Clone the Repo.
- Open the project with Android Studio.
- Run the project and check that it compiles correctly.


###Summarized tasks
The application you will build must:

- Include Main screen with a list of clickable asteroids as seen in the provided design using a RecyclerView with its adapter. You could insert some fake manually created asteroids to try this before downloading any data.
- Include a Details screen that displays the selected asteroid data once it’s clicked in the Main screen as seen in the provided design. The images in the details screen are going to be provided with the starter code: an image for a potentially hazardous asteroid and another one for the non-hazardous ones, you have to display the correct image depending on the isPotentiallyHazardous asteroid parameter. Navigation xml file is already included with starter code.
- Download and parse the data from NASA NeoWS (Near Earth Object Web Service) API. As this response cannot be parsed directly with Moshi, we are providing a method to parse the data “manually” for you, it’s called parseAsteroidsJsonResult inside NetworkUtils class, we recommend trying for yourself before using this method or at least take a close look at it as it is an extremely common problem in real-world apps. For this response we need retrofit-converter-scalars instead of Moshi, you can check this dependency in build.gradle (app) file.
- When asteroids are downloaded, save them in the local database.
- Fetch and display the asteroids from the database and only fetch the asteroids from today onwards, ignoring asteroids before today. Also, display the asteroids sorted by date (Check SQLite documentation to get sorted data using a query).
- Be able to cache the data of the asteroid by using a worker, so it downloads and saves today's asteroids in background once a day when the device is charging and wifi is enabled.
- Download Picture of Day JSON, parse it using Moshi and display it at the top of Main screen using Picasso Library. (You can find Picasso documentation here: https://square.github.io/picasso/) You could use Glide if you are more comfortable with it, although be careful as we found some problems displaying NASA images with Glide.
- Add content description to the views: Picture of the day (Use the title dynamically for this), details images and dialog button. Check if it works correctly with talk back.
- Make sure the entire app works without an internet connection.

###Bonus Tasks
The following are bonus tasks to challenge yourself, although they are not going to be taken into account for grading your project, you can implement them to learn more:

1. Modify the app to support multiple languages, device sizes, and orientations.
2. Make the app delete asteroids from the previous day once a day using the same workManager that downloads the asteroids.
3. Match the styles for the details screen subtitles and values to make it consistent, and make it look like what is in the designs.