package mk.ukim.finki.soa.masterthesis.model.command.mentor

import com.google.type.DateTime
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId

data class MentorReviewProposalCommand(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val comments: String,
    val approved: Boolean,
    val timestamp: DateTime
)
