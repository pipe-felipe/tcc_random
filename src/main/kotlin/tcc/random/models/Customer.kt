package tcc.random.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Customer(
    @Id
    val id: String? = null,
    val name: String,

    @Email(message = "Email is not valid", regexp = EMAIL_VALIDATOR)
    @NotEmpty(message = "Email cannot be empty")
    val email: String,
    @Indexed(unique = true)
    val document: String,
    val creditCard: CreditCard? = null,
    val address: Address? = null
) {
    companion object {
        const val EMAIL_VALIDATOR = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
    }
}