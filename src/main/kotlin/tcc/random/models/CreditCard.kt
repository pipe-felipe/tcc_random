package tcc.random.models

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class CreditCard (
    val number: String?,
    val name: String?
)