package mk.ukim.finki.soa.masterthesis.service

import mk.ukim.finki.soa.masterthesis.repository.EventMessagingRepository
import mk.ukim.finki.soa.masterthesis.service.impl.EventMessagingServiceImpl
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class EventMessagingServiceTest {
    private val repo = mock<EventMessagingRepository>()
    private val service = EventMessagingServiceImpl(repo)

    @Test
    fun `send delegates correctly`() {
        service.send("topic", "key", "payload")
        verify(repo, times(1)).send("topic", "key", "payload")
    }
}
