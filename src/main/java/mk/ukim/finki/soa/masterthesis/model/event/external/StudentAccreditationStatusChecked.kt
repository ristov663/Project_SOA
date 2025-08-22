package mk.ukim.finki.soa.masterthesis.model.event.external

import mk.ukim.finki.soa.masterthesis.model.event.AbstractEvent
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId

data class StudentAccreditationStatusChecked(
    val studentId: StudentId,
    val isApproved: Boolean
) : AbstractEvent(studentId)
