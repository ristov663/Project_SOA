package mk.ukim.finki.soa.masterthesis.model.valueObject

import java.time.LocalDateTime
import java.util.UUID

data class DocumentInfo(
    val documentId: UUID,
    val fileName: String,
    val fileSize: Long,
    val mimeType: String,
    val uploadDate: LocalDateTime,
    val uploadedBy: StudentId,
    val documentType: MasterThesisDocumentType
)
