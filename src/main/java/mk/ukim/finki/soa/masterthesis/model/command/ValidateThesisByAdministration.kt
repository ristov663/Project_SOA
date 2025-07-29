package mk.ukim.finki.soa.masterthesis.model.command

import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class ValidateThesisByAdministration(
    @TargetAggregateIdentifier
    val thesisId: MasterThesisId,
    val administratorId: ExternalUserId,
    val approved: Boolean,
    val remarks: String?,
    val documentsVerified: Boolean,
    val studentEligibilityConfirmed: Boolean,
    val validationDate: LocalDateTime = LocalDateTime.now()
)
