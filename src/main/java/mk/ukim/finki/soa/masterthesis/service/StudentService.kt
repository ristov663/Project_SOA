package mk.ukim.finki.soa.masterthesis.service

import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentIndex

interface StudentService {
    fun isStudentEligible(studentIndex: StudentIndex): Boolean
}
