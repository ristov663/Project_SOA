package mk.ukim.finki.soa.masterthesis.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ApproveThesisProposalCommand
import mk.ukim.finki.soa.masterthesis.model.command.mentor.MentorReviewProposalCommand
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ReviewThesisDraftCommand
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ScheduleThesisDefenseCommand
import mk.ukim.finki.soa.masterthesis.service.MentorMasterThesisService
import mk.ukim.finki.soa.masterthesis.web.dto.*


@RestController
@RequestMapping("/mentor/master-thesis")
@Tag(name = "Mentor Master Thesis Command API", description = "Commands by mentors for master thesis process.")
class MentorMasterThesisCommandRestApi(
    private val mentorMasterThesisService: MentorMasterThesisService
) {

    @Operation(summary = "Approve thesis proposal")
    @PostMapping("/approve-proposal")
    fun approveThesisProposal(@RequestBody dto: ApproveThesisProposalCommandDto): ResponseEntity<Any> {
        val command = ApproveThesisProposalCommand(
            thesisId = dto.thesisId,
            mentorId = dto.mentorId,
            comments = dto.comments,
            timestamp = dto.timestamp
        )
        mentorMasterThesisService.approveThesisProposal(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Mentor review proposal")
    @PostMapping("/mentor-review")
    fun mentorReviewProposal(@RequestBody dto: MentorReviewProposalCommandDto): ResponseEntity<Any> {
        val command = MentorReviewProposalCommand(
            thesisId = dto.thesisId,
            mentorId = dto.mentorId,
            comments = dto.comments,
            approved = dto.approved,
            timestamp = dto.timestamp
        )
        mentorMasterThesisService.mentorReviewProposal(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Mentor review thesis draft")
    @PostMapping("/review-draft")
    fun reviewThesisDraft(@RequestBody dto: ReviewThesisDraftCommandDto): ResponseEntity<Any> {
        val command = ReviewThesisDraftCommand(
            thesisId = dto.thesisId,
            reviewerId = dto.reviewerId,
            feedback = dto.feedback,
            requestRevisions = dto.requestRevisions,
            timestamp = dto.timestamp
        )
        mentorMasterThesisService.reviewThesisDraft(command)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Mentor schedule thesis defense")
    @PostMapping("/schedule-defense")
    fun scheduleThesisDefense(@RequestBody dto: ScheduleThesisDefenseCommandDto): ResponseEntity<Any> {
        val command = ScheduleThesisDefenseCommand(
            thesisId = dto.thesisId,
            defenseDate = dto.defenseDate,
            location = dto.location,
            panelMembers = dto.panelMembers,
            timestamp = dto.timestamp
        )
        mentorMasterThesisService.scheduleThesisDefense(command)
        return ResponseEntity.ok().build()
    }
}
