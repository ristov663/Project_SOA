package mk.ukim.finki.soa.masterthesis.model.exception

import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId

class MentorNotEligibleException(
    val mentorId: ProfessorId
) : RuntimeException("Mentor with id $mentorId is not eligible.")
