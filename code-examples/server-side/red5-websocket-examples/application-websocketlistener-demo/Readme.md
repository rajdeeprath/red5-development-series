# Websocket listener example (also usable as a websocket application template)
---


## About
---

Application demonstrates how to integrate websockets in your red5 application. The sample WebSocketDataListener logs out connect, disconnect and messages.You can use the client code provided along with the web application to conenct to it.


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

1. Deploy application to server
2. Open client at `http://{host:{port}/application-websocketlistener-demo/client`
3. Ensure the connection url and subprotocol match the application.
4. Press `connect` to make connection to server. Watch console for response messages
5. Terminate browser window or referesh to kill client connection




## Notes
---
