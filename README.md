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

## create function app (java)

```
    Dear, Azure - I don't want to use maven.
    Could you please provide instructions on how to build & run with gradle?
    
    But hey, I decided to follow your maven-based tutorial ...
    
    tutorial: https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-maven-intellij
    
    ... and ...
    
    $ make -C example-java help
    $ make -C example-java up
    
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

### status

https://github.com/Azure/azure-functions-host/blob/dev/sample/Java/HttpTrigger/function.json

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

    
    
    A cause ...
   
    
    [02/16/2019 08:35:50] Parameter specified as non-null is null: method azure.tika.FunctionKt.kotlinPing, parameter context
    [02/16/2019 08:35:50] Result: Parameter specified as non-null is null: method azure.tika.FunctionKt.kotlinPing, parameter context
    [02/16/2019 08:35:50] Exception: Parameter specified as non-null is null: method azure.tika.FunctionKt.kotlinPing, parameter context
    [02/16/2019 08:35:50] Stack: java.lang.IllegalArgumentException: Parameter specified as non-null is null: method azure.tika.FunctionKt.kotlinPing, parameter context
    [02/16/2019 08:35:50] 	at azure.tika.FunctionKt.kotlinPing(Function.kt)
    
    
    
    Findings ...
    
    the fat jar:
    - you don't need to bundle your resources/functions/**.json into fat jar
    - the json files need to be provided in the azure-dist folder, e.g.:
    
    build/faas:
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

### side note: run func local (jvm)

- this entire thing is weird and feels pre-alpha

- issue: CTRL+C
```
      Stopping host...
[02/17/2019 09:31:05] Stopping JobHost
[02/17/2019 09:31:05] Job host stopped
info: Host.General[0]
      Host shutdown completed.
[02/17/2019 09:31:05] Language Worker Process exited.
[02/17/2019 09:31:05] /Users/sebastian.schmidt/.sdkman/candidates/java/current/bin/java exited with code 137
[02/17/2019 09:31:05]  .
[02/17/2019 09:31:05] Language Worker Process exited.
[02/17/2019 09:31:05] Worker process is not attached. . Microsoft.Azure.WebJobs.Script: Cannot access a disposed object.
[02/17/2019 09:31:05] Object name: 'ScriptEventManager'.

Unhandled Exception: System.ObjectDisposedException: Cannot access a disposed object.
Object name: 'ScriptEventManager'.
   at Microsoft.Azure.WebJobs.Script.Eventing.ScriptEventManager.ThrowIfDisposed() in C:\azure-webjobs-sdk-script\src\WebJobs.Script\Eventing\ScriptEventManager.cs:line 34
   at Microsoft.Azure.WebJobs.Script.Eventing.ScriptEventManager.Publish(ScriptEvent scriptEvent) in C:\azure-webjobs-sdk-script\src\WebJobs.Script\Eventing\ScriptEventManager.cs:line 16
   at Microsoft.Azure.WebJobs.Script.Rpc.LanguageWorkerChannel.HandleWorkerError(Exception exc) in C:\azure-webjobs-sdk-script\src\WebJobs.Script\Rpc\LanguageWorkerChannel.cs:line 480
   at Microsoft.Azure.WebJobs.Script.Rpc.LanguageWorkerChannel.OnProcessExited(Object sender, EventArgs e) in C:\azure-webjobs-sdk-script\src\WebJobs.Script\Rpc\LanguageWorkerChannel.cs:line 205
   at Microsoft.Azure.WebJobs.Script.Rpc.LanguageWorkerChannel.<StartProcess>b__34_2(Object sender, EventArgs e) in C:\azure-webjobs-sdk-script\src\WebJobs.Script\Rpc\LanguageWorkerChannel.cs:line 154
   at System.Diagnostics.Process.OnExited()
   at System.Diagnostics.Process.RaiseOnExited()
   at System.Diagnostics.Process.CompletionCallback(Object waitHandleContext, Boolean wasSignaled)
   at System.Threading.ExecutionContext.RunInternal(ExecutionContext executionContext, ContextCallback callback, Object state)
--- End of stack trace from previous location where exception was thrown ---
   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw() 

```

- issue: thread-safety?
```

sometimes my function gets an instance of ExecutionContextDataSource passed in as argument

result:
Hello from echo at 2019-02-17T09:29:02.981Z.  req=com.microsoft.azure.functions.worker.binding.ExecutionContextDataSource@38ab9bd3

reproduce?

- create fun(req:Any?):String
- binding: name="req"
-> curl this func

-> curl another func ... now this func gets ExecutionContextDataSource as well

```

- issue: no ExecutionContext
```
Did not manage to access the ExecutionContext in function (NPE !)

```

- issue: no request query params
```
Did not manage to access query params in function (NPE !)
Lack of docs? Or is it broken at all?

```




