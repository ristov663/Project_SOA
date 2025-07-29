package mk.ukim.finki.soa.masterthesis.configuration

import org.axonframework.deadline.SimpleDeadlineManager
import org.axonframework.deadline.DeadlineManager
import org.axonframework.config.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration as SpringConfiguration

@SpringConfiguration
class SagaConfiguration {

    @Bean
    fun deadlineManager(axonConfiguration: Configuration): DeadlineManager {
        return SimpleDeadlineManager.builder()
            .scopeAwareProvider(axonConfiguration.scopeAwareProvider())
            .build()
    }
}
