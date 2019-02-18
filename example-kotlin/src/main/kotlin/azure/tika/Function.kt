package azure.tika


import com.microsoft.azure.functions.*
import com.microsoft.azure.functions.annotation.AuthorizationLevel
import com.microsoft.azure.functions.annotation.HttpTrigger
import java.time.Instant
import java.util.*

fun run(
        request: HttpRequestMessage<Optional<String>>,
        context: ExecutionContext
): HttpResponseMessage {

    //return "RUN."

    context.logger.info("Java HTTP trigger processed a request.")

    // Parse query parameter
    val query = request.queryParameters["name"]
    val name = request.body.orElse(query)
    val readEnv = System.getenv("AzureWebJobsStorage")

    if (name == null) {
        return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build()
    }
    return if (readEnv == null) {
        request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("AzureWebJobsStorage is empty").build()
    } else request.createResponseBuilder(HttpStatus.OK).body("Hello, $name").build()

}


fun foo(
        @HttpTrigger(name = "req", methods = [HttpMethod.GET, HttpMethod.POST], authLevel = AuthorizationLevel.ANONYMOUS)
        req: HttpRequestMessage<Optional<String>>?
): String {
    return "foo() req=$req" // null, awesome ;)
}

fun ping(): String = "Hello from ping at ${now()} ."
fun ping2(context: ExecutionContext): String {
    context.logger.info {
        "=====> handle api call ping2 ... fn=${context.functionName} invocationId=${context.invocationId}"
    }
    return "Hello from ping2 at ${now()} . context=$context"
}


fun echo(req: Any?): String = "Hello from echo at ${now()}.  req=$req."
fun echo2(req: Any?, name: Any?): String {

    val isExecutionContext = req is ExecutionContext
    return """
        Hello from echo2 at ${now()}.
        req=$req.  req.isExecutionContext=$isExecutionContext
        name=$name

        """
}


private fun now(): Instant = Instant.now()