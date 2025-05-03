package mk.ukim.finki.soa.masterthesis.model.event.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import java.time.ZonedDateTime

data class ThesisRegistrationInitiatedEvent(
    val studentId: StudentId,
    val initiatedAt: ZonedDateTime
)
