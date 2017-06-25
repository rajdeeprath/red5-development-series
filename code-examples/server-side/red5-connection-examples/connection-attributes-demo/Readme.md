# Reading & Writing Connection Attributes
---

## About
---


This example demonstrates how to store and retrieve arbitrary attributes on the connection object in Red5 / Red5 Pro. Attributes are stored as `key-value` pairs. Attributes live in the object as long as the connection is alive. Red5 supports storing basic data types etc as `Integer`, `Boolean`, `Double`  as well as complex data types such as `Arrays`, `Maps`, `Custom objects` and more.

Every Red5 / Red5 Pro connection is represented by the [IConnection](http://red5.org/javadoc/red5-server-common/index.html?org/red5/server/api/class-use/IConnection.html) interface. The IConnection interface inherits attribute store/read capabilities from the [IAttributeStore](#http://red5.org/javadoc/red5-server-common/org/red5/server/api/IAttributeStore.html#getAttribute-java.lang.String-) interface. Check out the [IAttributeStore](#http://red5.org/javadoc/red5-server-common/org/red5/server/api/IAttributeStore.html#getAttribute-java.lang.String-) interface for detailed api on working with attributes.


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


This example stores some arbitrary attributes on the connection object when the client connects to the application and then logs out the attribute names. Then again when the client disconencts it logs out the attributes that were stored.

To see this example in action, you need to connct to this application - `connection-attributes-demo` using RTMP or RTSP or Red5 pro SDK based WebRTC client and then disconnect after a few seconds. you shoudl be able to see logging in console.



## Notes
---

NA
