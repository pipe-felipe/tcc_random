package tcc.random.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tcc.random.errors.CustomerAlreadyExists
import tcc.random.errors.CustomerAndEmailDidNotMatch
import tcc.random.errors.CustomerNotFound
import tcc.random.models.Customer
import tcc.random.repositories.CustomerRepository
import tcc.random.services.CustomerService

@RestController
@RequestMapping("customer")
class CustomerController(
    val repository: CustomerRepository,
    val service: CustomerService
) {

    @PostMapping
    fun createCustomer(@RequestBody customer: Customer) {
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
        ResponseEntity.ok(repository.save(customer))
    }

    @GetMapping
    fun readConsumers(page: Pageable): ResponseEntity<Page<Customer>> = ResponseEntity.ok(repository.findAll(page))

    @PutMapping("{document}")
    fun updateConsumer(
        @PathVariable document: String, @RequestBody customer: Customer
    ): ResponseEntity<Customer> {
        service.customerEmailHandler(document, customer).let {
            if (it) {
                throw CustomerAndEmailDidNotMatch("This ${customer.email} and ${customer.document} did not match")
            }
        }

        val customerToUpdate = repository.findByDocument(document)

        val toSave = customerToUpdate.orElseThrow { CustomerNotFound("This Document: $document does not exists") }
            .copy(
                name = customer.name,
                transactionCount = customerToUpdate.get().transactionCount?.plus(1),
                allTransactions = service.allTransactionsHandler(customerToUpdate, customer),
                transactionValue = customer.transactionValue
            )
        return ResponseEntity.ok(repository.save(toSave))
    }

    @DeleteMapping("{document}")
    fun deleteCustomer(@PathVariable document: String) =
        repository.findByDocument(document).ifPresent { repository.delete(it) }
}