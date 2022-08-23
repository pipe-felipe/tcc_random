package tcc.random.services

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import tcc.random.errors.CustomerAlreadyExists
import tcc.random.models.Customer
import tcc.random.repositories.CustomerRepository
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.LocalDate
import java.time.Period
import java.util.*


@Service
class CustomerService(val repository: CustomerRepository) {

    fun allTransactionsHandler(optional: Optional<Customer>, customer: Customer): MutableList<Double>? {
        val transactionsUpgrade = optional.get().allTransactions

        if (transactionsUpgrade != null) {
            customer.transactionValue.let { transactionsUpgrade.add(it) }
        }
        return transactionsUpgrade
    }

    fun customerEmailHandler(document: String, customer: Customer): Boolean {
        val customerToUpdateDocument = repository.findByDocument(document)
        val customerToUpdateEmail = repository.findByEmail(customer.email)
        if (customerToUpdateDocument.get().id != customerToUpdateEmail.get().id) {
            return true
        }
        return false
    }

    fun newTransactionHandler(customer: Customer): Customer {
        repository.existsByDocument(customer.document).let {
            if (it) {
                throw CustomerAlreadyExists("This document ${customer.document} already exists")
            }
        }
        repository.existsByEmail(customer.email).let {
            if (it) {
                throw CustomerAlreadyExists("This email ${customer.email} already exists")
            }
        }
        customer.birthDate?.let { customer.defineAge(it) }
        customer.transactionCount = 1

        return customer
    }

    fun sendToRulesEngine(customer: Customer) {
        val objectMapper = ObjectMapper()
        val requestBody: String = objectMapper.writeValueAsString(customer)
        print("Look the request here: $requestBody")
        val client = HttpClient.newBuilder().build()
        print(requestBody)

        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8082/engine/customer")).headers()
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println(response.body())
    }

    fun sendToRulesEngine2(customer: Customer) {
        val url = URL("http://localhost:8082/engine/customer")
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        con.requestMethod = "POST"
        con.setRequestProperty("Content-Type", "application/json")
        con.setRequestProperty("Accept", "application/json")
        con.doOutput = true

        con.outputStream.use { os ->
            val input: ByteArray = customer.toString().toByteArray()
            os.write(input, 0, input.size)
        }

        BufferedReader(
            InputStreamReader(con.inputStream, "utf-8")
        ).use { br ->
            val response = StringBuilder()
            var responseLine: String? = null
            while (br.readLine().also { responseLine = it } != null) {
                response.append(responseLine!!.trim { it <= ' ' })
            }
            println(response.toString())
        }
    }

    companion object {
        fun calculateCustomerAge(birthDate: Date): Int {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Brazil"))
            calendar.time = birthDate
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            return Period.between(
                LocalDate.of(year, month, day),
                LocalDate.now()
            ).years
        }
    }
}