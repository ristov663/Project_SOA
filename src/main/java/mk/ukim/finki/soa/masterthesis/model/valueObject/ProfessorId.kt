package mk.ukim.finki.soa.masterthesis.model.valueObject

import jakarta.persistence.Embeddable
import mk.ukim.finki.soa.masterthesis.model.common.Identifier
import mk.ukim.finki.soa.masterthesis.model.oldView.ProfessorSnapshot
import java.util.*

@Embeddable
class ProfessorId(value: String) : Identifier<ProfessorSnapshot>(value, ProfessorSnapshot::class.java) {
    constructor() : this(UUID.randomUUID().toString())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false
        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
