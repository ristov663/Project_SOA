package mk.ukim.finki.soa.masterthesis.service.impl

import mk.ukim.finki.soa.masterthesis.repository.EventMessagingRepository
import mk.ukim.finki.soa.masterthesis.service.EventMessagingService
import org.springframework.stereotype.Service

@Service
class EventMessagingServiceImpl(val eventMessagingRepository: EventMessagingRepository) : EventMessagingService {
    override fun send(topic: String, key: String, payload: String) {
        eventMessagingRepository.send(topic, key, payload)
    }
}
