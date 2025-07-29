package mk.ukim.finki.soa.masterthesis.model.command

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class AutoApproveCommissionValidation(
    @TargetAggregateIdentifier
    val thesisId: MasterThesisId,
    val autoApprovalDate: LocalDateTime = LocalDateTime.now(),
    val reason: String?
)
