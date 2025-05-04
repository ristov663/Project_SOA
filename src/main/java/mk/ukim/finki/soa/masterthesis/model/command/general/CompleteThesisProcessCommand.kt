package mk.ukim.finki.soa.masterthesis.model.command.general

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentIndex
import java.time.ZonedDateTime

data class CompleteThesisProcessCommand(
    val thesisId: MasterThesisId,
    val studentIndex: StudentIndex,
    val completionDate: ZonedDateTime,
    val timestamp: ZonedDateTime
)
