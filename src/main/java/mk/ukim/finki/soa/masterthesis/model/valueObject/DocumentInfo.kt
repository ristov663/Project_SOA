package mk.ukim.finki.soa.masterthesis.model.valueObject

import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType
import java.time.LocalDateTime
import java.util.UUID

@Embeddable
data class DocumentInfo(
    val documentId: UUID,
    val fileName: String,
    val fileSize: Long,
    val mimeType: String,
    val uploadDate: LocalDateTime,
    @Embedded
    val uploadedBy: StudentId,
    @Enumerated(EnumType.STRING)
    val documentType: MasterThesisDocumentType
) {
    // No-arg constructor for JPA
    protected constructor() : this(
        UUID(0, 0),
        "",
        0L,
        "",
        LocalDateTime.MIN,
        StudentId(""),
        MasterThesisDocumentType.THESIS_JUSTIFICATION
    )
}
