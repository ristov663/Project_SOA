package mk.ukim.finki.soa.masterthesis.web.dto

import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import java.time.ZonedDateTime

data class InitiateThesisRegistrationCommandDto(
    val studentIndex: StudentIndex,
    val thesisId: MasterThesisId,
    val timestamp: ZonedDateTime
)
