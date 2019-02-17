# azure-functions-kotlin
experimental

## create function app (node.js)

Why node.js ? 
Because: simple + straight forward

```
    $ make help
    $ make fn.create-app.nodejs APP_NAME="example-nodejs"
    $ make fn.app.start APP_NAME=example-nodejs
    
    $ curl -v http://localhost:7071/api/Func001?name=fooo
    $ curl -v http://localhost:7071/api/Func002?name=fooo
```

## create function app (java)

```
    TODO: as soon as there is a proper gradle plugin available - 2021?

```

## create function app (kotlin)

```
    still trial and error with azure docs.
    
    What files (*.jar, *.json) have to go where? 
    How hard can it be to add this to docs? ... on 1st place ?
    
    
    DIST folder structure
    
    build/azure-functions:
    - function.jar
    - host.json
    - local.settings.json
    - swagger.yaml
    - func001/function.json
    - func002/function.json
    
    
    $ make -C example-kotlin help    
    $ make -C example-kotlin build       
    $ make -C example-kotlin start
    $ curl -v http://localhost:7071/api/kotlinping
    $ ab -n 1000 -c 100 http://localhost:7071/api/kotlinping

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
    
    a function:
    - must have a definition in functions/{{func-name}}/function.json
    - must be whitelisted in host.json
    
    the annotations, e.g. @FunctionName("pingxxx")
    - have no meaning ? just just by the maven-plugin to generate functions/{{func-name}}/function.json ???

```

