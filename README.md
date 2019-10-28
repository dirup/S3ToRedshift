# S3ToRedshift
This service is responsible for poll of S3 bucket based on naming standard and push the contents to Redshift table. 
This service is also triggered at T+1 hours on a daily basis

This Microservice is developed using Springboot. Current project structure is the general template structure using Springboot.

The main component in this microservice is SpringBootJDBCController. SpringBootJDBCController is used to connect to Redshift using jdbc driver and the data is copied to 
Redshift using Copy command configured in application.properties file. EmailService is used to send the email notification using AWS in case of any exception.

All the configurable properties in this microservices like username, password, bucketname, accesskey, secretkey, copy command and redShiftUrl are maintained in application.properties
file under resources folder, so it is easy to configure them even in runtime and supports loose coupling.


Amazon aws sdk for java is required to connect to S3 using java code and Redshift jar is required to connect to Redshift using jdbc connection.
