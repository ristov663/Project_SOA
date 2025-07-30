package mk.ukim.finki.soa.masterthesis.service.impl

import mk.ukim.finki.soa.masterthesis.model.command.administration.ArchiveThesis
import mk.ukim.finki.soa.masterthesis.model.command.administration.SecondaryValidateByTeachingAndResearchCommission
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateFourthSecretaryPhase
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateSecondSecretaryPhase
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThesisByAdministration
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThesisByCommission
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThesisBySecretary
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThirdSecretaryPhase
import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import mk.ukim.finki.soa.masterthesis.service.AdministrationMasterThesisService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class AdministrationMasterThesisServiceImpl(
    val commandGateway: CommandGateway

) : AdministrationMasterThesisService {
    override fun validateThesisByAdministration(command: ValidateThesisByAdministration): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

    override fun validateThesisByCommission(command: ValidateThesisByCommission): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

    override fun validateThesisBySecretary(command: ValidateThesisBySecretary): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

    override fun validateSecondSecretaryPhase(command: ValidateSecondSecretaryPhase): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

    override fun validateThirdSecretaryPhase(command: ValidateThirdSecretaryPhase): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

    override fun validateFourthSecretaryPhase(command: ValidateFourthSecretaryPhase): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

    override fun secondaryValidateByTeachingAndResearchCommission(command: SecondaryValidateByTeachingAndResearchCommission): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

    override fun archiveThesis(command: ArchiveThesis): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

}
