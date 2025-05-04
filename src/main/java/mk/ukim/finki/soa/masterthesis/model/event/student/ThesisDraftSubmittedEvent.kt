package mk.ukim.finki.soa.masterthesis.model.event.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisDocumentType
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentIndex
import java.time.ZonedDateTime

data class ThesisDraftSubmittedEvent(
    val thesisId: MasterThesisId,
    val studentIndex: StudentIndex,
    val draftDocumentType: MasterThesisDocumentType,
    val draftVersion: Int,
    val draftText: ByteArray,
    val submittedAt: ZonedDateTime
)
