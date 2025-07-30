package mk.ukim.finki.soa.masterthesis.model.command.system

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class AutoApproveSecondCommissionValidation(
    @TargetAggregateIdentifier
    val thesisId: MasterThesisId,
    val autoApprovalDate: LocalDateTime = LocalDateTime.now(),
    val reason: String?
)
