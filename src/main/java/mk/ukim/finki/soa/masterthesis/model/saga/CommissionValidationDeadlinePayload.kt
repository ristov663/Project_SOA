package mk.ukim.finki.soa.masterthesis.model.saga

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import java.time.LocalDateTime

/**
 * Payload for deadline tokens to carry additional context
 */
data class CommissionValidationDeadlinePayload(
    val thesisId: MasterThesisId,
    val validationStartDate: LocalDateTime,
    val validationType: CommissionValidationType
)

enum class CommissionValidationType {
    FIRST_COMMISSION,
    SECOND_COMMISSION
}
