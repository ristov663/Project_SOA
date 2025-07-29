package mk.ukim.finki.soa.masterthesis.model.event

import mk.ukim.finki.soa.masterthesis.model.valueObject.DocumentInfo
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.LocalDateTime

data class RevisedThesisDraftUploaded(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val revisedDraftDocument: DocumentInfo,
    val previousDraftArchived: Boolean,
    val remarks: String?,
    val uploadDate: LocalDateTime,
    val newState: MasterThesisStatus = MasterThesisStatus.DRAFT_CHECK
)
