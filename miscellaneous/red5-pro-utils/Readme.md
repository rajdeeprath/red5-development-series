# red5-pro-utils

__red5-pro-utils__ is a red5 / red5pro helper library designed to provide quick solutions around common Red5 api problems that are not currently availabel via the official java api directly.

The project is open to sugegstions and requests that can be accomodated to help speed up the development process for Red5 developers. This is a maven project which can be compiled locally to a jar file for portability. The __red5-pro-utils__ classes can be used with both `web applications` and `plugins`.

---
> NOTE: To use this library you ned to build this project and the output file - `red5-pro-utils.jar` file should be placed in the `RED5_HOME/lib` directory.
---



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

> For more information all the methods itis recommended you make use of the library yourself in your Red5 project.
