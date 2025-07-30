package mk.ukim.finki.soa.masterthesis.model.event.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisArea
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.LocalDateTime

data class ThesisValidatedByMentor(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val approved: Boolean,
    val remarks: String?,
    val field: MasterThesisArea?,
    val validationDate: LocalDateTime,
    val newState: MasterThesisStatus = MasterThesisStatus.MENTOR_VALIDATION
)