package mk.ukim.finki.soa.masterthesis.model.view

import mk.ukim.finki.soa.masterthesis.model.valueObject.Email
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorId
import mk.ukim.finki.soa.masterthesis.model.valueObject.ProfessorTitle

data class ProfessorSnapshot(
    val professorId: ProfessorId,
    val name: String,
    val email: Email,
    val professorTitle: ProfessorTitle,
    val orderingRank: Int,
    val officeName: String,
    val eventNumber: Long
)