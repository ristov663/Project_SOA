package mk.ukim.finki.soa.masterthesis.model.command.general

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.ZonedDateTime

data class AssignMentorToThesisCommand(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val timestamp: ZonedDateTime
)
