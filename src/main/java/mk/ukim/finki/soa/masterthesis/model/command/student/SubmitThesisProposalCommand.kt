package mk.ukim.finki.soa.masterthesis.model.command.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisDocumentType
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import java.time.ZonedDateTime

data class SubmitThesisProposalCommand(
    val studentId: StudentId,
    val proposalDocument: MasterThesisDocumentType,
    val timestamp: ZonedDateTime
)
