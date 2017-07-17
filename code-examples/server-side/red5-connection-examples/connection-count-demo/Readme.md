# Counting Red5 Connections
---


## About
---

This example demonstrates how to count the total number of connections in a Red5 Pro application. Red5 pro supports `RTMP`, `RTSP` ([Android](https://www.red5pro.com/docs/streaming/android.html)/[IOS](https://www.red5pro.com/docs/streaming/ios.html) sdk) and `WebRTC` ([Red5pro HTML5 SDK](https://www.red5pro.com/docs/streaming/web.html)) type connections. All of these connection types are counted when you use the [getClientConnections](http://red5.org/javadoc/red5-server-common/org/red5/server/api/scope/IScope.html#getClientConnections--) method of the [IScope](http://red5.org/javadoc/red5-server-common/org/red5/server/api/scope/IScope.html#getClientConnections--) interface. This gives you total connections in that `scope`

Similarly to look up connectiosn in a room (sub-scope) you can use the [getClientConnections](http://red5.org/javadoc/red5-server-common/org/red5/server/api/scope/IScope.html#getClientConnections--) method on the `ROOM` type scope.


## Build & Deploy
---


To deploy the war to red5 / red5 pro server :

1. Stop server if it is running.

2. Extract the content of the `war file` to directory by war name. 

> The java war file is simply a `archive file` similar to `zip` format. you can extract it using a archive tool such as [7zip](#http://www.7-zip.org/), [Winrar trial](#http://www.rarlab.com/download.htm) etc

3. Copy the folder into `RED5_HOME/webapps/` directory.

4. Start server.



## How To Use Example
---

In this example we call the method `getTotalConnections` whenever a client connects or disconnects from the application scope and/or a sub scope. The method logs the current connections count on the `IScope` object passed to it. To see the example in action properly you need to connect at least two clients to the application one after the other sequentially. Once they are all connected, disconnect each client in a similar fashion one after the other.

When the first client connects the `appConnect` is triggered which calls the `getTotalConnections` method. But at this point the total count will be zero because the client has not completed connection to the scope. 

```

[INFO] [NioProcessor-2] org.red5.connection.examples.totalconnections.Application - Client connect RTMPMinaConnection from 127.0.0.1 (in: 3483 out: 3073) session: 2KBUO1LL0M9FU state: connected
[INFO] [NioProcessor-2] org.red5.connection.examples.totalconnections.Application - Total connections currently in scope /default = 0

```

The `appConnect` is a callback indicating a client is attempting to connect. When the second connection connects, the `getTotalConnections` method will log total count as 1 because it counts the the previously connected client but not the current one.


```

[INFO] [NioProcessor-3] org.red5.connection.examples.totalconnections.Application - Client connect RTMPMinaConnection from 127.0.0.1 (in: 3483 out: 3073) session: OI2X1IAOUBNTR state: connected
[INFO] [NioProcessor-3] org.red5.connection.examples.totalconnections.Application - Total connections currently in scope /default = 1

```

If you wish to get a count as soon as a client connects, recommended that you use  a [IConnectionListener](http://red5.org/javadoc/red5-server-common/org/red5/server/api/listeners/IConnectionListener.html) instead fo the `appConnect` handler to ensure that a accurate value counting the current connection as well. A detailed example on IConnectionListener is [published here](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-connection-examples/connection-listener-demo).



## Eclipse
---

You can edit the server side code in your eclipse JEE IDE such as Luna, Mars, Neon etc. To import the code into your IDE:

1. Navigate to the repository folder
2. Execute maven command `mvn eclipse:eclipse`. This will generate files necessary for eclipse to read the maven project properly.
3. In eclipse go to `File -> Import -> Existing Maven Projects` and click `Next`.
4. Browse and select `the project root` and Click `Finish` to import the project.



## Additional Notes
---

Alternative ways to call the `getTotalConnections` method include:

1. Add a java scheduled job to call the `getTotalConnections` method periodically
2. Invoke the `getTotalConnections` method from client as through a `RMI` (remote method invocation) call.

> Calling application adapter methods from clients as RMI is discussed in a different example.



