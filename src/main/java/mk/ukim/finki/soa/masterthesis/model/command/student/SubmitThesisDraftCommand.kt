package mk.ukim.finki.soa.masterthesis.model.command.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisDocumentType
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import java.time.ZonedDateTime

data class SubmitThesisDraftCommand(
    val thesisId: MasterThesisId,
    val studentId: StudentId,
    val draftDocument: MasterThesisDocumentType,
    val draftVersion: Int,
    val timestamp: ZonedDateTime
)
