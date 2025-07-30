package mk.ukim.finki.soa.masterthesis.web.dto.mentor

import mk.ukim.finki.soa.masterthesis.model.valueObject.DocumentInfo
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.LocalDateTime

data class SubmitCommissionReportDto(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val reportDocument: DocumentInfo,
    val corrections: String?,
    val remarks: String?,
    val conclusions: String?,
    val submissionDate: LocalDateTime = LocalDateTime.now()
)
