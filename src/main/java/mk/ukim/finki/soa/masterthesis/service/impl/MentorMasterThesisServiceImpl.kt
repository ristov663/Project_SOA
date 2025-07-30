package mk.ukim.finki.soa.masterthesis.service.impl

import mk.ukim.finki.soa.masterthesis.model.command.mentor.MarkThesisAsDefended
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ScheduleThesisDefense
import mk.ukim.finki.soa.masterthesis.model.command.mentor.SelectCommissionMembers
import mk.ukim.finki.soa.masterthesis.model.command.mentor.SubmitCommissionReport
import mk.ukim.finki.soa.masterthesis.model.command.mentor.UploadRevisedThesisDraft
import mk.ukim.finki.soa.masterthesis.model.command.mentor.UploadThesisDraft
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ValidateThesisByMentor
import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import mk.ukim.finki.soa.masterthesis.service.MentorMasterThesisService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture


@Service
class MentorMasterThesisServiceImpl(
    val commandGateway: CommandGateway

) : MentorMasterThesisService {
    override fun validateThesisByMentor(command: ValidateThesisByMentor): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

    override fun uploadThesisDraft(command: UploadThesisDraft): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

    override fun selectCommissionMembers(command: SelectCommissionMembers): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

    override fun uploadRevisedThesisDraft(command: UploadRevisedThesisDraft): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

    override fun submitCommissionReport(command: SubmitCommissionReport): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

    override fun scheduleThesisDefense(command: ScheduleThesisDefense): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

    override fun markThesisAsDefended(command: MarkThesisAsDefended): CompletableFuture<MasterThesisId> =
        commandGateway.send(command)

}
