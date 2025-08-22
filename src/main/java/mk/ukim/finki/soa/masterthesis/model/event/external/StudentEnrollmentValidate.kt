package mk.ukim.finki.soa.masterthesis.model.event.external

import mk.ukim.finki.soa.masterthesis.model.event.AbstractEvent
import mk.ukim.finki.soa.masterthesis.model.valueObject.Email
import mk.ukim.finki.soa.masterthesis.model.valueObject.Enrollment
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentIndex

class StudentEnrollmentValidate(
    val studentId: StudentId,
    val name: String,
    val email: Email,
    val studentIndex: StudentIndex,
    val enrollment: Enrollment,
    val validated: Boolean
) : AbstractEvent(studentId)
