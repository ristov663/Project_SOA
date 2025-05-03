package mk.ukim.finki.soa.masterthesis.model.event.general

import mk.ukim.finki.soa.masterthesis.model.valueObject.AppRole
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import java.time.ZonedDateTime

data class ThesisStatusUpdatedEvent(
    val thesisId: MasterThesisId,
    val newStatus: MasterThesisStatus,
    val reason: String,
    val updatedBy: AppRole,
    val updatedAt: ZonedDateTime
)
