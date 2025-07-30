package mk.ukim.finki.soa.masterthesis.model.event.administration

import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import java.time.LocalDateTime

data class ThesisValidatedByCommission(
    val thesisId: MasterThesisId,
    val commissionCoordinatorId: ExternalUserId,
    val approved: Boolean,
    val remarks: String?,
    val validationDate: LocalDateTime,
    val newState: MasterThesisStatus = MasterThesisStatus.COMMISSION_VALIDATION
)