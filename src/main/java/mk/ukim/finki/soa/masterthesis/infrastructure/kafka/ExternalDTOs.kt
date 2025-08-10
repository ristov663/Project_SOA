package mk.ukim.finki.soa.masterthesis.infrastructure.kafka

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class StudentIdDTO(val value: String)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ThesisIdDTO(val value: String)

@JsonIgnoreProperties(ignoreUnknown = true)
data class StudentAccreditationStatusCheckedDTO(
    val studentId: StudentIdDTO,
    val isApproved: Boolean
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class StudentEnrollmentValidateDTO(
    val studentId: StudentIdDTO,
    val validated: Boolean
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ThesisCanceledDTO(
    val studentId: StudentIdDTO,
    val thesisId: ThesisIdDTO
)
