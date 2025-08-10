package mk.ukim.finki.soa.masterthesis.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import mk.ukim.finki.soa.masterthesis.infrastructure.kafka.MasterThesisEventTranslator
import mk.ukim.finki.soa.masterthesis.infrastructure.kafka.StudentAccreditationStatusCheckedDTO
import mk.ukim.finki.soa.masterthesis.infrastructure.kafka.StudentEnrollmentValidateDTO
import mk.ukim.finki.soa.masterthesis.infrastructure.kafka.ThesisCanceledDTO
import mk.ukim.finki.soa.masterthesis.service.MasterThesisModificationService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaEventConsumer(
    private val eventTranslator: MasterThesisEventTranslator,
    private val thesisService: MasterThesisModificationService
) {

    private val objectMapper = ObjectMapper()
        .registerModules(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())

    @KafkaListener(topics = ["student.accreditation.checked"])
    fun handleAccreditation(record: ConsumerRecord<String, String>) {
        try {
            val eventDTO = objectMapper.readValue<StudentAccreditationStatusCheckedDTO>(record.value())
            val command = eventTranslator.toApproveAccreditationCommand(eventDTO)
            thesisService.approveAccreditation(command)
        } catch (ex: Exception) {
            println("Error processing accreditation event: ${ex.message}")
        }
    }

    @KafkaListener(topics = ["student.enrollment.validated"])
    fun handleEnrollment(record: ConsumerRecord<String, String>) {
        try {
            val eventDTO = objectMapper.readValue<StudentEnrollmentValidateDTO>(record.value())
            val command = eventTranslator.toValidateEnrollmentCommand(eventDTO)
            thesisService.validateEnrollment(command)
        } catch (ex: Exception) {
            println("Error processing enrollment event: ${ex.message}")
        }
    }

    @KafkaListener(topics = ["thesis.canceled"])
    fun handleThesisCanceled(record: ConsumerRecord<String, String>) {
        try {
            val eventDTO = objectMapper.readValue<ThesisCanceledDTO>(record.value())
            val command = eventTranslator.toArchiveThesisCommand(eventDTO)
            thesisService.archiveThesis(command)
        } catch (ex: Exception) {
            println("Error processing thesis canceled event: ${ex.message}")
        }
    }
}
