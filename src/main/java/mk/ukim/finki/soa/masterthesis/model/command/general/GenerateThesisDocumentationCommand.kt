package mk.ukim.finki.soa.masterthesis.model.command.general

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisDocumentType
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import java.time.ZonedDateTime

data class GenerateThesisDocumentationCommand(
    val thesisId: MasterThesisId,
    val documentType: MasterThesisDocumentType,
    val timestamp: ZonedDateTime
)
