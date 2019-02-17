package azure.tika

import com.microsoft.azure.serverless.functions.ExecutionContext
import com.microsoft.azure.serverless.functions.annotation.FunctionName
import java.time.Instant
import com.microsoft.azure.serverless.functions.annotation.AuthorizationLevel
import com.microsoft.azure.serverless.functions.annotation.HttpTrigger

@FunctionName("pingxxx")
fun ping(
): String {
    return "Hello from ping! ${Instant.now()}"
}

@FunctionName("echo")
fun echo(
        @HttpTrigger(name = "req", methods = ["post","get"], authLevel = AuthorizationLevel.ANONYMOUS)
        name: String?
): String {
    return "Hello, name=$name."
}

@FunctionName("ping1")
fun ping1(name: String? = "World"):String {
    return "response from ping1:  name=$name ${Instant.now()}"
}

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