package mk.ukim.finki.soa.masterthesis.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mk.ukim.finki.soa.masterthesis.model.command.mentor.MarkThesisAsDefended
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ScheduleThesisDefense
import mk.ukim.finki.soa.masterthesis.model.command.mentor.SelectCommissionMembers
import mk.ukim.finki.soa.masterthesis.model.command.mentor.SubmitCommissionReport
import mk.ukim.finki.soa.masterthesis.model.command.mentor.UploadRevisedThesisDraft
import mk.ukim.finki.soa.masterthesis.model.command.mentor.UploadThesisDraft
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ValidateThesisByMentor
import mk.ukim.finki.soa.masterthesis.service.MentorMasterThesisService
import mk.ukim.finki.soa.masterthesis.web.dto.mentor.MarkThesisAsDefendedDto
import mk.ukim.finki.soa.masterthesis.web.dto.mentor.ScheduleThesisDefenseDto
import mk.ukim.finki.soa.masterthesis.web.dto.mentor.SelectCommissionMembersDto
import mk.ukim.finki.soa.masterthesis.web.dto.mentor.SubmitCommissionReportDto
import mk.ukim.finki.soa.masterthesis.web.dto.mentor.UploadRevisedThesisDraftDto
import mk.ukim.finki.soa.masterthesis.web.dto.mentor.UploadThesisDraftDto
import mk.ukim.finki.soa.masterthesis.web.dto.mentor.ValidateThesisByMentorDto

@RestController
@RequestMapping("/mentor/master-thesis")
@Tag(
    name = "Mentor Master Thesis Command API",
    description = "Commands by mentors for master thesis process."
)
class MentorMasterThesisCommandRestApi(
    private val mentorMasterThesisService: MentorMasterThesisService
) {

    @Operation(summary = "Mark thesis as defended",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/mark-thesis")
    fun markThesisAsDefended(@RequestBody dto: MarkThesisAsDefendedDto): ResponseEntity<Any> {
        val command = MarkThesisAsDefended(
            thesisId = dto.thesisId,
            defenseDate = dto.defenseDate,
            successful = dto.successful,
            finalGrade = dto.finalGrade,
            defenseRemarks = dto.defenseRemarks
        )
        mentorMasterThesisService.markThesisAsDefended(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Schedule thesis defense",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/schedule-defense")
    fun scheduleThesisDefense(@RequestBody dto: ScheduleThesisDefenseDto): ResponseEntity<Any> {
        val command = ScheduleThesisDefense(
            thesisId = dto.thesisId,
            mentorId = dto.mentorId,
            defenseDate = dto.defenseDate,
            roomId = dto.roomId,
            remarks = dto.remarks,
            schedulingDate = dto.schedulingDate
        )
        mentorMasterThesisService.scheduleThesisDefense(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Select commission members",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/select-commission-members")
    fun selectCommissionMembers(@RequestBody dto: SelectCommissionMembersDto): ResponseEntity<Any> {
        val command = SelectCommissionMembers(
            thesisId = dto.thesisId,
            mentorId = dto.mentorId,
            commissionMember1Id = dto.commissionMember1Id,
            commissionMember2Id = dto.commissionMember2Id,
            remarks = dto.remarks,
            selectionDate = dto.selectionDate
        )
        mentorMasterThesisService.selectCommissionMembers(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Submit commission report",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/submit-commission-report")
    fun submitCommissionReport(@RequestBody dto: SubmitCommissionReportDto): ResponseEntity<Any> {
        val command = SubmitCommissionReport(
            thesisId = dto.thesisId,
            mentorId = dto.mentorId,
            reportDocument = dto.reportDocument,
            corrections = dto.corrections,
            remarks = dto.remarks,
            conclusions = dto.conclusions,
            submissionDate = dto.submissionDate
        )
        mentorMasterThesisService.submitCommissionReport(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Upload revised thesis draft",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/upload-revised-thesis")
    fun uploadRevisedThesisDraft(@RequestBody dto: UploadRevisedThesisDraftDto): ResponseEntity<Any> {
        val command = UploadRevisedThesisDraft(
            thesisId = dto.thesisId,
            mentorId = dto.mentorId,
            revisedDraftDocument = dto.revisedDraftDocument,
            remarks = dto.remarks,
            uploadDate = dto.uploadDate
        )
        mentorMasterThesisService.uploadRevisedThesisDraft(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Upload thesis draft",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/upload-thesis")
    fun uploadThesisDraft(@RequestBody dto: UploadThesisDraftDto): ResponseEntity<Any> {
        val command = UploadThesisDraft(
            thesisId = dto.thesisId,
            mentorId = dto.mentorId,
            draftDocumentType = dto.draftDocumentType,
            remarks = dto.remarks,
            uploadDate = dto.uploadDate
        )
        mentorMasterThesisService.uploadThesisDraft(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Validate thesis by mentor",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/validate-by-mentor")
    fun submitCommissionReport(@RequestBody dto: ValidateThesisByMentorDto): ResponseEntity<Any> {
        val command = ValidateThesisByMentor(
            thesisId = dto.thesisId,
            mentorId = dto.mentorId,
            approved = dto.approved,
            remarks = dto.remarks,
            field = dto.field,
            validationDate = dto.validationDate
        )
        mentorMasterThesisService.validateThesisByMentor(command)
        return ResponseEntity.ok().build()
    }
}
