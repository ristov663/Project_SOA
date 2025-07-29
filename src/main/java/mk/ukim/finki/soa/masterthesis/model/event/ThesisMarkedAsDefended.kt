package mk.ukim.finki.soa.masterthesis.model.event

import mk.ukim.finki.soa.masterthesis.model.valueObject.Grade
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import java.time.LocalDateTime

data class ThesisMarkedAsDefended(
    val thesisId: MasterThesisId,
    val defenseDate: LocalDateTime,
    val successful: Boolean,
    val finalGrade: Grade,
    val defenseRemarks: String?,
    val newState: MasterThesisStatus = MasterThesisStatus.FINISHED
)
