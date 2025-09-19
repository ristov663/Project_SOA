package mk.ukim.finki.soa.masterthesis.service

import mk.ukim.finki.soa.masterthesis.model.command.administration.ApproveStudentAccreditation
import mk.ukim.finki.soa.masterthesis.model.command.administration.ArchiveThesis
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateStudentEnrollment
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import java.util.concurrent.CompletableFuture

interface MasterThesisModificationService {
    fun approveAccreditation(command: ApproveStudentAccreditation): CompletableFuture<MasterThesisId>
    fun validateEnrollment(command: ValidateStudentEnrollment): CompletableFuture<MasterThesisId>
    fun archiveThesis(command: ArchiveThesis): CompletableFuture<MasterThesisId>
}
