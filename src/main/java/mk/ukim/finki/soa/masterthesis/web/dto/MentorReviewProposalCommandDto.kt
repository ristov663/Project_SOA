package mk.ukim.finki.soa.masterthesis.web.dto

import com.google.type.DateTime
import mk.ukim.finki.soa.masterthesis.model.valueObject.*

data class MentorReviewProposalCommandDto(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val comments: String,
    val approved: Boolean,
    val timestamp: DateTime
)
