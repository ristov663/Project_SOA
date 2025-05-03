package mk.ukim.finki.soa.masterthesis.model.event.general

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.ZonedDateTime

data class MentorAssignedToThesisEvent(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val assignedAt: ZonedDateTime
)
