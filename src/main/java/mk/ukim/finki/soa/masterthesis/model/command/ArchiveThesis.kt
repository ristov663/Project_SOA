package mk.ukim.finki.soa.masterthesis.model.command

import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class ArchiveThesis(
    @TargetAggregateIdentifier
    val thesisId: MasterThesisId,
    val administratorId: ExternalUserId,
    val processValidated: Boolean,
    val remarks: String?,
    val archiveDate: LocalDateTime = LocalDateTime.now()
)
