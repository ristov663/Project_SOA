package mk.ukim.finki.soa.masterthesis.web.dto.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.Grade
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import java.time.LocalDateTime

data class MarkThesisAsDefendedDto(
    val thesisId: MasterThesisId,
    val defenseDate: LocalDateTime,
    val successful: Boolean,
    val finalGrade: Grade,
    val defenseRemarks: String?
)
