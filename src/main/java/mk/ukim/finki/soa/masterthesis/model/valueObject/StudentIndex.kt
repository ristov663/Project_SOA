package mk.ukim.finki.soa.masterthesis.model.valueObject

import jakarta.persistence.Embeddable

@Embeddable
data class StudentIndex(val value: String) {

    protected constructor() : this("")

    init {
        require(value.isNotBlank()) { "Student index must not be blank." }
        require(value.matches(Regex("^\\d{6}$"))) {
            "Student index must be exactly 6 digits: $value"
        }
    }

    override fun toString(): String = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as StudentIndex
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
