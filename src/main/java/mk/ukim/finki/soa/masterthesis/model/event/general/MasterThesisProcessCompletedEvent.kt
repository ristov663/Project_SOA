package mk.ukim.finki.soa.masterthesis.model.event.general

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import java.time.ZonedDateTime

data class MasterThesisProcessCompletedEvent(
    val thesisId: MasterThesisId,
    val studentId: StudentId,
    val completionDate: ZonedDateTime,
    val recordedAt: ZonedDateTime
)
