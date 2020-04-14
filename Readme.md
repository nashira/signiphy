
```
|-------------------------------|
|            Android            |
|-------------------------------|
|    Activity   |               |
|---------------|  Service/     |
|    ViewModel  |    Job        |
|-------------------------------|
|            Logic              |
|-------------------------------|
| Remote Models | Local Models  |
|-------------------------------|
|       Net/API | Data/DB       |
|-------------------------------|
```

## Overview ##

The app is organized into a few main sections that seem pretty universal amongst all apps.  Since this app is simple there is one package per section, I'll go through them in roughly front-to-back order.

The UI observes local persistent data through a ViewModel and sends user interaction events to the ViewModel.
The ViewModel processes raw events into logical events and calls the logic class to handle them.
The logic class handles syncing data between local storage and remote services which triggers data changes that are observed by the UI.

## UI ##
All the UI related stuff goes here like Activities, Fragments, ViewModels, Adapters, etc.  The two main jobs here are 
1 - watch for data changes and update UI to match, 
2 - take user input and pass it to the logic layer.

## Logic ##
This contains business logic and is the integration point for the data, net, and ui layers.  
It handles UI events passed from ViewModels and applies validation, triggers events locally and remotely, syncs with server, translates from remote domain to local domain, etc.

## Data ##
This is the local persistent storage layer, built with Room/Sqlite.  It is the data model used by the logic layer and other layers that don't require their own data model.   

## Net ##
This layer defines the Giphy API and client.  The API is defined in retrofit and the client is created with any serializers or interceptors added.
Here the api_key is added to every request via an Interceptor.
Also, network data models are defined here for de/serialization from/to the remote domain.

## Inject ##
Fairly standard Dagger Android setup.  One feature to point out is how ViewModels are injected into a ViewModel.Factory to integrate Dagger and Lifecycle worlds so that ViewModelProviders can control the lifecycle of your ViewModels while still having them be Injectable.
