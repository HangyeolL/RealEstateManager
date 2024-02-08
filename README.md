
# Real Estate Manager

Real Estate Manager is a functional Native Android app built entirely with Kotlin and Jetpack Libraries. It is intented to follow the Android design and development best practices.
As a running app, it's goal is to help real estate agents to access and manage their properties on various devices such as mobile and tablet.

![Screenshot RealEstateManager Banner](docs/images/RealEstateManager_Banner.png "Screenshot showing List, Detail, Search, Add or Modify screen and tablet mode screen")
# Features

Real Estate Manager displays the list of real estate properties. User can browse to detail screen by clicking on the list item.
User can add or modify the real estate property and see the change applied immediately.
User can use the search feature to sort out the list with many criterias available.
User can also check the google map with or without geo-localisation to check the real estate properties around.
Lastly, user can customize some setting features.
- List 
- Detail 
- Add or Modify 
- Search
- Google map
- Setting
# Architecture

The app follows the official architecture guidance.   
Therefore, there are **Data - Domain - UI** layers.  
*Dependency Injection* is powered by `HILT`.
There are two modules, one to provide the dependencies and second one to bind the repository interfaces from domain layer with actual implementation in data layer. 
`Jetpack Navigation` is implemented which is the most up to date navigation solution at this moment, it also helps supporting various device navigation flow such as tablet.


## Data

All the datasets for the app is provided by local `asset`.  
The asset is populated at the creation of the `Room` database.  
There are also some remote APIs using `Retrofit` such as `Autocomplete`,`Geocoding`.
`Coroutine` is used to do the *Asynchronous* programming so that some operations that has a possibility to take a long time is done in antoher thread which makes the main thread safe.
`Flow` is also adopted for a stream of data input which makes the app reactive.

## Domain

This layer is responsible for encapsulate buisiness logic from the *repositories* which is from the Data layer.
It also contains some *model* classes to use only the data that we need in UI layer rather than conusming the whole properties of the model classes from the data layer directly.

## UI

This layer is composed with theses 3 main components :
*View(Fragment, Activity) - ViewModel - ViewState*.
Each View has it's own ViewModel except some simple child dialog fragments. In this case they use their parent's ViewModel.
When the dynamic data and static data inputs both needed to transform the ViewState, either there are several seperated ViewState classes or `Sealed` class is used.
`CoroutineLiveData` is the form of data output. It allows to launch the Coroutine live data scope with desired thread.
One shot Events which happens outside of data inputs is handled by `SingleLiveEvent`
`Design System` is also in place at UI layer for reusable components which helps to facilitate the maintenance and increase the readablity of code.












# Unit Test

`InstantTaskExecutorRule` and `TestCoroutineRule` are used.
The common process of unit testing viewmodel is like this : 
First, all the dependencies to create the `ViewModel` instance is mocked using `Mockk` library.
At `Before`, all the behaviours are explicitly thought to simulate the ViewModel then the instance is initialised.
At each `Test`, sepcific secnarios are distributed. The process is composed in 3 steps : `Given`, `When`, `Then`.
You can find dummy dataset in Defaults class.

# More info 

`Room` database uses the class Converters to store property like LatLng as String. 

`ViewBinding` is delegated using ViewBindingDelegates class which can be found in utills folder.
It ensures the proper behaviour of LifecycleObserver and reduce boilerplate code in UI.

`SlidingPaneLayout` component supports showing two panes side by side on larger devices while automatically adapting to show only one pane at a time on smaller devices such as phones.

`GoogleMap` is realised thanks to `SupportMapFragment`.
To use remote APIs, you need to provide your own API key in local.properties file.
By default, my personal API key is hidden(git ignored) due to security reason.

`DataStore` preference is used for SettingsFragment. 
`FusedLocationProviderClient` is used for the geo-localisation function.  
`Glide` is chosen to rend images.

In MainApplication class you can see that the notification channel is created. This channel is used to send a notification when the user requests `Firebase` remote database synchronization.
There is also FirebaseSynchronizationWorker class which is used in MainViewModel to enque the notification request using `WorkManager`.






