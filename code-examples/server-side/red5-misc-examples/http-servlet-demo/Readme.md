# Exposing Red5 Pro to http clients - HTTP Servlet Example
---


## About
---



T# Exposing Red5 Pro to http clients - HTTP Servlet Example
---


## About
---



This example demonstrates how you can open a HTTP channel between a red5 server side application and a http client. The application registers a simple [java servlet](https://en.wikipedia.org/wiki/Java_servlet) `SampleHttpSevlet`class on the red5 web application. 

The servlet exposes & activates itself through the `web.xml` file which is located at `RED5_HOME/webapps/{appname}/WEB-INF/web.xml`. If you take a look at the servlet class `SampleHttpSevlet.java`, you will see how the red5 application object is injected into it using `@Autowired` annotation.

We can then invoke custom application methods and standard [MultiThreadedApplicationAdapter](http://red5.org/javadoc/red5-server/org/red5/server/adapter/MultiThreadedApplicationAdapter.html) methods from the servlet itself. The servlet itself is capable to handling web requests from Http clients. Thus we can connect http client to application adapter easily.




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


Once you have the server application running, access the servlet running on server from your browser as:

``` 
http://{host}:5080/http-servlet-demo/endpoint
```

> Where ` {host}`  is where you are running the server. Ex: for local testing it will be `localhost`.


The http call will return the following response from the servlet :


``` 
Application context path = /default
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



hsi example demonstrates how you can open a HTTP channel between a red5 server side application and a http client. The application registers a simple [java servlet](https://en.wikipedia.org/wiki/Java_servlet) `SampleHttpSevlet`class on the red5 web application. 

The servlet exposes & activates itself through the `web.xml` file which is located at `RED5_HOME/webapps/{appname}/WEB-INF/web.xml`. If you take a look at the servlet class `SampleHttpSevlet.java`, you will see how the red5 application object is injected into it using `@Autowired` annotation.

We can then invoke custom application methods and standard [MultiThreadedApplicationAdapter](http://red5.org/javadoc/red5-server/org/red5/server/adapter/MultiThreadedApplicationAdapter.html) methods from the servlet itself. The servlet itself is capable to handling web requests from Http clients. Thus we can connect http client to application adapter easily.




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


Once you have the server application running, access the servlet running on server from your browser as:

``` 
http://{host}:5080/http-servlet-demo/endpoint
```

> Where ` {host}`  is where you are running the server. Ex: for local testing it will be `localhost`.


The http call will return the following response from the servlet :


``` 
Application context path = /default
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





