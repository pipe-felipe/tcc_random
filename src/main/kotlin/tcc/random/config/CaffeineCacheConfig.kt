package tcc.random.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit


@Configuration
class CaffeineCacheConfig {

    @Bean
    fun cacheManager(): CacheManager =
            CaffeineCacheManager("Customer")
                    .apply {
                        isAllowNullValues = false
                        setCaffeine(
                                Caffeine.newBuilder()
                                        .maximumSize(2)
                                        .expireAfterAccess(1, TimeUnit.MINUTES)
                        )
                    }
}