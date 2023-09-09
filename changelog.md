# Changelog

## 1.0.0 - 9 Sep 2023

The first launch of the application has been released on [bryanlohjw.com](http://bryanlohjw.com). Being my first deployed application, I am really happy that I have managed to get it up and running and have friends try the functionalities so far. Being the first changelog, I will also introduce the application, its functionalities and technical implementations.

### Introduction

Remote queue for spotify was conceived during a mahjong session with my friends. Each of them wanted to add songs to the queue and I had to either pass my phone to them, or let them tell me what songs to add. This application is a response to that problem statement that I had.

Although spotify already has a similar function called [Remote Group Session](https://support.spotify.com/us/article/remote-group-session/), this application is distinguished in the following ways:

-   only one device and spotify account is actively playing songs in a physical setting
-   users adding songs to the queue do not need a spotify account

From the differentiation, the application is solving a different problem as compared to Spotify's Remote Group Session.

### Functionalities

The current functionalities that the application has is to enable the the owner (a spotify premium account holder) to create a room/session on the website. The link of the room with the pin can be sent to friends to join and to search for songs and add them to the queue.

For other users, having the link leads them to the sign-up page for the room. They can key in the pin and a unique name to enter the room. Once in, they can start searching for songs and adding them to the queue.

### Application implementation

Spotify has APIs for developers to use. The API can be called with an access token tagged to a user's account. The APIs are very comprehensive and most functionalities are not required by the remote queue application.

The frontend enables owners to login to spotify using OAuth2. After the owner has entered the credentials and consents to the permissions given to the remote queue application, Spotify will provide an access code. The access code is given to the backend to be exchanged for an access token from spotify. After that, a room will be created that can be shared with its link and pin for other users to access. The frontend application will provide the different functionalities such as searching and allowing users to add songs to the current queue.

The backend purpose is to store the access token and tie it to a room. Whenever any users in the room wants to use a spotify function, the backend will retrieve a token, generate the request and return Spotify's response to the frontend. The backend also has JWT security to enable authenticate users to a specific room.

The application is currently deployed onto AWS EC2 instances in Docker images. The deployment process is currently very manual and there are plans to improve this in the future.

### Side notes

The application is in the beginning phase of development. More features will be added for user's ease of use in the future.

Currently, the application has limited access to Spotify's APIs and users have to be whitelisted before they can create rooms on the application.

If Spotify sees this application and finds any breach of their terms of use, do reach out to me at my [email](bryanlohjw97@gmail.com).
