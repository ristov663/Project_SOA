package mk.ukim.finki.soa.masterthesis.web.dto.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.DocumentInfo
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.LocalDateTime

data class UploadRevisedThesisDraftDto(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val revisedDraftDocument: DocumentInfo,
    val remarks: String?,
    val uploadDate: LocalDateTime = LocalDateTime.now()
)
