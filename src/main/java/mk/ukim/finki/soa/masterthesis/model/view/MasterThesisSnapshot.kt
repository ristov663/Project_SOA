package mk.ukim.finki.soa.masterthesis.model.view

import mk.ukim.finki.soa.masterthesis.model.valueObject.ArchiveNumber
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisTitle
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId

data class MasterThesisSnapshot(
    val masterThesisId: MasterThesisId,
    val title: MasterThesisTitle,
    val archiveNumber: ArchiveNumber,
    val studentId: StudentId,
    val eventNumber: Long
)
