package mk.ukim.finki.soa.masterthesis.model.command.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.ZonedDateTime

data class ReviewThesisDraftCommand(
    val thesisId: MasterThesisId,
    val reviewerId: ProfessorId,
    val feedback: String,
    val requestRevisions: Boolean,
    val timestamp: ZonedDateTime
)
