package mk.ukim.finki.soa.masterthesis.model.event.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentIndex
import java.time.ZonedDateTime

data class ThesisRegistrationInitiatedEvent(
    val studentIndex: StudentIndex,
    val initiatedAt: ZonedDateTime
)
