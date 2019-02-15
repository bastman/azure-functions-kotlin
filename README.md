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

