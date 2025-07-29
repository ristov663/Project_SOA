package mk.ukim.finki.soa.masterthesis.model.event

import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import java.time.LocalDateTime

data class ThesisRegistrationSubmitted(
    val thesisId: MasterThesisId,
    val studentId: StudentId,
    val mentorId: ProfessorId,
    val title: MasterThesisTitle,
    val shortDescription: MasterThesisDescription,
    val requiredDocuments: List<DocumentInfo>,
    val submissionDate: LocalDateTime,
    val newState: MasterThesisStatus = MasterThesisStatus.STUDENT_THESIS_REGISTRATION
)
