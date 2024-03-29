package tcc.random.handler

import org.springframework.stereotype.Service
import tcc.random.errors.CustomerAlreadyExists
import tcc.random.models.Customer
import tcc.random.repositories.CustomerRepository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*


@Service
class CustomerHandler(val repository: CustomerRepository) {

    fun allTransactionsHandler(optional: Optional<Customer>, customer: Customer): MutableList<Double>? {
        val transactionsUpgrade = optional.get().allTransactions

        if (transactionsUpgrade != null) {
            customer.transactionValue.let { transactionsUpgrade.add(it) }
        }
        return transactionsUpgrade
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
        if (customer.birthDate != null) {
            customer.defineAge(customer.birthDate!!)
        }
        customer.transactionCount = 1
        return customer
    }

    companion object {
        fun calculateCustomerAge(birthDate: String): Int {
            val dateFromBirthDateString = SimpleDateFormat("yyyy-MM-dd").parse(birthDate)
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Brazil"))
            calendar.time = dateFromBirthDateString
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