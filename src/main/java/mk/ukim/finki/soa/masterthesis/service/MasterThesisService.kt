package mk.ukim.finki.soa.masterthesis.service

import mk.ukim.finki.soa.masterthesis.model.command.administration.ApproveCompletedThesisCommand
import mk.ukim.finki.soa.masterthesis.model.command.administration.EvaluateThesisCommand
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ApproveThesisProposalCommand
import mk.ukim.finki.soa.masterthesis.model.command.mentor.MentorReviewProposalCommand
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ReviewThesisDraftCommand
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ScheduleThesisDefenseCommand
import mk.ukim.finki.soa.masterthesis.model.command.student.InitiateThesisRegistrationCommand
import mk.ukim.finki.soa.masterthesis.model.command.student.SubmitThesisDraftCommand
import mk.ukim.finki.soa.masterthesis.model.command.student.SubmitThesisProposalCommand
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import java.util.concurrent.CompletableFuture

interface StudentMasterThesisService {
    fun isStudentEligible(studentIndex: String): Boolean
    fun initiateThesisRegistration(command: InitiateThesisRegistrationCommand): CompletableFuture<MasterThesisId>
    fun submitThesisProposal(command: SubmitThesisProposalCommand): CompletableFuture<MasterThesisId>
    fun submitThesisDraft(command: SubmitThesisDraftCommand): CompletableFuture<MasterThesisId>
}

interface MentorMasterThesisService {
    fun approveThesisProposal(command: ApproveThesisProposalCommand): CompletableFuture<MasterThesisId>
    fun mentorReviewProposal(command: MentorReviewProposalCommand): CompletableFuture<MasterThesisId>
    fun reviewThesisDraft(command: ReviewThesisDraftCommand): CompletableFuture<MasterThesisId>
    fun scheduleThesisDefense(command: ScheduleThesisDefenseCommand): CompletableFuture<MasterThesisId>
}

interface AdministrationMasterThesisService {
    fun approveCompletedThesis(command: ApproveCompletedThesisCommand): CompletableFuture<MasterThesisId>
    fun evaluateThesis(command: EvaluateThesisCommand): CompletableFuture<MasterThesisId>
}
