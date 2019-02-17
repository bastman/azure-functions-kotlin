package azure.tika

import com.microsoft.azure.serverless.functions.ExecutionContext
import com.microsoft.azure.serverless.functions.HttpRequestMessage
import com.microsoft.azure.serverless.functions.annotation.AuthorizationLevel
import com.microsoft.azure.serverless.functions.annotation.HttpTrigger
import java.time.Instant
import java.util.*


/*
public static String HttpTriggerJavaMetadata(
@HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
@BindingName("firstName") String queryValue1, @BindingName("lastName") String queryValue2
)
*/


fun foo(
        @HttpTrigger(name = "req", methods = ["GET", "POST"] , authLevel = AuthorizationLevel.ANONYMOUS)
        req: HttpRequestMessage<Optional<String>>?
):String {
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
fun echo2(req: Any?, name:Any?): String {

    val isExecutionContext = req is ExecutionContext
    return """
        Hello from echo2 at ${now()}.
        req=$req.  req.isExecutionContext=$isExecutionContext
        name=$name

        """
}


private fun now(): Instant = Instant.now()