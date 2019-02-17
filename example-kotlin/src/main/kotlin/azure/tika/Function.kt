package azure.tika

import com.microsoft.azure.serverless.functions.ExecutionContext
import java.time.Instant

fun ping(): String = "Hello from ping at ${now()} ."
fun echo(name: String?): String = "Hello from echo at ${now()}.  req.name=$name."
fun echo2(name: String? = "<the-default-name>"): String = "Hello from echo at ${now()}.  req.name=$name."
fun ping2(context: ExecutionContext): String {
    context.logger.info {
        "=====> handle api call ping2 ... fn=${context.functionName} invocationId=${context.invocationId}"
    }
    return "Hello from ping2 at ${now()} ."
}

private fun now(): Instant = Instant.now()