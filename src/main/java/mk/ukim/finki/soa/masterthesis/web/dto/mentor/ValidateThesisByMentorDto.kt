package mk.ukim.finki.soa.masterthesis.web.dto.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisArea
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.LocalDateTime

data class ValidateThesisByMentorDto(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val approved: Boolean,
    val remarks: String?,
    val field: MasterThesisArea?,
    val validationDate: LocalDateTime = LocalDateTime.now()
)
