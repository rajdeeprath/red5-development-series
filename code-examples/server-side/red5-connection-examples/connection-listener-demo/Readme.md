# Red5 ConnectionListener
---


## About
---

Thsi server side example demonstrates the use of the Red5 [IConnectionListener interface](http://red5.org/javadoc/red5-server-common/org/red5/server/api/listeners/class-use/IConnectionListener.html) to track connections made to the server. This includes only those connections that have successfully connected to the server (not pending ones).

A [IConnectionListener](http://red5.org/javadoc/red5-server-common/org/red5/server/api/listeners/class-use/IConnectionListener.html) object normally listens to connectiosn made to the server itself and not per `APPLICATION` / `ROOM` scope. This example also demosntrates how to use the [IConnectionListener interface](http://red5.org/javadoc/red5-server-common/org/red5/server/api/listeners/class-use/IConnectionListener.html) to track connectiosn made at `APPLICATION` / `ROOM` scope level.



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

This server side example simply listens for connections made to the server and logs out information if the connection was made to the example application and again logs out information when the connection disconnects from the example application. 

To get the example to work you need to deploy the application to server and connect to the server side application `connection-listener-demo` using RTMP or Red5 Pro Mobile SDK or Red5 pro HTML5 SDK based client and then disconnect after a few seconds. You should be able to see logging in console / log file indicating  successful connection and disconnection of the client from the server side application.




## Eclipse
---

You can edit the server side code in your eclipse JEE IDE such as Luna, Mars, Neon etc. To import the code into your IDE:

1. Navigate to the repository folder
2. Execute maven command `mvn eclipse:eclipse`. This will generate files necessary for eclipse to read the maven project properly.
3. In eclipse go to `File -> Import -> Existing Maven Projects` and click `Next`.
4. Browse and select `the project root` and Click `Finish` to import the project.



## Additional Notes
---




