# Integrating Red5 Pro with external systems

[Introduction](#)

[Integration with Red5 Pro - implication and expectations](#)

[Server-side integration](#)
  * [Elements of Red5 Pro server side](#)
    * [Red5 Pro Webapps]()
    * [Red5 Pro Plugins]()
  * [Out of the box features for server-side integration](#)
    * [Ready to use streaming applications]()
    * [Ready to use plugins]()
    * [Ready to use APIs]()
  * [Developing custom server side features for integration](#)
    * [When and where custom development is required](#)
    * [Webapp vs Plugin development](#)
      * [Custom webapp development](#)
      * [Custom plugin development](#)
    * [RPC and Low Level integrations](#)
  * [Autoscale specific server side integration factors and considerations](#)
    * [Design considerations for autoscale oriented server side development](#)
    * [Deploying Red5 pro plugins in autoscaling environments](#)
    * [Deploying Red5 pro webapps in autoscaling environments](#)
  * [Conclusion on server side integration](#)

[Client-side integration](#)
  * [Elements of Red5 Pro client side](#)
    * [Client SDKs](#)
    * [Autoscale specific client side integration factors and considerations](#)
    * [Out of the box features for client-side integration](#)
      * [Client Testbeds](#)
    * [Developing custom client side features for integration](#)
      * [When and where custom client-side development is required](#)
    * [Common activities of a Red5 pro client application](#)
      * [Loading configuration](#)
      * [Generating stream names](#)
      * [Configuring clients](#)
      * [Loading configuration](#)      
      * [Adding Authentication for clients](#)
      * [Publishing](#)
      * [Subscribing](#)
      * [Building coordination between publishers and subscribers](#)
      * [ABR Subscription](#)
      * [Muting Audio](#)
      * [Adding data capabilities - SharedObjects](#)
      * [Sending messages over the stream](#)
      * [Handling interruptions](#)
      * [Capturing snapshot](#)
      * [Recording the session](#)
      * [Storing and Viewing VOD Recordings](#)
* [Use cases](#)

## Introduction

System integration is the process of aggregating different sub-systems to build a single large system, where every sub-system functions together with other sub-systems co-operatively, while operating within its own limited scope, to achieve a larger system.

In the context of Red5 Pro, this means, how different businesses, offering different products & services can integrate with Red5 pro services irrespective of their technology and architecture to include video/audio and data services as a part of their system.

While the problem of system integration is not new, some of the integration aspects of Red5 Pro (being a java based stream server) are different than usual web technologies. In this document, we will be taking a look at different aspects of integrating Red5 Pro, the challenges to expect, their solutions and few real-world integration use cases.

At this point, I will assume that you are either a developer or a business owner or a solution analyst who has either tried out Red5 Pro streaming demo and is now interested in integrating Red5 Pro with your own system or are in the process of analyzing Red5 pro as a streaming solution for your product. In either case, this document is here to help you do just that.

## Integration with Red5 Pro - implication and expectations

Integrating your business system with Red5 Pro is a multi-point process. Every business application (product or service) will usually consist of server and client stacks. Server stack will consist of backend services such as application servers, database services, data processing servers, etc. On the other hand, the client stack will consist of client applications targetting desktop, mobile devices and other special prospective client types such as IoT devices, IP cameras, Drones, etc.

![Integration Stack](images/business-stack.png)

For successful system integration, it is necessary to identify the different integration stacks and the content of each stack with respect to your business requirements.

**Integrating the client stack with Red5 pro implies (but is not limited to) integrating client applications to:**

- Consume streaming services through Red5 Pro media server, using appropriate protocol supported by Red5 Pro matching the client's capabilities.
- Developing any inter-client communication system between multiple clients using the media server
- Triggering methods/mechanisms on the server from the client-side (RPC)

**Similarly the scope of server stack integration covers (but is not limited to), connecting with Red5 pro to**

- Gather usage & performance statistics
- Monitoring
- Obtain connection & stream information
- Perform server administration tasks
- Implement custom business logic via webapps and plugins
- Pushing notifications from the client

So now that, you probably have a good idea of what integration is all about, let us proceed with taking a deeper look into both server and client integration stacks in details.

## Server-side integration

Server-side integration is required when you need granular control over the streaming subsystem at different levels to be able to communicate with it and control it from another sub-system.

### Elements of Red5 Pro server side

#### Red5 Pro Webapps

Red5 Pro webapps are the main entry points for clients wanting to consume streaming services through Red5 Pro. Red5 pro webapps, alongside streaming services, can also provide standard web services such as serving web pages, handling parameterized HTTP requests, etc.

#### Red5 Pro Plugins

Red5 Pro plugins are the `minions` of the Red5 pro platform that can help you accomplish almost anything. Plugins, unlike webapps, are headless entities. They can augment any existing webapp with new behavior, or even implement it across all webapps on the server. You can use plugins to implement isolated features or tap into the existing features provided by the Red5 pro platform.

### Out of the box features for server-side integration

#### Ready to use streaming applications

In general Red5 Pro is a streaming-ready server. This means you can simply point a compatible client to the server and publish/subscribe without having to write any code on the server side. Red5 Pro is distributed with certain pre-built **server applications** that are stream ready in nature. You can use them to stream live content, record as well as playback a recorded media.

A very good example of this is the `live` webapp. The `live` webapp is the one that is used by the server for demonstrating simple publish and subscribe at `http://localhost:5080/live/broadcast.jsp?host=localhost` & `http://localhost:5080/live/subscribe.jsp?host=localhost`pages respectively.

This is a simple application with no additional features other than streaming, recording, and playback. There is no security enabled on it either. You can use our [authentication plugin](https://www.red5pro.com/docs/server/authplugin.html) to attach custom security to it.

#### Ready to use plugins

The red5 pro distribution includes some basic plugins that give you a head start in integrating Red5 pro with your own system. Following plugins are provided out of the box for system integration:

- [Simple Authentication Plugin](https://www.red5pro.com/docs/server/authplugin.html): A basic yet high flexible and extensible authentication plugin that can help you achieve almost any kind of authentication (credentials based or anonymous token-based) for clients using your own application server or database server.

The plugin supports standardized JWT based authentication/authorization mechanism for clients **out of the box**. You can then use the [plugin's documentation to implement your own authentication/authorization service & configure the plugin to use it for validating clients](https://www.red5pro.com/docs/server/red5proroundtripauthvalidator.html#red5-pro-round-trip-authentication-validator).

- [Cloud Storage Plugin](https://red5pro.com/docs/server/cloudstoragevod.html): A cloud storage solution for Amazon S3. It helps you copy audio/video recordings to your AWS bucket. The plugin can be used with both standalone Red5 pro instances and Red5 pro autoscaling instances alike. If you wish to have support added for a different cloud platform, you can [get in touch with us to discuss it](https://calendly.com/red5-pro/15-minute-phone-call?month=2019-05).

- [HLS Plugin](https://www.red5pro.com/docs/server/hlsdocs.html): Helps generate HLS files for live and recording sessions. You can use this to control HLS latency or even disable HLS generation completely.

And I will reiterate here, if you something more, such as uploading file to your own application server, notifying your application server whenever a client joins or leaves Red5 pro, etc, you can always create your own plugin with your business logic. There is a [detailed documentation on plugin development](https://red5pro.com/docs/serverside-guide/plugin-development.html) on our website. We have both paid and free support options to help you out!.

#### Ready to use APIs

The red5 pro platform provides an [extensive API](https://red5pro.com/docs/server/serverapi.html) to help gather information about the server and its different entities. You can query standard `statistics` on resources such as `applications`, `connections`, `streams` etc. You can also perform administrative and non-administrative actions on those resources such as `disconnecting clients`, `closing streams`, `recording live streams`, etc.

For autoscaling systems there is the [Stream Manager API](https://www.red5pro.com/docs/autoscale/streammanagerapi.html), to help manage clusters and autoscaled nodes and other autoscaling resources on the target platform along with different calls for reading statistics. You can use both autoscale and single server API alongside each other to have maximum control over the system.

All API calls return industry-standard JSON formatted data and responses. Therefore, you can use any technology to interface with our API calls through a simple API consumer client.

### Developing custom features for integration

We have seen the various `out of the box` solutions that the Red5 Pro platform offers. All of these are extremely helpful when you want to integrate Red5 Pro with your own system. But there will always be use-cases where these offerings may not suffice. Let us take a quick peek at such use cases:

#### When and where custom development is required

- You are building a solution for your product, where you wish to enable video and audio recording of customer reviews. Further, you wish to store these videos on your own server after recordings are completed.

- You are building a virtual classroom solution where you need to persist the state of the whiteboard on a remote server under a customer's account which resides on your application server.

- You are building a dating site, where there will be one to one chat sessions between clients, authenticated by your remote server and the stream names are pre-generated by your system.

- You want to log the detailed usage of your streaming application to your remote server including events such as client connect, disconnect, publish & subscribe.

There could be many more use cases, but these are good enough to help you understand, when/if you need custom effort on for server-side integration. Each of the use cases mentioned above warrants custom development either partially or completely.

So the next question in your mind would be - **Where do I start ?**. To answer that, the first thing you need to do is sketch out the integration requirement. Here are a few sample topics that you should go over, as you decide on server side integration efforts:

- What are the requirements for integration?
- What do you need from the server side for your system?
- Are the APIs not sufficient for your use case?
- Do you need more/different APIs?
- Does your team have good expertise in java?
- Do you want an addition to an existing Red5 pro feature?

Having thought this over, you should now be ready to dive into some of the technical details of Red5 pro-server-side integrations.

#### Webapp vs Plugin development

Till now we have seen that the two ways to deploy custom server-side logic on Red5 pro is either using a webapp or a plugin. While both options are fulfilling, let us try to see which option is more suitable for what use case. In other words when to choose webapp development and when to choose plugin development for deploying custom logic to Red5 pro.

##### Custom webapp development

As I mentioned earlier, webapps are the point of contact for a client on the Red5 Pro server. In Red5 terminology, webapps are also called top-level `scopes`. every client that connects to Red5 pro needs a `scope` to do so. You cannot connect to the server at `{protocol}://{host}:{port}` alone. Every service that Red5 pro provides over various protocols such as RTMP, RTSP, RTC, and HLS, is provided through a point of contact - a.k.a `webapp` or `scope`. So to consume services you need to use the following URL pattern - `{protocol}://{host}:{port}/{webapp}`.

It is therefore imperative to develop your own webapp when you need more control over the point of contact. Every custom webapp can do all that the default webapp `live` can do unless explicitly controlled. This is to say that every custom webapp can allow broadcast, playback, recording, SharedObjects, etc over supported protocols by default.

A **webapp can better tap into its lifecycle** than a plugin and therefore is more efficient and preferred for capturing different events pertaining to the application, its clients and other resources such as stream and shared objects.

Here are a few typical use cases that make a good case for a custom webapp:

- Building an application with live stream recording. After being broadcast is complete you want to either process the file or move it to a custom location on the same server or a remote server.

- Send a notification to a remote server whenever a broadcast starts.

- Send a notification to a remote server whenever a subscriber starts subscribing.

- Run FFmpeg on the recorded file, soon after recording stops to extract images.

- Add a custom API to the streaming application to read/write custom data. Webapps are perfect candidates to host an HTTP service. In Java that is achieved through [servlets](https://www.geeksforgeeks.org/introduction-java-servlets/).

- Implementing a secure file upload/download mechanism for your service. This is also a candidate for Java servlets.

**Coding References:**

- For custom, webapp development checks out the [Red5 pro develop series by Dominick Accattato](https://www.red5pro.com/docs/developerseries/). It will guide you through the prerequisites, setting up the development environment, creating a webapp, etc in details.

- For more snippets and understanding on different things that you can do with Red5 pro, check out [the collection of ready to go webapp examples demonstrating different Red5 java APIs on GitHub by Rajdeep Rath](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side)

- Here are some good [webapp examples on using java servlets to implement HTTP hooks on a Red5 pro application](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-misc-examples).

**Design considerations:**

There are different ways to interface communications between systems. The path taken is purely a matter of choice. However, I would recommend always sticking to industry standards for these things.

- Always use standard data formats such as JSON (preferred) or XML for exchanging messages between Red5 pro and the external system.

- Use HTTP for simple requests and Websockets where the real-time flow is needed. For information on implementing WebSocket communication with your webapp checkout [our documentation](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-websocket-examples/application-websocketlistener-demo) and this ready to use webapp example.

- When designing to check for user presence in a chat or conference applications, rely on standard web solutions such as AJAX, etc, instead of using Red5 pro sockets if possible. That will reduce the load on your system. If you must implement user tracking through Red5 pro check out this example on [application callback methods](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-application-examples/application-adapter-demo).

- Java is a compiled language and thus, it requires you to restart the server whenever you make an update to code or configuration. Thus placing most of your business logic on your own application server is preferable compared to putting in their webapp. You can then connect Red5 pro webapp to your application server using HTTP/Websockets. That way whenever you make an update to the business logic you won't need to restart Red5 pro.

##### Custom plugin development

Red5 pro server plugins are your head-less interface into the server. They can do pretty much anything that webapps can do since they have access to all the same resources and configurations across the system. However, plugins have no scope of their own. That means, you do not and cannot connect to a plugin using a client. Additionally, if a plugin needs to expose any HTTP API or WebSocket service, it needs a webapp to hook into. Plugins cannot host servlets on their own.

By design paradigm, as I mentioned earlier, use a plugin when you need to augment the features and behavior of a webapp. Plugins are reusable components, which means that you can target more than one webapp with the same plugin.

Here are a few typical use cases that make a good case for a custom plugin:

- Authenticate connections on your webapp.
- Creating a media file cleaner, that automatically deletes files older than a period of time.
- You have one or more webapps that require some sort of common behavior augmented to them at `runtime`.
- Isolating your code into plugins and attaching them to webapps at runtime, means you can share the plugin with others without sharing your entire webapp.

**Coding References:**

- For custom plugin development check out our [official documentation on Red5 Pro plugins](https://www.red5pro.com/docs/serverside-guide/plugin-development.html). It will guide you through creating your first plugin.

- For more snippets and understanding on different things that you can do with Red5 pro, check out [the collection sample plugins on GitHub by Rajdeep Rath](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-plugin-examples)

#### RPC and Low Level integrations

Sometimes developers need lower-level access to the server side methods for more control and tighter integration. Commonly there can be two ways for two distinct technologies to communicate with each other at a low level. They can either use the runtime to access their classes directly at a low level or they can use a common interface `RPC bridge` over sockets. 

* **Direct access**: Direct access allows a source language to use the interpreter/runtime of the target language to gain direct access to a method and execute it as if it were native of the source language itself. [Pyjnius](https://pyjnius.readthedocs.io/en/stable/) is such an implementation which allows python to use java runtime as a means to access the java classes and methods directly in python projects. There are similar implementations for other languages/technologies as well. The greatest advantage of such access is development speed and flexibility. On the other hand, the disadvantage is that direct access sometimes requires special configurations and dependencies. Sometimes direct access is closely tied to the version of the runtime that it works with. 

* **RPC access**: RPC bridge is a mechanism which is more popular than direct access because of its ease of setup. It does not carry specific dependencies for the mechanism to work properly. An RPC bridge uses language/technology-independent way of communicating such as XML, JSON, etc. In general RPC bridges are preferred over direct access mechanism due to simplicity to set up. Python's [Py4j](https://www.py4j.org/) is such an implementation. It implements its RPC bridge to use an internal port for communication over the socket. RPC bridges translate your method call request into XML/JSON formatted structure and pass it to the target language/technology. The RPC bridge logic on the target decodes the structure to identify the method name, parameters, etc to execute the method and send back a response to the caller.


Some popular examples of low level implementations are:

- [Jython](https://www.jython.org/) or [Py4J](https://www.py4j.org/) for java-python integration
- [Node-Java](https://github.com/joeferner/node-java) for nodejs and Java integration
- [Php-java bridge](http://php-java-bridge.sourceforge.net/pjb/) for PHP and Java integration
- [JNBridge](https://jnbridge.com/) for dot net java integrations

If you do want to make use of such integrations, I would advise going with RPC style integrations instead of runtime based integrations for clarity.

### Autoscale specific server side integration factors and considerations

Server side integrations for autoscale setups is similar to a single server or manual cluster setups with a few differences. In this section, I won't be deep-diving into autoscaling deployments. Rather we will take a look at the implications that the autoscaling deployment has on server side integrations.

#### Design considerations for autoscale oriented server side development

* Never rely on the local file system of the VM to save media or other assets at runtime. VMs are volatile and might get deleted at any time due to scale down, failure, etc.

* Avoid depending on per-node client-server RMI calls and other server side mechanism that cannot be globalized in the cluster. These calls are fine for single-server setups only. For global communication over cluster use shared objects. You can design a cross-cluster communication solution using SharedObjects by combining server side and client side shared objects API. You can also take a look at our documentation on [extending the clustering solution](https://www.red5pro.com/docs/server/clusters.html#extending) and working with [server side sharedobjects](https://red5pro.com/docs/serverside-guide/state-management.html) for guidance.

You can also use cloud-based solutions such as Amazon SNS and google Pub-Sub for communication between nodes.

> Prefer to use cloud offerings such as AWS efs, S3, google filestore, etc to externalize and synchronize data between nodes for media storage and sharing.

#### Deploying Red5 pro plugins in autoscaling environments

Red5 pro plugins in an autoscale environment should be deployed via the Red5 pro image that is used to spin up an autoscale node. At the time of writing this document, we don't support specifying different images for each node type (`origin`, `edge` etc).

Thus you will have the same node image being for every node type, and that means the plugin will be deployed to every node created. If you want to deploy the plugin only specific node types only, then you will need to create your own mechanism for it. Every platform has an option for creating a `startup script`, which is usually a simple bash script that will run when the VM is created.

> The `startup script` option is supported for AWS and Google through Stream Manager's launch configuration format. You can also use the [standard Linux procedures](https://linuxtechlab.com/executing-commands-scripts-at-reboot/) to define a `startup script`, which will be executed when the operating system starts.

The `role` assigned to a node by the stream manager, is usually attached to the VM instance as `metadata` or `tags` (depending on the platform). You can access this data from within the VM using the platform's API for reading metadata. Once you have the `role`, you can use conditional logic in your startup script to configure the plugin differently as needed.

You can also pull in the plugin and deploy it to the node dynamically from a remote site at the time of initialization using the `startup script`. This is a more flexible and advanced way of deploying assets to autoscaling nodes. This way of installing the plugin will help you make updates without having to create a new image every time.

#### Deploying Red5 pro webapps in autoscaling environments

Similar to plugins the primary and recommended method to deploy custom webapps in an autoscale environment is to add them in the node `image`. While plugins may or may not be installed on all the nodes, a webapp has to be present on all node types. This is because of how the clustering system works.

Again similar to plugins, you can also pull in the webapp and deploy it to the node dynamically from a remote site at the time of initialization using the `startup script`. 

Make sure the folder structure of the webapp being deployed is correct as per a Red5 pro webapps. Once you have checked it thoroughly, deploy it to a remote location as a `zip` or `war` file. then configure your script to pull the file to the server and extract it into the webapps folder.

> The `war` file is also an archive. Just rename it to `zip` and extract it.

### Conclusion on server side integration

So with that, we conclude the discussion on server side integrations for Red5 pro. All the points and links shared in the previous sections should be good enough to help you integrate your system with Red5 pro server side without hiccups. If you still have problems and need help, get in touch with me & my peers on [slack](https://red5pro.slack.com).

## Client side integration

Client-side integration is required when you need to interface your customers/clients with the streaming system to be able to consume services. Client integrations requirement is usually more common than server integration requirements for most of the simple use cases.

As we discussed earlier, there are lots of out of the box solutions provided by the Red5 pro platform, that can help build your solution around Red5 pro with little or no server integration. Therefore we are left with the client-side of things which is the main area of focus for most businesses.

### Elements of Red5 Pro client side

#### Client SDKs

The Red5 Pro platform provides you with a [collection of SDKs](https://www.red5pro.com/docs/streaming/) to help you translate your business ideas into streaming interfaces for Android, IOS and Desktop browsers.

**Android SDK**

The [Android SDK](https://www.red5pro.com/docs/streaming/android.html) empowers you to add streaming and real-time data exchange capabilities to your native android app. You can take browse the [Android API documentation](https://www.red5pro.com/docs/static/android-streaming/) to take a peek at the various methods it offers.

**IOS SDK**

The [IOS SDK](https://www.red5pro.com/docs/streaming/ios.html) empowers you to add streaming and real-time data exchange capabilities to your native IOS app. You can take browse the [IOS API documentation](https://www.red5pro.com/docs/static/ios-streaming/) to take a peek at the various methods it offers.

**HTML5 SDK**

The [HTML5 SDK](https://www.red5pro.com/docs/streaming/web.html) allows you to add streaming and real-time data exchange capabilities to your HTML5 applications through Red5 pro's **WebRTC** implementation. You can take browse the [HTML5 API documentation](https://www.red5pro.com/docs/static/html5-streaming/) to take a peek at the various methods it offers.

**It is worth mentioning here that the HTML5 SDK is perfectly compatible with both mobile and desktop browsers.**

So the very first step in client-side integration would be to identify the different platforms that are going to target for clients, and then take a look at the appropriate SDKs.

### Autoscale specific client side integration factors and considerations

If you are using an autoscale setup environment, you approach to develop client-side integration solutions will be different than single-server solutions.

**Always use Stream Manager**

In an autoscale environment, clients need to closely interact with the Stream Manager to receive the host and stream information using the Stream Manager Read Stream API call. Once you receive a successful response with HTTP code `200`, should configure your client to use that node address and stream name to consume services. **Never consume services directly using the Host/IP address, if it was not requested through Stream Manager.**

Stream Manager constantly evaluates the optimal node of a given type. Hence it is always imperative to request the Stream Manager to provide you with an optimal node's address for consuming services.

**Using WebRTC Proxy to Publish/Subscribe**

By web standards, publishers and consumers are required to consume services over a secure connection (SSL enabled). However, since autoscaling spins dynamic nodes, it is not feasible to install the SSL certificate on them and assign a unique hostname. To solve this problem autoscaling provides **WebRTC proxy**, hosted aboard Stream Manager that allows you to publish/subscribe on unsecured nodes using Stream Manager's SSL certificate to prove that the connection **originates** from a secure server.

Thus WebRTC clients have to first request a node using the Stream Manager's API and then connect to the proxy gateway to consume services through the nodes. So when building WebRTC client applications, make sure your code interfaces with the `proxy` in order to consume services from nodes, otherwise, the connection will fail.

Our [HTML5 SDK testbed has examples](https://github.com/red5pro/streaming-html5/tree/master/src/page/sm-test) specifically for working with `Stream Manager proxy`.

### Out of the box features for client-side integration

#### Client Testbeds

To help you with using the SDKs and some out of the box code snippets, we have created something called testbeds. There is a testbed repository on GitHub containing various examples for each of the SDKs that we offer. You can see the testbed as an opportunity to get accustomed to the SDKs and at the same time learn about different things that can be built using the SDKs with Red5 pro. Whenever you need to look up a 'how-to' on client-side integration, the testbed is the first place to get started.

**Android Testbed**

The Android testbed is located at [https://github.com/red5pro/streaming-android](https://github.com/red5pro/streaming-android).

**IOS Testbed**

The IOS testbed is located at [https://github.com/red5pro/streaming-ios](https://github.com/red5pro/streaming-ios).

**HTML5 Testbed**

The HTML5 testbed is located at [https://github.com/red5pro/streaming-html5](https://github.com/red5pro/streaming-html5).

**React Native**

As a bonus we have included react native examples as well, in case you want to integrate Red5 pro streaming into a react native app. The react native examples can be located at [https://github.com/infrared5/react-native-red5pro](https://github.com/infrared5/react-native-red5pro)

### Developing custom features for integration

We have seen the different SDKs that Red5 pro offers for building/integrating clients with Red5 pro. While the SDKs themselves are not Open Source, there are various possibilities to extend the functionality or even develop your feature using the SDKs to help your client code integrate better with Red5 pro.

Most of the custom development requirements are centered around using the SDKs & the testbed examples to build a complete client-side application.

**At the time of writing this article, none of the SDKs is open source. If you need a feature added or a bug to be fixed, please use the Github issue reporting system in the appropriate repository. Additionally, you can get in touch with us via email or slack channel if you need any undocumented information on one of the SDKs.**

#### When and where custom client-side development is required

- You wish to develop your own communication mechanism based on WebSockets or HTTP, other than the ones provided in the SDKs, such as SharedObjects.

- If you have a custom logic/method on the server to be invoked from the client. Example if you need to set/update a property on the client's `IConnection` object on the server side, whenever the client interacts with the application.

There could be many more use cases, but these are good enough to help you understand, when/if you need custom effort on for server side integration. Each of the use cases mentioned above warrants custom development either partially or completely.

Before getting started with client-side development you should ask yourself the same questions that we discussed for server side development :

- What are the requirements for client integration?
- How will the server side interface with the client-side?
- Are the testbed examples enough for developing what you need?
- Do you have a custom webapp or plugin on the server, that your client needs to interface with?
- What are the platforms you wish to target - HTML5/Android/IOS?
  - What type of clients do you expect?
- Do you want an addition to an existing SDK features?

Having thought this over, you should now be ready to dive into some of the technical details of Red5 pro-client-side integrations.

### Common activities of a Red5 pro client application

Based on application use cases and development patterns, there are few easily identifiable activities in Red5 pro client applications. Almost every Red5 pro client application will include one or more of these features directly or indirectly.

Red5 Pro features may make up for a small part of a majority of your application functionality. In this section, we will discuss those features and take a look at how they can be included in our own application.

#### Loading configuration

The first part is to load the Red5 pro related configuration data into your application. The freedom of choosing the design is up to you. You can keep things simple by hardcoding the configuration data in your application code (not recommended) or add it via a configuration file or even have it load from your application server over `HTTPS`.

**While the testbed examples show configurations stored in a particular way such as embedded in code or stored in a config file, you can load your configuration from anywhere as long as you feed it to the SDK elements correctly.**

Mobile SDKs require you to specify your `SDK LICENSE KEY` as well. Take special care as to not expose this to your users. You can normally keep this embedded in the application itself. Use obfuscation to protect the license.

**Generally guidelines for loading configurations:**

- Avoid hardcoding configuration data in your application.
- As much as possible try to keep the configuration parameters stored away from the application.
- For mobile application, obfuscation is very much recommended to protect your license code.
- If you do intend to store the license key in the application, avoid loading it in plain text over the network.
- Opt for a comprehensive, descriptive and easy to parse data format such as JSON to represent your configuration data.

#### Generating stream names

Stream names are the `key` component of a streaming context. Stream Name is used to create and access streams in the system. Thus it is important to pay special attention to them.

- Stream names can be loaded in the application as part of the configuration or separately.

- Stream names can be dynamic or static depending on your application's needs. Using dynamic stream names always ensures better security.

- In simple applications such as two-way chats or conferences, it is simpler to use the unique identifier of the application platform such as the `username` or `userid` to generate the stream name. You can always obfuscate it further by adding salt to it.

- For applications that have an `anonymous user` pattern or for one to many streaming use cases, you can generate unique stream names on each access. It is then the job of the developer to coordinate the stream names between users so that they can find each other.

- Do not generate names starting with a number or that containing only numbers. Stream name should start with alphabets followed by special characters or numbers. Use only `_` or `-` special characters.

#### Configuring clients

Once you have the mechanism to load the configuration in place, the next step is to properly configure your clients for publishing/subscribing.

##### Resolution and framerate

Quality is a subtle combination of an appropriate resolution, framerate, keyframe interval, and bandwidth settings. It a commonly known concept that motion requires bandwidth. So depending on your content you can try higher resolution at the same bandwidth if you know there is little or no motion.

**RTMP Publishers**

If you are using a third party RTMP encoder (non-flash), take a look at our [encoder configuration guide](https://www.red5pro.com/docs/server/rtmppublishers.html) on recommended settings. When using third-party encoders, you can take advantage of their industry-standard presets for recommended resolution and framerate settings.

**Android/IOS Publishers**

For mobile SDK publishers, `854x480` at either `750Kbps` or `1000Kbps` is a standard value. You can experiment around it to find what best suits your application. If you wish to set high-quality resolutions such as `720P` or `1080P`, make sure to test it thoroughly with the intended devices to make sure that the network, as well as the device, can handle it. You can check out the [testbed configuration](https://github.com/red5pro/streaming-android/blob/master/app/src/main/res/raw/tests.xml) to see the different configuration attributes available and sample values.

**HTML5 Publishers**

For HTML5 SDK publishers, the SDK provides a simple way of defining publish settings as a JSON object. Capture dimensions, bandwidth, keyframe interval and framerate the primary parameters for a publisher client. The SDK will use the `mediaConstraints` defined and then cycle through the `gUM` to find an appropriate match that is allowed by the browser. You can check out the [testbed configuration](https://github.com/red5pro/streaming-html5/blob/master/static/script/testbed-config.js) to see the different configuration attributes available and sample values.

##### FrameRate

Framerate is usually the most concealed settings of a streaming application. When you see a video stream you probably cannot make a qualitative or quantitative assumption of the framerate that is used. The human brain can interpret up to 1000fps but it cannot make out any difference above 60fps. TV standards NTSC and PAL are set at 29.27 fps & 25 fps respectively, whereas the webcam stream appears `just fine` at 15 fps. However, the determination of framerate also must consider the bandwidth available. More pictures you want to send over the network, more bandwidth you need.

So the selection of Framerate depends on the type of content being broadcasted, where it will be played back and how much bandwidth you have.

##### KeyFrame Interval

KeyFrame interval dictates how often should the encoder transmit `keyframes`. The keyframe (I-frame) is the full frame of the image in a video. Subsequent frames, the delta frames, only contain the information that has changed. It is all about how video compression works in transmission. We won't be going into the details of it here. But you should just know that in general, `frequent keyframes can improve quality and the initial playback time at the cost of significant bandwidth and CPU`.

##### Bandwidth

If you are using a web-based encoder, you can do a bandwidth test to determine the optimal bandwidth quality settings for the client. We have bandwidth detection examples for each SDK in the respective testbeds. Optionally you may also provide selectable presets of quality settings. Based on the quality settings you can initialize the publisher's camera device for a broadcast.

#### Adding Authentication for clients

Next, depending on your requirements it is both important and prudent to secure your system against unauthorized client access.

Here let me talk about two things - authentication and authorization.

**Authentication**

Authentication implies granting access to your resources. It just simply means you need to prove that you can access the resources. The simplest example is a `login system`. At the topmost level, the form will just validate your access to the system without caring for who you are. In the context of a streaming application associated with Red5 pro, authentication is useful when all clients will have equal rights. Example: for an a/v chat if you assume that everyone can publish and everyone can subscribe and there is no admin then all you care about is authentication.

You can authenticate your clients at your own application level using a remote database or API, or you can even authenticate them using Red5 pro server side code.

**Authorization**

Authorization implies granting role-specific permissions. So your authentication can be augmented with authorization on the server level. This means you want to have separate rights for separate clients. In the context of a streaming application associated with Red5 pro, authorization is necessary to separate client behaviour based on the roles they play in the system.

Since a lot of this (authorization by role) is related to the server side behaviour, you might need to interface client code with some custom server side code.

For implementing security we provide the [Red5 pro simple auth plugin](https://red5pro.com/docs/server/authplugin.html) for the server, which can help you implement authentication or authorization very easily. All of this is provided `out of the box` with simple configuration and comprehensive instructions for client integrations. You can also extend the auth module to have your security system do more.

Our testbed examples contain examples to help you integrate the server side security plugin with your own client. Check out the [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishAuth), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishAuthTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishAuth) examples on how to send authentication parameters from client to server.

> For WebRTC clients in autoscale setups, make sure to use [publish proxy](https://github.com/red5pro/streaming-html5/tree/master/src/page/sm-test/publishStreamManagerProxy) and [subscribe proxy](https://github.com/red5pro/streaming-html5/tree/master/src/page/sm-test/subscribeStreamManagerProxy) examples as a base for building your publisher and subscriber clients respectively.

You can also take a look at the `round-trip` authentication examples using [Red5 pro simple auth plugin](https://www.red5pro.com/docs/server/authplugin.html) for [publisher](https://github.com/red5pro/streaming-html5/tree/master/src/page/sm-test/publishStreamManagerProxyRoundTripAuth) and [subscriber](https://github.com/red5pro/streaming-html5/tree/master/src/page/sm-test/subscribeStreamManagerProxyRoundTripAuth) clients.

**Additional Recommended Reads**

- [Red5 Pro Simple Auth Plugin - Introduction](https://red5pro.com/docs/server/authplugin.html)
- [Red5 Pro Simple Auth Plugin - Walkthrough](https://blog.red5pro.com/simple-authentication-walkthrough/)
- [Red5 Pro Simple Auth Plugin - Documentation](https://github.com/red5pro/red5pro-simple-auth-plugin)

#### Publishing

Once yourself publisher client is configured properly with necessary broadcast settings and authentication parameters as necessary, you can implement the mechanism to initialize your publisher client and start publishing. Depending on your requirement, you may want to publish with different constraints and options. 

> For WebRTC clients in autoscale setups, make sure to use [publish proxy](https://github.com/red5pro/streaming-html5/tree/master/src/page/sm-test/publishStreamManagerProxy) example as a base for building your publisher client and then augment it with additional features.

**Simple Publish**

For simple publishing example take a look at the `Publish` testbed example for [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publish), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/Publish) in the respective testbeds.

**Changing Camera**

In case of multiple camera devices, such as in mobile phones and desktops with more than one USB cameras, you can easily select which device you wish to use for publishing. In some cases, it is possible to even swap devices while publishing.

For  Camera Select and Camera Change examples take a look the following HTML5, Android and IOS testbeds.

* [HTML5 Camera Swap](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishCameraSwap)
* [HTML5 Camera Select](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishCameraSource) 
* [HTML5 live Camera Swap](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishMediaStreamCamera)
* [Android Camera Swap](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishCameraSwapTest)
* [IOS Camera Swap](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/CameraSwap)

**HQ Audio**

If audio quality is of more importance in your application checkout the HQ audio examples for [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishHQAudio), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishHQAudioTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishHQAudio) respectively.

**Custom Video Source**

For mobile devices, if you are looking to use a custom video source instead of the default camera devices, then perhaps the Custom Video Source example for [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishCustomSourceTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishCustomSource) is what you need to take a look at.

#### Subscribing

Similar to the publisher, once yourself subscriber client is configured properly with necessary playback settings and authentication parameters as necessary, you can implement the mechanism to initialize your subscriber client and start subscribing. Depending on your requirement, you may want the subscription with different constraints and options. 

> For WebRTC clients in autoscale setups, make sure to use [subscribe proxy](https://github.com/red5pro/streaming-html5/tree/master/src/page/sm-test/subscribeStreamManagerProxy) example as a base for building your subscriber client and then augment it with additional features.

**Simple Subscribe**

For simple subscribe example take a look at the `Subscribe` testbed example for [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/subscribe), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/Subscribe) in the respective testbeds.

**Audio only subscription**

If the target stream is audio oriented or if you do not want to subscribe to the video in the stream, check out the `Audio only subscription` examples for [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/subscribeVideoMute), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeNoViewTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SubscribeNoView) in the respective testbeds.

**VOD Playback**

Each Red5 Pro SDK also provides the capability to subscribe to a recorded video (video on demand), with the platform's / protocol's limitation under consideration. 

For HTML5 use the [VOD playback](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/playbackVOD) example, whereas for Android and IOS you can follow the standard subscribe examples, with the difference that you specify the `filename with extension` instead of `live stream name`.

For HLS / Mp4 VOD you can also play your media file from a cloud bucket (S3 or GStorage) in the mobile browser of a webview as a standard HTML5 media.

#### Building coordination between publishers and subscribers

Once you have clients configured, the next step is to work on coordination. This will give rise to some fundamental design questions such as:

- How will the client know about other clients in the system
- How can we hide certain clients from other clients in the system
- How do subscribers find publishers

and so on.

Remember that everything in Red5 pro is resources. `Connections`, `streams`, `scopes` and `shared objects` are all nothing but resources. So the problem of finding or not finding clients is actually the problem of being able to control access to these resources at a design level.

For example, if I want to see your stream I need to know the application scope or subscope that you are connected to and the stream name that you are publishing. Thus if you want me to see your stream you should coordinate the application scope/subscope name, path and the stream name that you are publishing.

Resources can be coordinated between the client in different ways:

- `Hardcoded`: When developing applications, sometimes it is simpler to have the resource names embedded in the code for testing. This is the simplest way of coordinating resources. This way is **not** recommended for production use.

- `Polling`: Client polls the database or a remote API endpoint for available resources. When the resource list seems to be populated, the client can pick out the data and access the resource.

**Example**

The publisher publishes a stream to the server application called `live`. A mechanism will detect `publish` event, and register the stream name in the database. Subscriber polls periodically to see if there is a stream name available to subscribe to. When the response contains a stream name entry, the subscriber will connect to the server application `live` and play the stream.

- `Push Data`: Client connects to a remote service or Red5 pro shared objects and awaits a push data containing information about resources. Once data is received, the client will access the resource using the data in the `push notification`.

**Example**

Publisher connects to the server application called `live` and acquires a SharedObject called `users`. the publisher then publishes a stream to the server application. On successful publish acknowledgment from the server, the publisher pushes the stream name into the shared object.Subscriber(s) also follow the same initialization sequence and acquires a SharedObject called `users`. As soon as publisher pushes data into the shared object, the server will push it to all connected clients. The subscribers can then read the stream name from the payload and play the stream. Subscribers that connect late will also receive the pushed data, as soon as they connect to the shared object.

> You can also use any other third-party push notification system such as Amazon SNS etc to relay the information between clients.

#### ABR Subscription

The abbreviation ABR stands for adaptive bitrate streaming. This is a feature used to distribute a stream to multiple clients having different bandwidths efficiently by matching an appropriate version (by quality) of the stream with each client.

To use ABR, you need to first create a [stream provision using the Stream Manager API](https://red5pro.com/docs/autoscale/smapi-streamprovision). `Provisioning` implies, describing the different stream qualities that you want to generate. Once the original stream is published, the transcoder will accordingly generate ABR. Subsequently, when you subscribe to the stream using the [Red5 pro HTML5 SDK](https://github.com/red5pro/streaming-html5), the client will automatically be switched to the appropriate quality based on its bandwidth.

ABR is not supported for single-server installs or even manual multi-server clusters. If you need ABR, you need to use Red5 pro autoscaling. for more information, I recommend reading through the [ABR guide](https://red5pro.com/docs/streaming/abruserguide.html) and the use of [transcode nodes](https://red5pro.com/docs/autoscale/transcoder.html)

> At the time of writing this, ABR is not supported for RTSP clients. Clients can subscribe to the stream quality of their choice manually.

#### Muting Audio

When publishing or subscribing, sometimes you might want to mute the audio of the stream. Typically with online public events, sometimes when there is a break or so, the video is still running and the audio is paused to avoid noise to subscribers. On the other side, a subscriber too might sometimes want to watch just the video.

**Publishers**

To implement audio/video mute/unmute for a publisher client, check out the [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishMute), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishPauseTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishPause) examples respectively.

**Subscribers**

For subscribers, you can implement audio control in two ways. You can either control the volume to implement mute effect or simply use the mute API.

HTML5 clients can invoke [<subcriberImplementation>.mute()](https://www.red5pro.com/docs/static/html5-streaming/RTCSubscriber.html#mute). Although this API is for `RTCSubscriber`, `RTMPSubscriber` and `HLSSubscriber` implement it as well. Essentially this causes a mute on the video element of the page. The server does not stop sending audio.

Android clients can either [adjust the volume](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SubscribeSetVolume) or [entirely mute](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SubscribeMuteAudio) the stream.

similarly, IOS clients can either [adjust the volume](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeSetVolumeTest) or [entirely mute](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeMuteTest) the stream.

#### Adding data capabilities - SharedObjects

In a lot of application use cases, there is a need to work with real-time data. Real-time data can be thought of as chat messages, control messages, shared temporary data stores and so on.

Red5 Pro has multiple ways of transmitting data in real-time, with each having its own usage scenario.

- **Sending data over the stream**: Transmitting data over a stream using the `stream.send` API provided in each SDK.
- **Client to server RPC:** Invoking custom Red5 pro server side methods from the client.
- **Red5 pro SharedObjects:** Using Red5 pro shared object to build interactive two communication between two or more clients.

In this section, we will be discussing [Red5 pro SharedObjects](https://www.red5pro.com/docs/streaming/sharedobject.html) only. You can read the basics of [Red5 pro SharedObjects](https://www.red5pro.com/docs/streaming/sharedobject.html) in our official documentation.

In the context of a Red5 pro application, you use shared objects, when you need to communicate in real-time between clients. Usually shared objects facilitate bidirectional communication, but you can design your application to restrict communication as you see fit.

We offer SharedObjects as part of each SDK and also the server side API. Shared objects can be used with or without streams in general. If you need shared objects support in your application takes a look at following testbed examples:

**HTML5 SDK**

- [SharedObject with publisher](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishSharedObject)

* [SharedObject with subscriber](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/subscribeSharedObject)

* [Standalone SharedObjects](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/sharedObject)

**Android**

- [Standalone SharedObjects](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SharedObjectTest)

**IOS**

- [Standalone SharedObjects](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SharedObject)

**A few good use cases for SharedObjects**

**Communication and state sharing**

If you are designing a video conference, you can use shared objects to share information about the participants such as usernames, stream names, etc. Whenever a client joins the system, the username, stream name, etc are pushed into a commonly shared object associated with the session. As soon as the connecting clients subscribe to the shared object, they get updated about users that are already in the system. Similarly, the existing clients also get notified about the new client.

Through the session, you can also communicate the muted/unmuted state of devices of a client to other clients, if your application warrants of it.

If you are building a game or whiteboard application you can relay/share the state of the game/whiteboard with other clients in real-time using shared objects.

**Tracking stream subscribers in real-time**

You can also use shared objects as a means of sharing arbitrary data in real-time.

Consider that you have a server side application that tracks subscriber count. Now if you want to share that with clients (ex: display active subscriber count on the player), you can have the server side logic push this information into a shared object. On the client-side, you can subscribe to this sharedobject to receive real-time updates of changing subscriber count.

#### Sending messages over the stream

Red5 pro has multiple ways to transmit data in real-time. While SharedObject and RPCs are common to use cases, transmitting data over the stream is yet another possibility.

Sometimes a publisher might want to quickly want to send a text or control message to all client currently subscribed to the publishing stream. This is where you use transmission over the stream channel.

**Use cases**

- Sending control messages to indicate when a publisher is muted/unmuted
- Transmitting stream stats info to subscriber clients

#### Handling interruptions

Unforeseen interruptions are always a part of any activity, and streaming is no different. Connection drops, power interruptions, system crash or even platform induced disturbances are just a few causes why your publish/subscribe session might be lost.

That's where a well-designed application will tend to create a seamless resume mechanism. To assist you to design a graceful resume functionality, we have special examples for each of the SDKs.

There is a subscriber reconnect example in the [HTML5 SDK testbed](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/subscribeReconnect), the [Android testbed](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeReconnectTest) and the [IOS testbed](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SubscribeReconnect).

Publisher interruption can have different reasons and different ways to resume for each reason. Once you identify the reasons you will have a reconnect strategy for each.I recommend getting in touch with us via email or slack channel if you want help on this.

**Mobile device interruptions**

As compared to desktops, the mobile platform is prone to special types of interruptions such as switching between applications, incoming phone call, etc. We have provided some special examples in the respective testbeds to help you build better user experience on mobile devices:

**Background subscribe**

[Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/SubscribeBackgroundTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/SubscribeBackground) examples for subscribing in the background, allowing people to multitask without needing to disconnect from their stream.

**Background publish**

[Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishBackgroundTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishBackground) examples for publishing in the background, allowing people to multitask without needing to disconnect from their stream. And an additional example on [handling telephony interrupts on IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishTelephonyInterrupt)

#### Capturing snapshot

Capturing a snapshot of the live stream and displaying it as the poster image for the live stream on your website is one of the most prominent features in live streaming systems.

At the time of writing this, there is no built-in API/mechanisms to get a snapshot image of the live stream. that said there are two ways to capture an image from a live red5 pro stream.

**Client side:**

On client side create a mechanism to capture the video renderer screenshot, depending on what platform/SDK you are using. Encode the captured data as jpeg/png and then encode it as a base64 string.

You can then send this data to your backend server where a script can base64 decode it and write it to the disk as a jpeg/png. This is the easiest way to capture snapshots of the client.

Additionally to help you our SDKS have an example of image capture for [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishImageCapture), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishImageTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/PublishStreamImage) in their testbeds.

**Server side**

If you want to capture snapshots on the server side directly, you can use FFmpeg to do the same. Check out our [guide on using FFmpeg with live streams](https://www.red5pro.com/docs/server/ffmpegstreaming.html). Check out [this integrated webapp example](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-misc-examples/ffmpeg-thumbnails) to see how to read the stream as soon as `publish` starts.

#### Recording the session

Now irrespective of whether you need a poster image or not, wanting to record the live stream is the most common need for most streaming systems. At the time of writing this guide, Red5 Pro allows you to records a stream in `FLV` and `HLS` containers.

Red5 pro gives you multiple ways to record a stream. You can choose the way that is optimal for your use case. the following options are available to enable recording of a live stream.

1. As a client, if you can direct the server to record the stream by specifying the publish mode as `record` in your client code. We have examples for [HTML5](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/publishRecord), [Android](https://github.com/red5pro/streaming-android/tree/master/app/src/main/java/red5pro/org/testandroidproject/tests/PublishTest) and [IOS](https://github.com/red5pro/streaming-ios/tree/master/R5ProTestbed/Tests/Publish)

For IOS and Android examples, simply change the publish mode to `record` to test the code.

2. To enable automatic recording for all streams, you can set the server side flag `broadcaststream.auto.record=true` in `{RED5_HOME}/conf/red5.properties`.

3. You can also use [Red5 pro server api](https://red5pro.com/docs/server/serverapi.html#recordlivestream) to record your streams. Using an out of the box API can be very useful when you need to initiate recording from a remote location on demand. while options `#1` and `#2` cannot be toggled on the fly without stopping the client or server, API gives you a flexible solution to record/stop without disturbing either.

4. If you are writing your own webapp and want to record a live stream on some internal condition, you can call the `IProStream.saveAs()` method to start recording. Every Red5 pro stream is an `IProStream` object.

By default, the automatic recording captures FSL ad HLS files. If you do not want HLS recordings across the system, you can simply delete the `red5pro-mpegts-plugin-5.5.0.343-RELEASE.jar` file.

#### Storing and Viewing VOD Recordings

By default, recordings are stored in the `streams` directory of the webapp in the context. If you want you can move the recordings to a remote server for storage and later serving.

If you write your own custom solution as a webapp or plugin, you can connect with us on slack for development tips on subjects on working with VOD files.

As an out of the box solution, we offer [Red5 pro cloud storage plugin](https://red5pro.com/docs/server/cloudstoragevod.html), which can help you move your recordings to AWS or Google bucket automatically. It can also help transcode flv to mp4 before uploading.

If you wish to serve all types of clients, make sure you have `MP4` version of the recording alongside `FLV` and `HLS`. You can build any custom security around VOD similar to live streams using the [Red5 pro simple auth plugin](https://red5pro.com/docs/server/authplugin.html).

Check out the following testbed examples to get more info on implementing VOD in your application:

[HTML5 SDK](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/playbackVOD)
[Android](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/playbackVOD)
[IOS](https://github.com/red5pro/streaming-html5/tree/master/src/page/test/playbackVOD)

#### Developing desktop solutions

Going beyond the supported client types, some businesses have a custom requirement to develop streaming applications for the desktop. While we do not have any custom offering desktop solutions separately, it is quite possible to use existing offerings to develop a desktop solution.

* **RTMP Support for desktop solutions**: 

You can add RTMP support for your desktop solution using a third party `RTMP SDK` or the []`Flash player` distributable](https://www.adobe.com/products/flashplayer/distribution.html) solution. 

Flash player redistribution allows you to bundle the flash player DLLs along with your application files and access the player in your application normally. For `DOT NET` development flash player can be accessed as an `ActiveX` or `shockwave flash object` component. For other platforms such as Java, python, etc, you will need a custom RTMP SDK to enable encoding/decoding of streams.

* **RTC Support for desktop solutions**:

For RTC support in your application, the simplest way is to make sure your application can integrate the WebKit engine or more accurately - the chrome engine. That makes development easy as you can leverage the existing HTML5 SDK (Javascript). You can use [electron](https://electronjs.org/) solution for packaging and distributing your application.

For **Dot Net** you can try open-source [CEFSharp](http://cefsharp.github.io/) module which lets you embed chromium through WinForms.

If your development platform is not HTML5 supportive or cannot support WebKit/Chrome then you can also try leveraging **3rd party RTMP SDKs**. Red5 Pro uses open RTMP specifications. Any library based on standard RTMP specifications should, in theory, work well with Red5 pro.

> We do not take responsibility or provide support for 3rd party SDKs or libraries.

## Use cases

### Online astrology consultation system

##### Overview of the system

An online astrology consultation system is centered around a service provider and a service receiver. The service provider, in this case, is an astrologer (one who provides [astrology](https://en.wikipedia.org/wiki/Astrology) related services) and the service receiver is a client interested is receiving astrology related services.

The nature of the business may be commercial or free (which beyond our scope of discussion), but the principles on which it is built will remain the same. The client schedules an appointment with the astrologer through the business application.

Once the appointment is booked (accepted by system), the system will send a mail or notification (on mobile apps), when the time arrives. The astrologer will receive intimation prior to the client since the astrologer is like a `host` of the event.

Astrologer will come online and start publishing. Soon the client joins in and starts publishing own stream and subscribes to the astrologer's stream. A session between the astrologer starts and continues for a period of time. Participants may (optionally) also send text messages to each other.

 The session is terminated by participants or the system as per business requirements.

##### Deciphering the problem and separating concerns

In the stated use case, we have two participants, one of whom is the host and the other is the client. It is important to note that none of them may have too much control over the system. Instead, it is the system that connects, coordinates and disconnects them. This is, therefore, a typical case of two-way communication.

Once the pattern has been identified, the next logical step is to understand and breakdown the requirements into two parts, - The business application requirements and the media server requirements. Separation of concerns is necessary for delegation of the task as well as proper execution of the workflow.

We will not be going deep into `Business application development`, as that is not within our scope of discussion. However, we will be brushing through the overview of the features and functionality that has to be developed in that domain. Our main focus will be the development of the streaming system and interfacing with the business application.

##### Business application development (Non Red5 pro)

**Business application** integration will involve standard application development procedures such as **registration**, **login**, etc. Apart from this, there can also be a database table(s) to track sessions, stream names, stream states, etc. Business application, will also take care of **notifying** participants prior to scheduled event time, **initiating & tracking** the session, **coordinating**  between participants (this functionality can be shared by the business application and the media server) and finally **terminating** session (log out user, etc) when the session is complete / deemed completed.

##### Streaming application development (Red5 pro)

**Streaming application** integration will revolve around developing media server application (if needed) and clients applications that interface with the media server to consume services. A server side application will provide an entry point for clients to connect to the media server and also custom logic as needed for building any communication between clients, real-time coordination, etc.

**Server application development**

For the server side application development our requirements are as follows:

* Allow users to connect and stream live
* Authenticate users
* Track state of client streams (online/offline)
* Allow administration of client sessions by the business application
* Optionally track client session time

Now let us address each of these requirements, using custom development / out-of-the-box options available to us.

1. **Entry point**: Develop a custom Red5 pro web application for the solution, which will be used as an entry point to the media server by the participants. A custom webapp will allow the implementation of custom business logic as and when needed.

> It is important to note that the business application server should assign stream names for each participant in the application and it should be unique across the system.

2. **Authenticate users**: Implement security on the webapp using [Red5 pro simple auth plugin](https://www.red5pro.com/docs/server/authplugin.html)'s [roundtrip validation](https://github.com/red5pro/red5pro-simple-auth-plugin/blob/master/red5proroundtripauthvalidator.md) mechanism.

Both participants will log in into the business application and get assigned a session token. They will then connect to the custom webapp using the token. The webapp will use the auth plugin to authenticate participants.

3. **Tracking the state of clients**: When you have a custom webapp you have more control over the behaviour of the application. In this case, we will be leveraging the Red5 pro [MultiThreadedApplicationAdapter](http://red5.org/javadoc/red5-server/org/red5/server/adapter/MultiThreadedApplicationAdapter.html) callback handlers to add functionality. You can check out [this documentation](https://www.red5pro.com/docs/serverside-guide/streams.html#2-red5-pro-stream-action-callbacks-in-application-adapter) on the different handlers available to a webapp.

For our use case, we are only interested in two handlers - `streamBroadcastStart` and `streamBroadcastClose`.

**Snippet**
```java

	@Override
	public void streamBroadcastStart(IBroadcastStream stream) {
		log.info("streamBroadcastStart {} ", stream);
		super.streamBroadcastStart(stream);
	}

  @Override
	public void streamBroadcastClose(IBroadcastStream stream) {
		log.info("streamBroadcastClose {} ", stream);
		super.streamBroadcastClose(stream);
	}

```

In the above handlers, you can add custom code just before the `super` calls. Here you can add `HTTP calls` to notify the business application server whenever a stream starts or stops. The business application can use this to track stream state (participant's state) and show an offline/online indicator if needed.

4. **Real-time state tracking**: For proper communication and seamless user experience you can also make use of Red5 pro shared objects alongside `HTTP calls`. Shared objects are based on socket connections and will generally be faster and more responsive than using HTTP to track state.

To use this option you must enable your webapp to be able to work with [Red5 pro server side shared objects](https://www.red5pro.com/docs/serverside-guide/state-management.html). The shared object should be initialized on the server side and entirely managed by the webapp code. The clients will merely subscribe to it. 

> In the `Client application development` section, you will see how we can leverage this server side shared object implementation for building coordination between clients.

For an example of using server side shared objects, see [the following code examples](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-sharedobject-examples).

**Designing the server side flow with shared objects**:

* In your custom webapp add mechanism to dynamically **create**, **destroy** and **read** server side shared objects on demand (CRUD). Each shared objects will represent a real-time session between participants.

* Add HTTP API (hooks) to your webapp such that, you can trigger these methods using an HTTP client remotely as an administrator. The simplest way to add HTTP hooks to your webapp is via servlets. Check out [this example](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-misc-examples/http-servlet-demo) of adding HTTP servlets to your webapp.

* When the appointment time arrives, the business application server will use the `create` HTTP hook to request the creation of a server side shared object using a `unique session name`, that identifies the session.

* When participant clients try to connect, they provide a `username/id` and the `session name` in the connection params (along with any other parameters) when connecting to the webapp. 

* In your application adapter's implement (IConnectionListener)(http://red5.org/javadoc/red5-server-common/org/red5/server/api/listeners/class-use/IConnectionListener.html) as shown in [this example](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-connection-examples/connection-listener-demo) to monitor successful connect attempt and store the participant's information (such as username/id etc) into the shared object.

> If shared object by the requested `session name` does not exist reject/close the client's connection.

* When a participant disconnects remove the information of that participant from the shared object. Additionally when the business application server wants it can make a `HTTP call`, to request deletion of the shared object and subsequently terminate the connected clients using the [Red5 pro server api](https://www.red5pro.com/docs/server/serverapi.html).

5. **Administration of client sessions**: Administration of the client sessions will be controlled by the business application server. With regards to the streaming subsystem, the main administration tasks may include getting information about a participant session and terminating sessions.

Both these activities can be done using the [Red5 pro server api](https://www.red5pro.com/docs/server/serverapi.html). To be precise, you can use the [client control api](https://www.red5pro.com/docs/server/serverapi.html#client-control-api) and [stream api](https://www.red5pro.com/docs/server/serverapi.html#streams-api) methods to get information about each participant's connection and stream as well as terminate the participant's session as needed.

6. **Tracking session duration**: To track total time spent by a participant in the media server you will need to make use of a few different things.

First of all, implement a [IConnectionListener](https://github.com/rajdeeprath/red5-development-series/blob/master/code-examples/server-side/red5-connection-examples/connection-listener-demo/src/main/java/org/red5/connection/examples/connectionlistener/ConnectionMonitor.java) in your web application. On successful connect event to capture the timestamp. Finally, when the participant disconnects, capture the timestamp using the `appDisconnect` handler of the MultiThreadedApplicationAdapter.

**Client application development**

Since we know that we are developing a two-way communication, it makes sense to use the Red5 pro `two-way` testbed examples (HTM5/Android/IOS) as a starting point for developing the client application.

For the client side application development our requirements are as follows:

1. **Load configuration**: The first step is client initialization. Irrespective of whether it is a mobile or desktop client, you will need a standard way of loading configuration into the client. A commonly accepted flow for this is to have the user login into the system (business application) and then load the configuration data into the client.

> When a user logs in into the system, they will also receive a `JWT token` that will be used in conjunction with client authentication through the media server. 

2. **Connecting to media server application**: Once the client application has initialized and is ready to roll, you need to connect to the media server with required connection parameters (such as the authentication token) and publish client's stream. Once the server has successfully authenticated the client it will trigger the `streamBroadcastStart` handler on the webapp. 

3. **Coordinating participants**: Building coordination between participants is the most important part of this system. By coordination we mean each participant being aware of the presence and state of the other. Once a participant knows that the other participant is available and publishing, the former can then subscribe to the latter's stream. This coordination can be achieved in two ways:

* `Using the business application server`: As we discussed earlier, about capturing stream state through the webapp into the business application, the captured data is helpful in determining whether a stream is online or offline. Which same as saying whether the participant stream is available or not. The business application server can share this data with the other participant through simple HTTP(s) calls made periodically to get the latest data.

* `Using Red5 pro media server`: Another way to share information about the state of participants is to use [Red5 pro shared objects](https://www.red5pro.com/docs/serverside-guide/state-management.html). Shared objects are perfect for real-time information sharing between connected clients.

> This section is applicable only if you have implemented server side shared objects in your webapp as discussed earlier in the `server application development` section.

Once you have implemented server side shared objects in your webapp, the server will take care of creating and destroying shared objects. The client application will simply need to subscribe to & communicate using the shared object when they are connected to the application.

**Designing the client side flow with shared objects**:

* Depending on your client platform, check out the appropriate SDK testbed example for working on shared objects.

* Each participant client should be provided with the unique `session name` required to connect to the server when the login and load configuration data.

* Client will pass in the `session name` along with `username/id` and any required authentication parameters while connecting to the media server.

* Once successfully connected, the clients will subscribe to the server side shared object identified by the `session name` and listen for shared object updates. 

* Participants can then use the shared object for communicating their presence as well as their devices, publishing state, etc. Whenever an update is made, the shared object will trigger the update listener on the client side. Each client can check against the `user information` data and make sure to only react to changes in other participant's data.

##### System Layers Overview

![Astrology Consultation System Layers](astrology-consultation-system-layers.png "System Layers")

##### Takeaways

* Developing two-way communication applications on Red5 pro are both simple and creative.
* Server side applications can be hooked up to a remote business application server through the `MultiThreadedApplicationAdapter` callback handlers and simple HTTP(s) calls.
* Whenever possible use out of the box offerings such as existing administration APIs.
* HTTP servlets are the simplest way to create new APIs for your webapp and create hooks to work with the application without caring about Java. 
* It is best to authenticate clients outside the webapp (ex: roundtrip authentication). Avoid interacting with the database directly from within a webapp unless absolutely necessary.