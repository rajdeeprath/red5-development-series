# Integrating Red5 Pro with external systems

## Introduction

System integration is the process of aggregating different sub-systems to build a single large system, where every sub-system functions together with other sub-system co-operatively, while operating within its own limited scope, to achieve a larger system.

In the context of Red5 Pro this means, how different businesses, offering different products & services can integrate with Red5 pro services irrespective of their technology and architecture to include video/audio and data services as a part of their system.

While the problem of system integration is not new, some of the integration aspects of Red5 Pro (being a java based stream server) are different then usual web technologies.In this document we will be taking a look at different aspects of integrating Red5 Pro, the challenges to expect, their solutions and few real world integration use cases.

At this point i will assume that you are either a developer or a business owner or a solution analyist who has either tried out Red5 Pro streaming demo and is now interested in integrating Red5 Pro with your own system, or are in the process of analysing Red5 pro as a streaming solution for your product. In either case this document is here to help you do just that.

## Integration with Red5 Pro - implication and expectations

Integrating your business system with Red5 Pro is a multi-point process. Every business application (product or service) will usually consist of server and client stacks. Server stack will consist of backend services such as application servers, database services, data processing servers etc. On the other hand the client stack will consist of client applications targetting desktop, mobile devices and other special prospective client types such as IOT devices, IP cameras, Drones etc.

![Integration Stack](images/business-stack.png)

For a successful system integration, it is necessary to identify the different integration stacks and the content of each stack with respect to your business requirements. 

**Integrating the client stack with Red5 pro implies (but is not limited to) integrating client applications to:**

* Consume streaming services through Red5 Pro media server, using appropriate protocol supported by Red5 Pro matching the client's capabilities.
* Developing any inter-client communication system between multiple clients using the media server
* Triggering methods/mechanisms on server from client side (RPC)

**Similarly the scope of server stack integration covers (but is not limited to), connecting with Red5 pro to:**

* Gather usage  & performance statistics
* Monitoring
* Obtain connection & stream information
* Perform server administration tasks
* Implement custom business logic via webapps and plugins
* Pushing notifications from client

So now that, you probably have a good idea of what integration is all about, let us proceed with taking deeper look into both server and client integration stacks in details.

## Server side integration

Server side integration is required when you need granular control over the streaming sub system at different levels to be able to communicate with it and control it from another sub-system.

### Introuction to important elements of Red5 Pro server side

**Red5 Pro Webapps**

Red5 Pro webapps are the main entry points for clients wanting to consume streaming services through Red5 Pro. Red5 pro webapps, alongside streaming services can also provide standard webservices such as serving web pages, handling parameterized HTTP requests etc.

**Red5 Pro Plugins**

Red5 Pro plugins are the `minions` of the Red5 pro platform that can help you accomplish almost anything. Plugins unlike webapsp atre headless entities. They can augment any existing webapp with new behaviour, or even implement it across all webapps on the server. You can use plugins to implement isolated features or tap into the existing features provided by the Red5 pro platform.

### Out of the box features for server side integration

**Ready to use streaming applications**

In general Red5 Pro is a streaming ready server. This means, you can simply point a compatible client to the server and publish/subscribe without having to write any code on the server side. Red5 Pro is distributed with certain pre-built **server applications** that are stream ready in nature. You can use them to stream live content, record as well as playback a recorded media.

A very good example of this is the `live` webapp. The `live` webapp is the one that is used by the server for demonstrating simple publish and subscribe at `http://localhost:5080/live/broadcast.jsp?host=localhost` & `http://localhost:5080/live/subscribe.jsp?host=localhost`pages respectively.

