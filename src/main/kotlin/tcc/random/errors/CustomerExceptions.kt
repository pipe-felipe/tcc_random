package tcc.random.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
open class CustomerAlreadyExists(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
open class CustomerNotFound(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.CONFLICT)
open class CustomerAndEmailDidNotMatch(message: String) : RuntimeException(message)