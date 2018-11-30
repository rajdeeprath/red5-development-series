# Using ffprobe to get media info on server side
---


## About
---

This example demonstrates how to extract information from a flv/mp4 file using ffprobe.The only configuration requirement application requires you to point the path of ffprobe on the system. 

Media file samples are provided along with the web application to be used with ffprobe, located at ` {RED5_HOME}/webapps/ffmpeg-mediainfo/streams/` .

The webapp uses [Apache commons exec](https://commons.apache.org/proper/commons-exec/) to integrate ffmpeg/ffprobe with the java code.


### Configuration properties

Web application's configurable properties can be found in red5-web.properties file located at `{RED5_HOME}/webapps/ffmpeg-thumbnails/WEB-INF/red5-web.properties` .

* `conf.ffmpegPath` : Path to ffprobe executable. Do not use path to `ffmpeg`!


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

3. Make sure the `streams` directory and the media files are present in the extracted content.

4. Copy the folder into `RED5_HOME/webapps/` directory.

5. Start server.


## How To Use Example
---


1. Deploy the application to server

2. Stop the server if it is running

3. Configure the `ffprobe` path in the red5-web.properties file as described earlier to point to your ffprobe binary .

4. Restart server.

5. Once you have the server started, the webapp will automatically run its `appStart` method and thereby print the information about the sample flv file located in the streams directory.

6. You can put in your own flv/mp4 in the streams directory and update the code to look for yuor file instead of the samples.

> This code is intended to be used asa guidance only.


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

