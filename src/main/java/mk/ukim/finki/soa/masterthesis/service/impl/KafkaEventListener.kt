package mk.ukim.finki.soa.masterthesis.service.impl

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaEventListener {
    @KafkaListener(topics = ["events"], groupId = "my-group")
    fun onMessage(record: ConsumerRecord<String, String>) {
        val key = record.key()
        val payload = record.value()

    }
}