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

