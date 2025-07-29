package mk.ukim.finki.soa.masterthesis.model.command

import mk.ukim.finki.soa.masterthesis.model.valueObject.Grade
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class MarkThesisAsDefended(
    @TargetAggregateIdentifier
    val thesisId: MasterThesisId,
    val defenseDate: LocalDateTime,
    val successful: Boolean,
    val finalGrade: Grade,
    val defenseRemarks: String?
)
