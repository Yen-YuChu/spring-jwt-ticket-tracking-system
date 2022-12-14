# spring-jwt-ticket-tracking-system
This is base on Spring boot JWT to create a Ticket Tracking System.
Development environment :
 - JDK 1.8
 - IntelliJ (we just use default Maven on it, don't need to install)
 - All you need maven plugin is on 'pom.xml'
 - MySQL 5.7 (I use mysql 5.7, you can upgrade what you want, but remember to update pom.xml dependency)
 
 Install MySQL 5.7, after you install, you need to update the 'application.properties' to config the jdbc url
 because I use docker image to install mysql, so my ip is my local private ip, if you install yours don't using docker, 
 then yours will be 'localhost'.
 
 docker mysql reference: https://hub.docker.com/_/mysql
 ```mysql docker install and create user and database```
 ```docker run --name ruckus-mysql -e MYSQL_ROOT_PASSWORD=welcome1 -d mysql:5.7```

```docker run --name ruckus-mysql --network macvlan --ip 192.168.56.100 -e MYSQL_ROOT_PASSWORD=welcome1 -d mysql:5.7 ```

```docker exec -it ruckus-mysql bash```
```mysql -u root -p```

```create database db_example;```

```create user 'springuser'@'%' identified by 'ThePassword';```

```grant all on db_example.* to 'springuser'@'%';```

```mysql> create database db_example; -- Creates the new database```

```mysql> create user 'springuser'@'%' identified by 'ThePassword'; -- Creates the user```

```mysql> grant all on db_example.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database```

```mysql -u springuser -pThePassword db_example```
 
 After you install the mysql, you need to create your database, then also update the 'application.properties' for all DB config.
 After you can using the created user to connect your database name, e.g. db_example
 You need to do something manually for create table and Role, since I don't make them be automatically.
 I created a file name 'createSchemal.sql' below /resources.
 
 After you 'git clone' my repository and run it on IDE,
 you can execute on IDE Terminal: "./mvnw clean package"
 then you will get the 'jar' file, under 'your project folder/target'
 then run it with java runtime '1.8' with 'java -jar target\ticket-tracking-system-0.0.1-SNAPSHOT.jar'
 
 So for now you can start testing.
 I create an API Document with Swagger, I put a yaml file called 'ticket-tracking-system.yaml' under '/resources'
 You can upload the file to https://editor.swagger.io/
 Then you will see all the apis definition with document.  
 
 And also you can see 'TTS-SequenceDiagram.txt' under '/resources,
 you can pass all the description language to https://sequencediagram.org/
 then you will get the Sequence Diagram for this project.
 
 And also you can see 'plantUML.txt' under '/resources,
 you can pass all the description language to https://plantuml-editor.kkeisuke.com/
 then you will get UML for a system architecture.
 
 This is the document to describe all apis and test cases. https://docs.google.com/document/d/1d1CpeOd9nSkixQXiIn3ANPsO4Vdvy5nss0KEy0AznIY/edit?usp=sharing
 
 
