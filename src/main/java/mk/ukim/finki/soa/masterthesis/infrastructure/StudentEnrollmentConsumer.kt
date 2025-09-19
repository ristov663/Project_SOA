package mk.ukim.finki.soa.masterthesis.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import mk.ukim.finki.soa.masterthesis.infrastructure.kafka.external.StudentEnrollmentValidatedExternalDto
import mk.ukim.finki.soa.masterthesis.model.command.student.ConfirmStudentEnrollmentForThesis
import mk.ukim.finki.soa.masterthesis.model.valueObject.Enrollment
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import mk.ukim.finki.soa.masterthesis.repository.MasterThesisViewRepository
import org.axonframework.commandhandling.gateway.CommandGateway
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class StudentEnrollmentConsumer(
    private val masterThesisViewRepository: MasterThesisViewRepository,
    private val commandGateway: CommandGateway
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val mapper: ObjectMapper = ObjectMapper()
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)


    @KafkaListener(topics = ["student.enrollment.validated"], groupId = "master-thesis-service")
    fun consume(rawMessage: String) {
        try {
            // Handle both raw JSON and escaped JSON
            val cleanJson = if (rawMessage.trim().startsWith("\"")) {
                rawMessage.trim()
                    .removePrefix("\"")
                    .removeSuffix("\"")
                    .replace("\\\"", "\"")
                    .replace("\\n", "")
                    .replace("\\r", "")
            } else {
                rawMessage
            }

            val root = mapper.readTree(cleanJson)
            val dataNode = root.get("data") ?: root

            val external = mapper.treeToValue(dataNode, StudentEnrollmentValidatedExternalDto::class.java)

            logger.info(
                "Received StudentEnrollmentValidated for studentId={}, program={}",
                external.studentId, external.program
            )

            val studentId = StudentId(external.studentId)
            val enrollment = Enrollment(program = external.program, year = external.year)

            val thesisViews = masterThesisViewRepository.findByStudentId(studentId)

            if (thesisViews.isEmpty()) {
                logger.info("No master thesis found for studentId=${studentId}. Nothing to update.")
                return
            }

            thesisViews.forEach { view ->
                val thesisId = view.thesisId ?: return@forEach
                if (view.currentState == MasterThesisStatus.STUDENT_THESIS_REGISTRATION) {
                    logger.info("Sending ConfirmStudentEnrollmentForThesis to thesisId=$thesisId")
                    val cmd = ConfirmStudentEnrollmentForThesis(
                        thesisId = thesisId,
                        studentId = studentId,
                        enrollment = enrollment,
                        validationDate = external.validationDate
                    )
                    commandGateway.sendAndWait<Void>(cmd)
                } else {
                    logger.info("Thesis $thesisId is in state ${view.currentState} - skipping enrollment confirmation")
                }
            }
        } catch (ex: Exception) {
            logger.error("Failed to handle StudentEnrollmentValidated CloudEvent", ex)
            throw ex
        }
    }
}
