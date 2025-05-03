package mk.ukim.finki.soa.masterthesis.model.event.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisDocumentType
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import java.time.ZonedDateTime

data class ThesisDraftSubmittedEvent(
    val thesisId: MasterThesisId,
    val studentId: StudentId,
    val draftDocument: MasterThesisDocumentType,
    val draftVersion: Int,
    val submittedAt: ZonedDateTime
)
