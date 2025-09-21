package mk.ukim.finki.soa.masterthesis.service

import mk.ukim.finki.soa.masterthesis.model.command.student.SubmitThesisRegistration
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.service.impl.StudentMasterThesisServiceImpl
import org.axonframework.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.UUID
import java.util.concurrent.CompletableFuture

class StudentMasterThesisServiceTest {

    private lateinit var commandGateway: CommandGateway
    private lateinit var service: StudentMasterThesisServiceImpl

    @BeforeEach
    fun setup() {
        commandGateway = mock()
        service = StudentMasterThesisServiceImpl(commandGateway)
    }

    @Test
    fun `submit thesis registration delegates to gateway`() {
        val id = MasterThesisId(UUID.randomUUID().toString())
        whenever(commandGateway.send<MasterThesisId>(ArgumentMatchers.any()))
            .thenReturn(CompletableFuture.completedFuture(id))

        val command = mock<SubmitThesisRegistration>()
        service.submitThesisRegistration(command)
        verify(commandGateway).send<MasterThesisId>(command)
    }
}
