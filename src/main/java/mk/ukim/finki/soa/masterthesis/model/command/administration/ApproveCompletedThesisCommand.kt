package mk.ukim.finki.soa.masterthesis.model.command.administration

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.ZonedDateTime

data class ApproveCompletedThesisCommand(
    val thesisId: MasterThesisId,
    val approveId: ProfessorId,
    val finalGrade: Int,
    val timestamp: ZonedDateTime
)
