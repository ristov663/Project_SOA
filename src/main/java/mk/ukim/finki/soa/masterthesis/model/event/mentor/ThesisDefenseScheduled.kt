package mk.ukim.finki.soa.masterthesis.model.event.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.LocalDateTime

data class ThesisDefenseScheduled(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val defenseDate: LocalDateTime,
    val roomId: String,
    val remarks: String?,
    val schedulingDate: LocalDateTime,
    val newState: MasterThesisStatus = MasterThesisStatus.PROCESS_FINISHED
)