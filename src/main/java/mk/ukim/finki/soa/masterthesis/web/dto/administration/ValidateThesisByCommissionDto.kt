package mk.ukim.finki.soa.masterthesis.web.dto.administration

import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import java.time.LocalDateTime

data class ValidateThesisByCommissionDto(
    val thesisId: MasterThesisId,
    val commissionCoordinatorId: ExternalUserId,
    val approved: Boolean,
    val remarks: String?,
    val validationDate: LocalDateTime = LocalDateTime.now()
)
