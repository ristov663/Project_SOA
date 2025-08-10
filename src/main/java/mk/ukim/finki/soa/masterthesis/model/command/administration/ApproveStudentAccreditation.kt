package mk.ukim.finki.soa.masterthesis.model.command.administration


data class ApproveStudentAccreditation(
    val studentId: String,
    val isApproved: Boolean
)
