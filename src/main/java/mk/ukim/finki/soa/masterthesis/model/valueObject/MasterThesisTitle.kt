package mk.ukim.finki.soa.masterthesis.model.valueObject

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class MasterThesisTitle(@Column(name = "title") val value: String) {

    protected constructor() : this("")

    init {
        require(value.isNotBlank()) { "Master thesis title must not be blank." }
        require(value.length in 5..255) { "Title must be between 5 and 255 characters." }
    }

    override fun toString(): String = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MasterThesisTitle
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
