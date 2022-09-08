package tcc.random

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableCaching
class TccRandomApplication

fun main(args: Array<String>) {
    runApplication<TccRandomApplication>(*args)
}
