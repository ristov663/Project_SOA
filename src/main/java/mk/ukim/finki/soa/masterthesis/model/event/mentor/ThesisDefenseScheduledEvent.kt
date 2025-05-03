package mk.ukim.finki.soa.masterthesis.model.event.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.ZonedDateTime

data class ThesisDefenseScheduledEvent(
    val thesisId: MasterThesisId,
    val defenseDate: ZonedDateTime,
    val location: String, // RoomId (valueObject to be added)
    val panelMembers: List<ProfessorId>,
    val scheduledAt: ZonedDateTime
)
