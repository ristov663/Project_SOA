package mk.ukim.finki.soa.masterthesis.web.dto

import mk.ukim.finki.soa.masterthesis.model.valueObject.*

data class SubmitThesisProposalCommandDto(
    val studentIndex: StudentIndex,
    val title: MasterThesisTitle,
    val area: MasterThesisArea,
    val description: MasterThesisDescription
)
