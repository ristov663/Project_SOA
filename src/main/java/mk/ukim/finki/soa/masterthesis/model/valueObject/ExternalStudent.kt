package mk.ukim.finki.soa.masterthesis.model.valueObject

data class ExternalStudent(
    val id: ExternalUserId,
    val index: StudentIndex,
    val name: String,
    val email: Email,
)
