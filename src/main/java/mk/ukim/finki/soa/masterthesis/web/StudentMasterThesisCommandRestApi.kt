package mk.ukim.finki.soa.masterthesis.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mk.ukim.finki.soa.masterthesis.model.command.student.SubmitThesisRegistration
import mk.ukim.finki.soa.masterthesis.service.StudentMasterThesisService
import mk.ukim.finki.soa.masterthesis.web.dto.student.SubmitThesisRegistrationDto

@RestController
@RequestMapping("/student/master-thesis")
@Tag(
    name = "Student Master Thesis Command API",
    description = "Commands by students for master thesis process."
)
class StudentMasterThesisCommandRestApi(
    private val studentMasterThesisService: StudentMasterThesisService
) {

    @Operation(summary = "Submit thesis registration")
    @PostMapping("/submit-thesis")
    fun submitThesisRegistration(@RequestBody dto: SubmitThesisRegistrationDto): ResponseEntity<Any> {
        val command = SubmitThesisRegistration(
            thesisId = dto.thesisId,
            studentId = dto.studentId,
            mentorId = dto.mentorId,
            title = dto.title,
            shortDescription = dto.shortDescription,
            requiredDocuments = dto.requiredDocuments,
            submissionDate = dto.submissionDate
        )

        studentMasterThesisService.submitThesisRegistration(command)
        return ResponseEntity.ok().build()
    }
}
