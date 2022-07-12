package tcc.random.models

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Address (
    val street: String?,
    val number: Int?,
    val city: String?,
    val state: String?,
    val country: String?,
    val zipCode: String?
)