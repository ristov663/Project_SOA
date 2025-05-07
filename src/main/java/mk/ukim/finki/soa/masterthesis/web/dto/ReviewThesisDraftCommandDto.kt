package mk.ukim.finki.soa.masterthesis.web.dto

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.ZonedDateTime

data class ReviewThesisDraftCommandDto (
    val thesisId: MasterThesisId,
    val reviewerId: ProfessorId,
    val feedback: String,
    val requestRevisions: Boolean,
    val timestamp: ZonedDateTime
)
