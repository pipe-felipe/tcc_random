package tcc.random.remote

import com.google.gson.Gson
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import tcc.random.remote.dto.EngineRequest


class EngineHandlerImpl : EngineHandler {

    override fun sendToEngine(engineRequest: EngineRequest) {

        val gson = Gson()
        val jsonRequest = gson.toJson(engineRequest)

        val entity = StringEntity(jsonRequest,
                ContentType.APPLICATION_JSON)

        val httpClient: HttpClient = HttpClientBuilder.create().build()
        val request = HttpPost("http://localhost:8082/engine/customer")
        request.entity = entity

        val response: HttpResponse = httpClient.execute(request)
        println(response.statusLine.statusCode)
    }
}