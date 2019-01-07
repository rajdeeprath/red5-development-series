# Basic WebRTC enabled application
---




## About
---



This example demonstrates the bare minimum skeleton of a WebRTC enabled webapp running on Red5 Pro **version: 5.4.x**.This application is also compatible with Red5 Open source **version: M10**. The code / logic in this web app demonstrates hwo to enable WebSockets on the application level, thereby making is possible for WebRTC client (on Red5 Pro) and simple WebSocket clients (on Red5 Pro and Red5 Open Source) to be abl eto conenct to the server.

The main `Application` class demonstrates hwo to obtain an instance of the `WebSocketScopeManager` and then register the application scope to it on the overridden `appStart` handler of the `MultiThreadedApplicationAdapter`. Similarly we deregister the application from the `WebSocketScopeManager` on `appStop` handler.

Additionally the configuration file `web.xml` must include an additional WebSocket filter to enable the `WebSocketScopeManager` to respond to client requests. It is important to note that Red5 open source as well as Red5 Pro now support WebSockets over the existing HTTP and HTTPs ports `5080` and `443` respectively. The following filter must be present in your web application's web.xml file:

```xml

<!-- WebSocket filter -->
 <filter>
     <filter-name>WebSocketFilter</filter-name>
     <filter-class>org.red5.net.websocket.server.WsFilter</filter-class>
     <async-supported>true</async-supported>         
 </filter>  
 <filter-mapping>   
     <filter-name>WebSocketFilter</filter-name> 
     <url-pattern>/*</url-pattern>  
     <dispatcher>REQUEST</dispatcher>   
     <dispatcher>FORWARD</dispatcher>   
 </filter-mapping>

```

> Please checkout the web.xml file provided in this sample application for more info.



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

**Red5 Pro** :  Once the application has been deployed on server :

1. Navigate to the testbed application at `http://{host}:5080/webrtcexamples/index.html`
2. Change `WebApp` value to `simple-webrtc-streamer`
3. Go to **TestBed** menu and click `Publish`

**Red5 OpenSource** : TO DO

## Eclipse
---

You can edit the server side code in your eclipse JEE IDE such as Luna, Mars, Neon etc. To import the code into your IDE:

1. Navigate to the repository folder
2. Execute maven command `mvn eclipse:eclipse`. This will generate files necessary for eclipse to read the maven project properly.
3. In eclipse go to `File -> Import -> Existing Maven Projects` and click `Next`.
4. Browse and select `the project root` and Click `Finish` to import the project.

## Additional Notes
---

NA