This is a simple application with no additional features other than streaming, recording and playback. There is no security enabled on it either. You can use our [authentication plugin](https://www.red5pro.com/docs/server/authplugin.html) to attach custom security to it.

**Ready to use plugins**

Red5 pro distribution includes some basic plugins that give you a head start in integrating Red5 pro with your own system. Following plugins are provided out of the box for system integration:

* [Simple Authentication Plugin](https://www.red5pro.com/docs/server/authplugin.html): A basic yet high flexible and extensible authentication plugin that can help you achieve almost any kind of authentication (credentials based or anonymous token based) for clients using your own application server or database server. 

The plugin supports standardized JWT based authentication/authorization mechanism for clients **out of the box**. You can then use the [plugin's documentation to implement your own authentication/authorization service & configure the plugin to use it for validating clients](https://www.red5pro.com/docs/server/red5proroundtripauthvalidator.html#red5-pro-round-trip-authentication-validator).

* [Cloud Storage Plugin](https://red5pro.com/docs/server/cloudstoragevod.html): A cloud storage solution for Amazon S3. It helps you copy audio/video recordngs to your aws bucket. The plugin can be used with both standalone Red5 pro instances and Red5 pro autoscaling instances alike. If you wish to have support added for a different cloud platform, you can [get in touch with us to discuss it](https://calendly.com/red5-pro/15-minute-phone-call?month=2019-05).

* [HLS Plugin](https://www.red5pro.com/docs/server/hlsdocs.html): Helps generate HLS files for live and recording sessions. You can use this to control HLS latency or even diable HLS generation completely.

And i will reiterate here, if you something more, such as uploading file to your own application server, notifying your application server whenever a client joins or leaves Red5 pro etc, you can always create your own plugin with your business logic. There is a [detailed documentation on plugin development](https://red5pro.com/docs/serverside-guide/plugin-development.html) on our website.We have both paid and free support options to help you out!. 

**Ready to use APIs**

Red5 pro platform provides an [extensive API](https://red5pro.com/docs/server/serverapi.html) to help gather information about the server and its different entities. You can query standard `statistics` on resources such as `applications`, `connections`, `streams` etc. You can also perform administrative and non-administrative actions on those resources such as `disconnecting clients`, `closing streams`, `recording live streams` etc.

For autoscaling systems there is the [Stream Manager API](https://www.red5pro.com/docs/autoscale/streammanagerapi.html), to help manage clusters and autoscaled nodes and other autoscaling resources on the target platform along with different calls for reading statistics. You can use both autoscale and single server api alongside each other to have maximum control over the system.

All API calls return industry standard JSON formatted data and responses. Therefore, you can use any technology to interace with our API calls through a simple API consumer client.

<Examples / Snippets>

<PHP>/<NODE>/<c#>/<java>
    
### Developing custom features for integration

We have seen the various `out of the box` solutions that the Red5 Pro platform offers. All of these are extremely helpful when you want to integrate Red5 pro with your own system. But there will always be use-cases where these offerings may not suffice. Let us take a  quick peek at such use cases:

**Sample use-cases where custom development is required**

* You are building a solution for your product, where you wish to enable video and audio recording of customer reviews. Futher you wish to store these videos on your own server after recordings is completed.

* You are building a virtual classroom solution where you need to persist the state of the whiteboard on a remote server under a customer's account which resides on your application server.

* You are building a dating site, where there will be one to one chat sessions between clients, authenticated by your remote server and the stream names are pre-generated by your system.

* You want to log the detailed usage of your streaming application to your remote server including events such as client connect, client disconenct, client publish, client subscribe.

There could be many more use cases, but these are good enough to help you understand, when/if you need custom effort on for server side integration. Each of the use cases mentioned above, warrants custom development either partially or completely. 

So the next question in your mind would be - **Where do i start ?**. To answer that, the first thing you need to do is sketch out the integration requirement. Here are a few sample topics that you should go over, as you decide on server side integration efforts:

* What are the requirements for integration ? 
* What do you need from the server side for your system ?
* Are the APIs not sufficient for your use case?
* Do you need more/different APIs ?
* Does your team have a good expertise in java ?
* Do you want an addition to an existing Red5 pro feature ?

Having thought this over, you should now be ready to dive into some of the technical details of Red5 pro server side integrations.

#### Webapp vs Plugin development

Till now we have seen that the two ways to deploy custom server side logic on Red5 pro is either using a webapp or a plugin. While both options are fulfilling , let us try to see which option is more suitable for what use case. In other words when to choose webapp development and when to choose plugin development for deploying custom logic to Red5 pro.

##### Custom webapp development

As i mentioned earlier, webapps are the point of contact for a client on the Red5 pro server. In Red5 terminology webapps are also called top level `scopes`. every client that connects to Red5 pro needs a `scope` to do so. You cannot connect to the server at `{protocol}://{host}:{port}` alone. Every service that Red5 pro provides over various protocols such as RTMP, RTSP, RTC and HLS, is provided through a point of contact - a.k.a `webapp`  or `scope`. So to consume services you need to use the following url pattern - `{protocol}://{host}:{port}/{webapp}`.

It is therefore imperative to develop you own webapp when you need more control over the point of contact. Every custom webapp can do all that the default webapp `live` can do, unless explicitly controlled. This is to say  that every custom webapp can allow broadcast, playback, recording, SharedObjects etc over supported protocols by default.

A **webapp can better tap into its lifecycle** than a plugin and therefore is more efficient and preferred for capturing different events pertaining to the application, its clients and other resources such as stream and sharedobjects.

Here are a few typical use cases that make a good case for a custom webapp:

* Building an application with live stream recording. After are broadcast is complete you want to either process the file or move it to a custom location on the same server or a remote server.

* Send a notification to a remote server whenever a stream broadcast starts.

* Send a notification to a remote server whenever a subscriber starts subscribinga stream.

* Run ffmpeg on the recorded file, soon after recording stops to extract images.

* Add a custom API to the streaming application to read/write custom data. Webapps are perfect candidates to host a HTTP service. In java that is achieved through [servlets](https://www.geeksforgeeks.org/introduction-java-servlets/).

* Implementing secure file upload/download mechanism for your service. This is also a candidate for java servlets.

**Coding References:**

* For custom webapp development checkout the [Red5 pro develop series by Dominick Accattato](https://www.red5pro.com/docs/developerseries/). It will guide you through the prerequisites, setting up the development environment, creating a webapp etc in details.

* For more snippets and understanding on different things that you can do with Red5 pro, checkout [the collection of ready to go webapp examples demonstrating different Red5 java apis on github by Rajdeep Rath](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side)

* Here are some good [webapp examples on using java servlets to implement HTTP hooks on a Red5 pro application](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-misc-examples).

**Design considerations:**

There are differnt ways to interface communications betweeen systems. The path taken is purely a matter of choice. However i would recommend always sticking to industry standards for these things.

* Always use standard data formats such as JSON (preferred) or XML for exchanging messages between Red5 pro and the external system.

* Use HTTP for simple requests and Websockets where realtime flow is needed. For information on implementing websocket communication with your webapp checkout [our documentation](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-websocket-examples/application-websocketlistener-demo) and this ready to use webapp example.

* When designing to check for user presence in a chat or conference applications, rely on standard web solutions such as AJAX etc, instead of using Red5 pro sockets if possible. That will reduce load on your system. If you must implement user tracking through Red5 pro check out this example on [application callback methods](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-application-examples/application-adapter-demo). 

* Java is a compiled language and thus, it requires you to restart the server whenever you make an update to code or configuration. Thus placing most of your business logic on your own application server is a preferable compared to putting in ther webapp. You can then connect Red5 pro webapp to your application server using HTTP/Websockets. That way whenever you make an update to the business logic you wont need to restart Red5 pro.

##### Custom plugin development

Red5 pro server plugins are your head-less interface into the server. They can do pretty much anything that webapps can do, since thay have access to all the same resources and configurations across the system. However plugins have no scope of their own. That means, you do not and cannot connect to a plugin using a client. Additionally if a plugin needs to expose any HTTP API or websocket service, it needs a webapp to hook into. Plugins cannot host servlets on their own. 

By design paradigm, as i mentioned earlier, use a plugin when you need to augment the features and behaviour of a webapp. Pluigins are reusable components, which means that you can target more than one webapp with the same plugin.

Here are a few typical use cases that make a good case for a custom plugin:

* Authenticate connections on your webapp. 

* Creating a media file cleaner, that automatically deletes files older than a a period of time.

* You have one or more webapps that require some sort of common behaviour augmented to them at `runtime`.

* Isolating your code into plugins and attaching them to webapps at runtime, means you can share the plugin with others without sharing your entire webapp.

**Coding References:**

* For custom plugin development checkout our [official documentation on Red5 Pro plugins](https://www.red5pro.com/docs/serverside-guide/plugin-development.html).It will guide you through creating your first plugin.

* For more snippets and understanding on different things that you can do with Red5 pro, checkout [the collection sample plugins on github by Rajdeep Rath](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-plugin-examples)

##### Runtime integrations or RPC integrations

There is another type of integration, which is also popular amonst a certain types developers. Its called runtime integration. Usually two different technologies can talk to each other by either loading each other's runtimes to access their classes at low level or each one talsk to the other using an internal RPC mechanism over sockets. Some popular examples of such implementations are:

* [Jython](https://www.jython.org/) or [Py4J](https://www.py4j.org/) for java-python integration
* [Node-Java](https://github.com/joeferner/node-java) for nodejs and java integration
* [Php-java bridge](http://php-java-bridge.sourceforge.net/pjb/) for php and java integration
* [JNBridge](https://jnbridge.com/) for dot net java integrations

If you do want to make use of such integrations, i would advise going with RPC style integrations instead of runtime based integrations for clarity.

### Autoscale specific integration factors and considerations

TODO

**Conclusion**

So with that we conclude the discussion on server side integrations for Red5 pro. All the points and links shared in the previous sections should be good enough to help you integrate your system with Red5 pro server side withotu hiccups. If you still have problems and need help, get in touch with me & my peers on [slack](https://red5pro.slack.com).

## Client side integration

Client side integration is required when you need to interface your customers/clients with the streaming system to be able to consume services. Client integrations requirement are usually more common than server integration requiremnts for most of the simple use cases.

As we discussed earlier, there are lots of out of the box solutions provided by the Red5 pro platform, that can help build your solution aroudn Red5 pro with little or no server integration. Therefore we are left with the client side of things which is the main are of focus for most businesses.

### Introduction to important elements of Red5 Pro client side

#### Client SDKs

The Red5 pro platform provides you with a [collection of SDKs](https://www.red5pro.com/docs/streaming/) to help you translate your business ideas into streaming interfaces for Android, IOS and Desktop browsers.

**Android SDK**

The [Android SDK](https://www.red5pro.com/docs/streaming/android.html) empowers you to add streaming  and realtime data exchange capabilities to your native android app. You can take browse the [Android API documentation](https://www.red5pro.com/docs/static/android-streaming/) to take a peek at the various methods it offers.

**IOS SDK**

The [IOS SDK](https://www.red5pro.com/docs/streaming/ios.html) empowers you to add streaming  and realtime data exchange capabilities to your native IOS app.You can take browse the [IOS API documentation](https://www.red5pro.com/docs/static/ios-streaming/) to take a peek at the various methods it offers.

**HTML5 SDK**

The [HTML5 SDK](https://www.red5pro.com/docs/streaming/web.html) allows you to add streaming  and realtime data exchange capabilities to your HTML5 applications through Red5 pro's **WebRTC** implementation.You can take browse the [HTML5 API documentation](https://www.red5pro.com/docs/static/html5-streaming/) to take a peek at the various methods it offers.

**It is worth mentioning here that the HTML5 SDK is perfectly compatible with both mobile and desktop browsers.**

So the very first step in client side integration would be to identify the different platforms that are going to target for clients, and then take a look at the appropriate SDKs.

### Out of the box features for client side integration

#### Client Testbeds

To help you with using the sdks and some out of the box code snippets, we have created something called testbeds. There is a testbed repository on github containing various examples for each of the SDKs that we offer. You can see test bed as a opportunity to get accustomed with the SDKs and at the same time learn about different things that can be built using the SDKs with Red5 pro. Whenever you need to lookup a 'how to' on client side integration, testebes is the first place to get started.

**Android Testbed**

The Android testbed is located at [https://github.com/red5pro/streaming-android](https://github.com/red5pro/streaming-android).

**IOS Testbed**

The IOS testbed is located at [https://github.com/red5pro/streaming-ios](https://github.com/red5pro/streaming-ios).

**HTML5 Testbed**

The HTML5 testbed is located at [https://github.com/red5pro/streaming-html5](https://github.com/red5pro/streaming-html5).

**React Native**

As a bonus we have included react native examples as well, in case you want to integrate Red5 pro streaming into a react native app. The react native examples can be located at [https://github.com/infrared5/react-native-red5pro](https://github.com/infrared5/react-native-red5pro)

##### Important Testbed examples

Let me quickly point you to relevant testbed examples that can help you solve your real world use cases  quickly.

* Building two way a/v chat:

  * [Android example](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/TwoWayTest)
  * [IOS example](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/TwoWay)
  * [HTML5 example](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/twoWay)
  
* Building one to many streaming

  * Android [publisher](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishTest) and [subscriber](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeTest) examples
  * IOS [publisher](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/Publish) and [subscriber](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/Subscribe) examples
  * HTML5 [publisher](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publish) and [subscriber](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/subscribe) examples
  
* Authenticating clients

  * Android [publisher](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishAuthTest) and [subscriber](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeAuthTest) examples
  * IOS [publisher](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishAuth) and [subscriber](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SubscribeAuth) examples
  * HTML5 [publisher](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishAuth) and [subscriber](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/subscribeAuth) examples
  
* Building realtime data oriented apps such as chat etc

  * Android publisher and subscriber examples
  * IOS publisher and subscriber examples
  * HTML5 publisher and subscriber examples

### Developing custom features for integration

**Design considerations:**

## Use Cases


## Tips and tricks

