package mk.ukim.finki.soa.masterthesis.model.exception

import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId

class StudentNotEligibleException(
    val studentId: StudentId
) : RuntimeException("Student with id $studentId is not eligible to register for a thesis.")
