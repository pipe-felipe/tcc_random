package tcc.random.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import tcc.random.models.Customer
import java.util.*

@Repository
interface CustomerRepository : MongoRepository<Customer, String> {

    fun findByDocument(document: String): Optional<Customer>

    fun findByEmail(email: String): Optional<Customer>
    fun existsByDocument(document: String): Boolean
    fun existsByEmail(email: String): Boolean
}
