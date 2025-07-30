package mk.ukim.finki.soa.masterthesis.model.event.administration

import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import java.time.LocalDateTime

data class ThesisValidatedByAdministration(
    val thesisId: MasterThesisId,
    val administratorId: ExternalUserId,
    val approved: Boolean,
    val remarks: String?,
    val documentsVerified: Boolean,
    val studentEligibilityConfirmed: Boolean,
    val validationDate: LocalDateTime,
    val newState: MasterThesisStatus = MasterThesisStatus.ADMINISTRATION_VALIDATION
)