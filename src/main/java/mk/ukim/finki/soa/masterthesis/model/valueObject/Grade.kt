package mk.ukim.finki.soa.masterthesis.model.valueObject

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Grade(@Column(name = "grade") val value: Int) {

    protected constructor() : this(0)

    init {
        // Only validate when value is not 0 (0 used for internal persistence instantiation)
        if (value != 0) {
            require(value in 5..10) { "Grade must be between 5 and 10." }
        }
    }

    override fun toString(): String = value.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Grade
        return value == other.value
    }

    override fun hashCode(): Int = value.hashCode()
}
