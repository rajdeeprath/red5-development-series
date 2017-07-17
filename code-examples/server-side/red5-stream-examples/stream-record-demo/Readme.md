# Serverside stream recording example
---


## About
---

This server side application demonstrates server side recording feature in Red5. Using server side recording, you can save a live stream to a file for later vieweing as a VOD etc:. In Red5 publishsing streams are [ClientBroadcastStream](http://red5.org/javadoc/red5-server-common/org/red5/server/stream/ClientBroadcastStream.html) objects.Therefore we can cast any BroadcastStream to a [ClientBroadcastStream](http://red5.org/javadoc/red5-server-common/org/red5/server/stream/ClientBroadcastStream.html) and invoke the recording methods on it.

A logical place to start recording is the `streamBroadcastStart` callback provided by the [MultiThreadedApplicationAdapter](http://red5.org/javadoc/red5-server/org/red5/server/adapter/MultiThreadedApplicationAdapter.html).


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

To try out this example, you need to deploy the application `stream-record-demo` to server, connect to it using RTMP/RTSP/WebRTC client and start publishing a live stream. The server side code will automatically record the stream to a flv file using the Broadcast stream's name.After a little while stop publishing and close the client.

You should be able to see the recorded file in the `streams` directory on your web application.ie: `RED5_HOME/webapps/stream-record-demo/streams`.



## Eclipse
---

You can edit the server side code in your eclipse JEE IDE such as Luna, Mars, Neon etc. To import the code into your IDE:

1. Navigate to the repository folder
2. Execute maven command `mvn eclipse:eclipse`. This will generate files necessary for eclipse to read the maven project properly.
3. In eclipse go to `File -> Import -> Existing Maven Projects` and click `Next`.
4. Browse and select `the project root` and Click `Finish` to import the project.



## Additional Notes
---


Although the recording stops automatically when you unpublish the stream, you can also make use of a RMI call or a HTTP servlet based call to the application and program it to stop recording on demand. For more information on these subjects take a look at the [connection-messaging-demo](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-connection-examples/connection-messaging-demo) and [http-servlet-demo](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-misc-examples/http-servlet-demo) examples.
