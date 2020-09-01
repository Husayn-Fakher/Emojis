# Emojis
An application demonstrating the use of the latest notions in android

The application is written in Kotlin using the MVVM architecture.

I use LiveData and coroutines for asynchronous code.

I use retrofit for networking.

A custom gson converter factory and moshi in order to parse the API response.

Room android library for data storage. The database is used as a cash 
in order to reduce network traffic and improve UI delays.


RecyclerViews for displaying data in lists. I implement features such as
pull to refresh and deletion and moving of items by touch events.

I use the android paging library in order to show large lists.

-----------------------------------------------------------------------------

                                  App Usage:

When you start the app are shown a screen with 5 buttons and an EditText.
 
 The first button is to diplay a random emoticons in the imageView.
 
 The second button will display a list of all the Emoticons stored on the device.
 You can delete an emoticon by clicking on its image. You can also move it by long pressing 
 its image.Note that the image will not be deleted form the database. It will be shown
 again if you do a pull to refresh.
 
 After that you have an editText where you can input the name of a git user account. You will get 
 the avatar or the image associated with the username by clicking on the search icon
 next to the editText. Each time you do a search the result will be saved on the device and you 
 can see the images of all previous searches by clicking on the next button named
 Avatar List. You can delete an image by clicking it. Here the image will be also deleted
 from the database.
 
 
 The last button Get repos will list all the google repositories in a scrollable screen.


