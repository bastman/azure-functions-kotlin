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
```

## create function app (java)

```
    Dear, Azure - I don't want to use maven.
    Could you please provide instructions on how to build & run with gradle?
    
    But hey, I decided to follow your maven-based tutorial ...
    
    tutorial: https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-maven-intellij
    
    ... and ...
    
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
    
    
    => Should I continue spending more time on this?
    => ... or rather invest that amount of time in AWS Lambda, knative, ... ?

```

## create function app (kotlin)

```
    still trial and error with azure docs.
    
    What files (*.jar, *.json) have to go where? 
    How hard can it be to add this to docs? ... on 1st place ?
    
    
    DIST folder structure
    
    build/faas:
    - function.jar
    - host.json
    - local.settings.json
    - swagger.yaml
    - func001/function.json
    - func002/function.json
    
    to serve that you need to ...
    $ cd build/faas && func start
    

    $ make -C example-kotlin help    
    $ make -C example-kotlin build       
    $ make -C example-kotlin start
    $ curl -v http://localhost:7071/api/kotlinping

    -> returns 500 (when build with "com.microsoft.azure:azure-functions-java-core:1.0.0-beta-2")    
    -> returns 404 (when build with "com.microsoft.azure:azure-functions-java-core:1.0.0-beta-3")
        --> "NOT FOUND" ? for an internal server error?

    
    
    "it's azure  ;)" ...
    
    [02/16/2019 08:35:50] Parameter specified as non-null is null: method azure.tika.FunctionKt.kotlinPing, parameter context
    [02/16/2019 08:35:50] Result: Parameter specified as non-null is null: method azure.tika.FunctionKt.kotlinPing, parameter context
    [02/16/2019 08:35:50] Exception: Parameter specified as non-null is null: method azure.tika.FunctionKt.kotlinPing, parameter context
    [02/16/2019 08:35:50] Stack: java.lang.IllegalArgumentException: Parameter specified as non-null is null: method azure.tika.FunctionKt.kotlinPing, parameter context
    [02/16/2019 08:35:50] 	at azure.tika.FunctionKt.kotlinPing(Function.kt)
    
    
    
    Findings ...
    
    the fat jar:
    - you don't need to bundle your resources/functions/**.json into fat jar
    - the json files need to be provided in the azure-dist folder, e.g.:
    
    build/azure-functions:
    - function.jar
    - host.json
    - local.settings.json
    - swagger.yaml
    - func001/function.json
    - func002/function.json
    
    a function:
    - must have a definition {{func-name}}/function.json
    - must be whitelisted in host.json
    
    the annotations, e.g. @FunctionName("pingxxx") ...
    - have no meaning in runtime ? 
    - just used in compile-time by the maven-plugin to auto-magically generate functions/{{func-name}}/function.json ???

```

