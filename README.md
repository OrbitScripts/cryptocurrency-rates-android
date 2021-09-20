# Сryptocurrency rates android application 
The sample application written entirely in Java and uses the Gradle build system.

# Features
The app displays a list of cryptocurrencies rates. 

For each currency, the following is displayed:
- Logo
- Name of the coin
- Currency symbol
- Price of the coin
- Market capitalization. Price times circulating supply
- 24h trade volume (graph)

Sorting by:
- Price of the coin
- 24h trade volume

# Development Environment
The app is written entirely in Java.

Asynchronous tasks are handled with
[RxJava](https://github.com/ReactiveX/RxJava).

Dependency Injection is implemented with
[Hilt](https://developer.android.com/training/dependency-injection/hilt-android).

[Room](https://developer.android.com/jetpack/androidx/releases/room) is used
for store data local.

[Retrofit](https://square.github.io/retrofit/) is used
for recive data from API.

[Paging library 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) is used
for displaing paging from a layered data source: network API data source with a local database cache (Room).


# Build
To build the app, use the `gradlew build` command or use "Import Project" in Android Studio. Android Studio Arctic Fox or newer is required.

