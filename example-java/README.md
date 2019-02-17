# azure-functions-java

Followed tutorial: https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-maven-intellij


```
    $ cd example-java
    $ mvn azure-functions:run
    
    should work, right?
    
    well, it's azure ...


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