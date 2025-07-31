package mk.ukim.finki.soa.masterthesis.model.valueObject

import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import java.time.LocalDateTime

@Embeddable
data class SecretaryValidation(
    @Embedded
    val secretaryId: ExternalUserId = ExternalUserId(""),

    @Embedded
    val archiveNumber: ArchiveNumber = ArchiveNumber(""),

    val validationDate: LocalDateTime = LocalDateTime.MIN,

    val phase: Int = 0
)
