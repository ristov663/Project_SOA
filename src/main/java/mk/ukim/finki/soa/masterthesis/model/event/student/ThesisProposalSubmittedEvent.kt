package mk.ukim.finki.soa.masterthesis.model.event.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisDocumentType
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import java.time.ZonedDateTime

data class ThesisProposalSubmittedEvent(
    val studentId: StudentId,
    val proposalDocument: MasterThesisDocumentType,
    val submittedAt: ZonedDateTime
)
