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

- Consume streaming services through Red5 Pro media server, using appropriate protocol supported by Red5 Pro matching the client's capabilities.
- Developing any inter-client communication system between multiple clients using the media server
- Triggering methods/mechanisms on server from client side (RPC)

**Similarly the scope of server stack integration covers (but is not limited to), connecting with Red5 pro to:**

- Gather usage & performance statistics
- Monitoring
- Obtain connection & stream information
- Perform server administration tasks
- Implement custom business logic via webapps and plugins
- Pushing notifications from client

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

- [Simple Authentication Plugin](https://www.red5pro.com/docs/server/authplugin.html): A basic yet high flexible and extensible authentication plugin that can help you achieve almost any kind of authentication (credentials based or anonymous token based) for clients using your own application server or database server.

The plugin supports standardized JWT based authentication/authorization mechanism for clients **out of the box**. You can then use the [plugin's documentation to implement your own authentication/authorization service & configure the plugin to use it for validating clients](https://www.red5pro.com/docs/server/red5proroundtripauthvalidator.html#red5-pro-round-trip-authentication-validator).

- [Cloud Storage Plugin](https://red5pro.com/docs/server/cloudstoragevod.html): A cloud storage solution for Amazon S3. It helps you copy audio/video recordngs to your aws bucket. The plugin can be used with both standalone Red5 pro instances and Red5 pro autoscaling instances alike. If you wish to have support added for a different cloud platform, you can [get in touch with us to discuss it](https://calendly.com/red5-pro/15-minute-phone-call?month=2019-05).

- [HLS Plugin](https://www.red5pro.com/docs/server/hlsdocs.html): Helps generate HLS files for live and recording sessions. You can use this to control HLS latency or even diable HLS generation completely.

And i will reiterate here, if you something more, such as uploading file to your own application server, notifying your application server whenever a client joins or leaves Red5 pro etc, you can always create your own plugin with your business logic. There is a [detailed documentation on plugin development](https://red5pro.com/docs/serverside-guide/plugin-development.html) on our website.We have both paid and free support options to help you out!.

**Ready to use APIs**

Red5 pro platform provides an [extensive API](https://red5pro.com/docs/server/serverapi.html) to help gather information about the server and its different entities. You can query standard `statistics` on resources such as `applications`, `connections`, `streams` etc. You can also perform administrative and non-administrative actions on those resources such as `disconnecting clients`, `closing streams`, `recording live streams` etc.

For autoscaling systems there is the [Stream Manager API](https://www.red5pro.com/docs/autoscale/streammanagerapi.html), to help manage clusters and autoscaled nodes and other autoscaling resources on the target platform along with different calls for reading statistics. You can use both autoscale and single server api alongside each other to have maximum control over the system.

All API calls return industry standard JSON formatted data and responses. Therefore, you can use any technology to interace with our API calls through a simple API consumer client.

<Examples / Snippets>

<PHP>/<NODE>/<c#>/<java>

### Developing custom features for integration

We have seen the various `out of the box` solutions that the Red5 Pro platform offers. All of these are extremely helpful when you want to integrate Red5 pro with your own system. But there will always be use-cases where these offerings may not suffice. Let us take a quick peek at such use cases:

**Sample use-cases where custom development is required**

- You are building a solution for your product, where you wish to enable video and audio recording of customer reviews. Futher you wish to store these videos on your own server after recordings is completed.

- You are building a virtual classroom solution where you need to persist the state of the whiteboard on a remote server under a customer's account which resides on your application server.

- You are building a dating site, where there will be one to one chat sessions between clients, authenticated by your remote server and the stream names are pre-generated by your system.

- You want to log the detailed usage of your streaming application to your remote server including events such as client connect, client disconenct, client publish, client subscribe.

There could be many more use cases, but these are good enough to help you understand, when/if you need custom effort on for server side integration. Each of the use cases mentioned above, warrants custom development either partially or completely.

So the next question in your mind would be - **Where do i start ?**. To answer that, the first thing you need to do is sketch out the integration requirement. Here are a few sample topics that you should go over, as you decide on server side integration efforts:

- What are the requirements for integration ?
- What do you need from the server side for your system ?
- Are the APIs not sufficient for your use case?
- Do you need more/different APIs ?
- Does your team have a good expertise in java ?
- Do you want an addition to an existing Red5 pro feature ?

Having thought this over, you should now be ready to dive into some of the technical details of Red5 pro server side integrations.

#### Webapp vs Plugin development

Till now we have seen that the two ways to deploy custom server side logic on Red5 pro is either using a webapp or a plugin. While both options are fulfilling , let us try to see which option is more suitable for what use case. In other words when to choose webapp development and when to choose plugin development for deploying custom logic to Red5 pro.

##### Custom webapp development

As i mentioned earlier, webapps are the point of contact for a client on the Red5 pro server. In Red5 terminology webapps are also called top level `scopes`. every client that connects to Red5 pro needs a `scope` to do so. You cannot connect to the server at `{protocol}://{host}:{port}` alone. Every service that Red5 pro provides over various protocols such as RTMP, RTSP, RTC and HLS, is provided through a point of contact - a.k.a `webapp` or `scope`. So to consume services you need to use the following url pattern - `{protocol}://{host}:{port}/{webapp}`.

It is therefore imperative to develop you own webapp when you need more control over the point of contact. Every custom webapp can do all that the default webapp `live` can do, unless explicitly controlled. This is to say that every custom webapp can allow broadcast, playback, recording, SharedObjects etc over supported protocols by default.

A **webapp can better tap into its lifecycle** than a plugin and therefore is more efficient and preferred for capturing different events pertaining to the application, its clients and other resources such as stream and sharedobjects.

Here are a few typical use cases that make a good case for a custom webapp:

- Building an application with live stream recording. After are broadcast is complete you want to either process the file or move it to a custom location on the same server or a remote server.

- Send a notification to a remote server whenever a stream broadcast starts.

- Send a notification to a remote server whenever a subscriber starts subscribinga stream.

- Run ffmpeg on the recorded file, soon after recording stops to extract images.

- Add a custom API to the streaming application to read/write custom data. Webapps are perfect candidates to host a HTTP service. In java that is achieved through [servlets](https://www.geeksforgeeks.org/introduction-java-servlets/).

- Implementing secure file upload/download mechanism for your service. This is also a candidate for java servlets.

**Coding References:**

- For custom webapp development checkout the [Red5 pro develop series by Dominick Accattato](https://www.red5pro.com/docs/developerseries/). It will guide you through the prerequisites, setting up the development environment, creating a webapp etc in details.

- For more snippets and understanding on different things that you can do with Red5 pro, checkout [the collection of ready to go webapp examples demonstrating different Red5 java apis on github by Rajdeep Rath](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side)

- Here are some good [webapp examples on using java servlets to implement HTTP hooks on a Red5 pro application](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-misc-examples).

**Design considerations:**

There are differnt ways to interface communications betweeen systems. The path taken is purely a matter of choice. However i would recommend always sticking to industry standards for these things.

- Always use standard data formats such as JSON (preferred) or XML for exchanging messages between Red5 pro and the external system.

- Use HTTP for simple requests and Websockets where realtime flow is needed. For information on implementing websocket communication with your webapp checkout [our documentation](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-websocket-examples/application-websocketlistener-demo) and this ready to use webapp example.

- When designing to check for user presence in a chat or conference applications, rely on standard web solutions such as AJAX etc, instead of using Red5 pro sockets if possible. That will reduce load on your system. If you must implement user tracking through Red5 pro check out this example on [application callback methods](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-application-examples/application-adapter-demo).

- Java is a compiled language and thus, it requires you to restart the server whenever you make an update to code or configuration. Thus placing most of your business logic on your own application server is a preferable compared to putting in ther webapp. You can then connect Red5 pro webapp to your application server using HTTP/Websockets. That way whenever you make an update to the business logic you wont need to restart Red5 pro.

##### Custom plugin development

Red5 pro server plugins are your head-less interface into the server. They can do pretty much anything that webapps can do, since thay have access to all the same resources and configurations across the system. However plugins have no scope of their own. That means, you do not and cannot connect to a plugin using a client. Additionally if a plugin needs to expose any HTTP API or websocket service, it needs a webapp to hook into. Plugins cannot host servlets on their own.

By design paradigm, as i mentioned earlier, use a plugin when you need to augment the features and behaviour of a webapp. Pluigins are reusable components, which means that you can target more than one webapp with the same plugin.

Here are a few typical use cases that make a good case for a custom plugin:

- Authenticate connections on your webapp.

- Creating a media file cleaner, that automatically deletes files older than a a period of time.

- You have one or more webapps that require some sort of common behaviour augmented to them at `runtime`.

- Isolating your code into plugins and attaching them to webapps at runtime, means you can share the plugin with others without sharing your entire webapp.

**Coding References:**

- For custom plugin development checkout our [official documentation on Red5 Pro plugins](https://www.red5pro.com/docs/serverside-guide/plugin-development.html).It will guide you through creating your first plugin.

- For more snippets and understanding on different things that you can do with Red5 pro, checkout [the collection sample plugins on github by Rajdeep Rath](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-plugin-examples)

##### Runtime integrations or RPC integrations

There is another type of integration, which is also popular amonst a certain types developers. Its called runtime integration. Usually two different technologies can talk to each other by either loading each other's runtimes to access their classes at low level or each one talsk to the other using an internal RPC mechanism over sockets. Some popular examples of such implementations are:

- [Jython](https://www.jython.org/) or [Py4J](https://www.py4j.org/) for java-python integration
- [Node-Java](https://github.com/joeferner/node-java) for nodejs and java integration
- [Php-java bridge](http://php-java-bridge.sourceforge.net/pjb/) for php and java integration
- [JNBridge](https://jnbridge.com/) for dot net java integrations

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

The [Android SDK](https://www.red5pro.com/docs/streaming/android.html) empowers you to add streaming and realtime data exchange capabilities to your native android app. You can take browse the [Android API documentation](https://www.red5pro.com/docs/static/android-streaming/) to take a peek at the various methods it offers.

**IOS SDK**

The [IOS SDK](https://www.red5pro.com/docs/streaming/ios.html) empowers you to add streaming and realtime data exchange capabilities to your native IOS app.You can take browse the [IOS API documentation](https://www.red5pro.com/docs/static/ios-streaming/) to take a peek at the various methods it offers.

**HTML5 SDK**

The [HTML5 SDK](https://www.red5pro.com/docs/streaming/web.html) allows you to add streaming and realtime data exchange capabilities to your HTML5 applications through Red5 pro's **WebRTC** implementation.You can take browse the [HTML5 API documentation](https://www.red5pro.com/docs/static/html5-streaming/) to take a peek at the various methods it offers.

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

Let me quickly point you to relevant testbed examples that can help you solve your real world use cases quickly.

- Bandwidth detection:

  - Android [upload](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/BandwidthDetectionUploadOnlyTest), [download](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/BandwidthDetectionDownloadOnlyTest) & [generic](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/BandwidthDetectionTest) examples
  - IOS [upload](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/BandwidthDetection), [download](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/BandwidthDetectionDownloadOnly) & [generic](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/BandwidthDetection) examples

  - [Android example](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/TwoWayTest)
  - [IOS example](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/TwoWay)
  - [HTML5 example](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/twoWay)

- Building two way a/v chat:

  - [Android example](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/TwoWayTest)
  - [IOS example](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/TwoWay)
  - [HTML5 example](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/twoWay)

- Building one to many streaming

  - Android [publisher](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishTest) and [subscriber](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeTest) examples
  - IOS [publisher](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/Publish) and [subscriber](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/Subscribe) examples
  - HTML5 [publisher](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publish) and [subscriber](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/subscribe) examples

- Authenticating clients

  - Android [publisher](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishAuthTest) and [subscriber](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeAuthTest) examples
  - IOS [publisher](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishAuth) and [subscriber](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SubscribeAuth) examples
  - HTML5 [publisher](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishAuth) and [subscriber](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/subscribeAuth) examples

- Building realtime data oriented apps such as chats, multiplayer games etc

  - Android [SharedObject](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SharedObjectTest) example
  - IOS [SharedObject](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SharedObject) examples
  - HTML5 [publisher](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishSharedObject) and [subscriber](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/subscribeSharedObject) examples
  - HTML5 [conference](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/sharedObjectMultistream) example

### Developing custom features for integration

We have seen the different SDKs that Red5 pro offers for building/integrating clients with Red5 pro. While the SDKs themselves are not Open Source, there are various possibilities to extend the functionality or even develop your feature using the SDKs to help your client code integrate better with Red5 pro.

Most of the custom development requirements are centered around using the SDKs & the testbed examples to build a complete client side application.

**At the time of writing this article, none of the SDKs are open source. If you need a feature added or a bug to be fixed, please use the Github issue reporting system in the appropriate repository. Additionally you can get in touch wih us via email or slack channel if you need any undocuemnted information on one of the SDKs.**

**Sample use-cases where custom development is required**

- You wish to develop your own communication mechanism based on WebSockets or HTTP, other than the ones provided in the SDKs, such as SharedObjects.

- If you have a custom logic/method on the server to be invoked from the client. Example if you need to set/update a property on the client's `IConnection` object on the server side, whenever the client interacts with the application.

There could be many more use cases, but these are good enough to help you understand, when/if you need custom effort on for server side integration. Each of the use cases mentioned above, warrants custom development either partially or completely.

Before getting started with client side development you shoudl ask yourself the same questions that we discussed for server side development :

- What are the requirements for client integration ?
- How will the server side interface with the client side ?
- Are the testbed examples enough for developing what you need ?
- Do you have a custom webapp or plugin on the server, that your client nees to interface with ?
- What are the platforms you wish to target - HTML5/Android/IOS ?
  - What type of clients do you expect ?
- Do you want an addition to an existing SDK features ?

Having thought this over, you should now be ready to dive into some of the technical details of Red5 pro client side integrations.

### Common activities of a Red5 pro client application

Based on application use cases and development patterns, there are few easily identifyable activities in a Red5 pro client applications. Almost every Red5 pro client application will include one or more of these features directly or indirectly.

Red5 Pro features may makeup for a small part or a majority of your application functionality.In this section, we will discuss those features and take a look at how they can be included in our own application.

#### Loading configuration

The first part is to load the Red5 pro related configuration data into your application. The freedom of choosing the design is upto you. You can keep thinsg simple by hardcoding the configuration data in your application code (not recommended) or add it via a configuration file or even have it load from your application server over `HTTPS`.

**While the testbed examples show configurations stored in a particular way such as embedded in code or stored in a config file, you can load your configuration from anywhere as long as you feed it to the sdk elements correctly.**

Mobile SDKs require you to specify your `SDK LICENSE KEY` as well. Take special care as to not expose this to your users. You can normally keep this embedded in the application itself. Use obfuscation to protect the license.

**Generally guidelines for loading configurations:**

- Avoid hardcoding configuration data in your application.
- As much as possible try to keep the configuration parameters stored away from the application.
- For mobile application obfuscation is very much recommended to protect your license code.
- If you do intend to store the license key in the application, avoid loading it in plain text over the network.
- Opt for a comprehensive, descriptive and heasy to parse data format such as JSON to represent your configuration data.

#### Generating stream names

Stream names are the `key` component of a streaming context. Stream Name is used to create and access streams in the system. Thus it is important to pay special attention to them.

- Stream names can be loaded in the application as part of the configuration or separately.

- Stream names can be dynamic or static depending on your application's needs. Using dynamic stream names always ensures better security.

- In simple applications such as two way chats or conferences, it is simpler to use the unique identifier of the application platform such as the `username` or `userid` to generate the stream name. You can always obfuscate it further by adding a salt to it.

- For applications that have a `anonymous user` pattern or for one to many streaming use cases, you can generate unique stream names on each access. It is then the job of the developer to coordinate the stream names between users so that they can find each other.

- Do not generate names starting with a number or names that have only numbers. Stream name should start with alphabets followed by special characters or numbers. Use only `_` or `-` a special characters.

#### Configuring clients

Once you have the mechanism to load the configuration in place, the next step is to properly configuring your clients for publishing/subscribing.

##### Resolution and framerate

Quality is a subtle combination of appropriate resolution, framerate, keyframe interval and bandwidth settings.It a commonly known concept that motion requires bandwidth. So depending on your content you can try higher resolution at same bandwidth if you know there is little or no motion.

**RTMP Publishers**

If you are using a third party RTMP encoder (non flash), take a look at our [encoder configuration guide](https://www.red5pro.com/docs/server/rtmppublishers.html) on recommended settings. When using third party encoders, you can take advantage of their industry standard presets for recommedned resolution and framerate settings.

**Android/IOS Publishers**

For mobile SDK publishers, `854x480` at either `750Kbps` or `1000Kbps` is a standard value. You can experiment around it to find what best suits your application. If you wish to set high quality resolutions such as `720P` or `1080P`, make sure to test it thoroughly with the intended devices to make sure that the network as well as the device can handle it. You can checkout the [testbed configuration](https://github.com/red5pro/streaming-android/blob/master/app/src/main/res/raw/tests.xml) to see the different configuration attributes available and sample values.

**HTML5 Publishers**

For HTML5 SDK publishers, the SDK provides simple way of defining publish settings as a JSON object. Capture dimensions, bandwidth, keyFrameInterval and framerate the primary parameters for a publisher client. The SDK will use the `mediaConstraints` defined and then cycle through the `gUM` to find a appropriate match that is allowed by the browser. You can checkout the [testbed configuration](https://github.com/red5pro/streaming-html5/blob/master/static/script/testbed-config.js) to see the different configuration attributes available and sample values.

##### FrameRate

Framerate is usally the most concealed settings of a streaming application. When you see a video stream you probably cannot make a qualitative or quantitative assumption of the framerate that is used. Human brain can interpret upto 1000fps but it cannot makeout any difference above 60fps. TV standards NTSC and PAL are set at 29.27 fps & 25 fps respectively, whereas webcam stream appreas `just fine` at 15 fps. However, the determination of framerate also must consider the bandwidth available. More pictures you want to send over the network, more bandwidth you need.

So the selection of Framerate depends on the type of content being broadcasted, where it will be played back and how much bandwidth you have.

##### KeyFrame Interval

KeyFrame interval dictates how often shoudl the encoder transmit `keyframes`. The keyframe (i-frame) is the full frame of the image in a video. Subsequent frames, the delta frames, only contain the information that has changed. It is all about how video compression works in transmission. We dwont be going into the details of it here. But you should just know that in general, `frequent keyframes can improve quality and initial playback time at the cost of significant bandwidth and cpu`.

##### Bandwidth

If you are using a web based encoder, you can do a bandwidth test to determine the optimal bandwidth quality settings for the client. We have bandwidth detection examples for each SDK in the respective testbeds. Optionally you may also provide selectable presets of quality settings.Based on the quality settings you can intialize the publisher's camera device for a broadcast.

#### Building coordination between publishers and subscribers

Once you have clients configured, the next step is to work on coordination. This will give rise to some fundamental design questions such as:

- How will the a client know about other clients in the system
- How can we hide certain clients from other clients in the system
- How do subscribers find publishers

and so on..

Remember that everything in Red5 pro are resources. `Connections`, `streams`, `scopes` and `sharedobjects` are all nothing but resources. So the problem of finding or not finding clients is actually the problem of being able control access to these resources at a design level.

For example, if i want to see your stream i need to know the application scope or subscope that you are connected to and the stream name that you are publishing. Thus if you want me to see yourt stream you should coordinate the application scope/subscope name, path and the stream name that you are publishing.

Resources can be coordinated between client in different ways:

- `Hardcoded`: When developing applications, sometimes it is simpler to have the resource names embedded in the code for testing. This is the simplest way of coordinating resources. This way is **not** recommended for production use.

- `Polling`: Client polls the database or a remote API endpoint for available resources. When the resource list seem to be populated, the client can pick out the data and access the resource.

**Example**

Publisher publishes a stream to the server application called `live`. A mechanism will detect `publish` event, and register the stream name in the database. Subscriber polls periodically to see if there is a stream name available to subscribe to. When the response contains a stream name entry, the subscriber will conenct to the server application `live` and play the stream.

- `Push Data`: Client connects to a remote service or Red5 pro sharedObjects and awaits a push data containing information about resources. Once data is received, the client will access the resource using the data in the `push notification`.

**Example**

Publisher connects to the server application called `live` and acquires a SharedObject called `users`. the publisher then publishes a stream to the server application. On successful publish acknowledgement from server, the publisher pushes the stream name into the sharedobject.Subscriber(s) also follow the same initialization sequence and acquires a SharedObject called `users`. As soon as publisher pushes data into the sharedobject, the server will push it to all connected clients. The subscribers can then read the stream name from the payload and play the stream. Subscribers that connect late will also recieve the pushed data, as soon as they conenct to the sharedobject.

> You can also use any other third party push notification system such as Amazon SNS etc to relay the information between clients.

#### Adding Authentication for clients

Next, depending on your requirements it is both important and prudent to secure your system against unauthorized client access.

Here let me talk about two things - authentication and authorization.

**Authentication**

Authentication implies granting access to your resources. It just simply means you need to prove that you can access the resources. Simplest example is a `login system`. At the top most level the form will just validate your access to the system without caring for who you are. In the context of a streaming application associated with Red5 pro, authentication is useful when all clients will have equal rights. Example: for an a/v chat if you assume that everyone can publish and everyone can subscribe and there is no admin then all you care about is authentication.

You can authenticate your clients at your own application level using a remote database or api, or you can even authenticate them using Red5 pro server side code.

**Authorization**

Authorization implies granting role specific permissions. So your authentication can be augmented with authorization on server level. This means you want to have separate rights for seperate clients. In the context of a streaming application associated with Red5 pro, authorization is necessary to seperate client behaviour based on the roles they play in the system.

Since a lot of this (authorization by role) is related to the server side behaviour, you might need to interface client code with some custom server side code.

For implementing security we provide the [Red5 pro simple auth plugin](https://red5pro.com/docs/server/authplugin.html) for the server, which can help you implement authentication or authorization very easily. All of this is provided `out of the box` with simple configuration and comprehencive instructions for client integrations. You can also extend the auth module to have your security system do more.

Our testbed examples contain examples to help you integrate the server side security plugin with your own client. Check out the [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishAuth), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishAuthTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishAuth) examples on how to send authentication parameters from clien to server.

**Additional Recommended Reads**

- [Red5 Pro Simple Auth Plugin - Introduction](https://red5pro.com/docs/server/authplugin.html)
- [Red5 Pro Simple Auth Plugin - Walkthrough](https://blog.red5pro.com/simple-authentication-walkthrough/)
- [Red5 Pro Simple Auth Plugin - Documentation](https://github.com/red5pro/red5pro-simple-auth-plugin)

#### Publishing

Once yourself publisher client is configured properly with necessary broadcast settings and authentication parameters as necessary, you can implement the mechanism to initialize your publisher client and start publishing. Depending on your requirement, you may want publishing with different contrains and options. 

**Simple Publish**

For simple publishing example take a look at the `Publish` testbed example for [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publish), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/Publish) in the respective testbeds.

**Changing Camera**

In case of multiple camera devices, such as as in mobile phones and desktops with more than one USB cameras, you can easily select which device you with to use for publishing. In come cases it is possible to even swap devices while publishing.

For  Camera Select and Camera Change examples take a look the following HTML5, Android and IOS testbeds.

*  [HTML5 Camera Swap](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishCameraSwap)
* [HTML5 Camera Select](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishCameraSource) 
* [HTML5 live Camera Swap](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishMediaStreamCamera)
* [Android Camera Swap](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishCameraSwapTest)
* [IOS Camera Swap](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/CameraSwap)

**HQ Audio**

If audio quality is of more importance in your application checkout the HQ audio examples for [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishHQAudio), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishHQAudioTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishHQAudio) respectively.

**Custom Video Source**

For mobile devices if you are looking to use a custom video source instead of the default camera devices, then perhaps the Custom Video Source example for [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishCustomSourceTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishCustomSource) is what you need to taek a look at.

#### Subscribing

Similar to the publisher, once yourself subscriber client is configured properly with necessary playback settings and authentication parameters as necessary, you can implement the mechanism to initialize your subscriber client and start subscribing. Depending on your requirement, you may want the subscription with different contrains and options. 

**Simple Subscribe**

For simple subscribe example take a look at the `Subscribe` testbed example for [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/subscribe), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/Subscribe) in the respective testbeds.

**Audio only subscription**

If the target stream is audio oriented or if you do not want to subscribe to the video in the stream, checkout the `Audio only subscription` examples for [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/subscribeVideoMute), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeNoViewTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SubscribeNoView) in the respective testbeds.

**VOD Playback**

Each Red5 Pro SDK also provides the capability to subscribe to a recorded video (video on demand), with the platform's / protocol's limitation under consideration. 

For HTML5 use the [VOD playback](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/playbackVOD) example, whereas for Android and IOS you can follow the standard subscribe examples, with the differnce that you specify the `filename with extension` instead of `live stream name`.

For HLs / Mp4 VOD you can also play your media file from a cloud bucket (S3 or GStorage) in the mobile browser of a webview as a standard HTML5 media.

#### ABR

The abbreviation ABR stands for adaptive bitrate streaming. This is a feature used to distribute a stream to multiple clients having different bandwidths efficiently by matching a appropriate version (by quality) of the stream with each client.

ABR is not supported for single server installs or even manual multi server clusters. If you need ABR, you need to use Red5 pro autoscaling. for more information i recommend reading through the [ABR guide](https://red5pro.com/docs/streaming/abruserguide.html) and the use of [transcode nodes](https://red5pro.com/docs/autoscale/transcoder.html)

At the time of writing this, ABR is not supported for RTSP clients. Clients can subscribe to the stream quality of their choice manually.

#### Muting Audio

When publishing or subscribing, sometimes you might want to mute audio of the stream. Typically with online public events, sometimes when there is a break or so, the video is still running and the audio is paused to avoid noise to subscribers. On the other side a subscriber too might sometimes want to watch just the video.

In such cases you need to implement the mute audio funtionality in your application.

**HTML5 SDK**

To implement audio/video mute/unmute for a HTML5 SDK client, check out the [example on publisher audio/video mute](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishMute). On the other hand, subscribers can either adjust the volume of the playback stream or completely mute it.

**Android/IOS SDK**

**Publishers**

To implement audio/video mute/unmute for a Android /IOS SDK client, check out the [android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishPauseTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishPause) examples on publisher audio/video mute funtionality.

**Subscribers**

**Android**

- [Adjust the volume](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SubscribeSetVolume)
- [Entirely mute](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SubscribeMuteAudio)

**IOS**

- [Adjust the volume](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeSetVolumeTest)
- [Entirely mute](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeMuteTest)

#### Adding data exchange capability

In lot of application use cases, there is a need to work with realtime data. Realtime data can be thought of as chat messages, control messages, shared temporary data stores and so on.

Red5 pro has multiple ways of transmistting data in realtime, with each having its own usage scenario.

- **Sending data over stream:** : Transmitting data over stream using the `stream.send` api provided in each SDK.
- **Client to server RPC:** Invoking custom Red5 pro server side methods from client.
- **Red5 pro SharedObjects:** Using Red5 pro SharedObject to build interactive two communication between two or more clients.

In this section we will be discussing about [Red5 pro SharedObjects](https://www.red5pro.com/docs/streaming/sharedobject.html) only. You can read the basics of [Red5 pro SharedObjects](https://www.red5pro.com/docs/streaming/sharedobject.html) in our official documentations.

In the context of a Red5 pro application, you use sharedobjects, when you need to communicate in real time between clients. Usually sharedobjects facilitate bidirectional communication, but you can design your application to restrict communication as you see fit.

We offer SharedObjects as part of each SDK and also the server side API. SharedObjects can be used with or without streams in general. If you needs sharedobjects support in your application take a look at following testbed examples:

**HTML5 SDK**

- [SharedObject with publisher](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishSharedObject)

* [SharedObject with subscriber](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/subscribeSharedObject)

* [Standalone SharedObjects](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/sharedObject)

**Android**

- [Standalone SharedObjects](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SharedObjectTest)

**IOS**

- [Standalone SharedObjects](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SharedObject)

**A few good use cases**

**Communication and state sharing**

If you are designing a video conference, you can use shared objects to share information about the participants such as usernames, stream names etc. Whenever a client joins the system, the username, stream name etc are pushed into a common shared object associated with the session. As soon as the connecting clients subscribes to the shared object, they get updated about users that are already in the system. Similarly the existing clients also get notified about the new client.

Through the session, you can also communicate the muted/unmuted state of devices of a client to other clients, if your application warrants of it.

If you are building a game or whiteboard application you can relay/share the state of the game/whiteboard with other clients in realtime using sharedobjects.

**Tracking stream subscribers in realtime**

You can also use shared objects as a means of sharing arbitrary data in realtime.

Consider that you have an server side application that tracks subscriber count. Now if you want to share that with clients (ex: display active subscriber count on player), you can have the server side logic push this informatrion intoa shared object. On client side, you can subscribe to this sharedobject to receieve realtime updates of changing subscriber count.

#### Sending messages over stream

Red5 pro has multiple ways to transmit data in realtime. While SharedObject and RPCs are common to use cases, transmitting data over the stream is yet another possibility.

Sometimes a publisher might want to quickly want to send a text or control message to all client currently subscribed to the publishing stream. This is where you use transmission over the stream channel.

**Use cases**

- Sending control messages to indicate when publisher is muted/unmuted
- Transmitting stream stats info to subscriber clients

#### Handling interruptions

Unforseen interruptions are always a part of any activity, and streaming is no different. Connection drops, power interruptions, system crash or even human induced disturbances are just a few causes why your publish/subscribe session might be lost.

Thats where a well designed application will tend to create a seamless resume mechanism. To assist you design a graceful resum funtionality, we have special examples for each of the SDKs.

There is a subscriber reconnect example in the [HTML5 SDK testbed](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/subscribeReconnect), the [Android testbed](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeReconnectTest) and the [IOS testbed](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SubscribeReconnect).

Publisher interruption can have different reasons and different ways to resume for each reason. Once you identify the reasons you will have a reconnect strategy for each.I recommend getting in touch with us via email or slack channel if you want help on this.

**Mobile device interruptions**

As compared to desktops, the mobile platform is prone to special types of interruptions such as switching between applications, incoming phone call etc. We have provided some special examples in the respective testbeds to help you build better user experience on mobile devices:

**Background subscribe**

[Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeBackgroundTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SubscribeBackground) examples for subscribing in background, allowing people to multitask without needing to disconnect from their stream.

**Background publish**

[Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishBackgroundTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishBackground) examples for publishing in background, allowing people to multitask without needing to disconnect from their stream. And an additional example on [handling telephony interrupts on IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishTelephonyInterrupt)

#### Capturing snapshot

Capturing a snapshot of the live stream and displaying it as the poster image for the live stream on your website is oen of the most prominent features in live streaming systems.

At the time of writing this, there is no built-in api/mechanims to get a snapshot image o fthe live stream. that said there are two ways to capture image from a live red5 pro stream.

**Client side:**

On client side create a mechanism to capture the video renderer screenshot, depending on what platform/SDK you are using. Encode the captured data as jpeg/png and then encode it as a base64 string.

You can then send this data to your backend server where a script can base64 decode it and write it to the disk as a jpeg/png. This is the easiest way to capture snapshots of the client.

Additionally to help you our SDKS have an example of image capture for [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishImageCapture), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishImageTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishStreamImage) in their testbeds.

**Server side**

If you want to capture snapshots on the server side directly, you can use ffmpeg to do the same. Check out our [guide on using ffmpeg with live streams](https://www.red5pro.com/docs/server/ffmpegstreaming.html). Check out [this integrated webapp example](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-misc-examples/ffmpeg-thumbnails) so see how to read stream as soon as `publish` starts.

#### Recording the session

Now irrespective of whether you need a poster image or not, wanting to record the live stream is the most common need for most streaming systems. At the time of writing this guide, Red5 pro allows you to records a stream in `FLV` and `HLS` containers.

Red5 pro gives you multiple ways to record a stream. You can choose the way that is optimal for your use case. the following optiosn are available to enable recording of a live stream.

1. As a client if you can direct the server to record the stream by specifying the publish mode as `record` in your client code. We have examples for [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishRecord), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/Publish)

For IOS and Android examples, simply change the publish mode to `record` to test the code.

2. To enable automatic recording for all streams, you can set the server side flag `broadcaststream.auto.record=true` in `{RED5_HOME}/conf/red5.properties`.

3. You can also use [Red5 pro server api](https://red5pro.com/docs/server/serverapi.html#recordlivestream) to record your streams. Using an out of the box api can be very useful when you need to initiate recording from a remote location on demand. while options `#1` and `#2` cannot be toggled on the fly without stopping the client or server, API gives you a flexible solution to record/stop without disturbing either.

4. If you are writing your own webapp and want to record a live stream on some internal condition, you can cal the `IProStream.saveAs()` method to start recording. Every Red5 pro stream is an `IProStream` object.

By default automatic recording captures FSL ad HLS files. If you do not want HLS recordings across the system, you can simply delete the `red5pro-mpegts-plugin-5.5.0.343-RELEASE.jar` file.

#### Storing and Viewing VOD Recordings

By default recordings are stored in the `streams` directory of the webapp in the context. If you want you can move the recordings to a remote server for storage and later serving.

If you write your own custom solution as a webapp or plugin, you can conenct with us on slack for development tips on subjects such as detecting when to upload file etc.

As an out fo the box solution, we offer [Red5 pro cloud storage plugin](https://red5pro.com/docs/server/cloudstoragevod.html), which can help you move your recordings to AWS or Google bucket automatically. It can also help transcode flv to mp4 before uploading.

If you wish to serve all types fo clients, make sure you have `MP4` version of the recording alongside `FLV` and `HLS`. You can build any custom security around VOD similar to live streams using the [Red5 pro simple auth plugin](https://red5pro.com/docs/server/authplugin.html).

## Use Cases

## Tips and tricks