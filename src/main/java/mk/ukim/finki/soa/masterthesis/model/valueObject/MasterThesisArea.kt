package mk.ukim.finki.soa.masterthesis.model.valueObject

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class MasterThesisArea(@Column(name = "field") val value: String) {

    protected constructor() : this("")

    init {
        require(value.isNotBlank()) { "Area must not be blank." }
        require(value.length in 3..100) { "Area must be between 3 and 100 characters." }
    }

    override fun toString(): String = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MasterThesisArea
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
