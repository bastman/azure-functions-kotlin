package azure.tika

import com.microsoft.azure.serverless.functions.ExecutionContext

fun kotlinGreetingDataClass(name: String? = "World", context: ExecutionContext): KotlinGreetingResponse {
    context.logger.info("kotlinGreetingDataClass Called")
    return KotlinGreetingResponse(response = "Hello, ${name ?: "World"}")
}


data class KotlinGreetingResponse(val response: String = "")

class KotlinGreetingResponse2(val response: String = "")

data class KotlinTikaResponse(val metadata: Metadata, val content: String)