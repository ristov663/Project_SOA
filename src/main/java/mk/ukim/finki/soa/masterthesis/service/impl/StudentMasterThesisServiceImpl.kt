package mk.ukim.finki.soa.masterthesis.service.impl

import mk.ukim.finki.soa.masterthesis.model.command.student.SubmitThesisRegistration
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.service.StudentMasterThesisService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class StudentMasterThesisServiceImpl(
    val commandGateway: CommandGateway

) : StudentMasterThesisService {
    override fun submitThesisRegistration(command: SubmitThesisRegistration): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

}
