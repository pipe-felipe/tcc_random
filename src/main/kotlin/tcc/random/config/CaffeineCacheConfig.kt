package tcc.random.config

import org.springframework.boot.autoconfigure.cache.CacheProperties.Caffeine
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class CaffeineCacheConfig {

    @Bean
    fun caffeine(): Caffeine? {
        return Caffeine()
    }
}