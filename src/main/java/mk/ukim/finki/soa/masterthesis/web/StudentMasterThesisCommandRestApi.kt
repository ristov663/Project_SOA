package mk.ukim.finki.soa.masterthesis.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mk.ukim.finki.soa.masterthesis.model.command.student.InitiateThesisRegistrationCommand
import mk.ukim.finki.soa.masterthesis.model.command.student.SubmitThesisDraftCommand
import mk.ukim.finki.soa.masterthesis.model.command.student.SubmitThesisProposalCommand
import mk.ukim.finki.soa.masterthesis.service.StudentMasterThesisService
import mk.ukim.finki.soa.masterthesis.web.dto.*


@RestController
@RequestMapping("/student/master-thesis")
@Tag(name = "Student Master Thesis Command API", description = "Commands by students for master thesis process.")
class StudentMasterThesisCommandRestApi(
    private val studentMasterThesisService: StudentMasterThesisService
) {

    @Operation(summary = "Initiate registration of master thesis")
    @PostMapping("/register")
    fun registerThesis(@RequestBody dto: InitiateThesisRegistrationCommandDto): ResponseEntity<Any> {
        val command = InitiateThesisRegistrationCommand(
            thesisId = dto.thesisId,
            timestamp = dto.timestamp,
            studentIndex = dto.studentIndex
        )
        studentMasterThesisService.initiateThesisRegistration(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Submit thesis proposal")
    @PostMapping("/submit-proposal")
    fun submitProposal(@RequestBody dto: SubmitThesisProposalCommandDto): ResponseEntity<Any> {
        val command = SubmitThesisProposalCommand(
            studentIndex = dto.studentIndex,
            title = dto.title,
            area = dto.area,
            description = dto.description
        )
        studentMasterThesisService.submitThesisProposal(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Submit thesis draft")
    @PostMapping("/submit-draft")
    fun submitDraft(@RequestBody dto: SubmitThesisDraftCommandDto): ResponseEntity<Any> {
        val command = SubmitThesisDraftCommand(
            thesisId = dto.thesisId,
            studentIndex = dto.studentIndex,
            draftDocumentType = dto.draftDocumentType,
            draftVersion = dto.draftVersion,
            draftText = dto.draftText,
            submittedAt = dto.submittedAt,
        )
        studentMasterThesisService.submitThesisDraft(command)
        return ResponseEntity.ok().build()
    }
}
