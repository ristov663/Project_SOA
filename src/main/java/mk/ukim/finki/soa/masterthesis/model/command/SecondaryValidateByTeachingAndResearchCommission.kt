package mk.ukim.finki.soa.masterthesis.model.command

import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class SecondaryValidateByTeachingAndResearchCommission(
    @TargetAggregateIdentifier
    val thesisId: MasterThesisId,
    val commissionCoordinatorId: ExternalUserId,
    val approved: Boolean,
    val remarks: String?,
    val validationDate: LocalDateTime = LocalDateTime.now()
)
