package mk.ukim.finki.soa.masterthesis.model.command.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.DocumentInfo
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class SubmitCommissionReport(
    @TargetAggregateIdentifier
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val reportDocument: DocumentInfo,
    val corrections: String?,
    val remarks: String?,
    val conclusions: String?,
    val submissionDate: LocalDateTime = LocalDateTime.now()
)
