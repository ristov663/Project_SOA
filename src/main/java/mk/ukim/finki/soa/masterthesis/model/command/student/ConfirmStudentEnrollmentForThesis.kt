package mk.ukim.finki.soa.masterthesis.model.command.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.Enrollment
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class ConfirmStudentEnrollmentForThesis(
    @TargetAggregateIdentifier
    val thesisId: MasterThesisId,
    val studentId: StudentId,
    val enrollment: Enrollment,
    val validationDate: LocalDateTime
)