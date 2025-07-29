package mk.ukim.finki.soa.masterthesis.model.view

import mk.ukim.finki.soa.masterthesis.model.valueObject.Email
import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId

data class ExternalUserSnapshot (
    val externalUserId: ExternalUserId,
    val name: String,
    val email: Email,
    val orderingRank: Int,
    val officeName: String,
    val eventNumber: Long
)
