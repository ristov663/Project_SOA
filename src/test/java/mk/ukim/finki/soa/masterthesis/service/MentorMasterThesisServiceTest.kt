package mk.ukim.finki.soa.masterthesis.service

import mk.ukim.finki.soa.masterthesis.model.command.mentor.*
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.service.impl.MentorMasterThesisServiceImpl
import org.axonframework.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.UUID
import java.util.concurrent.CompletableFuture

class MentorMasterThesisServiceTest {

    private lateinit var commandGateway: CommandGateway
    private lateinit var service: MentorMasterThesisServiceImpl

    @BeforeEach
    fun setup() {
        commandGateway = mock()
        service = MentorMasterThesisServiceImpl(commandGateway)
    }

    @Test
    fun `all mentor commands delegate to gateway`() {
        val id = MasterThesisId(UUID.randomUUID().toString())
        whenever(commandGateway.send<MasterThesisId>(ArgumentMatchers.any()))
            .thenReturn(CompletableFuture.completedFuture(id))

        val validate = mock<ValidateThesisByMentor>()
        service.validateThesisByMentor(validate)
        verify(commandGateway).send<MasterThesisId>(validate)

        val upload = mock<UploadThesisDraft>()
        service.uploadThesisDraft(upload)
        verify(commandGateway).send<MasterThesisId>(upload)

        val select = mock<SelectCommissionMembers>()
        service.selectCommissionMembers(select)
        verify(commandGateway).send<MasterThesisId>(select)

        val revised = mock<UploadRevisedThesisDraft>()
        service.uploadRevisedThesisDraft(revised)
        verify(commandGateway).send<MasterThesisId>(revised)

        val report = mock<SubmitCommissionReport>()
        service.submitCommissionReport(report)
        verify(commandGateway).send<MasterThesisId>(report)

        val schedule = mock<ScheduleThesisDefense>()
        service.scheduleThesisDefense(schedule)
        verify(commandGateway).send<MasterThesisId>(schedule)

        val mark = mock<MarkThesisAsDefended>()
        service.markThesisAsDefended(mark)
        verify(commandGateway).send<MasterThesisId>(mark)
    }
}
