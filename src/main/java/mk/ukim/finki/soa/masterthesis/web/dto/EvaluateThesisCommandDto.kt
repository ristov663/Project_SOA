package mk.ukim.finki.soa.masterthesis.web.dto

import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import java.time.ZonedDateTime

data class EvaluateThesisCommandDto(
    val thesisId: MasterThesisId,
    val committeeId: AppRole,
    val evaluation: Int,
    val isApproved: Boolean,
    val timestamp: ZonedDateTime
)
