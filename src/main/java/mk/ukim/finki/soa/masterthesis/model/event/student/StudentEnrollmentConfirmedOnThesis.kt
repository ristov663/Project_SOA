package mk.ukim.finki.soa.masterthesis.model.event.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.Enrollment
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import java.time.LocalDateTime

data class StudentEnrollmentConfirmedOnThesis(
    val thesisId: MasterThesisId,
    val studentId: StudentId,
    val enrollment: Enrollment,
    val validationDate: LocalDateTime
)
