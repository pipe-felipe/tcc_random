package tcc.random.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tcc.random.models.Customer
import tcc.random.repositories.CustomerRepository

@RestController
@RequestMapping("customer")
class CustomerController(val repository: CustomerRepository) {

    @PostMapping
    fun createConsumer(@RequestBody customer: Customer) = ResponseEntity.ok(repository.save(customer))

    @GetMapping
    fun read() = ResponseEntity.ok(repository.findAll())
}