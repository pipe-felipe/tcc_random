package tcc.random.remote

import com.google.gson.Gson
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import tcc.random.models.Customer
import tcc.random.remote.dto.CafeRequest


class TransacitonalRetriever {

    fun sendToEngine(customer: Customer) {

        val cafeRequest = CafeRequest(
            name = customer.name,
            email = customer.email,
            document = customer.document,
            creditCard = customer.creditCard,
            address = customer.address,
            age = customer.age,
            transactionValue = customer.transactionValue
        )

        val gson = Gson()
        val jsonRequest = gson.toJson(cafeRequest)

        val entity = StringEntity(
            jsonRequest,
            ContentType.APPLICATION_JSON
        )

        val httpClient: HttpClient = HttpClientBuilder.create().build()
        val request = HttpPost("http://localhost:8083/cafe")
        request.entity = entity

        val response: HttpResponse = httpClient.execute(request)
        println(response.statusLine.statusCode)
    }
}