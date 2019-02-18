# azure-functions-kotlin
experimental

## create function app (node.js)

Why node.js ? 

Because: 
- simple 
- straight forward
- works


```
    $ make help
    $ make fn.create-app.nodejs APP_NAME="example-nodejs"
    $ make fn.app.start APP_NAME=example-nodejs
    
    $ curl -v http://localhost:7071/api/Func001?name=fooo
    $ curl -v http://localhost:7071/api/Func002?name=fooo
    
    response: 200 - Hello foo
```

## create function app (kotlin)

### related vendor projects

- the lib: https://github.com/Azure/azure-functions-java-library
- the runtime: https://github.com/Azure/azure-functions-java-worker
- example: https://github.com/Azure/azure-functions-host/blob/dev/sample/Java/HttpTrigger/function.json

### quick start

```
    $ make -C example-kotlin help    
    $ make -C example-kotlin up
    
    $ curl http://localhost:7071/api/ping
    => response: 200 - Hello from ping! 2019-02-17T08:53:45.683Z
    
    Finally, it works :)
    took me just 1 weekend to make a simple "hello world" work ;)
    
    $ curl -v POST http://localhost:7071/api/run?name=123
    ==> response: 200 -  Hello, 123
```

### the trick

Azure documentation lacks some essentials. 

Here they are ...

1. use the "right" lib from maven central

```
    compile("com.microsoft.azure.functions:azure-functions-java-library:1.2.2")
```

```
// WARNING! DO NOT USE THIS ...
// compile("com.microsoft.azure:azure-functions-java-core:1.0.0-beta-3")

 ```

2. create a fat jar
 
```
    $ gradlew shadowJar
``` 

3. create a distribution (e.g.: build/faas)

    distribution folder structure
    
    build/faas:
    - function.jar
    - host.json
    - local.settings.json
    - swagger.yaml
    - func001/function.json
    - func002/function.json

4. start a function app from distribution folder

```
    $ cd build/faas && func start
```

## FAQ:

Q: I can't see the annotations, mentioned in the java-maven-based tutorial, e.g.:
    
    @FunctionName
    @HttpTrigger

   Do I need them?
   
A: No. 

    They have no meaning in runtime. 
    The maven-plugin use them in compile time to generate your {{funcname}}/function.json



## create function app (java)

```
    Dear, Azure - I don't want to use maven.
    Could you please provide instructions on how to build & run with gradle?
    
    The maven based tutorial: https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-maven-intellij
    
    $ make -C example-java help
    $ make -C example-java up
    
    it does not work :(
    
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD FAILURE
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 41.800 s
    [INFO] Finished at: 2019-02-17T09:05:35+01:00
    [INFO] Final Memory: 42M/267M
    [INFO] ------------------------------------------------------------------------
    [ERROR] Failed to execute goal com.microsoft.azure:azure-functions-maven-plugin:1.2.2:run (default-cli) on project example-java: Stage directory not found. Please run mvn package first. -> [Help 1]
    [ERROR]
    [ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
    [ERROR] Re-run Maven using the -X switch to enable full debug logging.
    [ERROR]
    [ERROR] For more information about the errors and possible solutions, please read the following articles:
    [ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException
    

```
