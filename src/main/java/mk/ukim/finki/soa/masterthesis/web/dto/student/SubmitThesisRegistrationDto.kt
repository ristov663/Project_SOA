package mk.ukim.finki.soa.masterthesis.web.dto.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.DocumentInfo
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisDescription
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisTitle
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import java.time.LocalDateTime

data class SubmitThesisRegistrationDto(
    val thesisId: MasterThesisId,
    val studentId: StudentId,
    val mentorId: ProfessorId,
    val title: MasterThesisTitle,
    val shortDescription: MasterThesisDescription,
    val requiredDocuments: List<DocumentInfo>,
    val submissionDate: LocalDateTime = LocalDateTime.now()
)
