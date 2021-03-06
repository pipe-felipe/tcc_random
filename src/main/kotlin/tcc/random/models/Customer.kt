package tcc.random.models

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import tcc.random.services.CustomerService
import java.util.*

@Document
data class Customer(
    @Id
    val id: String? = null,

    val name: String,

    @Email(
        message = "Email is not valid",
        regexp =
        "\"^[\\\\w!#\$%&'*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#\$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}\$\""
    )
    @NotEmpty(message = "Email cannot be empty")
    val email: String,

    @Indexed(unique = true)
    val document: String,

    val creditCard: CreditCard? = null,
    val address: Address? = null,

    @JsonFormat(pattern = "yyyy-MM-dd")
    var birthDate: Date? = null,
    var age: Int? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: Date? = Date(),

    val transactionValue: Double,
    var transactionCount: Int?,

    val allTransactions: MutableList<Double>? = mutableListOf(transactionValue)
) {
    fun defineAge(birthDate: Date) {
        this.birthDate = birthDate
        this.age = CustomerService.calculateCustomerAge(birthDate)
    }
}