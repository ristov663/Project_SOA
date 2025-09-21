package mk.ukim.finki.soa.masterthesis.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mk.ukim.finki.soa.masterthesis.model.command.system.AutoApproveCommissionValidation
import mk.ukim.finki.soa.masterthesis.model.command.system.AutoApproveSecondCommissionValidation
import mk.ukim.finki.soa.masterthesis.service.SystemMasterThesisService
import org.springframework.security.access.prepost.PreAuthorize

@RestController
@RequestMapping("/system/master-thesis")
@Tag(
    name = "System Master Thesis Command API",
    description = "Commands by system for master thesis process."
)
class SystemMasterThesisCommandRestApi(
    private val systemMasterThesisService: SystemMasterThesisService
) {

    @Operation(summary = "Auto approve commission validation",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/approve-commission")
    fun approveCommissionValidation(@RequestBody dto: AutoApproveCommissionValidation): ResponseEntity<Any> {
        val command = AutoApproveCommissionValidation(
            thesisId = dto.thesisId,
            autoApprovalDate = dto.autoApprovalDate,
            reason = dto.reason
        )

        systemMasterThesisService.autoApproveCommissionValidation(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Auto approve second commission validation",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/approve-second-commission")
    fun approveSecondCommissionValidation(@RequestBody dto: AutoApproveSecondCommissionValidation): ResponseEntity<Any> {
        val command = AutoApproveSecondCommissionValidation(
            thesisId = dto.thesisId,
            autoApprovalDate = dto.autoApprovalDate,
            reason = dto.reason
        )

        systemMasterThesisService.autoApproveSecondCommissionValidation(command)
        return ResponseEntity.ok().build()
    }
}
