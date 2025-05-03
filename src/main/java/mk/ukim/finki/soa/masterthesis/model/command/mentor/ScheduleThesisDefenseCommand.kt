package mk.ukim.finki.soa.masterthesis.model.command.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.ZonedDateTime

data class ScheduleThesisDefenseCommand(
    val thesisId: MasterThesisId,
    val defenseDate: ZonedDateTime,
    val location: String, // RoomId (valueObject to be add)
    val panelMembers: List<ProfessorId>,
    val timestamp: ZonedDateTime
)
