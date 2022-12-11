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
 Then you will see all the apis definition.  
 
 
 
