package mk.ukim.finki.soa.masterthesis.infrastructure.kafka

import mk.ukim.finki.soa.masterthesis.model.command.administration.ApproveStudentAccreditation
import mk.ukim.finki.soa.masterthesis.model.command.administration.ArchiveThesis
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateStudentEnrollment
import mk.ukim.finki.soa.masterthesis.model.valueObject.ExternalUserId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MasterThesisEventTranslator {

    fun toApproveAccreditationCommand(event: StudentAccreditationStatusCheckedDTO): ApproveStudentAccreditation {
        return ApproveStudentAccreditation(
            studentId = event.studentId.value,
            isApproved = event.isApproved
        )
    }

    fun toValidateEnrollmentCommand(event: StudentEnrollmentValidateDTO): ValidateStudentEnrollment {
        return ValidateStudentEnrollment(
            studentId = event.studentId.value,
            validated = event.validated
        )
    }

    fun toArchiveThesisCommand(event: ThesisCanceledDTO): ArchiveThesis {
        return ArchiveThesis(
            thesisId = MasterThesisId(event.thesisId.value),
            administratorId = ExternalUserId("system"),
            processValidated = false,
            remarks = "Thesis canceled by external event",
            archiveDate = LocalDateTime.now()
        )
    }
}
