package mk.ukim.finki.soa.masterthesis.model.event.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.DocumentInfo
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.LocalDateTime

data class ThesisDraftUploaded(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val draftDocument: DocumentInfo,
    val remarks: String?,
    val uploadDate: LocalDateTime,
    val newState: MasterThesisStatus = MasterThesisStatus.STUDENT_DRAFT
)