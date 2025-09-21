package mk.ukim.finki.soa.masterthesis.service

import mk.ukim.finki.soa.masterthesis.model.command.administration.*
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.service.impl.AdministrationMasterThesisServiceImpl
import org.axonframework.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.UUID
import java.util.concurrent.CompletableFuture

class AdministrationMasterThesisServiceTest {

    private lateinit var commandGateway: CommandGateway
    private lateinit var service: AdministrationMasterThesisServiceImpl

    @BeforeEach
    fun setup() {
        commandGateway = mock()
        service = AdministrationMasterThesisServiceImpl(commandGateway)
    }

    @Test
    fun `all administration commands delegate to gateway`() {
        val id = MasterThesisId(UUID.randomUUID().toString())
        whenever(commandGateway.send<MasterThesisId>(ArgumentMatchers.any()))
            .thenReturn(CompletableFuture.completedFuture(id))

        val validateAdministration = mock<ValidateThesisByAdministration>()
        service.validateThesisByAdministration(validateAdministration)
        verify(commandGateway).send<MasterThesisId>(validateAdministration)

        val validateCommission = mock<ValidateThesisByCommission>()
        service.validateThesisByCommission(validateCommission)
        verify(commandGateway).send<MasterThesisId>(validateCommission)

        val validateSecretary = mock<ValidateThesisBySecretary>()
        service.validateThesisBySecretary(validateSecretary)
        verify(commandGateway).send<MasterThesisId>(validateSecretary)

        val secondSecretaryPhase = mock<ValidateSecondSecretaryPhase>()
        service.validateSecondSecretaryPhase(secondSecretaryPhase)
        verify(commandGateway).send<MasterThesisId>(secondSecretaryPhase)

        val thirdSecretaryPhase = mock<ValidateThirdSecretaryPhase>()
        service.validateThirdSecretaryPhase(thirdSecretaryPhase)
        verify(commandGateway).send<MasterThesisId>(thirdSecretaryPhase)

        val fourthSecretaryPhase = mock<ValidateFourthSecretaryPhase>()
        service.validateFourthSecretaryPhase(fourthSecretaryPhase)
        verify(commandGateway).send<MasterThesisId>(fourthSecretaryPhase)

        val secondaryValidate = mock<SecondaryValidateByTeachingAndResearchCommission>()
        service.secondaryValidateByTeachingAndResearchCommission(secondaryValidate)
        verify(commandGateway).send<MasterThesisId>(secondaryValidate)

        val archive = mock<ArchiveThesis>()
        service.archiveThesis(archive)
        verify(commandGateway).send<MasterThesisId>(archive)
    }
}
