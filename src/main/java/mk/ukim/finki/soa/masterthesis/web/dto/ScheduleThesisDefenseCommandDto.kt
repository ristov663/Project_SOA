package mk.ukim.finki.soa.masterthesis.web.dto

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.ZonedDateTime

data class ScheduleThesisDefenseCommandDto (
    val thesisId: MasterThesisId,
    val defenseDate: ZonedDateTime,
    val location: String,
    val panelMembers: List<ProfessorId>,
    val timestamp: ZonedDateTime
)
