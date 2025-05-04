package mk.ukim.finki.soa.masterthesis.model.event.student

import mk.ukim.finki.soa.masterthesis.model.valueObject.*

data class ThesisProposalSubmittedEvent(
    val studentIndex: StudentIndex,
    val title: MasterThesisTitle,
    val area: MasterThesisArea,
    val description: MasterThesisDescription
)
