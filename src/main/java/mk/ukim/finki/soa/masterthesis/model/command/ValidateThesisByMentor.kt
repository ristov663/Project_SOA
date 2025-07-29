package mk.ukim.finki.soa.masterthesis.model.command

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisArea
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class ValidateThesisByMentor(
    @TargetAggregateIdentifier
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val approved: Boolean,
    val remarks: String?,
    val field: MasterThesisArea?,
    val validationDate: LocalDateTime = LocalDateTime.now()
)
