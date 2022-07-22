package tcc.random.services

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import tcc.random.models.Customer
import tcc.random.repositories.CustomerRepository
import java.time.LocalDate
import java.time.Period
import java.util.*

@Service
class CustomerService(val repository: CustomerRepository) {

    fun updateCustomer(customer: Customer): ResponseEntity<Customer> {
        val customerDbOptional = repository.findByDocument(customer.document)

        val transactionHandle = customerDbOptional.get().allTransactions
        if (transactionHandle != null) {
            customer.transactionValue.let { transactionHandle.add(it) }
        }

        val toSave = customerDbOptional
            .orElseThrow { RuntimeException("Customer document: ${customer.document} not found") }
            .copy(
                transactionValue = customer.transactionValue,
                allTransactions = transactionHandle
            )
        return ResponseEntity.ok(repository.save(toSave))
    }

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