package mk.ukim.finki.soa.masterthesis.model.command.administration

data class ValidateStudentEnrollment(
    val studentId: String,
    val validated: Boolean
)