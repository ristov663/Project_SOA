package mk.ukim.finki.soa.masterthesis.model.valueObject

import jakarta.persistence.Embeddable
import mk.ukim.finki.soa.masterthesis.model.common.Identifier
import mk.ukim.finki.soa.masterthesis.model.view.MasterThesisSnapshot
import java.util.*

@Embeddable
class MasterThesisId(value: String) : Identifier<MasterThesisSnapshot>(value, MasterThesisSnapshot::class.java) {

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
