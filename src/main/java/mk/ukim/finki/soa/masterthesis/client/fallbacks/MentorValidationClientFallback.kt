package mk.ukim.finki.soa.masterthesis.client.fallbacks

import mk.ukim.finki.soa.masterthesis.client.MentorValidationClient
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import org.springframework.stereotype.Component

@Component
class MentorValidationClientFallback : MentorValidationClient {
    override fun existsMentor(id: ProfessorId): Boolean {
        return true
    }
}
