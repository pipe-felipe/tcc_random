package tcc.random.remote.dto

import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema
import tcc.random.models.Address
import tcc.random.models.CreditCard

@JsonSerializableSchema
data class CafeRequest(
    var name: String,
    var email: String,
    var document: String,
    var creditCard: CreditCard?,
    var address: Address?,
    var age: Int?,
    var transactionValue: Double,
    var sentMethod: String?
)