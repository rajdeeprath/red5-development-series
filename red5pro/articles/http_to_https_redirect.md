# HTTP to HTTPS redirect

## Introduction

A lot of times you might have configured a Red5 Pro standalone instance or an autoscaling setup involving Stream Manager on different cloud and managed platforms. A common but minor problem to run into is that the Red5 Pro HTTP server runs on port `5080` whereas an SSL enabled server can only be accessed over port `443`.

The HTTP port might still need to be open for various reasons such as testing, cloud health checks etc. Here in sometimes, you might run into a case where the user accesses your the client application on Red5 Pro over HTTP instead of HTTPS, resulting in WebRTC connection being rejected due to `insecure` WebSockets.

## Creating a Redirect Rule

If your HTML client app is hosted on a different server you will need to create an HTTP to HTTPS redirect rule accordingly. However, if your client application is hosted on the Red5 Pro server you need to use [tuckey URL rewriter](http://tuckey.org/urlrewrite/) to activate redirection.

To enable HTTP to HTTPS Redirection:

1. Navigate to [http://tuckey.org/urlrewrite/](http://tuckey.org/urlrewrite/) and download the urlrewrite jar file
2. Copy the jar file into your Red5 lib directory **{RED5_HOME}/lib**. This will make the classes available across the server.
3. Add the following `filter` to the web app's `web.xml` file.

```xml

<filter>
    <filter-name>UrlRewriteFilter</filter-name>
    <filter-class>org.tuckey.web.filters.urlrewrite.UrlRewriteFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>UrlRewriteFilter</filter-name>
    <url-pattern>/*</url-pattern>
    <dispatcher>REQUEST</dispatcher>
    <dispatcher>FORWARD</dispatcher>
</filter-mapping>

```

4. Add a copy of the [urlrewrite.xml](http://cdn.rawgit.com/paultuckey/urlrewritefilter/master/src/doc/manual/4.0/urlrewrite.xml) file in the **WEB-INF** folder of the web app with the following rule.

```xml

<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE urlrewrite PUBLIC "-//tuckey.org//DTD UrlRewrite 4.0//EN"
        "http://www.tuckey.org/res/dtds/urlrewrite4.0.dtd">

<!--

    Configuration file for UrlRewriteFilter
    http://www.tuckey.org/urlrewrite/

-->
<urlrewrite>

  <rule match-type="regex">
    <condition operator="notequal" name="x-forwarded-proto">https</condition>
    <condition type="scheme" operator="notequal">https</condition>
    <from>^.*$</from>
    <to last="true" qsappend="true" type="permanent-redirect">https://%{server-name}%{request-uri}</to>
  </rule>  

</urlrewrite>


```

**Note: you cannot enable redirection by adding the configuration to the `root` web app alone. the same configuration needs to be added to every webapp in the `webapps` folder on which want to enable redirection on access. For example if you need redirection on the webrtcexamples web app, you need to add the configuration changed to it explicitly**

5. Save changes and restart server.

6. Now if you navigate to `http://host:5080` you will be redirected to `https://host`.

## Futher interests

There are a whole range of things you can do with tuckey urlrewriter such as redirect, shortening url, controlling access and more.

We encourage you to checkout the following reference urls for more information:

* [http://tuckey.org/urlrewrite/](http://tuckey.org/urlrewrite/)
* [http://tuckey.org/urlrewrite/manual/3.0/guide.html](http://tuckey.org/urlrewrite/manual/3.0/guide.html)
* [http://tuckey.org/urlrewrite/manual/3.0/](http://tuckey.org/urlrewrite/manual/3.0/)