# Using Ffmpeg to extract thumbnails on server side
---




## About
---



This example demonstrates how to extract thumbnails from live streams using ffmpeg.

The application exposes certain ffmpeg configuration variables as well as the command string used to extract the thumbnails.

Thumbnails are created and stored in the webapp's thumbs directory located at ` {RED5_HOME}/webapps/ffmpeg-thumbnails/thumbs` .

The webapp uses [Apache commons exec](https://commons.apache.org/proper/commons-exec/) to integrate ffmpeg with the java code.


### Configuration properties

Web application's configurable properties can be found in red5-web.properties file located at `{RED5_HOME}/webapps/ffmpeg-thumbnails/WEB-INF/red5-web.properties` .


* `conf.ffmpegPath` : Path to ffmpeg executable

* `conf.protocol` : Protocol used for reading the stream. Defaults to `rtsp`.

* `conf.port` : Port used to read the stream. Must match the protocol being used. Defaults to `8554` 

* `conf.thumbnailExtractCommand` : Fffmpeg image extraction command



## Build & Deploy
---


### Build
---

To build this application : open a shell prompt in the application's project directory (where the pom.xml file resides). run the following maven command in your shell -> 

``` 
mvn clean package 

```

The above command will generate a `war` file in the `target` directory inside the project directory. 


### Deploy
---

To deploy the war to red5 / red5 pro server :

1. Stop server if it is running.

2. Extract the content of the `war file` to directory by war name. 

> The java war file is simply a `archive file` similar to `zip` format. you can extract it using a archive tool such as [7zip](#http://www.7-zip.org/), [Winrar trial](#http://www.rarlab.com/download.htm) etc

3. Copy the folder into `RED5_HOME/webapps/` directory.

4. Start server.


## How To Use Example
---


1. Deploy the application to server

2. Stop the server if it is running

3. Configure the ffmpeg path in the red5-web.properties file as described earlier to point to your ffmpeg installation.

4. Restart server.

5. Once you have the server application running, connect to the web application `ffmpeg-thumbnails`, using RTMP/RTSP/WebRTC client.

6. Publish a live stream to the application

7. Check the thumbs folder at `{RED5_HOME}/webapps/ffmpeg-thumbnails/thumbs`

8. You should see a image file by the stream name created


* For continuous frame extraction use :

```
# Continuous extraction
conf.thumbnailExtractCommand=-y -vf fps=1 %s%d.png
```

* For single extraction use :

```
# Continuous extraction
conf.thumbnailExtractCommand=-y -vframes 1 %s.jpg
```


## Eclipse
---

You can edit the server side code in your eclipse JEE IDE such as Luna, Mars, Neon etc. To import the code into your IDE:

1. Navigate to the repository folder
2. Execute maven command `mvn eclipse:eclipse`. This will generate files necessary for eclipse to read the maven project properly.
3. In eclipse go to `File -> Import -> Existing Maven Projects` and click `Next`.
4. Browse and select `the project root` and Click `Finish` to import the project.



## Additional Notes
---

NA

