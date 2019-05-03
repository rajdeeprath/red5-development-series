# Integrating Red5 Pro with external systems

## Introduction

System integration is the process of aggregating different sub-systems to build a single large system, where every sub-system functions together with other sub-system co-operatively, while operating within its own limited scope, to achieve a larger system.

In the context of Red5 Pro this means, how different businesses, offering different products & services can integrate with Red5 pro services irrespective of their technology and architecture to include video/audio and data services as a part of their system.

While the problem of system integration is not new, some of the integration aspects of Red5 Pro (being a java based stream server) are different then usual web technologies.In this document we will be taking a look at different aspects of integrating Red5 Pro, the challenges to expect, their solutions and few real world integration use cases.

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


## server side integration - examples: php, asp.net, nodejs

Server side integration is required when you need granular control over the streaming sub system at different levels to be able to communicate with it and control it from another sub-system.

**Red5 Pro Webapps**

**Red5 Pro Plugins**

Red5 Pro plugins are the minions of the Red5 pro platform that can help you accomplish almost anything. Plugins unlike webapsp atre headless entities. They can augment any existing webapp with new behaviour, or even implement it across all webapps on the server. You can use plugins to implement isolated features or tap into the existing features provided by the Red5 pro platform.

### Out of the box features for integration

**Ready to use streaming applications**

In general Red5 Pro is a streaming ready server. This means at the simplest, you can point a compatible client to the server and broacdcast/subscribe without having to write any code on the server side. Red5 Pro is distributed with certain pre-built **server applications** that are stream ready in nature. You can use them to stream live content, record as well as playback a recorded media.

<about the live webapp>

**Ready to use plugins**

Red5 pro distribution includes some basic plugins that give you a head start in integrating Red5 pro with your own system. Following plugins are provided out of the box for system integration:

* [Simple Authentication Plugin](https://www.red5pro.com/docs/server/authplugin.html): A basic yet high flexible and extensible authentication plugin that can help you achieve almost any kind of authentication (credentials based or anonymous token based) for clients using your own application server or database server.
* [Cloud Storage Plugin](https://red5pro.com/docs/server/cloudstoragevod.html): A cloud storage solution for Amazon S3. It helps you copy audio/video recordings to your aws bucket.
* [HLS Plugin](https://www.red5pro.com/docs/server/hlsdocs.html): Helps generate HLS files for live and recording sessions. You can use this to control HLS latency or even diable HLS generation completely.

If you might need anything more, such as uploading file to your own application server, notifying your application server whenever a client joins or leaves Red5 pro etc, you can always create your own plugin with your business logic. There is a [detailed documentation on plugin development](https://red5pro.com/docs/serverside-guide/plugin-development.html) on our website.

**Ready to use APIs**

Red5 pro platform provides an [extensive API](https://red5pro.com/docs/server/serverapi.html) to help gather information about the server and its elements. You can query standard statistics on resources such as applications, connections and streams. You can also perform administrative and non-administrative actions on those resources such as disconnecting clients, closing streams, recording live streams etc.

For autoscaling we offer an alternative rich [set of API calls](https://www.red5pro.com/docs/autoscale/streammanagerapi.html) alongside the [standard single instance api calls](https://red5pro.com/docs/server/serverapi.html), to help manage clusters and instances and other autoscaling resources on the cloud platform along with different calls for reading statistics.You can use any technology to interace with these API calls through a simple API consumer client.





## client side integration - desktop, mobile


## use cases


## Tips and tricks

