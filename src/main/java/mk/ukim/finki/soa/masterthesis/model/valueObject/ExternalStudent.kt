package mk.ukim.finki.soa.masterthesis.model.valueObject

data class ExternalStudent(
    val id: ExternalId,
    val index: StudentIndex,
    val name: String,
    val email: Email,
)
