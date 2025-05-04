package mk.ukim.finki.soa.masterthesis.model.command.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentIndex
import java.time.ZonedDateTime

data class InitiateThesisRegistrationCommand(
    val studentIndex: StudentIndex,
    val timestamp: ZonedDateTime
)
