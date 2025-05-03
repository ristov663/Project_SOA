package mk.ukim.finki.soa.masterthesis.model.command.general

import mk.ukim.finki.soa.masterthesis.model.valueObject.AppRole
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import java.time.ZonedDateTime

data class UpdateThesisStatusCommand(
    val thesisId: MasterThesisId,
    val newStatus: MasterThesisStatus,
    val reason: String,
    val updatedBy: AppRole,
    val timestamp: ZonedDateTime
)
