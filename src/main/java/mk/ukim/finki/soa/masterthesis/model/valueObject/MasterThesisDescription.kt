package mk.ukim.finki.soa.masterthesis.model.valueObject

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class MasterThesisDescription(@Column(name = "thesis_description") val value: String) {

    protected constructor() : this("<<undefined>>")

    init {
        if (value != "<<undefined>>") {
            require(value.isNotBlank()) { "Description must not be blank." }
            require(value.length in 10..1000) { "Description must be between 10 and 1000 characters." }
        }
    }

    override fun toString(): String = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MasterThesisDescription
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
