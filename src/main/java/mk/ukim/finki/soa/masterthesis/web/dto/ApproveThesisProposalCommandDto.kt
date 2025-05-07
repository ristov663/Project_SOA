package mk.ukim.finki.soa.masterthesis.web.dto

import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import java.time.ZonedDateTime

data class ApproveThesisProposalCommandDto(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val comments: String,
    val timestamp: ZonedDateTime
)
