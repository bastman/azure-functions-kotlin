package azure.tika


import com.microsoft.azure.functions.ExecutionContext
import com.microsoft.azure.functions.HttpRequestMessage
import com.microsoft.azure.functions.HttpResponseMessage
import com.microsoft.azure.functions.HttpStatus
import org.apache.tika.io.TikaInputStream
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.AutoDetectParser
import org.apache.tika.parser.ParseContext
import org.apache.tika.sax.BodyContentHandler
import java.time.Instant
import java.util.*

fun fooV1(req: HttpRequestMessage<Optional<String>>): String = "fooV1() req=$req"
fun pingV1(): String = "Hello from pingV1 at ${now()} ."
fun pingV2(context: ExecutionContext): String {
    context.logger.info { "${context.functionName} ${context.invocationId} - triggered by HttpTrigger. " }
    return """
        Hello from ${context.functionName} at ${now()}
    """.trimIndent()
}

fun echoV1(context: Any?): String = "Hello from echo at ${now()}.  context=$context."
fun echoV2(context: Any?, req: HttpRequestMessage<String?>): String {
    val isExecutionContext = context is ExecutionContext
    return """
        Hello from echoV2 at ${now()}.
        context=$context - isExecutionContext=$isExecutionContext
        req.method = ${req.httpMethod}
        req.uri = ${req.uri}
        req.body = ${req.body}
        req.queryParameters = ${req.queryParameters}
        req.headers = ${req.headers}
        """
}

fun runV1(request: HttpRequestMessage<Optional<String>>, context: ExecutionContext): HttpResponseMessage {
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


data class TikaResponse(val metadata: Metadata, val content: String)

fun tikaV1(sourceContent: ByteArray, context: ExecutionContext): TikaResponse {
    context.logger.info("${context.functionName} ${context.invocationId}")
    context.logger.info("sourceContent: $sourceContent")

    val parser = AutoDetectParser()
    val bodyContentHandler = BodyContentHandler()
    val metadata = Metadata()
    val parseContext = ParseContext()

    val stream: TikaInputStream = TikaInputStream.get(sourceContent.inputStream())
    parser.parse(stream, bodyContentHandler, metadata, parseContext)

    val sinkContent: String = bodyContentHandler.toString()
    context.logger.info("sinkMetadata: $metadata")
    context.logger.info("sinkContent: $sinkContent")
    context.logger.info("parser: $parser")
    context.logger.info("parseContext: $parseContext")

    return TikaResponse(
            metadata = metadata,
            content = sinkContent
    ).also { context.logger.info("response: $it") }
}

fun tikaV2(content: ByteArray, context: ExecutionContext): TikaResponse {
    context.logger.info("tika2")

    val parser = AutoDetectParser()
    val handler = BodyContentHandler()
    val metadata = Metadata()
    val parseContext = ParseContext()

    val stream: TikaInputStream = TikaInputStream.get(content.inputStream())
    parser.parse(stream, handler, metadata, parseContext)

    return TikaResponse(metadata, handler.toString())
}

private fun now(): Instant = Instant.now()