package mk.ukim.finki.soa.masterthesis.service.impl

import mk.ukim.finki.soa.masterthesis.model.command.administration.ApproveStudentAccreditation
import mk.ukim.finki.soa.masterthesis.model.command.administration.ArchiveThesis
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateStudentEnrollment
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.service.MasterThesisModificationService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class MasterThesisModificationServiceImpl(
    private val commandGateway: CommandGateway
) : MasterThesisModificationService {

    override fun approveAccreditation(command: ApproveStudentAccreditation): CompletableFuture<MasterThesisId> {
        return commandGateway.send(command)
    }

    override fun validateEnrollment(command: ValidateStudentEnrollment): CompletableFuture<MasterThesisId> {
        return commandGateway.send(command)
    }

    override fun archiveThesis(command: ArchiveThesis): CompletableFuture<MasterThesisId> {
        return commandGateway.send(command)
    }
}
