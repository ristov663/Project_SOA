package mk.ukim.finki.soa.masterthesis.model.event.administration

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.ZonedDateTime

data class CompletedThesisApprovedEvent(
    val thesisId: MasterThesisId,
    val approveId: ProfessorId,
    val finalGrade: String,
    val approvedAt: ZonedDateTime
)
