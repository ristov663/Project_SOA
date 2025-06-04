package mk.ukim.finki.soa.masterthesis.client.fallbacks

import mk.ukim.finki.soa.masterthesis.client.StudentSemesterEnrollmentClient
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import org.springframework.stereotype.Component

@Component
class StudentSemesterEnrollmentClientFallback : StudentSemesterEnrollmentClient {
    override fun existsStudent(id: StudentId): Boolean {
        return false
    }
}
