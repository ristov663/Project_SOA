package mk.ukim.finki.soa.masterthesis.service.impl

import mk.ukim.finki.soa.masterthesis.model.command.mentor.ApproveThesisProposalCommand
import mk.ukim.finki.soa.masterthesis.model.command.mentor.MentorReviewProposalCommand
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ReviewThesisDraftCommand
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ScheduleThesisDefenseCommand
import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import mk.ukim.finki.soa.masterthesis.service.MentorMasterThesisService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture


@Service
class MentorMasterThesisServiceImpl(
    val commandGateway: CommandGateway

) : MentorMasterThesisService {
    override fun approveThesisProposal(command: ApproveThesisProposalCommand): CompletableFuture<MasterThesisId> {
        return commandGateway.send(command)
    }

    override fun mentorReviewProposal(command: MentorReviewProposalCommand): CompletableFuture<MasterThesisId> {
        return commandGateway.send(command)
    }

    override fun reviewThesisDraft(command: ReviewThesisDraftCommand): CompletableFuture<MasterThesisId> {
        return commandGateway.send(command)
    }

    override fun scheduleThesisDefense(command: ScheduleThesisDefenseCommand): CompletableFuture<MasterThesisId> {
        return commandGateway.send(command)
    }
}
