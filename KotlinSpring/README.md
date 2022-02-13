## Overview
It'a Spring Boot REST Server's personalized docs and steeps associated.
And also some workflow for the Intellij IDE

### class name refactoring: shift+f6 (twice)
Sometime one class's name change need other related classes to apply that change. So better use IDE to change all that automatically.
> sometimes may need to switch project view to "project files" to change directory name

> for directory and package name "all lower case is a good solution" => com.mainuldip.packagename

### Rendering view:
Views are inside resources. Create index.html inside resources->static dir to attatch first view

### Annotation: @RestController and @RequestMapping
```kt
@RestController
@RequestMapping("api/hello")
class HelloWorldController {

    @GetMapping
    fun helloWorld(): String {
        return "Hello, this is a Rest endpoint"
    }

    @GetMapping("hello")
    //function shorter syntax, if it has one liner return only, no need to put return keyword
    fun helloWorld2(): String = "Hello from function expression body"
}
```

### application.properties
This is the entry point to configure the whole springboot application.
like port, tomcat options, login levels and custom configuration, etc.


### New Package:
In Intellij IDE, package represents directory. So creating a new package will create a folder.

### Generate equal(), hashCode() and toString():
code -> generate or using
Use alt + insert or "shift + alt + enter" to generate necessary class methods using IntelliJ IDE's

> data class ClassName(): same as Record in java15
> Note: kotlin data class automatically generates getter(), setter(), equal(), hashCode and toString()

### Architecture: 
Web Layer(Controllers, REST mapping)
Service Layer(Services, business logic)
Data Source (Data retrieval, storage)
Data Layer (Models, serialization)

> @Repository : this annotation is for repository for retrieving and storing data
> 
> ### creating Test Junit5:
Using IDE: Navigate -> Test || shift + ctrl + t

### Concepts & Terms:
There are some important 
1.Servlet: Servlets are the Java programs that run on the Java-enabled web server or application server. 
They let us handle http request obtained from the webserver, process the request, produce the response, then send a response back to the webserver.
It is more performant than CGI (common gateway interface). CGI server has to create and destroy the process for every request.

2. Application Context:

3. Been & IoC (Inversion of Control):

4. AnnotationConfigApplicationContext:

5: Annotations: these are special type of comments that 
1. Embed instruction for compiler, 
2. Embed instruction for source code processing tools, 
3. Embed metadata which can be read at runtime by java application or third party tools (Spring)
_### Built-in Java Annotationss: @Override, @Deprecated, @SuppressWarnings

_### Custom Annotations:
    _As adding passive documentation for code. The annotation will have no functionality in this case.
    _As input for java source code processor
    _As input for a Java library that accesses the annotations at runtime via Java Reflection.
```java
// Defining custom annotation by @interface
public @interface MyCustomAnnotation {
    
}
```


6. Java Reflection: (like data class in Kotlin)
    _ Java Reflection makes it possible to inspect classes, interfaces, fields and methods at runtime, without knowing the names of the classes, methods etc. at compile time. It is also possible to instantiate new objects, invoke methods and get/set field values using reflection
    - It can be used to work with arrays, annotations, generics and dynamic proxies, and do dynamic class loading and reloading, etc.
```java
// Reflection through direct class
Class dogClass = Dog.class;   
// Reflection through instantiated object
Class dogClass = new Dog().class;

System.out.println(dogClass.getName());
```
    
7. Dependency Injection:

8. Java Singleton
