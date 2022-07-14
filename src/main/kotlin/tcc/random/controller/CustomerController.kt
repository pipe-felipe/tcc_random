package tcc.random.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
    fun customerTransaction(@RequestBody customer: Customer) {

        if ((repository.existsByDocument(customer.document) && !repository.existsByEmail(customer.email))
            || (!repository.existsByDocument(customer.document) && repository.existsByEmail(customer.email))
        ) {
            throw IllegalArgumentException("The user ${customer.document} and ${customer.email} did not match")
        }

        if (repository.existsByDocument(customer.document)) {
            service.updateCustomer(customer)
            return
        }

        customer.birthDate?.let { customer.defineAge(it) }
        ResponseEntity.ok(repository.save(customer))
    }

    @GetMapping
    fun readConsumers() = ResponseEntity.ok(repository.findAll())

    @PutMapping("{document}")
    fun updateConsumer(
        @PathVariable document: String, @RequestBody customer: Customer
    ): ResponseEntity<Customer> {
        val customerDbOptional = repository.findByDocument(document)
        val toSave = customerDbOptional
            .orElseThrow { RuntimeException("Customer document: $document not found") }
            .copy(name = customer.name)
        return ResponseEntity.ok(repository.save(toSave))
    }

    @DeleteMapping("{document}")
    fun deleteCustomer(@PathVariable document: String) =
        repository.findByDocument(document).ifPresent { repository.delete(it) }
}