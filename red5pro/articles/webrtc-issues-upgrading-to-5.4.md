# Red5 Pro 5.4 Upgrade Trouble shooter

## Introduction

The latest version of Red5 Pro (5.4) brings a major change to the Red5 Pro ecosystem. The websocket servic enow flows through the standard http and https ports. There fore there is no neeed to have seperate ports - 8081 and 8083 opened for the same. This also means that custom websocket or webrtc applicatiosn previosuly built will break on the new server.This little guide aims to help you understand the changes and adapt your custom webapp accordingly.

## What has changed

* Websockets now use HTTP and HTTPs ports instead of seperate WS and WSS ports in prior versions
* The websocket plugin used in previosu versiosn is removed and we have switched to a more standardized `tomcatplugin`. Reference: [https://mvnrepository.com/artifact/org.red5/tomcatplugin](https://mvnrepository.com/artifact/org.red5/tomcatplugin)
* A special websocket filteer needs to be included in the webapps that need to support WebRTC or even plain WebSockets.

## Troubleshooter flow chart

![New websockets trouble shooter](images/5-4-server-upgrade-troubleshooter.png)

## What You should not do

* Upgrade your server as with previous versions carrying over som econfiguratgion files from old server into new

## What You need to do

* Configure the `red5.properties` and `jee-container.xml` files for SSL fresh from the 5.4 build
* Dont put your old webapp into new server without making it compatible with new websockets implementation
* In your firewall settings, make sure `http port` - `5080` and `https port` - `443` are allowed.
* Take a look at this [github sample application](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-application-examples/simple-webrtc-streamer) which is compatible with both open source Red5 (for Websockets) and Red5 Pro (for Websockets / WebRTC)