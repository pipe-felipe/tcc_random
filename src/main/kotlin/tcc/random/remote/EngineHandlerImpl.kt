package tcc.random.remote

import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import tcc.random.remote.dto.EngineRequest


class EngineHandlerImpl : EngineHandler {

    override fun sendToEngine(engineRequest: EngineRequest) {
        val entity = StringEntity(engineRequest.toString(),
                ContentType.APPLICATION_FORM_URLENCODED)

        val httpClient: HttpClient = HttpClientBuilder.create().build()
        val request = HttpPost("http://localhost:8082/engine/customer")
        request.entity = entity;

        val response: HttpResponse = httpClient.execute(request)
        println(response.statusLine.statusCode)
    }
}