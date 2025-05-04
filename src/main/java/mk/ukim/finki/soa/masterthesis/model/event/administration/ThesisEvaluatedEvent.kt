package mk.ukim.finki.soa.masterthesis.model.event.administration

import mk.ukim.finki.soa.masterthesis.model.valueObject.AppRole
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import java.time.ZonedDateTime

data class ThesisEvaluatedEvent(
    val thesisId: MasterThesisId,
    val committeeId: AppRole,
    val evaluation: Int,
    val isApproved: Boolean,
    val evaluatedAt: ZonedDateTime
)
