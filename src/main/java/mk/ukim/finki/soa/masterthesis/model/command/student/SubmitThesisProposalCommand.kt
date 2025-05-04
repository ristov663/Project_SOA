package mk.ukim.finki.soa.masterthesis.model.command.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.*

data class SubmitThesisProposalCommand(
    val studentIndex: StudentIndex,
    val title: MasterThesisTitle,
    val area: MasterThesisArea,
    val description: MasterThesisDescription
)
