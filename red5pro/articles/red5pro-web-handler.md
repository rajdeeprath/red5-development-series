# A glance at the heart of a Red5 Pro Web app

## About Web apps

A `Web App`, or better said, a `Web Application` is the primary point of contact within the Red5 Pro software which acts as the negotiation point or `handler` for your ingest and egress requests. The term `Web app` comes from [Apache Tomcat](http://tomcat.apache.org/) as Red5 Pro is based on the popular application JEE server.

So when you need to broadcast or subscribe, you point your application to a Web application on Red5 Pro and make the appropriate request. The application negotiates your request with the server on your behalf and the service is rendered.

Now let's dive a little deeper and take a look at the heart of a Web Application and try to understand the component where the negotiation happens. At the center of a web application lies a java class called an `application adapter`. In the context of a Red5 Pro web app, it is also called the `web handler`. For more ont the the anatomy of a web application and various parts that make it functional, check out this [informative GitHub article](https://github.com/rajdeeprath/red5-development-series/wiki/Understanding-the-application-structure) on Red5 Pro application structure.

In this post, I won't go into the nitty gritty details of coding a complete web application. For a deeper dive into creating the web handler and configuration files from scratch, I recommend taking a look at [Red5 Pro developer series by Dominick Accattato](https://www.red5pro.com/docs/developerseries/).

## Introduction to the Red5 Web Handler

The `application adapter` java class extends the Red5 base class [MultiThreadedApplicationAdapter](http://red5.org/javadoc/red5-server/org/red5/server/adapter/MultiThreadedApplicationAdapter.html) to inherit various properties and methods that are geared towards controlling negotiation requests as well as a host of exciting features.

If you have been using the popular Red5 Pro web app called `live` for most of your streaming needs, you can go ahead and open the file `{RED5_HOME}\webapps\live\WEB-INF\red5-web.xml` in your favorite text editor and locate the following line: `<bean id="web.handler" class="com.infrared5.red5pro.live.Red5ProLive" />`. That is the application adapter for your `live` app! As stated before, the `Red5ProLive` is a custom java class then extends the `MultiThreadedApplicationAdapter` class.

## Anatomy of a Red5 Web Handler

When a web handler is created inheriting the `MultiThreadedApplicationAdapter` it provides a few important handler methods that can be used to intercept various key events in the lifecycle of a ingest / egress mechanism. A few of the important methods are given below:

* `public boolean appStart(IScope app)`: Invoked when your handler successfully registers with Red5 core during startup.
* `public void app stop(IScope app)`: Invoked when your handler is stopped when the server shuts down gracefully.**Avoid using this to trigger critical business logic**
* `public boolean appConnect(IConnection conn, Object[] params)`: Invoked when a client **tries** to connect to your application. Return `false` or throw `ClientRejectedException` to deny connection or return true to accept.
* `public void appDisconnect(IConnection conn)`: Invoked when a client disconnects from the application.
* `public void streamBroadcastStart(IBroadcastStream stream)`: Invoked when a publisher stream starts
* `public void streamBroadcastClose(IBroadcastStream stream)`: Invoked when a publisher stream stops
* `public void streamSubscriberStart(ISubscriberStream stream)`: Invoked when a stream subscription starts
* `public void streamSubscriberClose(ISubscriberStream stream)`: Invoked when a stream subscription stops

For more information on these methods as well as other available methods that can be useful in your application check out [java docs on MultiThreadedApplicationAdapter](http://red5.org/javadoc/red5-server/org/red5/server/adapter/MultiThreadedApplicationAdapter.html) and the [MultiThreadedApplicationAdapter source code on github](https://github.com/Red5/red5-server/blob/master/src/main/java/org/red5/server/adapter/MultiThreadedApplicationAdapter.java)

Any of the methods for which you need to implement a custom business logic must be `overridden` in your own handler class.

```java

@Override
public boolean appConnect(IConnection conn, Object[] params) {
    return super.appConnect(conn, params);
}
```

A call to the `super` ensures that you relinquish control back to Red5 core after your own logic is done executing.

If you need an example of a web app that shows how to override and implement the common handler methods for a custom web app, check out the [application-adapter-demo sample web app on github](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-application-examples/application-adapter-demo/src/main/java/org/red5/application/examples/applicationadapterdemo).

## Special Notes and Gotchas

While a Red5 pro web handler is rather simple to implement, there are few things to be aware of to avoid potential pitfalls.

* The handler method `appConnect` is not executed when a client gets connected successfully. It is executed when a client is trying to connect. To affirm a successful connection, you need to use the `IConnectionListener` with your handler. For more info check out [connection-listener-demo example on github](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-connection-examples/connection-listener-demo).

* `appStop` should not be used to detect server shutdown/termination. It should only be used to unregister or stop java components at best.

* `streamBroadcastStart` method implies that the broadcast started. This should not be used to implement publish security. For intercepting publish or playback request, IStreamPublishSecurity or IStreamPlaybackSecurity interfaces should be used respectively. For more information on the subject, refer to **Securing publish & playback* in the [Red5 Pro streams documentation](https://www.red5pro.com/docs/serverside-guide/streams.html) and the [Red5 Pro simple authentication plugin documentation](https://red5pro.com/docs/server/authplugin.html#plugin-configuration).

You can also refer to the [sample webapp on github](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-stream-examples/stream-security-demo) demonstrating how to intercept publish/playback requests in Red5 Pro.

* Always make a call to `super` in the overridden method (when applicable), within your web handler after your custom logic to prevent breaking the flow of control in Red5 core.

## When Do You Need to Implement a Custom Red5 Web Handler

Although a custom web application and thereby a custom application adapter (Red5 Pro web handler) is recommended for every professional streaming business, let me highlight a few specific examples that will help you better understand the need for an application adapter. A custom application adapter extending the`MultiThreadedApplicationAdapter` could be used to:

* Execute a custom business logic to be executed when your server starts up
* Send a notification to a remote server when a stream broadcast starts
* Upload a recording to a remote server after your publisher disconnects
* Run a custom logic each time a subscriber request comes in

Obviously, this is not a comprehensive list by any means but should help illustrate the concept. 

## Conclusion

Red5 Pro web applications might seem a little alien to you if you come from a non-java background. However, they are quite straightforward and easy to implement once you get accustomed to the concepts. With just a little practice, you will quickly find yourself building advance streaming apps, real-time data apps and much more.

Take advantage of the extensive java library base from across the globe coupled with our extensive documentation and dedicated support to build anything and deploy anywhere.

For more examples & snippets on server side Red5 java API check out [red5-development-series repository on github](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side)e).
