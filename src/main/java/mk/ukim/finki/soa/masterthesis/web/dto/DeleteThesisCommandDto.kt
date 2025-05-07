package mk.ukim.finki.soa.masterthesis.web.dto

import mk.ukim.finki.soa.masterthesis.model.valueObject.*

data class DeleteThesisCommandDto(
    val thesisId: MasterThesisId,
    val appRole: AppRole
)
