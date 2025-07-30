package mk.ukim.finki.soa.masterthesis.model.command.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class ScheduleThesisDefense(
    @TargetAggregateIdentifier
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val defenseDate: LocalDateTime,
    val roomId: String,
    val remarks: String?,
    val schedulingDate: LocalDateTime = LocalDateTime.now()
)
