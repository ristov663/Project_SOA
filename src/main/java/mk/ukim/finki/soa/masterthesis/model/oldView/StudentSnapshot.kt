package mk.ukim.finki.soa.masterthesis.model.oldView

import mk.ukim.finki.soa.masterthesis.model.valueObject.Email
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId

data class StudentSnapshot(
    val studentId: StudentId,
    val name: String,
    val lastName: String,
    val email: Email,
    val eventNumber: Long  // Tracks the last event that led to this snapshot
)
