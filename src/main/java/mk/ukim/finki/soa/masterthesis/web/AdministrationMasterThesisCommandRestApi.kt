package mk.ukim.finki.soa.masterthesis.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mk.ukim.finki.soa.masterthesis.model.command.administration.ArchiveThesis
import mk.ukim.finki.soa.masterthesis.model.command.administration.SecondaryValidateByTeachingAndResearchCommission
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateFourthSecretaryPhase
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateSecondSecretaryPhase
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThesisByAdministration
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThesisByCommission
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThesisBySecretary
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThirdSecretaryPhase
import mk.ukim.finki.soa.masterthesis.service.AdministrationMasterThesisService
import mk.ukim.finki.soa.masterthesis.web.dto.administration.ArchiveThesisDto
import mk.ukim.finki.soa.masterthesis.web.dto.administration.SecondaryValidateByTeachingAndResearchCommissionDto
import mk.ukim.finki.soa.masterthesis.web.dto.administration.ValidateFourthSecretaryPhaseDto
import mk.ukim.finki.soa.masterthesis.web.dto.administration.ValidateSecondSecretaryPhaseDto
import mk.ukim.finki.soa.masterthesis.web.dto.administration.ValidateThesisByAdministrationDto
import mk.ukim.finki.soa.masterthesis.web.dto.administration.ValidateThesisByCommissionDto
import mk.ukim.finki.soa.masterthesis.web.dto.administration.ValidateThesisBySecretaryDto
import mk.ukim.finki.soa.masterthesis.web.dto.administration.ValidateThirdSecretaryPhaseDto


@RestController
@RequestMapping("/administration/master-thesis")
@Tag(
    name = "Administration Master Thesis Command API",
    description = "Commands by administrative staff."
)
class AdministrationMasterThesisCommandRestApi(
    private val administrationMasterThesisService: AdministrationMasterThesisService,
) {

    @Operation(summary = "Archive thesis",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/archive-thesis")
    fun archiveThesis(@RequestBody dto: ArchiveThesisDto): ResponseEntity<Any> {
        val command = ArchiveThesis(
            thesisId = dto.thesisId,
            administratorId = dto.administratorId,
            processValidated = dto.processValidated,
            remarks = dto.remarks,
            archiveDate = dto.archiveDate
        )

        administrationMasterThesisService.archiveThesis(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Secondary validate by teaching and research commission",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/secondary-validate-by-teaching-commission")
    fun secondaryValidateByTeachingAndSearchingCommission(@RequestBody dto: SecondaryValidateByTeachingAndResearchCommissionDto): ResponseEntity<Any> {
        val command = SecondaryValidateByTeachingAndResearchCommission(
            thesisId = dto.thesisId,
            commissionCoordinatorId = dto.commissionCoordinatorId,
            approved = dto.approved,
            remarks = dto.remarks,
            validationDate = dto.validationDate
        )
        administrationMasterThesisService.secondaryValidateByTeachingAndResearchCommission(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Validate thesis by administration",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/validate-by-administration")
    fun validateThesisByAdministration(@RequestBody dto: ValidateThesisByAdministrationDto): ResponseEntity<Any> {
        val command = ValidateThesisByAdministration(
            thesisId = dto.thesisId,
            administratorId = dto.administratorId,
            approved = dto.approved,
            remarks = dto.remarks,
            documentsVerified = dto.documentsVerified,
            studentEligibilityConfirmed = dto.studentEligibilityConfirmed,
            validationDate = dto.validationDate
        )

        administrationMasterThesisService.validateThesisByAdministration(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Validate thesis by commission",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/validate-by-commission")
    fun validateThesisByCommission(@RequestBody dto: ValidateThesisByCommissionDto): ResponseEntity<Any> {
        val command = ValidateThesisByCommission(
            thesisId = dto.thesisId,
            commissionCoordinatorId = dto.commissionCoordinatorId,
            approved = dto.approved,
            remarks = dto.remarks,
            validationDate = dto.validationDate
        )

        administrationMasterThesisService.validateThesisByCommission(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Validate thesis by secretary",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/validate-by-secretary")
    fun validateThesisBySecretary(@RequestBody dto: ValidateThesisBySecretaryDto): ResponseEntity<Any> {
        val command = ValidateThesisBySecretary(
            thesisId = dto.thesisId,
            secretaryId = dto.secretaryId,
            archiveNumber = dto.archiveNumber,
            remarks = dto.remarks,
            validationDate = dto.validationDate
        )

        administrationMasterThesisService.validateThesisBySecretary(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Validate second secretary phase",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/validate-second-secretary-phase")
    fun validateSecondSecretaryPhase(@RequestBody dto: ValidateSecondSecretaryPhaseDto): ResponseEntity<Any> {
        val command = ValidateSecondSecretaryPhase(
            thesisId = dto.thesisId,
            secretaryId = dto.secretaryId,
            commissionArchiveNumber = dto.commissionArchiveNumber,
            remarks = dto.remarks,
            validationDate = dto.validationDate
        )

        administrationMasterThesisService.validateSecondSecretaryPhase(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Validate third secretary phase",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/validate-third-secretary-phase")
    fun validateThirdSecretaryPhase(@RequestBody dto: ValidateThirdSecretaryPhaseDto): ResponseEntity<Any> {
        val command = ValidateThirdSecretaryPhase(
            thesisId = dto.thesisId,
            secretaryId = dto.secretaryId,
            archiveNumber = dto.archiveNumber,
            remarks = dto.remarks,
            validationDate = dto.validationDate
        )

        administrationMasterThesisService.validateThirdSecretaryPhase(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Validate fourth secretary phase",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/validate-fourth-secretary-phase")
    fun validateFourthSecretaryPhase(@RequestBody dto: ValidateFourthSecretaryPhaseDto): ResponseEntity<Any> {
        val command = ValidateFourthSecretaryPhase(
            thesisId = dto.thesisId,
            secretaryId = dto.secretaryId,
            archiveNumber = dto.archiveNumber,
            remarks = dto.remarks,
            validationDate = dto.validationDate
        )

        administrationMasterThesisService.validateFourthSecretaryPhase(command)
        return ResponseEntity.ok().build()
    }
}
