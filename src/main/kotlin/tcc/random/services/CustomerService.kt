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
        val upgradedTransaction = customerDbOptional
            .get()
            .transactionCount
            .plus(1)

        val transactionHandle = customerDbOptional.get().allTransactions
        if (transactionHandle != null) {
            customer.transactionValue.let { transactionHandle.add(it) }
        }

        val toSave = customerDbOptional
            .orElseThrow { RuntimeException("Customer document: ${customer.document} not found") }
            .copy(
                transactionValue = customer.transactionValue,
                transactionCount = upgradedTransaction,
                allTransactions = transactionHandle
            )
        return ResponseEntity.ok(repository.save(toSave))
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