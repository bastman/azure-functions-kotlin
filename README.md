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
    
    

```

