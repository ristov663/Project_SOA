package mk.ukim.finki.soa.masterthesis.model.valueObject

import jakarta.persistence.Embeddable

@Embeddable
data class Enrollment(
    val program: String,
    val year: Int
) {
    protected constructor() : this("", 0)
}
