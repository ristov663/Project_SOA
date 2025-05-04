package mk.ukim.finki.soa.masterthesis.repository

interface EventMessagingRepository {
    fun send(topic: String, key: String, payload: String)
}
