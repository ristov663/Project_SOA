package mk.ukim.finki.soa.masterthesis.client

import mk.ukim.finki.soa.masterthesis.client.fallbacks.StudentSemesterEnrollmentClientFallback
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "student-semester-enrollment",
    fallback = StudentSemesterEnrollmentClientFallback::class
)
interface StudentSemesterEnrollmentClient {
    @GetMapping("/students/exists/{id}")
    fun existsStudent(@PathVariable id: StudentId): Boolean
}
