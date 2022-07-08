package tcc.random.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import tcc.random.models.Customer
import java.util.Optional

@Repository
interface CustomerRepository : MongoRepository<Customer, String> {

    fun findByDocument(document: String): Optional<Customer>
}