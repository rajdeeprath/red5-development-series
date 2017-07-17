# SharedObject read/write  demo
---

## About
---

This server side example webapp demonstrates how to read and write SharedObject data on server side. The Red5 SharedObject manages data in a AttributeStore. We can use the [IAttributeStore](http://red5.org/javadoc/red5-server-common/org/red5/server/api/IAttributeStore.html) methods to read and write data as name-value paired attribute for a SharedObject. In the example web application's source code you can see different ways to read/write/update & even remove attributes. Each time the AttributeStore is updated, the connected clients receive a `SYNC` event depending on the client type and its capabilities.


> A SharedObject created by the server side code can be acquired & used by the client application but wont be destroyed once all clients have disconnected from it.


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


This example application is self executing in nature. ie: as soon as the application is deployed to the server, it starts executing various SharedObject read and write test operations automatically in 15 seconds of application startup on server. You will be able to see what the code is doing in your console or server logs. If there is a client connected to the SharedObject, it will receive update notifications via appropriate events.



## Eclipse
---

You can edit the server side code in your eclipse JEE IDE such as Luna, Mars, Neon etc. To import the code into your IDE:

1. Navigate to the repository folder
2. Execute maven command `mvn eclipse:eclipse`. This will generate files necessary for eclipse to read the maven project properly.
3. In eclipse go to `File -> Import -> Existing Maven Projects` and click `Next`.
4. Browse and select `the project root` and Click `Finish` to import the project.



## Additional Notes
---







