package mk.ukim.finki.soa.masterthesis.service

import mk.ukim.finki.soa.masterthesis.model.command.administration.ArchiveThesis
import mk.ukim.finki.soa.masterthesis.model.command.system.AutoApproveCommissionValidation
import mk.ukim.finki.soa.masterthesis.model.command.system.AutoApproveSecondCommissionValidation
import mk.ukim.finki.soa.masterthesis.model.command.mentor.MarkThesisAsDefended
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ScheduleThesisDefense
import mk.ukim.finki.soa.masterthesis.model.command.administration.SecondaryValidateByTeachingAndResearchCommission
import mk.ukim.finki.soa.masterthesis.model.command.mentor.SelectCommissionMembers
import mk.ukim.finki.soa.masterthesis.model.command.mentor.SubmitCommissionReport
import mk.ukim.finki.soa.masterthesis.model.command.student.SubmitThesisRegistration
import mk.ukim.finki.soa.masterthesis.model.command.mentor.UploadRevisedThesisDraft
import mk.ukim.finki.soa.masterthesis.model.command.mentor.UploadThesisDraft
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateFourthSecretaryPhase
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateSecondSecretaryPhase
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThesisByAdministration
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThesisByCommission
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ValidateThesisByMentor
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThesisBySecretary
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThirdSecretaryPhase
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import java.util.concurrent.CompletableFuture

interface StudentMasterThesisService {
    fun submitThesisRegistration(command: SubmitThesisRegistration): CompletableFuture<MasterThesisId>
}

interface MentorMasterThesisService {
    fun validateThesisByMentor(command: ValidateThesisByMentor): CompletableFuture<MasterThesisId>
    fun uploadThesisDraft(command: UploadThesisDraft): CompletableFuture<MasterThesisId>
    fun selectCommissionMembers(command: SelectCommissionMembers): CompletableFuture<MasterThesisId>
    fun uploadRevisedThesisDraft(command: UploadRevisedThesisDraft): CompletableFuture<MasterThesisId>
    fun submitCommissionReport(command: SubmitCommissionReport): CompletableFuture<MasterThesisId>
    fun scheduleThesisDefense(command: ScheduleThesisDefense): CompletableFuture<MasterThesisId>
    fun markThesisAsDefended(command: MarkThesisAsDefended): CompletableFuture<MasterThesisId>
}

interface AdministrationMasterThesisService {
    fun validateThesisByAdministration(command: ValidateThesisByAdministration): CompletableFuture<MasterThesisId>
    fun validateThesisByCommission(command: ValidateThesisByCommission): CompletableFuture<MasterThesisId>
    fun validateThesisBySecretary(command: ValidateThesisBySecretary): CompletableFuture<MasterThesisId>
    fun validateSecondSecretaryPhase(command: ValidateSecondSecretaryPhase): CompletableFuture<MasterThesisId>
    fun validateThirdSecretaryPhase(command: ValidateThirdSecretaryPhase): CompletableFuture<MasterThesisId>
    fun validateFourthSecretaryPhase(command: ValidateFourthSecretaryPhase): CompletableFuture<MasterThesisId>
    fun secondaryValidateByTeachingAndResearchCommission(command: SecondaryValidateByTeachingAndResearchCommission): CompletableFuture<MasterThesisId>
    fun archiveThesis(command: ArchiveThesis): CompletableFuture<MasterThesisId>
}

interface SystemMasterThesisService {
    fun autoApproveCommissionValidation(command: AutoApproveCommissionValidation): CompletableFuture<MasterThesisId>
    fun autoApproveSecondCommissionValidation(command: AutoApproveSecondCommissionValidation): CompletableFuture<MasterThesisId>
}
