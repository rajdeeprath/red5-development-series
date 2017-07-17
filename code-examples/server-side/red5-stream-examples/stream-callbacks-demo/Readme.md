#  A look at Stream callback methods in the application adapter
---

## About
---

Thsi is a simple Red5 application which outlites the various `Stream` related callback methods available in the Application class ([MultiThreadedApplicationAdapter](http://red5.org/javadoc/red5-server/org/red5/server/adapter/MultiThreadedApplicationAdapter.html)).


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

To deploy the war to red5 pro server :

1. Stop server if it is running.

2. Extract the content of the `war file` to directory by war name. 

> The java war file is simply a `archive file` similar to `zip` format. you can extract it using a archive tool such as [7zip](#http://www.7-zip.org/), [Winrar trial](#http://www.rarlab.com/download.htm) etc

3. Copy the folder into `RED5_HOME/webapps/` directory.

4. Start server.



## How To Use Example
---

To try out this example, you need to deploy the application `stream-callbacks-demo` to server, connect to it using RTMP/RTSP/WebRTC client for publishing & subscribing to a stream. You can publish and subscribe using  Red5 pro [Android](https://github.com/red5pro/streaming-android) / [IOS](https://github.com/red5pro/streaming-ios) / [HTML5](https://github.com/red5pro/streaming-html5) testbed examples as well (You may need to change the application name from `live` to `stream-callbacks-demo`).

As you publish or subscribe to this application, you can take a look at the console or logs to see callback method logging as they occur.



## Eclipse
---

You can edit the server side code in your eclipse JEE IDE such as Luna, Mars, Neon etc. To import the code into your IDE:

1. Navigate to the repository folder
2. Execute maven command `mvn eclipse:eclipse`. This will generate files necessary for eclipse to read the maven project properly.
3. In eclipse go to `File -> Import -> Existing Maven Projects` and click `Next`.
4. Browse and select `the project root` and Click `Finish` to import the project.



## Additional Notes
---





