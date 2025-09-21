package mk.ukim.finki.soa.masterthesis.repository

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.context.ActiveProfiles
import java.time.Duration

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = ["test-topic"])
@ActiveProfiles("test")
class KafkaMessagingRepositoryTest(
    @Autowired val repository: EventMessagingRepository,
    @Autowired val embeddedKafka: EmbeddedKafkaBroker
) {

    @Test
    fun `should send message to kafka topic`() {
        val topic = "test-topic"
        val key = "my-key"
        val payload = "hello-kafka"

        repository.send(topic, key, payload)

        val consumerProps = KafkaTestUtils.consumerProps("test-group", "true", embeddedKafka)
        KafkaConsumer<String, String>(
            consumerProps,
            StringDeserializer(),
            StringDeserializer()
        ).use { consumer ->
            embeddedKafka.consumeFromAnEmbeddedTopic(consumer, topic)

            val record = KafkaTestUtils.getSingleRecord(consumer, topic, Duration.ofSeconds(5))

            assertEquals(payload, record.value())
            assertEquals(key, record.key())
        }
    }
}
