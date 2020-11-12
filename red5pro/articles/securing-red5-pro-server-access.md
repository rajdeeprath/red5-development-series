# Red5 Pro Security Perspectives



Red5 Pro consists of two major components - Tomcat web server which does all the standard webserver activities and media server codebase that enabled streaming. As the server loads, the tomcat webserver cxomponent starts up, loading and activating the media server modules alongside. both componenst are tightly coupled to each other and therefore one or the other cannot be disabled/removed from the system.To secure the system therefore you need to look at it as a composition of the two components and focus on securing each component individually by its scope of operations. 


## The media server perspective
---


1. Protect the core media server services using simple auth plugin (https://github.com/red5pro/red5pro-simple-auth-plugin). This will ensure that only clients that are supposed to be able to use the services can use them. You will be passing in a token along with username password, that can be validated at a remote server over HTTPS. This will assure a validation of clients connecting over 1935 (RTMP) and 8554 (RTSP). 

If you need a different kind of security logic such as capturing client IP, issuing time limited tokens etc, you can extend the simple auth plugin to meet any of your security requiremenmts. and it does not end here. If you feel that extending the simple auth plugin wont be enough, you can write your own webapp or security plugin as per your needs for maximum customization options.

Also see: 

* [The Red5 Pro Simple Auth Plugin Overview](https://www.red5pro.com/docs/plugins/authplugin/overview/ )
* [Red5 Pro Simple Auth Plugin - Roundtrip authentication implementation](https://www.red5pro.com/docs/plugins/round-trip-auth/enabling-security/)
* [Developing custom Red5 Pro plugins](https://www.red5pro.com/docs/server/serverside-guides/server-plugin-development/)


2. Red5 Pro uses UDP for actual transmission of stream data between a client and a server. As of the latest known version of Red5 Pro, the UDP port range is defined in the {RED5_HOME}/conf/webrtc-plugin.properties:

```
# Port range for UDP and TCP

port.min=49152
port.max=65535
```

The server will bind to all ports in this port range randomly. A lot of ports are not reclaimed immediately after a client session ends. Therefore you should not try to limit the range even if you know that you will be receiving relatively low traffic. At the moment there are no other specific instructions available with us on the topic of UDP security



## The web server perspective
---


For web services offered over 443(HTTPS) or 5080(HTTP), the security approach is not media server centric but more like general java JEE web server centric. Following are some of the common topics/strategies for securing a Java JEE server that are also applicable to Red5 Pro.


1. **Publically accessible endpoints** : Remove the webapps that you dont need. This will reduce the possible urls that can be reached on the server publically. Depending on whether you are using autoscaling or a standalone setup, [you can get rid of the unnecessary webapps](https://www.red5pro.com/docs/server/installation/optimizing/#removing-unnecessary-plugins-and-webapps). For the root webapp and the webapp that you are using (that cannot be removed), delete unnecessary JSP files. They are just there to render web pages. If you have your own HTML application deployed elsewhere, you wont need these JSP files. The best part is tomcat does not enable directory browsing by default. So if you delete the jsp files, there isnt much one can see visiting your server url other than tomcat 404 messages.


2. **API Authentication** : All APIs in Red5 Pro (StreamManager API and Red5 Pro server API) implement some sort of basic security, which is usually good enough for normal usage. But you can still extend it by creating your own authentication scheme/mechanism in java and adding to the webapp through the WEB-INF/web.xml of the webapp as a security filter, such that every visit to the API path will trigger your custom authentication code.  Using a custom logic you can implement any kind of security requirement easily. To get a better idea of using servlet filters for security please the folowing articles:

 * [Oracle docs on authentication filter](https://docs.oracle.com/cd/E17904_01/web.1111/e13718/servlet.htm#DEVSP519)
 * [Javatpoint authentication filter tutorial](https://www.javatpoint.com/authentication-filter.)


3. **CORS Vunerability** : Cross-origin resource sharing (CORS) is a browser mechanism which enables controlled access to resources located outside of a given domain. By default browsers follow same origin policy, thereby  limiting the ability for a website to interact with resources outside of the source domain. The CORS policy is flexible and can be extended through proper use of browser headers which also makes it a security vunerability. 

When you are developing on Red5 Pro, sometimes you might need to develop client applications on your desktop machine while Red5 Pro runs on a remote server. In these cases you might sometimes need to access Red5 Pro APIs (Stream Manager API and Red5 Pro API) and thats when you will notice that API calls from your custom program are blocked. This is because of the cross origin policy. This can also happen when you have a application server on a different domain making API calls to a Red5 Pro instance that is running on a different domain.

On Red5 Pro CORS settings can be edited in the webapp's web.xml file, which is located at webapps/{webappname}/WEB-INF/web.xml. Not only can you add domians and ports but can also control what HTTP methods are allowed. Incorrect CORS settings can therefore expose your APIs to anyone. For instructiosn on managing CORS settings please check out the links referenced below.

* [Stream Manager CORS settings](https://www.red5pro.com/docs/autoscale/stream-manager-cors/solution/)
* [Generic Webapp CORS settings](https://red5pro.zendesk.com/hc/en-us/articles/115000260768-Enabling-Cross-Origin-Resource-Sharing-CORS)


4. **HTTP basic authentication for Webserver security** : One of the simplest techniques used to prevent HTTP/HTTPS access to a web server can is the implementation of HTTP basic authentication (https://en.wikipedia.org/wiki/Basic_access_authentication).

HTTP basic authentication can be a great way in tomcat to restrict access to specific webapps which do not provide WebRTC streaming services. For example the Red5 Pro server API (api webapp) that acts as a standalone API service can take advantage of HTTP basic authentication and have an extra layer of security added to itelf.

However if you are using WebRTC streaming in your webapp, you should not enable HTTP basic authentication for it. This is because Red5 Pro  WebRTC uses WebSockets for signalling and WebSockets connections are an upgrade of the standard HTTP connections. So enabling HTTP basic authentication will block WebSockets on Red5 Pro and the Red5 Pro HTML5 SDK wont know why it is unable to connect to the server. To know more about HTTP basic authentication in general and how to implement it on Red5 Pro webapps see the links below:


* [Tomcat Realm Configuration](https://tomcat.apache.org/tomcat-7.0-doc/realm-howto.html)
* [How to Password Protect Red5 Pro Apps Using Simple HTTP Basic Realm Authentication](https://red5pro.zendesk.com/hc/en-us/articles/217073838-How-to-Password-Protect-Red5-Pro-Apps-Using-Simple-HTTP-Basic-Realm-Authentication.)


5. **URL rewriting** : URL rewriting is an effective way of preventing various types of website attacks such as XSS, URL injection etc.You can also use URLRewrite to dynamically add parameters to URLs, create temprorary urls and so on. The possibilities are endless and this article is by no means a URL Rewriting guide. URL rewriting can definitely provide an extra shield against threats, but always be sure to identify and resolve the underlying root causes. To implement URL rewrite in a tomcat based server you cna make use of tuckey's [URLRewrite filter](https://tuckey.org/urlrewrite/).


Apart from the above mentioned points, it is noteworthy that standard linux adminsitration practices for securing webservers can also be very effective in adding another front of defence to your Red5 Pro deployment.
