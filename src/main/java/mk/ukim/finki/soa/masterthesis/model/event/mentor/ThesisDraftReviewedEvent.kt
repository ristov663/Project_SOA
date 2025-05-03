package mk.ukim.finki.soa.masterthesis.model.event.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.ZonedDateTime

data class ThesisDraftReviewedEvent(
    val thesisId: MasterThesisId,
    val reviewerId: ProfessorId,
    val feedback: String,
    val requestRevisions: Boolean,
    val reviewedAt: ZonedDateTime
)
