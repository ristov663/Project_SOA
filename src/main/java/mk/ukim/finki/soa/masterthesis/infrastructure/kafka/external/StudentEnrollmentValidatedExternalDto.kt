package mk.ukim.finki.soa.masterthesis.infrastructure.kafka.external

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class StudentEnrollmentValidatedExternalDto @JsonCreator constructor(
    @JsonProperty("studentId") val studentId: String = "",
    @JsonProperty("studentIndex") val studentIndex: String? = null,
    @JsonProperty("program") val program: String = "",
    @JsonProperty("year") val year: Int = 0,
    @JsonProperty("validationDate") val validationDate: LocalDateTime = LocalDateTime.now()
)
