package mk.ukim.finki.soa.masterthesis.service

import mk.ukim.finki.soa.masterthesis.model.command.system.AutoApproveCommissionValidation
import mk.ukim.finki.soa.masterthesis.model.command.system.AutoApproveSecondCommissionValidation
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.service.impl.SystemMasterThesisServiceImpl
import org.axonframework.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.UUID
import java.util.concurrent.CompletableFuture

class SystemMasterThesisServiceTest {

    private lateinit var commandGateway: CommandGateway
    private lateinit var service: SystemMasterThesisServiceImpl

    @BeforeEach
    fun setup() {
        commandGateway = mock()
        service = SystemMasterThesisServiceImpl(commandGateway)
    }

    @Test
    fun `auto approve commission validation delegates`() {
        val id = MasterThesisId(UUID.randomUUID().toString())
        whenever(commandGateway.send<MasterThesisId>(ArgumentMatchers.any()))
            .thenReturn(CompletableFuture.completedFuture(id))

        val command1 = mock<AutoApproveCommissionValidation>()
        service.autoApproveCommissionValidation(command1)
        verify(commandGateway).send<MasterThesisId>(command1)

        val command2 = mock<AutoApproveSecondCommissionValidation>()
        service.autoApproveSecondCommissionValidation(command2)
        verify(commandGateway).send<MasterThesisId>(command2)
    }
}
