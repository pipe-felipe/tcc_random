package tcc.random.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tcc.random.errors.CustomerAndEmailDidNotMatch
import tcc.random.errors.CustomerNotFound
import tcc.random.handler.CustomerHandler
import tcc.random.models.Customer
import tcc.random.remote.TransactionalRetriever
import tcc.random.repositories.CustomerRepository

@RestController
@RequestMapping("customer")
class CustomerController(
        val repository: CustomerRepository,
        val handler: CustomerHandler,
) {

    @PostMapping
    fun createTransactionalData(@RequestBody customer: Customer) {
        val retriever = TransactionalRetriever()
        handler.newTransactionHandler(customer)
        customer.sentMethod = "POST"
        retriever.sendToEngine(customer)
    }

    @PostMapping("/engine")
    fun retrieveTransactionalData(@RequestBody customer: Customer) {
        customer.transactionCount = 1
        ResponseEntity.ok(repository.save(customer))
    }

    @GetMapping
    fun readTransactionalData(page: Pageable): ResponseEntity<Page<Customer>> =
            ResponseEntity.ok(repository.findAll(page))

    @PutMapping("{document}")
    fun updateTransactionalData(
            @PathVariable document: String, @RequestBody customer: Customer
    ): ResponseEntity<Customer> {
        if (handler.customerEmailHandler(document, customer)) {
            throw CustomerAndEmailDidNotMatch("This ${customer.email} and ${customer.document} did not match")
        }


        val customerToUpdate = repository.findByDocument(document)

        val toSave = customerToUpdate.orElseThrow { CustomerNotFound("This Document: $document does not exists") }
                .copy(
                        name = customer.name,
                        transactionCount = customerToUpdate.get().transactionCount?.plus(1),
                        allTransactions = handler.allTransactionsHandler(customerToUpdate, customer),
                        transactionValue = customer.transactionValue
                )
        return ResponseEntity.ok(repository.save(toSave))
    }

    @DeleteMapping("{document}")
    fun deleteTransaction(@PathVariable document: String) =
            repository.findByDocument(document).ifPresent { repository.delete(it) }
}