package mk.ukim.finki.soa.masterthesis.service.impl

import mk.ukim.finki.soa.masterthesis.model.command.student.InitiateThesisRegistrationCommand
import mk.ukim.finki.soa.masterthesis.model.command.student.SubmitThesisDraftCommand
import mk.ukim.finki.soa.masterthesis.model.command.student.SubmitThesisProposalCommand
import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import mk.ukim.finki.soa.masterthesis.service.StudentMasterThesisService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture


@Service
class StudentMasterThesisServiceImpl(
    val commandGateway: CommandGateway

) : StudentMasterThesisService {

    override fun isStudentEligible(studentIndex: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun initiateThesisRegistration(command: InitiateThesisRegistrationCommand): CompletableFuture<MasterThesisId> {
        return commandGateway.send(command)
    }

    override fun submitThesisProposal(command: SubmitThesisProposalCommand): CompletableFuture<MasterThesisId> {
        return commandGateway.send(command)
    }

    override fun submitThesisDraft(command: SubmitThesisDraftCommand): CompletableFuture<MasterThesisId> {
        return commandGateway.send(command)
    }
}
