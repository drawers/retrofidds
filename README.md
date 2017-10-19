# retrofidds
A Retrofit service for the League of Legends API

Experiment using Immutables library and the generated GSON adapters to avoid the overhead of using reflection at runtime

Please see [this article about the NY Times app](https://open.blogs.nytimes.com/2016/02/11/improving-startup-time-in-the-nytimes-android-app/) for inspiration

Note this approach allows an integration test using a mock server.

https://github.com/drawers/retrofidds/blob/master/src/test/java/com/github/drawers/retrofidds/summoner/SummonerServiceTest.java
