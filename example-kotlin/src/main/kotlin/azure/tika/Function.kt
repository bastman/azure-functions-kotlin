package azure.tika


import com.microsoft.azure.functions.*
import com.microsoft.azure.functions.annotation.AuthorizationLevel
import com.microsoft.azure.functions.annotation.HttpTrigger
import java.time.Instant
import java.util.*

fun ping(): String = "Hello from ping at ${now()} ."
fun ping2(context: ExecutionContext): String {
    context.logger.info { "${context.functionName} ${context.invocationId} - triggered by HttpTrigger. " }
    return """
        Hello from ${context.functionName} at ${now()}
    """.trimIndent()
}

fun run(request: HttpRequestMessage<Optional<String>>, context: ExecutionContext): HttpResponseMessage {
    context.logger.info("${context.functionName} ${context.invocationId} - triggered by HttpTrigger. ")
    val azureWebJobsStorage: String = System.getenv("AzureWebJobsStorage")
            ?: return request
                    .createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("AzureWebJobsStorage is empty")
                    .build()

    // Parse query parameter
    val query = request.queryParameters["name"]
    val name: String = request.body.orElse(query)
            ?: return request
                    .createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Please pass a name on the query string or in the request body")
                    .build()

    return request.createResponseBuilder(HttpStatus.OK)
            .body(
                    """
                        Hello, $name .
                        - func: [${context.functionName} ${context.invocationId}]
                        - azureWebJobsStorage: $azureWebJobsStorage
                        - now: ${Instant.now()}
                    """.trimIndent()
            )
            .build()
}


fun foo(
        @HttpTrigger(name = "req", methods = [HttpMethod.GET, HttpMethod.POST], authLevel = AuthorizationLevel.ANONYMOUS)
        req: HttpRequestMessage<Optional<String>>?
): String {
    return "foo() req=$req" // null, awesome ;)
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