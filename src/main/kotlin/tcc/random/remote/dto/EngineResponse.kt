package tcc.random.remote.dto

import tcc.random.models.Address
import tcc.random.models.CreditCard

data class EngineResponse(
    val id: String?,
    val name: String,
    val email: String,
    val document: String,
    val creditCard: CreditCard?,
    val address: Address?,
    var birthDate: String?,
    var age: Int?,
    val transactionValue: Double,
    var transactionCount: Int?,
    val allTransactions: MutableList<Double>? = mutableListOf(transactionValue),
    var transactionStatus: String?
)