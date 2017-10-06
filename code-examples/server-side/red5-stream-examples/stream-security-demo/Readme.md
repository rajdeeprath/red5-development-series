# Implementing Stream Security in Red5
---


## About
---

This example demonstrates how to secure playback and publish operations on Red5 / Red5 Pro using [IStreamPlaybackSecurity](http://red5.org/javadoc/red5-server-common/org/red5/server/api/stream/IStreamPlaybackSecurity.html) and [IStreamPublishSecurity](http://red5.org/javadoc/red5-server-common/org/red5/server/api/stream/IStreamPublishSecurity.html) interfaces respectively.

1. An application needs to capture client parameters 
2. Implement the IStreamPlaybackSecurity and/or IStreamPublishSecurity interfaces to intercept playback and publish 
3. Within the intercepting methods of the implementations access client parameters and validate the action (publish/subscribe)

The entire process is explained in details in the following links. It is recommended that you read through before attempting to install this application.


* [Capturing client params](http://flashvisions.com/capturing-client-parameters-in-a-red5pro-application)

* [Intercepting and validating publishers](http://flashvisions.com/authenticating-publishers-in-a-red5pro-application)

* [Intercepting and validating subscribers](http://flashvisions.com/authenticating-subscribers-in-a-red5pro-application)




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

To see this example in action, deploy thsi application (stream-security-demo) on your server. Connect to it and attempt to it and attempt to publish or subscribe.


1. To publish to a stream successfully, your client must pass a parameter called 'token' with the value of 'red5pro#publisher'.

Code link: https://github.com/rajdeeprath/red5-development-series/blob/master/code-examples/server-side/red5-stream-examples/stream-security-demo/src/main/java/org/red5/streams/examples/security/PublishSecurity.java


2. To subscribe to a stream successfully, your client must pass a parameter called 'token' with the value of 'red5pro#subscriber'.

Code link: https://github.com/rajdeeprath/red5-development-series/blob/master/code-examples/server-side/red5-stream-examples/stream-security-demo/src/main/java/org/red5/streams/examples/security/PlaybackSecurity.java


> Check out [Capturing client params](http://flashvisions.com/capturing-client-parameters-in-a-red5pro-application), to see hwo to pass parameters to the application for RTMP/RTSP/WebRTC.


## Eclipse
---

You can edit the server side code in your eclipse JEE IDE such as Luna, Mars, Neon etc. To import the code into your IDE:

1. Navigate to the repository folder
2. Execute maven command `mvn eclipse:eclipse`. This will generate files necessary for eclipse to read the maven project properly.
3. In eclipse go to `File -> Import -> Existing Maven Projects` and click `Next`.
4. Browse and select `the project root` and Click `Finish` to import the project.



## Additional Notes
---



## Reference Links
---

[Capturing client params](http://flashvisions.com/capturing-client-parameters-in-a-red5pro-application)

[Intercepting and validating publishers](http://flashvisions.com/authenticating-publishers-in-a-red5pro-application)

[Intercepting and validating subscribers](http://flashvisions.com/authenticating-subscribers-in-a-red5pro-application)

[Writing a Red5 Pro plugin](https://red5pro.com/docs/serverside-guide/plugin-development.html)
