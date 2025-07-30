package mk.ukim.finki.soa.masterthesis.web.dto.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.LocalDateTime

data class SelectCommissionMembersDto(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val commissionMember1Id: ExternalUserId,
    val commissionMember2Id: ExternalUserId,
    val remarks: String?,
    val selectionDate: LocalDateTime = LocalDateTime.now()
)
