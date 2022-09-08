package tcc.random.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tcc.random.errors.CustomerAndEmailDidNotMatch
import tcc.random.errors.CustomerNotFound
import tcc.random.models.Customer
import tcc.random.repositories.CustomerRepository
import tcc.random.handler.CustomerHandler
import tcc.random.remote.TransacitonalRetriever

@RestController
@RequestMapping("customer")
class CustomerController(
        val repository: CustomerRepository,
        val service: CustomerHandler,
) {

    @PostMapping
    fun createTransactionalData(@RequestBody customer: Customer) {
        val retriever = TransacitonalRetriever()
        service.newTransactionHandler(customer)
        retriever.sendToEngine(customer)
        println(customer)
    }

    @PostMapping("/engine")
    fun retrieveTransactionalData(@RequestBody customer: Customer) {
        ResponseEntity.ok(repository.save(customer))
    }

    @GetMapping
    fun readTransactionalData(page: Pageable): ResponseEntity<Page<Customer>> =
            ResponseEntity.ok(repository.findAll(page))

    @PutMapping("{document}")
    fun updateTransactionalData(
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
    fun deleteTransaction(@PathVariable document: String) =
            repository.findByDocument(document).ifPresent { repository.delete(it) }
}