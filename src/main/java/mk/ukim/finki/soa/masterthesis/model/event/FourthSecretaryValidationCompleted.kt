package mk.ukim.finki.soa.masterthesis.model.event

import mk.ukim.finki.soa.masterthesis.model.valueObject.ArchiveNumber
import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import java.time.LocalDateTime

data class FourthSecretaryValidationCompleted(
    val thesisId: MasterThesisId,
    val secretaryId: ExternalUserId,
    val archiveNumber: ArchiveNumber,
    val remarks: String?,
    val validationDate: LocalDateTime,
    val newState: MasterThesisStatus = MasterThesisStatus.FOURTH_SECRETARY_VALIDATION
)
