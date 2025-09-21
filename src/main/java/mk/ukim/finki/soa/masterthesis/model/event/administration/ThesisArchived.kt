package mk.ukim.finki.soa.masterthesis.model.event.administration

import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import java.time.LocalDateTime

data class ThesisArchived(
    val thesisId: MasterThesisId,
    val administratorId: ExternalUserId,
    val archiveProcessValidated: Boolean,
    val remarks: String?,
    val archiveDate: LocalDateTime,
    val newState: MasterThesisStatus = MasterThesisStatus.ADMINISTRATION_ARCHIVING
)