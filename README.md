# Artist mobile application using Android Architecture Components

### Description:
Artist is the right place for all art lovers and people who try to find inspiration in the works of well-known but also unknown authors. This app offers a large number of artwork that can be filtered based on a variety of criteria. In addition, it provides basic information about the authors and the periods in which they worked. If you are a lover of shows, you will certainly be pleased with the list of future shows you can visit.

### Features:
* Java language used for development
* App fetches data from <a href="https://www.artsy.net/">Artsy API</a>
* App uses Firebase Job Scheduler and intent service to re-fetch new artworks once a day.
* Application saves fetched data for offline use
* App shows notification to users who subscribe for daily inspirational art
* App implements widget

### User interface:
<details>
  <img src="https://github.com/PaloPodstreleny/Capstone-Project/blob/master/ReadmePictures/art-artists.png"/>
  <img src="https://github.com/PaloPodstreleny/Capstone-Project/blob/master/ReadmePictures/art-artist-detail.png"/>
  <img src="https://github.com/PaloPodstreleny/Capstone-Project/blob/master/ReadmePictures/art-artworks.png"/>
  <img src="https://github.com/PaloPodstreleny/Capstone-Project/blob/master/ReadmePictures/art-genes.png"/>
  <img src="https://github.com/PaloPodstreleny/Capstone-Project/blob/master/ReadmePictures/shows.png"/>
  <img src="https://github.com/PaloPodstreleny/Capstone-Project/blob/master/ReadmePictures/shows-settings.png"/>
  <img src="https://github.com/PaloPodstreleny/Capstone-Project/blob/master/ReadmePictures/show-detail.png"/>
  <img src="https://github.com/PaloPodstreleny/Capstone-Project/blob/master/ReadmePictures/settings.png"/>
  <img src="https://github.com/PaloPodstreleny/Capstone-Project/blob/master/ReadmePictures/widget.png"/>
  <img src="https://github.com/PaloPodstreleny/Capstone-Project/blob/master/ReadmePictures/notification.png"/>
</details>



### Versions of components and requirements:
* Android studio: 3.1.4
* Gradle version: 4.4
* Libraries:
	* Glide: 4.7.1: loading and caching images
	* Retrofit: 2.4.0: handling network calls and connection to API
	* Android architecture components: 
		* Room: 1.1.1:  local data persistence
		* Live Data: 1.1.1
* ViewModel: 1.1.1 
* Support design library: 27.1.1
* RecyclerView: 27.1.1
* ButterKnife: 8.8.1
* Firebase JobDispatcher: 0.8.5: scheduling background jobs in the app
* Espresso: 3.0.2: ui tests
* Mockito: 2.+: unit tests

### Run application:
In order to see the same results as in user interface section you need to provide values for fields: `CLIENT_ID`, `CLIENT_SECRET` which are located in ApiKey class. You can apply for this values at https://developers.artsy.net/

