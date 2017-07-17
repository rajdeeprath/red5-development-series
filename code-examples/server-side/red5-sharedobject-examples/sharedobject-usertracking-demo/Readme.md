# SharedObject User tracking on server side - Chat Room example
---

## About
---


This sample red5 application demonstrates how to manage users using a SharedObject. User management involves keeping a track of connected users and sharing the data with other clients that connect to the same scope. This pattern is prevalent in games, chat room etc, remote presentation application etc:

The `sharedobject-usertracking-demo` Red5 application uses a [IConnectionListener](http://red5.org/javadoc/red5-server-common/org/red5/server/api/listeners/class-use/IConnectionListener.html) to listen for connection events. The client provides a username which should be unique in nature. The server adds the connection session id to the username string to make it truly unique and then pushes it into the SharedObject as a List type attribute. Similarly when a client disconnects the username is flushed out of the List and the SharedObject attribute is updated. Each addition/removal sends out a `sync` event to users connected to the SharedObject. The users can then read the current updated list and update their view accordingly.

This example showcases a minimal chat application with a real time user list feature implemented using SharedObject. The application is a co-ordinated effort of the client and sevrer applications. 


## Build & Deploy
---

### Build
---

To build this application : open a shell prompt in the application's project directory (where the pom.xml file resides). run the following maven command in your shell -> 

``` 
mvn clean package 

```

The above command will generate a `war` file in the `target` directory inside the project directory. 


### Deploy
---

To deploy the war to red5 / red5 pro server :

1. Stop server if it is running.

2. Extract the content of the `war file` to directory by war name. 

> The java war file is simply a `archive file` similar to `zip` format. you can extract it using a archive tool such as [7zip](#http://www.7-zip.org/), [Winrar trial](#http://www.rarlab.com/download.htm) etc

3. Copy the folder into `RED5_HOME/webapps/` directory.

4. Start server.



## How To Use Example
---

To run this application deploy it to the server and launch the sample flash client provided along with the server side web application in your browser by visiting:- `http://localhost:5080/sharedobject-usertracking-demo`. Open the same url in another browser window or a different browser. As both clients connect to the server, they should both be able to see each other in the user list UI. You can then send global messages to everyone in the room.

Note: Although the sample client is built in flash you can use Red5 Pro [Android](https://www.red5pro.com/docs/streaming/android.html)/[IOS](https://www.red5pro.com/docs/streaming/ios.html) SDK and the Red5 pro ]HTML5 SDK](https://www.red5pro.com/docs/streaming/web.html) to build clients for RTSP/WebRTC users.




## Eclipse
---

You can edit the server side code in your eclipse JEE IDE such as Luna, Mars, Neon etc. To import the code into your IDE:

1. Navigate to the repository folder
2. Execute maven command `mvn eclipse:eclipse`. This will generate files necessary for eclipse to read the maven project properly.
3. In eclipse go to `File -> Import -> Existing Maven Projects` and click `Next`.
4. Browse and select `the project root` and Click `Finish` to import the project.



## Additional Notes
---





