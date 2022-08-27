package tcc.random.remote.dto

import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema
import tcc.random.models.Address
import tcc.random.models.CreditCard
import java.util.*

@JsonSerializableSchema
data class EngineRequest(
    var name: String,
    var email: String,
    var document: String,
    var creditCard: CreditCard?,
    var address: Address?,
    var birthDate: Date?,
    var age: Int?,
    var transactionValue: Double,
    var transactionCount: Int?,
    var allTransactions: MutableList<Double>? = mutableListOf(transactionValue),
) {
    companion object
}