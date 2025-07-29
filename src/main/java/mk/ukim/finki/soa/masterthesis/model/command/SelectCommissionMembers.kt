package mk.ukim.finki.soa.masterthesis.model.command

import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class SelectCommissionMembers(
    @TargetAggregateIdentifier
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val commissionMember1Id: ExternalUserId,
    val commissionMember2Id: ExternalUserId,
    val remarks: String?,
    val selectionDate: LocalDateTime = LocalDateTime.now()
)
