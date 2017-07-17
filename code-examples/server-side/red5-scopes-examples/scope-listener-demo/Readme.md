# Red5 Scope Listener
---


## About
---

This example demonstrates the use of the Red5 [IScopeListener interface](http://red5.org/javadoc/red5-server-common/org/red5/server/api/listeners/IScopeListener.html). This interface is used to monitor the server for creation / removal of Scopes.

Similar to the IConnectionListener, the [IScopeListener](http://red5.org/javadoc/red5-server-common/org/red5/server/api/listeners/IScopeListener.html) can only be attached to the core `server` object and not to individual applications. This sample application demonstrates how to implement a scope listener using this interface and to attach it to the server object from within an application. 

If you wish to track creation/removal of ROOM scopes in a specific application only then checkout the [IConnectionListener example](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-connection-examples/connection-listener-demo) to see how to filter-out events/notifications specific to an application.



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





## Eclipse
---

You can edit the server side code in your eclipse JEE IDE such as Luna, Mars, Neon etc. To import the code into your IDE:

1. Navigate to the repository folder
2. Execute maven command `mvn eclipse:eclipse`. This will generate files necessary for eclipse to read the maven project properly.
3. In eclipse go to `File -> Import -> Existing Maven Projects` and click `Next`.
4. Browse and select `the project root` and Click `Finish` to import the project.



## Additional Notes
---





