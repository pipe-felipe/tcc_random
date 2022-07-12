package tcc.random.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tcc.random.models.Customer
import tcc.random.repositories.CustomerRepository

@RestController
@RequestMapping("customer")
class CustomerController(
    val repository: CustomerRepository
) {

    @PostMapping
    fun createConsumer(@RequestBody customer: Customer) =
        ResponseEntity
            .ok(repository.save(customer))

    @GetMapping
    fun readConsumers() = ResponseEntity
        .ok(repository.findAll())

    @PutMapping("{document}")
    fun updateConsumer(
        @PathVariable document: String,
        @RequestBody customer: Customer
    ): ResponseEntity<Customer> {
        val customerDbOptional = repository.findByDocument(document)
        val toSave = customerDbOptional
            .orElseThrow { RuntimeException("Customer document: $document not found") }
            .copy(name = customer.name)
        return ResponseEntity
            .ok(repository.save(toSave))
    }

    @DeleteMapping("{document}")
    fun deleteCustomer(@PathVariable document: String) = repository
        .findByDocument(document)
        .ifPresent { repository.delete(it) }
}