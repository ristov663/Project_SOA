package mk.ukim.finki.soa.masterthesis.model.event.general

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisDocumentType
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import java.time.ZonedDateTime

data class ThesisDocumentationGeneratedEvent(
    val thesisId: MasterThesisId,
    val documentType: MasterThesisDocumentType,
    val generatedAt: ZonedDateTime
)
