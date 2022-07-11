package tcc.random.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import tcc.random.repositories.CreditCard

@Document
data class Customer(
    @Id
    val id: String? = null,
    val name: String,
    @Indexed(unique = true)
    val document: String,
    val creditCard: CreditCard?
)