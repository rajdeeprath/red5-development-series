# Connection Messaging Demo
---


## About
---

This example demonstrates communication between server side code and client side. You can see hwo to cal lsever methods from client with/without parameters and how to call a client side method from server code.



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

Once the server app is deployed, navigate to `http://localhost:5080/connection-messaging-demo` in the browser and you will see a flash client interface.  

* Press `CONNECT` to establish connection with server.
* Press the Buttons provided in the interface to run that test.
* Observe logging on server side as well as the console on the client side for more information on flow of events


## Notes
---

NA
