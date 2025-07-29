package mk.ukim.finki.soa.masterthesis.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mk.ukim.finki.soa.masterthesis.model.oldCommands.administration.ApproveCompletedThesisCommand
import mk.ukim.finki.soa.masterthesis.model.oldCommands.administration.EvaluateThesisCommand
import mk.ukim.finki.soa.masterthesis.service.AdministrationMasterThesisService
import mk.ukim.finki.soa.masterthesis.web.dto.*


@RestController
@RequestMapping("/admin/master-thesis")
@Tag(name = "Administration Master Thesis Command API", description = "Commands by administrative staff.")
class AdministrationMasterThesisCommandRestApi(
    private val administrationMasterThesisService: AdministrationMasterThesisService,
) {

    @Operation(summary = "Approve completed thesis")
    @PostMapping("/approve-completed-thesis")
    fun approveCompletedThesis(@RequestBody dto: ApproveCompletedThesisCommand): ResponseEntity<Any> {
        val command = ApproveCompletedThesisCommand(
            thesisId = dto.thesisId,
            approveId = dto.approveId,
            finalGrade = dto.finalGrade,
            timestamp = dto.timestamp
        )
        administrationMasterThesisService.approveCompletedThesis(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Mentor schedule thesis defense")
    @PostMapping("/schedule-defense")
    fun evaluateThesis(@RequestBody dto: EvaluateThesisCommandDto): ResponseEntity<Any> {
        val command = EvaluateThesisCommand(
            thesisId = dto.thesisId,
            committeeId = dto.committeeId,
            evaluation = dto.evaluation,
            isApproved = dto.isApproved,
            timestamp = dto.timestamp
        )
        administrationMasterThesisService.evaluateThesis(command)
        return ResponseEntity.ok().build()
    }
}
