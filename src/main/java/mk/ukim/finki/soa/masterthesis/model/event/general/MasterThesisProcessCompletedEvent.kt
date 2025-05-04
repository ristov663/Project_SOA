package mk.ukim.finki.soa.masterthesis.model.event.general

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentIndex
import java.time.ZonedDateTime

data class MasterThesisProcessCompletedEvent(
    val thesisId: MasterThesisId,
    val studentIndex: StudentIndex,
    val completionDate: ZonedDateTime,
    val recordedAt: ZonedDateTime
)
