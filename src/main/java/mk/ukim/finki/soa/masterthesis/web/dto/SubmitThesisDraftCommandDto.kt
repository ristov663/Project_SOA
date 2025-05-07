package mk.ukim.finki.soa.masterthesis.web.dto

import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import java.time.ZonedDateTime

data class SubmitThesisDraftCommandDto(
    val thesisId: MasterThesisId,
    val studentIndex: StudentIndex,
    val draftDocumentType: MasterThesisDocumentType,
    val draftVersion: Int,
    val draftText: ByteArray,
    val submittedAt: ZonedDateTime
)
