package tcc.random.remote

import tcc.random.models.Customer
import tcc.random.remote.dto.EngineRequest

class PostEngineImpl(
    customer: Customer
) : PostEngine {
    private val engineRequest: EngineRequest = EngineRequest(
        id = customer.id,
        name = customer.name,
        email = customer.email,
        document = customer.document,
        creditCard = customer.creditCard,
        address = customer.address,
        birthDate = customer.birthDate,
        age = customer.age,
        transactionValue = customer.transactionValue,
        transactionCount = customer.transactionCount,
        allTransactions = customer.allTransactions,
    )

    override suspend fun sendToEngine() {
        print(engineRequest.age)
    }
}