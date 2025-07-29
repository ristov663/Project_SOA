package mk.ukim.finki.soa.masterthesis.model.event

import mk.ukim.finki.soa.masterthesis.model.valueObject.DocumentInfo
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisStatus
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import java.time.LocalDateTime

data class CommissionReportSubmitted(
    val thesisId: MasterThesisId,
    val mentorId: ProfessorId,
    val reportDocument: DocumentInfo,
    val corrections: String?,
    val remarks: String?,
    val conclusions: String?,
    val submissionDate: LocalDateTime,
    val newState: MasterThesisStatus = MasterThesisStatus.REPORT_VALIDATION
)
