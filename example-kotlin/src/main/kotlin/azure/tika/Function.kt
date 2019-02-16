package azure.tika

import com.microsoft.azure.serverless.functions.ExecutionContext
import com.microsoft.azure.serverless.functions.annotation.FunctionName
import java.time.Instant

//@FunctionName("kotlinPing")
fun kotlinPing(context: ExecutionContext):String {
    context.logger.info {
        "=====> handle api call kotlinPing ... fn=${context.functionName} invocationId=${context.invocationId}"
    }
    return "Pong: ${Instant.now()}"
}

/*
fun kotlinGreetingDataClass(name: String? = "World", context: ExecutionContext): KotlinGreetingResponse {
    context.logger.info("kotlinGreetingDataClass Called")
    return KotlinGreetingResponse(response = "Hello, ${name ?: "World"}")
}


data class KotlinGreetingResponse(val response: String = "")

class KotlinGreetingResponse2(val response: String = "")

data class KotlinTikaResponse(val metadata: Metadata, val content: String)
        */