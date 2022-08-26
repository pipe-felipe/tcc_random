package tcc.random.services

import org.springframework.stereotype.Service
import tcc.random.errors.CustomerAlreadyExists
import tcc.random.models.Customer
import tcc.random.remote.dto.EngineRequest
import tcc.random.repositories.CustomerRepository
import java.time.LocalDate
import java.time.Period
import java.util.*


@Service
class CustomerService(val repository: CustomerRepository) {

    fun allTransactionsHandler(optional: Optional<Customer>, customer: Customer): MutableList<Double>? {
        val transactionsUpgrade = optional.get().allTransactions

        if (transactionsUpgrade != null) {
            customer.transactionValue.let { transactionsUpgrade.add(it) }
        }
        return transactionsUpgrade
    }

    fun customerEmailHandler(document: String, customer: Customer): Boolean {
        val customerToUpdateDocument = repository.findByDocument(document)
        val customerToUpdateEmail = repository.findByEmail(customer.email)
        if (customerToUpdateDocument.get().id != customerToUpdateEmail.get().id) {
            return true
        }
        return false
    }

    fun newTransactionHandler(customer: Customer): Customer {
        repository.existsByDocument(customer.document).let {
            if (it) {
                throw CustomerAlreadyExists("This document ${customer.document} already exists")
            }
        }
        repository.existsByEmail(customer.email).let {
            if (it) {
                throw CustomerAlreadyExists("This email ${customer.email} already exists")
            }
        }
        customer.birthDate?.let { customer.defineAge(it) }
        customer.transactionCount = 1

        return customer
    }

    companion object {
        fun calculateCustomerAge(birthDate: Date): Int {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Brazil"))
            calendar.time = birthDate
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            return Period.between(
                    LocalDate.of(year, month, day),
                    LocalDate.now()
            ).years
        }
    }
}