package mk.ukim.finki.soa.masterthesis.web.dto.administration

import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import java.time.LocalDateTime

data class ValidateThesisByAdministrationDto(
    val thesisId: MasterThesisId,
    val administratorId: ExternalUserId,
    val approved: Boolean,
    val remarks: String?,
    val documentsVerified: Boolean,
    val studentEligibilityConfirmed: Boolean,
    val validationDate: LocalDateTime = LocalDateTime.now()
)
