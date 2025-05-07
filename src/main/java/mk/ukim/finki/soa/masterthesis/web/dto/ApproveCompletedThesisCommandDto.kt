package mk.ukim.finki.soa.masterthesis.web.dto

import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import java.time.ZonedDateTime

data class ApproveCompletedThesisCommandDto(
    val thesisId: MasterThesisId,
    val approveId: ProfessorId,
    val finalGrade: Int,
    val timestamp: ZonedDateTime
)
