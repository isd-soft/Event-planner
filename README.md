# event-planner
##### Tool that allows creating, organizing and attending Events.
####### Event-planer(back-end part) is a spring boot application. To start it could be used IntelliJ IDE (2019.1.3 version).
####### Also, for storing data in data base need to install MySQL data base server(mysql-installer-community-8.0.16.0).
####### For the convenience of viewing data from the DB was used MySQL Workbench 8.0 CE.
####### In order to avoid bugs in running the application, in application.propertiesin file, should be set next options for DB:
  - spring.datasource.url =jdbc:mysql://localhost:3306/eventsplanner?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull
        where: //localhost:3306  - is DB host
        and : eventsplanner - is DB name.
  - spring.datasource.username=root (default usrer name);
  - spring.datasource.password=free (default password);
  - spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect;
  - jwt.secret=mysecret (default token secret);
  
####### Requests are sent with Postman aplication ( Postman-win64-7.2.2 verssion). (as a requirement, to send requests with Postman, the user should get the token)
