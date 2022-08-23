package tcc.random.remote.dto

import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema
import tcc.random.models.Address
import tcc.random.models.CreditCard
import java.util.*

@JsonSerializableSchema
data class EngineRequest(
    val id: String?,
    val name: String,
    val email: String,
    val document: String,
    val creditCard: CreditCard?,
    val address: Address?,
    var birthDate: Date?,
    var age: Int?,
    val transactionValue: Double,
    var transactionCount: Int?,
    val allTransactions: MutableList<Double>? = mutableListOf(transactionValue),
)