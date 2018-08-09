### Spring Boot Request & Response Logger

Spring Boot package for logging any incoming requests and outgoing responses

It can used by just adding dependency to project's pom.xml. It has a default logging handler which is logging URL, request, response and status code to console. You can also implement your own Handler interface instead of using default.

pom.xml code (replace "latest version" with project version (e.g. 1.0)):

```
<dependencies>
    ..............
    <!-- request response logger !-->
    <dependency>
	<groupId>com.github.metglobal-compass</groupId>
	<artifactId>sboot-request-response-logger</artifactId>
	<version>"latest version"</version>
    </dependency>
    ..............
</dependencies>
```

After adding that, run `` mvn install`` to import dependency. And define bean in configuration as:

```
@Bean
public LoggingFilter loggingFilter() {
    return new LoggingFilter();
}
```

When any request sent to a URL, it automatically logs request and response to console by default log handler. If you want to implement your own log handler instead of default, you should define bean like this:

```
@Bean
public LoggingFilter loggingFilter() {
    return new LoggingFilter(new LogHandlerImpl());
}
```

