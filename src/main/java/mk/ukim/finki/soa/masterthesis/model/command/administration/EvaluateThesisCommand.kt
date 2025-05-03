package mk.ukim.finki.soa.masterthesis.model.command.administration

import mk.ukim.finki.soa.masterthesis.model.valueObject.AppRole
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import java.time.ZonedDateTime

data class EvaluateThesisCommand(
    val thesisId: MasterThesisId,
    val committeeId: AppRole,
    val evaluation: String,
    val isApproved: Boolean,
    val timestamp: ZonedDateTime
)
