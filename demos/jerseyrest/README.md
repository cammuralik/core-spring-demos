Spring Jersey Rest
==================


This application demonstrates using Spring with JSR-311/JAX-RS Jersey.



URL Breakdown
-------------
http://server:port/{jetty-plugin/WebAppConfig/contextPath}/{web.xml/servlet/url-pattern}/{service/@path}

http://server:port/{tomcat-plugin/path}/{web.xml/servlet/url-pattern}/{service/@path}


- Jetty Plugin
  - contextPath: ${project.name)
  - server base path resolves to: /jerseyrest


- Tomcat 7 Plugin
  - path: default ${project.name)
  - server base path resolves to: /jerseyrest



RESTful Base URL
----------------

http://localhost:8080/jerseyrest/ws/rest



Application Base URL
--------------------

http://localhost:8080/jerseyrest/app




Notes
-----

- Using EclipseLink MOXy





