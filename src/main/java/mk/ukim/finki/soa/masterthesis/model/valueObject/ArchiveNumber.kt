package mk.ukim.finki.soa.masterthesis.model.valueObject

import jakarta.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
data class ArchiveNumber(
    val value: String,
    val archiveDate: LocalDateTime? = null
) {
    protected constructor() : this("")

    init {
        require(value.isNotBlank()) { "Archive number must not be blank." }
        require(value.matches(Regex("^[A-Za-z0-9/\\-_.]+$"))) {
            "Invalid archive number format: $value"
        }
    }

    override fun toString(): String = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ArchiveNumber
        return value == other.value && archiveDate == other.archiveDate
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + (archiveDate?.hashCode() ?: 0)
        return result
    }
}
