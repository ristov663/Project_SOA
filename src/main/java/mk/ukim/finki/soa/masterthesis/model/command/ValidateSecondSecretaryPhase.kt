package mk.ukim.finki.soa.masterthesis.model.command

import mk.ukim.finki.soa.masterthesis.model.valueObject.ArchiveNumber
import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class ValidateSecondSecretaryPhase(
    @TargetAggregateIdentifier
    val thesisId: MasterThesisId,
    val secretaryId: ExternalUserId,
    val commissionArchiveNumber: ArchiveNumber,
    val remarks: String?,
    val validationDate: LocalDateTime = LocalDateTime.now()
)
