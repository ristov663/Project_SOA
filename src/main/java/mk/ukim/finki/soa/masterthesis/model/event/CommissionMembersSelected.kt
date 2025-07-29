package mk.ukim.finki.soa.masterthesis.model.event

import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.LocalDateTime

data class CommissionMembersSelected(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val commissionMember1Id: ExternalUserId,
    val commissionMember2Id: ExternalUserId,
    val remarks: String?,
    val selectionDate: LocalDateTime,
    val newState: MasterThesisStatus = MasterThesisStatus.MENTOR_COMMISSION_CHOICE
)
