package tcc.random.services

import org.springframework.stereotype.Service
import tcc.random.models.Customer
import tcc.random.repositories.CustomerRepository

@Service
class CustomerService(val repository: CustomerRepository) {

}