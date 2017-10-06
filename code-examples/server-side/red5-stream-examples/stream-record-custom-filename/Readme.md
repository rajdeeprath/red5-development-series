# IStreamFilenameGenerator example : Custom record / play locations and custom filenames
---


## About
---

This server side application demonstrates the use of [IStreamFilenameGenerator](http://red5.org/javadoc/red5-server-common/org/red5/server/api/stream/IStreamFilenameGenerator.html) intercface in Red5 for changing playback / recording locations and changing recording filenames on the fly. 

The [IStreamFilenameGenerator](http://red5.org/javadoc/red5-server-common/org/red5/server/api/stream/IStreamFilenameGenerator.html) interface implements two methods :

```
public String generateFilename(IScope scope, String name, GenerationType type)
```

and

```
public String generateFilename(IScope scope, String name, String extension, GenerationType type) 

```

Which return a media file's path along with the media name. For recording, Red5 uses the response String as record name and for playback it is used as a playback file name. The interface implements a additional method called [resolvesToAbsolutePath](http://red5.org/javadoc/red5-server-common/org/red5/server/api/stream/IStreamFilenameGenerator.html#resolvesToAbsolutePath--). The method returns a boolean value, indicating whether the string being returned is a absolute or a relative path.Relative media paths are looked up relative to the scope path, whereas absolute media paths are resolved in a absolute manner.

This example has two implementatiosn of the [IStreamFilenameGenerator](http://red5.org/javadoc/red5-server-common/org/red5/server/api/stream/IStreamFilenameGenerator.html) interface. The `CustomFileNames` class and the `CustomLocations` class.


* `CustomFileNames` : Demonstrates how to change a record filename on the fly for an application.
* `CustomLocations` : Demonstrates how to change the default record/playback paths for an application.


Both implementations are integrated into the application via `red5-web.xml` file of the application as shown below.

```
<!-- Custom directory locator for playback and recording -->
	<!--
	<bean id="streamFilenameGenerator" class="org.red5.streams.examples.recordname.CustomLocations"> 
   		<property name="recordPath" value="recordedStreams/" /> 
   		<property name="playbackPath" value="recordedStreams/" /> 
   		<property name="absolutePath" value="false" /> 
	</bean>
	-->
	
	
	<!-- Simple hash filename generator -->
	<bean id="streamFilenameGenerator" class="org.red5.streams.examples.recordname.CustomFileNames"></bean>
```

Be default the application is set to use the `CustomFileNames` implementation. To try out the `CustomLocations` implementation, comment out the second java Bean (Simple hash filename generator) and remove the commenst from the first bean (Custom directory locator for playback and recording).


#### NOTE ON CustomLocations Implementation


The `CustomLocations` implementation of [IStreamFilenameGenerator](http://red5.org/javadoc/red5-server-common/org/red5/server/api/stream/IStreamFilenameGenerator.html) interface allows you to configure a different path for playback and recording of media files. The default location is the 'streams' directory which relative to a Red5 web application.

Given below are the different bean configurations that can be used to configure the `CustomLocations` class to use relative/absolute path mode.

__For relative paths__

```xml
	<bean id="streamFilenameGenerator" class="org.red5.streams.examples.recordname.CustomLocations"> 
   		<property name="recordPath" value="recordedStreams/" /> 
   		<property name="playbackPath" value="recordedStreams/" /> 
   		<property name="absolutePath" value="false" /> 
	</bean>
```


__For absolute paths__

```xml
	<bean id="streamFilenameGenerator" class="org.red5.streams.examples.recordname.CustomLocations"> 
   		<property name="recordPath" value="/path/to/recordedStreams/" /> 
   		<property name="playbackPath" value="/path/to/videoStreams/" /> 
   		<property name="absolutePath" value="true" /> 
	</bean>
```

> Combination of relative and absolute paths is currently not supported.
> Any changes made to any configuration file demands a restart of the Red5 / Red5 pro media server.


A logical place to start recording is the `streamBroadcastStart` callback provided by the [MultiThreadedApplicationAdapter](http://red5.org/javadoc/red5-server/org/red5/server/adapter/MultiThreadedApplicationAdapter.html).


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

To deploy the war to red5 pro server :

1. Stop server if it is running.

2. Extract the content of the `war file` to directory by war name. 

> The java war file is simply a `archive file` similar to `zip` format. you can extract it using a archive tool such as [7zip](#http://www.7-zip.org/), [Winrar trial](#http://www.rarlab.com/download.htm) etc

3. Copy the folder into `RED5_HOME/webapps/` directory.

4. Start server.



## How To Use Example
---

To try out this example, you need to deploy the application `stream-record-custom-filename` to server, connect to it using RTMP/RTSP/WebRTC client and start publishing a stream in 'record' mode. 



## Eclipse
---

You can edit the server side code in your eclipse JEE IDE such as Luna, Mars, Neon etc. To import the code into your IDE:

1. Navigate to the repository folder
2. Execute maven command `mvn eclipse:eclipse`. This will generate files necessary for eclipse to read the maven project properly.
3. In eclipse go to `File -> Import -> Existing Maven Projects` and click `Next`.
4. Browse and select `the project root` and Click `Finish` to import the project.



## Additional Notes
---


Although the recording stops automatically when you unpublish the stream, you can also make use of a RMI call or a HTTP servlet based call to the application and program it to stop recording on demand. For more information on these subjects take a look at the [connection-messaging-demo](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-connection-examples/connection-messaging-demo) and [http-servlet-demo](https://github.com/rajdeeprath/red5-development-series/tree/master/code-examples/server-side/red5-misc-examples/http-servlet-demo) examples.



