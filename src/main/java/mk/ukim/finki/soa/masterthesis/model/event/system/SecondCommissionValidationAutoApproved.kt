package mk.ukim.finki.soa.masterthesis.model.event.system

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import java.time.LocalDateTime

data class SecondCommissionValidationAutoApproved(
    val thesisId: MasterThesisId,
    val autoApprovalDate: LocalDateTime,
    val reason: String,
    val newState: MasterThesisStatus = MasterThesisStatus.COMMISSION_CHECK
)