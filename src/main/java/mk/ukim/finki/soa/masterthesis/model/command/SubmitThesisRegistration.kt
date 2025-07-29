package mk.ukim.finki.soa.masterthesis.model.command

import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class SubmitThesisRegistration(
    @TargetAggregateIdentifier
    val thesisId: MasterThesisId,
    val studentId: StudentId,
    val mentorId: ProfessorId,
    val title: MasterThesisTitle,
    val shortDescription: MasterThesisDescription,
    val requiredDocuments: List<DocumentInfo>,
    val submissionDate: LocalDateTime = LocalDateTime.now()
)
