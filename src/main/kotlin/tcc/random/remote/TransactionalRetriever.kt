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


class TransactionalRetriever {

    private fun createEntity(customer: Customer): StringEntity {
        val cafeRequest = CafeRequest(
                name = customer.name,
                email = customer.email,
                document = customer.document,
                creditCard = customer.creditCard,
                address = customer.address,
                age = customer.age,
                transactionValue = customer.transactionValue,
                sentMethod = customer.sentMethod
        )

        val gson = Gson()
        val jsonRequest = gson.toJson(cafeRequest)

        return StringEntity(
                jsonRequest,
                ContentType.APPLICATION_JSON
        )
    }

    fun sendToEngine(customer: Customer) {

        val httpClient: HttpClient = HttpClientBuilder.create().build()
        val request = HttpPost("http://localhost:8083/cafe")
        request.entity = createEntity(customer)

        val response: HttpResponse = httpClient.execute(request)
        println(response.statusLine.statusCode)
        response.entity = createEntity(customer)
    }
}