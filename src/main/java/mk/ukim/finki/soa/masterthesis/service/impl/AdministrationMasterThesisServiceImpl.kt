package mk.ukim.finki.soa.masterthesis.service.impl

import mk.ukim.finki.soa.masterthesis.model.oldCommands.administration.ApproveCompletedThesisCommand
import mk.ukim.finki.soa.masterthesis.model.oldCommands.administration.EvaluateThesisCommand
import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import mk.ukim.finki.soa.masterthesis.service.AdministrationMasterThesisService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture


@Service
class AdministrationMasterThesisServiceImpl(
    val commandGateway: CommandGateway

) : AdministrationMasterThesisService {
    override fun approveCompletedThesis(command: ApproveCompletedThesisCommand): CompletableFuture<MasterThesisId> {
        return commandGateway.send(command)
    }

    override fun evaluateThesis(command: EvaluateThesisCommand): CompletableFuture<MasterThesisId> {
        return commandGateway.send(command)
    }
}
