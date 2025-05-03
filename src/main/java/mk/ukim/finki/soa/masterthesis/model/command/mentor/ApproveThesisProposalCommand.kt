package mk.ukim.finki.soa.masterthesis.model.command.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.ZonedDateTime

data class ApproveThesisProposalCommand(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val comments: String,
    val timestamp: ZonedDateTime
)
