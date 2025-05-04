package mk.ukim.finki.soa.masterthesis.service

interface StudentService {
    fun isStudentEligible(studentIndex: String): Boolean
}
