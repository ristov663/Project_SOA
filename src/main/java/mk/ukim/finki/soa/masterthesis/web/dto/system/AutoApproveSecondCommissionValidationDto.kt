package mk.ukim.finki.soa.masterthesis.web.dto.system

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import java.time.LocalDateTime

data class AutoApproveSecondCommissionValidationDto(
    val thesisId: MasterThesisId,
    val autoApprovalDate: LocalDateTime = LocalDateTime.now(),
    val reason: String?
)
