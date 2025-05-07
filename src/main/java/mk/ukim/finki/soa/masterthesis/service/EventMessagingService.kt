package mk.ukim.finki.soa.masterthesis.service

interface EventMessagingService {
    fun send(topic: String, key: String, payload: String)
}
