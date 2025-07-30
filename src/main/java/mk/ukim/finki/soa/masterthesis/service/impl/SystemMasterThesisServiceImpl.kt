package mk.ukim.finki.soa.masterthesis.service.impl

import mk.ukim.finki.soa.masterthesis.model.command.system.AutoApproveCommissionValidation
import mk.ukim.finki.soa.masterthesis.model.command.system.AutoApproveSecondCommissionValidation
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.service.SystemMasterThesisService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class SystemMasterThesisServiceImpl(
    val commandGateway: CommandGateway

) : SystemMasterThesisService {
    override fun autoApproveCommissionValidation(command: AutoApproveCommissionValidation): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

    override fun autoApproveSecondCommissionValidation(command: AutoApproveSecondCommissionValidation): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

}
