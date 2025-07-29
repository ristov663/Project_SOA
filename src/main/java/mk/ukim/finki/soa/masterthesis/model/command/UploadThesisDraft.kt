package mk.ukim.finki.soa.masterthesis.model.command

import mk.ukim.finki.soa.masterthesis.model.valueObject.DocumentInfo
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class UploadThesisDraft(
    @TargetAggregateIdentifier
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val draftDocumentType: DocumentInfo,
    val remarks: String?,
    val uploadDate: LocalDateTime = LocalDateTime.now()
)
