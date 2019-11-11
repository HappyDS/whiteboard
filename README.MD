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
  <img width="460" height="300" src="[admin-link]">
</p>  <br>
[![user][user-link]][user-link] <br>
[![whiteboard][whiteboard-link]][whiteboard-link] <br>


[admin-link]:https://raw.githubusercontent.com/HappyDS/whiteboard/master/gui/admin.png
[user-link]:https://raw.githubusercontent.com/HappyDS/whiteboard/master/gui/user.png
[whiteboard-link]:https://raw.githubusercontent.com/HappyDS/whiteboard/master/gui/whiteboard.png