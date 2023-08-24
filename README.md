# xplor-android-challenge
Android challenge for interviewing candidates

## The app has the following characteristics:
* MVVM Architecture w/ a ApiState sealed class wrapper for responses
* Vanilla Dagger2
* Room Database for caching
* Retrofit for networking
* Coroutines and Flows for asynchronous programming
* Kotlin programming language


## App Description
The app consists of 3 activites.
- MainActivity will present the user with the option to load the XML or Compose challenge
- XmlListActivity, this will load a horizontal and vertical recyclerviews using XML. The data will be provided downstream from Retrofit and Room
- ComposeActivity, this screen is purposely left in blank so that candidates can show their compose abilities and build UI around it.

## Possible Challenges
- Fix the broken code, currently there are 4 places where code is broken
- Reimplement the XML layout in Compose
- Create a error interceptor for the network layer
- Fix a UI bug. There is a UI bug in the PokedexAdapter class. 
- Whenever an item in the list is selected, the UI is not refreshed to reflect the `isFavorite` 
- state of the item; you will need to scroll away of the item and back to it to see the UI changes reflected.
  
## Working project

https://github.com/Mariana-Tek/xplor-android-challenge/assets/20048096/fd52512e-ff9d-4819-b044-aeadaf349226

