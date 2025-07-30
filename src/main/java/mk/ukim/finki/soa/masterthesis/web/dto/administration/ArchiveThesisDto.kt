package mk.ukim.finki.soa.masterthesis.web.dto.administration

import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import java.time.LocalDateTime

data class ArchiveThesisDto(
    val thesisId: MasterThesisId,
    val administratorId: ExternalUserId,
    val processValidated: Boolean,
    val remarks: String?,
    val archiveDate: LocalDateTime = LocalDateTime.now()
)
