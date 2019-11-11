# WhiteBoard

## Requirements
+ Apache Maven 3.6.1
+ JAVA 11.0.5

## Installation
```shell script
cd $PROJECT_PATH
mvn -f pom-User.xml install
mvn -f pom-Admin.xml install
```
## Usage
Start Server
```shell script
cd target
java -jar AdminMain.jar -Djava.security.policy=../policy.policy
```

Open another terminal, run Client
```shell script
cd $PROJECT_PATH/target
java -jar UserMain.jar -Djava.security.policy=../policy.policy
```
Don't forget to configure your firewall if you are not running on local.
## GUI

<p align="center">
  <img width="346" height="282" src="https://raw.githubusercontent.com/HappyDS/whiteboard/master/gui/admin.png"> <br>
    <img width="310" height="294" src="https://raw.githubusercontent.com/HappyDS/whiteboard/master/gui/user.png"> <br>
    <img width="640" height="525" src="https://raw.githubusercontent.com/HappyDS/whiteboard/master/gui/whiteboard.png"> <br>
</p>  
