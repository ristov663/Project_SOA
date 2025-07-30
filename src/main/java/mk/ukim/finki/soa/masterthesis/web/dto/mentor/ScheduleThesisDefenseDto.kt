package mk.ukim.finki.soa.masterthesis.web.dto.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.LocalDateTime

data class ScheduleThesisDefenseDto(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val defenseDate: LocalDateTime,
    val roomId: String,
    val remarks: String?,
    val schedulingDate: LocalDateTime = LocalDateTime.now()
)
