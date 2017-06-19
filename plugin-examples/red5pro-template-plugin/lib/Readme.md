# Installing Red5pro jar dependency

 
Once you have downloaded the latest red5pro server, extract it to a location on your system with sufficient read/rwrite permissions. The root directory of the server si known as RED5_HOME.
 
Now we will install the Red5pro jar into maven local repository so that it can find the dependency when it tries to compile simple auth plugin. 
 
 
Maven command to install a jar into maven local repo
=======================================================
```
mvn install:install-file
   -Dfile=<path-to-file>
   -DgroupId=<group-id>
   -DartifactId=<artifact-id>
   -Dversion=<version>
   -Dpackaging=<packaging>
   -DgeneratePom=true
 
Where: <path-to-file>  The absolute path to the red5pro jar file located at RED5_HOME/lib/red5pro-x.x.xxxx.jar
  <group-id>      The group that the file should be registered under. e.g: com.red5pro
  <artifact-id>   The artifact name for the file. e.g: red5pro-mega
  <version>       The version of the red5pro jar which should match the specified version in the plugin's pom.xml file. e.g: 3.4.4.1
  <packaging>    The packaging of the file e.g. jar
```
 
Open a shell prompt in the Red5pro simple auth plugin directory and execute the following command. This will register red5pro jar in the local maven repository on the system.
 
## SAMPLE MAVEN COMMAND TO INSTALL RED5PRO JAR (WINDOWS)
 
 ```  
mvn install:install-file  -Dfile=N:\Red5_Pro_Server_Builds\red5pro-server-3.4.4.b161-release\lib\red5pro-3.4.4.1.jar   -DgroupId=com.red5pro   -DartifactId=red5pro-mega   -Dversion=3.4.4.1   -Dpackaging=jar   -DgeneratePom=true
```
 
NOTE: The `-Dversion=3.4.4.1` specifies the dependency version. This must match the version specified in the plugin's pom.xml file and the version tag of the Red5pro jar that you are installing.  
 
You can edit the pom.xml file in a  text editor to look it up. Look for the red5pro-mega `dependency` tag in `pom.xml` file to locate the version number.
 
```
    	<dependency>
            <groupId>com.red5pro</groupId>
            <artifactId>red5pro-mega</artifactId>
            <version>3.4.4.1</version>
			<scope>provided</scope>
        </dependency>
```
 
 
__Example: If your jar version is 3.4.4.1 this should be the version number in your pom.xml and for -Dversion attribute.__ 
 
```
Jar File: N:\Red5_Pro_Server_Builds\red5pro-server-3.4.4.b161-release\lib\red5pro-3.4.4.1.jar
 
red5-mega version in pom.xml: 3.4.4.1
 
-Dversion=3.4.4.1
 
```
 
## SAMPLE MAVEN RESPONSE (WINDOWS)
 
 ```  
[INFO] --- maven-install-plugin:2.4:install-file (default-cli) @ standalone-pom ---
[INFO] Installing N:\Red5_Pro_Server_Builds\red5pro-server-3.4.4.b161-release\lib\red5pro-3.4.4.1.jar to C:\Users\rajde\.m2\repository\com\red5pro\red5pro-mega\3.4.4.1\red5pro-mega-3.4.4.1.jar
[INFO] Installing C:\Users\rajde\AppData\Local\Temp\mvninstall8230960242479609383.pom to C:\Users\rajde\.m2\repository\com\red5pro\red5pro-mega\3.4.4.1\red5pro-mega-3.4.4.1.pom
 ```  