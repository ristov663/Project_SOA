package mk.ukim.finki.soa.masterthesis.model.event.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.Email
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentIndex

data class StudentCreatedExternalEvent(
    val id: StudentId,
    val index: StudentIndex,
    val name: String,
    val email: Email,
)
