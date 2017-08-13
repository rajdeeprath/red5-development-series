# red5-pro-utils

__red5-pro-utils__ is a red5 / red5pro helper library designed to provide quick solutions around common Red5 api problems that are not currently available via the official java api directly.

The project is open to suggestions and requests that can be accommodated to help speed up the development process for Red5 developers. This is a maven project which can be compiled locally to a jar file for portability. The __red5-pro-utils__ classes can be used with both `web applications` and `plugins`.

---
> NOTE: To use this library you need to build this project and the output file - `red5-pro-utils.jar` file should be placed in the `RED5_HOME/lib` directory.
---



## Compilation
---

Prerequisites
 * [Maven 3+](http://maven.apache.org/download.cgi)
 * [Eclipse Mars](http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/mars2)
 
 
 
## Packaging
---

Packaging the artifact follows the ```<packaging>``` hint in the pom, normally this is set as _jar_ for Java Archive. To compile and package the project as a jar run the following maven command from within the project directory:

```sh
mvn clean package
```

Once you have obtained the final __red5-pro-utils.jar__  file, make sure to copy it into the Red pro lib directory - `RED5_HOME/lib`. 


## Using the jar in Eclipse
---

> This section is only required if you with to use classes from __red5-pro-utils.jar__ in your own code. If you are usign a java bean implementation fo a class you may not require this step.


To import the __red5-pro-utils.jar__  file in your eclipse IDE do the following steps:

1. In your eclipse IDE workspace, select the project, right click it and select `properties`.

2. Navigate to  `Java Build Path -> Libraries` and click on `Add External Jars` option. 

3. Browse and  selct the jar file to include it in your project. You can then start using thr classes in your project. Eclipse will now be able to find and reference classes from the __red5-pro-utils.jar__ file.

> Note: The library wont be exported automatically with your project. When you deploy your application/plugin, ensure to deploy (copy) the __red5-pro-utils.jar__ file into `RED5_HOME/lib` prior to deploying your code.



## Supported features / utilities
---


### CustomFilenameGenerator

The `CustomFilenameGenerator` class allows you to redefine the path for storage and playback of your Red5 supported media files. By default the media files are required to be in a directory called `streams`. To override this you need to implement your own `IStreamFilenameGenerator` as instructed here: [https://github.com/Red5/red5-server/wiki/Customize-Stream-Path](https://github.com/Red5/red5-server/wiki/Customize-Stream-Path).


The `CustomFilenameGenerator` implementation in __red5-pro-utils__ jar allows you to use the feature without having to write/compile anything. To use the `CustomFilenameGenerator` implementation, add the following bean to the  red5-web.xml file of your red5 web application. For example to change the recording and playback paths of the `live` web application, you need to add the `bean` to `RED5_HOME/webapps/live/WEB-INF/red5-web.xml`.



__For relative paths__

```xml
	<bean id="streamFilenameGenerator" class="com.red5pro.server.api.superutils.CustomFilenameGenerator"> 
   		<property name="recordPath" value="recordedStreams/" /> 
   		<property name="playbackPath" value="recordedStreams/" /> 
   		<property name="absolutePath" value="false" /> 
	</bean>
```


__For absolute paths__

```xml
	<bean id="streamFilenameGenerator" class="com.red5pro.server.api.superutils.CustomFilenameGenerator"> 
   		<property name="recordPath" value="/path/to/recordedStreams/" /> 
   		<property name="playbackPath" value="/path/to/videoStreams/" /> 
   		<property name="absolutePath" value="true" /> 
	</bean>
```

> Combination of relative and absolute paths is currently not supported.
> Any changes made to any configuration file demands a restart of the Red5 / Red5 pro media server.



### ConnectionUtils


The `ConnectionUtils` class provides a collections of utility methods which can be used to get useful information about the Red5 `IConnection` object.



__Few Sample Methods__


* `public static String getConnectionType(IConnection connection)` : Accepts a `IConnection` object and returns a String indicating the connection's type. ie: (`rtmp`. `rtsp`, `rtc`) 

* `public static boolean isRTSP(IConnection connection)` : Accepts a `IConnection` object checks if the connection is a RTSP connection or not.

> For more information all the methods it is recommended you make use of the library yourself in your Red5 project.



