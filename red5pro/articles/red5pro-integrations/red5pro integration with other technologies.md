# Integrating Red5 Pro with external systems

## introduction to the problem

System integration is the process of aggregating different sub-systems to build a single large system, where every sub-system functions together with other sub-system co-operatively, while operating within its own limited scope, to achieve a larger system.

In the context of Red5 Pro this means, how different businesses, offering different products & services can integrate with Red5 pro services irrespective of their technology and architecture to include video/audio and data services as a part of their system.

While the problem of system integration is not new, some of the integration aspects of Red5 Pro (being a java based stream server) are different then usual web technologies.In this document we will be taking a look at different aspects of integrating Red5 Pro, the challenges to expect, their solutions and few real world integration use cases.

## what is meaning and expectations of integration

Integrating your business system with Red5 Pro is a multi-point process. Every business application (product or service) will usually consist of server and client stacks. Server stack will consist of backend services such as application servers, database services, data processing servers etc. On the other hand the client stack will consist of client applications targetting desktop, mobile devices and other special prospective client types such as IOT devices, IP cameras, Drones etc.

![Integration Stack](images/business-stack.png)

For a successful system integration, it is necessary to identify the different stacks and the content of each of those stacks with respect to your business requirements. 

**Integrating the client stack with Red5 pro implies (but is not limited to) integrating client applications to:**

* Publish/Subscribe using Red5 Pro, by matching appropriate protocol supported by Red5 Pro with the client capabilities.
* Developing any inter-client communication system using the media server
* Triggering methods/mechanisms on server from client side (RPC)

**Similarly the scope of server stack integration covers (but is not limited to), connecting with Red5 pro to:**

* Gather usage  & performance statistics
* Obtain connection & stream information
* Perform server administration tasks
* Implement custom business logic via webapps and plugins
* Pushing notifications from client


## server side integration - examples: php, asp.net, nodejs


## client side integration - desktop, mobile


## use cases


## Tips and tricks

