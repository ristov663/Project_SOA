package mk.ukim.finki.soa.masterthesis.web.dto.administration

import mk.ukim.finki.soa.masterthesis.model.valueObject.ArchiveNumber
import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import java.time.LocalDateTime

data class ValidateThesisBySecretaryDto(
    val thesisId: MasterThesisId,
    val secretaryId: ExternalUserId,
    val archiveNumber: ArchiveNumber,
    val remarks: String?,
    val validationDate: LocalDateTime = LocalDateTime.now()
)
