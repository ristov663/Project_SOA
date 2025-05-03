package mk.ukim.finki.soa.masterthesis.model.command.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import java.time.ZonedDateTime

data class InitiateThesisRegistrationCommand(
    val studentId: StudentId,
    val timestamp: ZonedDateTime
)
