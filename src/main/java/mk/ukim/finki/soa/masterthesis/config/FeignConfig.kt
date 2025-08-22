package mk.ukim.finki.soa.masterthesis.config

import mk.ukim.finki.soa.masterthesis.client.interceptors.CorrelationIdInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfig {
    @Bean
    fun correlationIdInterceptor(): CorrelationIdInterceptor {
        return CorrelationIdInterceptor()
    }
}
