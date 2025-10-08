package mk.ukim.finki.soa.masterthesis.client

import mk.ukim.finki.soa.masterthesis.client.fallbacks.MentorValidationClientFallback
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "professor-semester-enrollment",
    fallback = MentorValidationClientFallback::class
)
interface MentorValidationClient {
    @GetMapping("/mentors/exists/{id}")
    fun existsMentor(@PathVariable id: ProfessorId): Boolean
}
