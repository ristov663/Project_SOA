package mk.ukim.finki.soa.masterthesis.repository.impl

import mk.ukim.finki.soa.masterthesis.repository.EventMessagingRepository
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Repository

@Repository
class KafkaMessagingRepositoryImpl(private val kafkaTemplate: KafkaTemplate<String, String>) :
    EventMessagingRepository {
    override fun send(topic: String, key: String, payload: String) {
        kafkaTemplate.send(topic, key, payload)
    }
}
