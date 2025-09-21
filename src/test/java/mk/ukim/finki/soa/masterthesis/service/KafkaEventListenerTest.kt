package mk.ukim.finki.soa.masterthesis.service

import mk.ukim.finki.soa.masterthesis.service.impl.KafkaEventListener
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.junit.jupiter.api.Test

class KafkaEventListenerTest {
    @Test
    fun `onMessage consumes event`() {
        val listener = KafkaEventListener()
        val record = ConsumerRecord("events", 0, 0L, "key", "payload")
        listener.onMessage(record)
    }
}
