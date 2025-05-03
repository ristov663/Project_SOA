package mk.ukim.finki.soa.masterthesis.model.valueObject

import jakarta.persistence.Embeddable

@Embeddable
data class ArchiveNumber(val value: String) {

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
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
