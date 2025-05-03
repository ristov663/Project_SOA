package mk.ukim.finki.soa.masterthesis.model.event.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.ZonedDateTime

data class ThesisProposalApprovedEvent(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val comments: String,
    val approvedAt: ZonedDateTime
)
