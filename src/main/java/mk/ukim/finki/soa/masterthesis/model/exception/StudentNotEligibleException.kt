package mk.ukim.finki.soa.masterthesis.model.exception

import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentIndex

class StudentNotEligibleException(
    val studentIndex: StudentIndex
) : RuntimeException("Student with index $studentIndex is not eligible to register for a thesis.")
