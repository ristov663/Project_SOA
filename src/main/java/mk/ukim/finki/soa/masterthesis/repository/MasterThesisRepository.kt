package mk.ukim.finki.soa.masterthesis.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import mk.ukim.finki.soa.masterthesis.model.aggregate.MasterThesis
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import org.axonframework.common.jpa.SimpleEntityManagerProvider
import org.axonframework.eventhandling.EventBus
import org.axonframework.messaging.annotation.ParameterResolverFactory
import org.axonframework.modelling.command.GenericJpaRepository
import org.axonframework.modelling.command.Repository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration("masterThesisRepositoriesConfiguration")
class AxonRepositoriesConfiguration(@PersistenceContext val entityManager: EntityManager) {

    @Bean("axonMasterThesisRepository")
    fun masterThesisGenericJpaRepository(
        eventBus: EventBus,
        parameterResolverFactory: ParameterResolverFactory
    ): Repository<MasterThesis> {
        return GenericJpaRepository.builder(MasterThesis::class.java)
            .entityManagerProvider(SimpleEntityManagerProvider(entityManager))
            .parameterResolverFactory(parameterResolverFactory)
            .eventBus(eventBus)
            .identifierConverter { MasterThesisId(it) }
            .build()
    }
}
