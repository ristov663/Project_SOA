package mk.ukim.finki.soa.masterthesis.model.aggregate

import mk.ukim.finki.soa.masterthesis.model.exception.*
import mk.ukim.finki.soa.masterthesis.model.command.*
import mk.ukim.finki.soa.masterthesis.model.command.administration.ArchiveThesis
import mk.ukim.finki.soa.masterthesis.model.command.administration.SecondaryValidateByTeachingAndResearchCommission
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateFourthSecretaryPhase
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateSecondSecretaryPhase
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThesisByAdministration
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThesisByCommission
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThesisBySecretary
import mk.ukim.finki.soa.masterthesis.model.command.administration.ValidateThirdSecretaryPhase
import mk.ukim.finki.soa.masterthesis.model.command.mentor.MarkThesisAsDefended
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ScheduleThesisDefense
import mk.ukim.finki.soa.masterthesis.model.command.mentor.SelectCommissionMembers
import mk.ukim.finki.soa.masterthesis.model.command.mentor.SubmitCommissionReport
import mk.ukim.finki.soa.masterthesis.model.command.mentor.UploadRevisedThesisDraft
import mk.ukim.finki.soa.masterthesis.model.command.mentor.UploadThesisDraft
import mk.ukim.finki.soa.masterthesis.model.command.mentor.ValidateThesisByMentor
import mk.ukim.finki.soa.masterthesis.model.command.student.ConfirmStudentEnrollmentForThesis
import mk.ukim.finki.soa.masterthesis.model.command.student.SubmitThesisRegistration
import mk.ukim.finki.soa.masterthesis.model.command.system.AutoApproveCommissionValidation
import mk.ukim.finki.soa.masterthesis.model.command.system.AutoApproveSecondCommissionValidation
import mk.ukim.finki.soa.masterthesis.model.event.*
import mk.ukim.finki.soa.masterthesis.model.event.administration.FourthSecretaryValidationCompleted
import mk.ukim.finki.soa.masterthesis.model.event.administration.SecondSecretaryValidationCompleted
import mk.ukim.finki.soa.masterthesis.model.event.administration.SecondaryValidatedByTeachingAndResearchCommission
import mk.ukim.finki.soa.masterthesis.model.event.administration.ThesisArchived
import mk.ukim.finki.soa.masterthesis.model.event.administration.ThesisValidatedByAdministration
import mk.ukim.finki.soa.masterthesis.model.event.administration.ThesisValidatedByCommission
import mk.ukim.finki.soa.masterthesis.model.event.administration.ThesisValidatedBySecretary
import mk.ukim.finki.soa.masterthesis.model.event.administration.ThirdSecretaryValidationCompleted
import mk.ukim.finki.soa.masterthesis.model.event.mentor.CommissionMembersSelected
import mk.ukim.finki.soa.masterthesis.model.event.mentor.CommissionReportSubmitted
import mk.ukim.finki.soa.masterthesis.model.event.mentor.RevisedThesisDraftUploaded
import mk.ukim.finki.soa.masterthesis.model.event.mentor.ThesisDefenseScheduled
import mk.ukim.finki.soa.masterthesis.model.event.mentor.ThesisDraftUploaded
import mk.ukim.finki.soa.masterthesis.model.event.mentor.ThesisMarkedAsDefended
import mk.ukim.finki.soa.masterthesis.model.event.mentor.ThesisValidatedByMentor
import mk.ukim.finki.soa.masterthesis.model.event.student.StudentEnrollmentConfirmedOnThesis
import mk.ukim.finki.soa.masterthesis.model.event.student.ThesisRegistrationSubmitted
import mk.ukim.finki.soa.masterthesis.model.event.system.CommissionValidationAutoApproved
import mk.ukim.finki.soa.masterthesis.model.event.system.SecondCommissionValidationAutoApproved
import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.time.LocalDateTime

@Aggregate
class MasterThesis {

    @AggregateIdentifier
    private lateinit var thesisId: MasterThesisId
    private lateinit var currentState: MasterThesisStatus
    private var studentId: StudentId? = null
    private var mentorId: ProfessorId? = null
    private var title: MasterThesisTitle? = null
    private var shortDescription: MasterThesisDescription? = null
    private var field: MasterThesisArea? = null
    private var submissionDate: LocalDateTime? = null
    private var lastUpdated: LocalDateTime = LocalDateTime.now()

    // Validation tracking
    private var mentorValidated: Boolean = false
    private var administrationValidated: Boolean = false
    private var commissionValidated: Boolean = false
    private var secretaryValidations: MutableList<SecretaryValidation> = mutableListOf()

    // Documents tracking
    private var requiredDocuments: MutableList<DocumentInfo> = mutableListOf()
    private var thesisDraft: DocumentInfo? = null
    private var revisedDraft: DocumentInfo? = null
    private var commissionReport: DocumentInfo? = null

    // Commission tracking
    private var commissionMember1Id: ExternalUserId? = null
    private var commissionMember2Id: ExternalUserId? = null
    private var commissionCoordinatorId: ExternalUserId? = null

    // Archive tracking
    private var archiveNumbers: MutableList<ArchiveNumber> = mutableListOf()

    // Defense tracking
    private var defenseScheduled: Boolean = false
    private var defenseDate: LocalDateTime? = null
    private var roomId: String? = null
    private var defended: Boolean = false
    private var finalGrade: Grade? = null

    // Enrollment tracking (new)
    private var studentEnrollmentValidated: Boolean = false
    private var studentEnrollmentInfo: Enrollment? = null
    private var studentEnrollmentValidationDate: java.time.LocalDateTime? = null

    // Auto-approval tracking
    private var administrationValidationDate: LocalDateTime? = null
    private var secondSecretaryValidationDate: LocalDateTime? = null

    // Default constructor for Axon
    constructor()

    // ====================
    // COMMAND HANDLERS
    // ====================

    @CommandHandler
    constructor(command: SubmitThesisRegistration) {
        // Business rule: Only allow registration in initial state
        validateInitialState()

        // Business rule: Validate required fields
        validateRegistrationData(command)

        AggregateLifecycle.apply(
            ThesisRegistrationSubmitted(
                thesisId = command.thesisId,
                studentId = command.studentId,
                mentorId = command.mentorId,
                title = command.title,
                shortDescription = command.shortDescription,
                requiredDocuments = command.requiredDocuments,
                submissionDate = command.submissionDate
            )
        )
    }

    @CommandHandler
    fun handle(command: ValidateThesisByMentor) {
        // Business rule: Only allow in STUDENT_THESIS_REGISTRATION state
        validateState(MasterThesisStatus.STUDENT_THESIS_REGISTRATION, "Mentor validation")

        // Business rule: Only assigned mentor can validate
        validateMentorAuthorization(command.mentorId)

        AggregateLifecycle.apply(
            ThesisValidatedByMentor(
                thesisId = command.thesisId,
                mentorId = command.mentorId,
                approved = command.approved,
                remarks = command.remarks,
                field = command.field,
                validationDate = command.validationDate
            )
        )
    }

    @CommandHandler
    fun handle(command: ValidateThesisByAdministration) {
        // Business rule: Only allow in MENTOR_VALIDATION state
        validateState(MasterThesisStatus.MENTOR_VALIDATION, "Administration validation")

        // Business rule: Mentor must have approved first
        if (!mentorValidated) {
            throw InvalidThesisStateException("Mentor validation must be completed first")
        }

        AggregateLifecycle.apply(
            ThesisValidatedByAdministration(
                thesisId = command.thesisId,
                administratorId = command.administratorId,
                approved = command.approved,
                remarks = command.remarks,
                documentsVerified = command.documentsVerified,
                studentEligibilityConfirmed = command.studentEligibilityConfirmed,
                validationDate = command.validationDate
            )
        )
    }

    @CommandHandler
    fun handle(command: ValidateThesisByCommission) {
        // Business rule: Only allow in ADMINISTRATION_VALIDATION state
        validateState(MasterThesisStatus.ADMINISTRATION_VALIDATION, "Commission validation")

        // Business rule: Check 5-day timeout hasn't passed
        validateCommissionTimeout(command.validationDate)

        AggregateLifecycle.apply(
            ThesisValidatedByCommission(
                thesisId = command.thesisId,
                commissionCoordinatorId = command.commissionCoordinatorId,
                approved = command.approved,
                remarks = command.remarks,
                validationDate = command.validationDate
            )
        )
    }

    @CommandHandler
    fun handle(command: AutoApproveCommissionValidation) {
        // Business rule: Only allow in ADMINISTRATION_VALIDATION state
        validateState(MasterThesisStatus.ADMINISTRATION_VALIDATION, "Auto-approval")

        // Business rule: Must be past 5-day deadline
        validateAutoApprovalTimeout(command.autoApprovalDate)

        AggregateLifecycle.apply(
            CommissionValidationAutoApproved(
                thesisId = command.thesisId,
                autoApprovalDate = command.autoApprovalDate,
                reason = command.reason ?: "Automatic approval due to 5-day timeout"
            )
        )
    }

    @CommandHandler
    fun handle(command: ValidateThesisBySecretary) {
        // Business rule: Only allow in COMMISSION_VALIDATION state
        validateState(MasterThesisStatus.COMMISSION_VALIDATION, "Secretary validation")

        // Business rule: Validate archive number format
        validateArchiveNumber(command.archiveNumber)

        AggregateLifecycle.apply(
            ThesisValidatedBySecretary(
                thesisId = command.thesisId,
                secretaryId = command.secretaryId,
                archiveNumber = command.archiveNumber,
                remarks = command.remarks,
                validationDate = command.validationDate
            )
        )
    }

    @CommandHandler
    fun handle(command: UploadThesisDraft) {
        // Business rule: Only allow in SECRETARY_VALIDATION state
        validateState(MasterThesisStatus.SECRETARY_VALIDATION, "Draft upload")

        // Business rule: Only mentor can upload
        validateMentorAuthorization(command.mentorId)

        // Business rule: Validate document
        validateDocument(command.draftDocumentType)

        AggregateLifecycle.apply(
            ThesisDraftUploaded(
                thesisId = command.thesisId,
                mentorId = command.mentorId,
                draftDocument = command.draftDocumentType,
                remarks = command.remarks,
                uploadDate = command.uploadDate
            )
        )
    }

    @CommandHandler
    fun handle(command: SelectCommissionMembers) {
        // Business rule: Only allow in STUDENT_DRAFT state
        validateState(MasterThesisStatus.STUDENT_DRAFT, "Commission member selection")

        // Business rule: Only mentor can select
        validateMentorAuthorization(command.mentorId)

        // Business rule: Commission members must be different
        if (command.commissionMember1Id == command.commissionMember2Id) {
            throw InvalidCommissionSelectionException("Commission members must be different")
        }

        AggregateLifecycle.apply(
            CommissionMembersSelected(
                thesisId = command.thesisId,
                mentorId = command.mentorId,
                commissionMember1Id = command.commissionMember1Id,
                commissionMember2Id = command.commissionMember2Id,
                remarks = command.remarks,
                selectionDate = command.selectionDate
            )
        )
    }

    @CommandHandler
    fun handle(command: ValidateSecondSecretaryPhase) {
        // Business rule: Only allow in MENTOR_COMMISSION_CHOICE state
        validateState(MasterThesisStatus.MENTOR_COMMISSION_CHOICE, "Second secretary validation")

        // Business rule: Validate archive number format
        validateArchiveNumber(command.commissionArchiveNumber)

        AggregateLifecycle.apply(
            SecondSecretaryValidationCompleted(
                thesisId = command.thesisId,
                secretaryId = command.secretaryId,
                commissionArchiveNumber = command.commissionArchiveNumber,
                remarks = command.remarks,
                validationDate = command.validationDate
            )
        )
    }

    @CommandHandler
    fun handle(command: SecondaryValidateByTeachingAndResearchCommission) {
        // Business rule: Only allow in SECOND_SECRETARY_VALIDATION state
        validateState(MasterThesisStatus.SECOND_SECRETARY_VALIDATION, "Second commission validation")

        // Business rule: Check 5-day timeout hasn't passed
        validateSecondCommissionTimeout(command.validationDate)

        AggregateLifecycle.apply(
            SecondaryValidatedByTeachingAndResearchCommission(
                thesisId = command.thesisId,
                commissionCoordinatorId = command.commissionCoordinatorId,
                approved = command.approved,
                remarks = command.remarks,
                validationDate = command.validationDate
            )
        )
    }

    @CommandHandler
    fun handle(command: AutoApproveSecondCommissionValidation) {
        // Business rule: Only allow in SECOND_SECRETARY_VALIDATION state
        validateState(MasterThesisStatus.SECOND_SECRETARY_VALIDATION, "Second auto-approval")

        // Business rule: Must be past 5-day deadline
        validateSecondAutoApprovalTimeout(command.autoApprovalDate)

        AggregateLifecycle.apply(
            SecondCommissionValidationAutoApproved(
                thesisId = command.thesisId,
                autoApprovalDate = command.autoApprovalDate,
                reason = command.reason ?: "Automatic approval due to 5-day timeout"
            )
        )
    }

    @CommandHandler
    fun handle(command: ValidateThirdSecretaryPhase) {
        // Business rule: Only allow in COMMISSION_CHECK state
        validateState(MasterThesisStatus.COMMISSION_CHECK, "Third secretary validation")

        // Business rule: Validate archive number format
        validateArchiveNumber(command.archiveNumber)

        AggregateLifecycle.apply(
            ThirdSecretaryValidationCompleted(
                thesisId = command.thesisId,
                secretaryId = command.secretaryId,
                archiveNumber = command.archiveNumber,
                remarks = command.remarks,
                validationDate = command.validationDate
            )
        )
    }

    @CommandHandler
    fun handle(command: UploadRevisedThesisDraft) {
        // Business rule: Only allow in THIRD_SECRETARY_VALIDATION state
        validateState(MasterThesisStatus.THIRD_SECRETARY_VALIDATION, "Revised draft upload")

        // Business rule: Only mentor can upload
        validateMentorAuthorization(command.mentorId)

        // Business rule: Validate document
        validateDocument(command.revisedDraftDocument)

        AggregateLifecycle.apply(
            RevisedThesisDraftUploaded(
                thesisId = command.thesisId,
                mentorId = command.mentorId,
                revisedDraftDocument = command.revisedDraftDocument,
                previousDraftArchived = true,
                remarks = command.remarks,
                uploadDate = command.uploadDate
            )
        )
    }

    @CommandHandler
    fun handle(command: SubmitCommissionReport) {
        // Business rule: Only allow in DRAFT_CHECK state
        validateState(MasterThesisStatus.DRAFT_CHECK, "Commission report submission")

        // Business rule: Only mentor can submit
        validateMentorAuthorization(command.mentorId)

        // Business rule: Validate report document
        validateDocument(command.reportDocument)

        AggregateLifecycle.apply(
            CommissionReportSubmitted(
                thesisId = command.thesisId,
                mentorId = command.mentorId,
                reportDocument = command.reportDocument,
                corrections = command.corrections,
                remarks = command.remarks,
                conclusions = command.conclusions,
                submissionDate = command.submissionDate
            )
        )
    }

    @CommandHandler
    fun handle(command: ValidateFourthSecretaryPhase) {
        // Business rule: Only allow in REPORT_VALIDATION state
        validateState(MasterThesisStatus.REPORT_VALIDATION, "Fourth secretary validation")

        // Business rule: Validate archive number format
        validateArchiveNumber(command.archiveNumber)

        AggregateLifecycle.apply(
            FourthSecretaryValidationCompleted(
                thesisId = command.thesisId,
                secretaryId = command.secretaryId,
                archiveNumber = command.archiveNumber,
                remarks = command.remarks,
                validationDate = command.validationDate
            )
        )
    }

    @CommandHandler
    fun handle(command: ArchiveThesis) {
        // Business rule: Only allow in FOURTH_SECRETARY_VALIDATION state
        validateState(MasterThesisStatus.FOURTH_SECRETARY_VALIDATION, "Thesis archiving")

        // Business rule: Process must be validated
        if (!command.processValidated) {
            throw ProcessValidationException("Process validation must be completed before archiving")
        }

        AggregateLifecycle.apply(
            ThesisArchived(
                thesisId = command.thesisId,
                administratorId = command.administratorId,
                processValidated = command.processValidated,
                remarks = command.remarks,
                archiveDate = command.archiveDate
            )
        )
    }

    @CommandHandler
    fun handle(command: ScheduleThesisDefense) {
        // Business rule: Only allow in ADMINISTRATION_ARCHIVING state
        validateState(MasterThesisStatus.ADMINISTRATION_ARCHIVING, "Defense scheduling")

        // Business rule: Only mentor can schedule
        validateMentorAuthorization(command.mentorId)

        // Business rule: Defense date must be in future
        if (command.defenseDate.isBefore(LocalDateTime.now())) {
            throw InvalidDefenseScheduleException("Defense date must be in the future")
        }

        AggregateLifecycle.apply(
            ThesisDefenseScheduled(
                thesisId = command.thesisId,
                mentorId = command.mentorId,
                defenseDate = command.defenseDate,
                roomId = command.roomId,
                remarks = command.remarks,
                schedulingDate = command.schedulingDate
            )
        )
    }

    @CommandHandler
    fun handle(command: MarkThesisAsDefended) {
        // Business rule: Only allow in PROCESS_FINISHED state
        validateState(MasterThesisStatus.PROCESS_FINISHED, "Marking as defended")

        // Business rule: Defense must be scheduled
        if (!defenseScheduled) {
            throw InvalidDefenseException("Defense must be scheduled before marking as defended")
        }

        AggregateLifecycle.apply(
            ThesisMarkedAsDefended(
                thesisId = command.thesisId,
                defenseDate = command.defenseDate,
                successful = command.successful,
                finalGrade = command.finalGrade,
                defenseRemarks = command.defenseRemarks
            )
        )
    }

    @CommandHandler
    fun handle(command: ConfirmStudentEnrollmentForThesis) {
        // allow only when thesis is in registration state
        validateState(MasterThesisStatus.STUDENT_THESIS_REGISTRATION, "Confirm student enrollment")

        // Check that this aggregate indeed belongs to the provided student
        if (this.studentId?.baseValue() != command.studentId.baseValue()) {
            throw UnauthorizedThesisOperationException("Enrollment confirmation does not match thesis student")
        }

        AggregateLifecycle.apply(
            StudentEnrollmentConfirmedOnThesis(
                thesisId = command.thesisId,
                studentId = command.studentId,
                enrollment = command.enrollment,
                validationDate = command.validationDate
            )
        )
    }

    // ====================
    // EVENT SOURCING HANDLERS
    // ====================

    @EventSourcingHandler
    fun on(event: ThesisRegistrationSubmitted) {
        this.thesisId = event.thesisId
        this.currentState = MasterThesisStatus.STUDENT_THESIS_REGISTRATION
        this.studentId = event.studentId
        this.mentorId = event.mentorId
        this.title = event.title
        this.shortDescription = event.shortDescription
        this.requiredDocuments = event.requiredDocuments.toMutableList()
        this.submissionDate = event.submissionDate
        this.lastUpdated = event.submissionDate
    }

    @EventSourcingHandler
    fun on(event: ThesisValidatedByMentor) {
        this.currentState = MasterThesisStatus.MENTOR_VALIDATION
        this.mentorValidated = event.approved
        this.field = event.field
        this.lastUpdated = event.validationDate
    }

    @EventSourcingHandler
    fun on(event: ThesisValidatedByAdministration) {
        this.currentState = MasterThesisStatus.ADMINISTRATION_VALIDATION
        this.administrationValidated = event.approved
        this.administrationValidationDate = event.validationDate
        this.lastUpdated = event.validationDate
    }

    @EventSourcingHandler
    fun on(event: ThesisValidatedByCommission) {
        this.currentState = MasterThesisStatus.COMMISSION_VALIDATION
        this.commissionValidated = event.approved
        this.commissionCoordinatorId = event.commissionCoordinatorId
        this.lastUpdated = event.validationDate
    }

    @EventSourcingHandler
    fun on(event: CommissionValidationAutoApproved) {
        this.currentState = MasterThesisStatus.COMMISSION_VALIDATION
        this.commissionValidated = true
        this.lastUpdated = event.autoApprovalDate
    }

    @EventSourcingHandler
    fun on(event: ThesisValidatedBySecretary) {
        this.currentState = MasterThesisStatus.SECRETARY_VALIDATION
        this.archiveNumbers.add(event.archiveNumber)
        this.secretaryValidations.add(
            SecretaryValidation(event.secretaryId, event.archiveNumber, event.validationDate, 1)
        )
        this.lastUpdated = event.validationDate
    }

    @EventSourcingHandler
    fun on(event: ThesisDraftUploaded) {
        this.currentState = MasterThesisStatus.STUDENT_DRAFT
        this.thesisDraft = event.draftDocument
        this.lastUpdated = event.uploadDate
    }

    @EventSourcingHandler
    fun on(event: CommissionMembersSelected) {
        this.currentState = MasterThesisStatus.MENTOR_COMMISSION_CHOICE
        this.commissionMember1Id = event.commissionMember1Id
        this.commissionMember2Id = event.commissionMember2Id
        this.lastUpdated = event.selectionDate
    }

    @EventSourcingHandler
    fun on(event: SecondSecretaryValidationCompleted) {
        this.currentState = MasterThesisStatus.SECOND_SECRETARY_VALIDATION
        this.archiveNumbers.add(event.commissionArchiveNumber)
        this.secretaryValidations.add(
            SecretaryValidation(event.secretaryId, event.commissionArchiveNumber, event.validationDate, 2)
        )
        this.secondSecretaryValidationDate = event.validationDate
        this.lastUpdated = event.validationDate
    }

    @EventSourcingHandler
    fun on(event: SecondaryValidatedByTeachingAndResearchCommission) {
        this.currentState = MasterThesisStatus.COMMISSION_CHECK
        this.lastUpdated = event.validationDate
    }

    @EventSourcingHandler
    fun on(event: SecondCommissionValidationAutoApproved) {
        this.currentState = MasterThesisStatus.COMMISSION_CHECK
        this.lastUpdated = event.autoApprovalDate
    }

    @EventSourcingHandler
    fun on(event: ThirdSecretaryValidationCompleted) {
        this.currentState = MasterThesisStatus.THIRD_SECRETARY_VALIDATION
        this.archiveNumbers.add(event.archiveNumber)
        this.secretaryValidations.add(
            SecretaryValidation(event.secretaryId, event.archiveNumber, event.validationDate, 3)
        )
        this.lastUpdated = event.validationDate
    }

    @EventSourcingHandler
    fun on(event: RevisedThesisDraftUploaded) {
        this.currentState = MasterThesisStatus.DRAFT_CHECK
        this.revisedDraft = event.revisedDraftDocument
        this.lastUpdated = event.uploadDate
    }

    @EventSourcingHandler
    fun on(event: CommissionReportSubmitted) {
        this.currentState = MasterThesisStatus.REPORT_VALIDATION
        this.commissionReport = event.reportDocument
        this.lastUpdated = event.submissionDate
    }

    @EventSourcingHandler
    fun on(event: FourthSecretaryValidationCompleted) {
        this.currentState = MasterThesisStatus.FOURTH_SECRETARY_VALIDATION
        this.archiveNumbers.add(event.archiveNumber)
        this.secretaryValidations.add(
            SecretaryValidation(event.secretaryId, event.archiveNumber, event.validationDate, 4)
        )
        this.lastUpdated = event.validationDate
    }

    @EventSourcingHandler
    fun on(event: ThesisArchived) {
        this.currentState = MasterThesisStatus.ADMINISTRATION_ARCHIVING
        this.lastUpdated = event.archiveDate
    }

    @EventSourcingHandler
    fun on(event: ThesisDefenseScheduled) {
        this.currentState = MasterThesisStatus.PROCESS_FINISHED
        this.defenseScheduled = true
        this.defenseDate = event.defenseDate
        this.roomId = event.roomId
        this.lastUpdated = event.schedulingDate
    }

    @EventSourcingHandler
    fun on(event: ThesisMarkedAsDefended) {
        this.currentState = MasterThesisStatus.FINISHED
        this.defended = true
        this.finalGrade = event.finalGrade
        this.lastUpdated = event.defenseDate
    }

    @EventSourcingHandler
    fun on(event: StudentEnrollmentConfirmedOnThesis) {
        this.studentEnrollmentValidated = true
        this.studentEnrollmentInfo = event.enrollment
        this.studentEnrollmentValidationDate = event.validationDate
        this.lastUpdated = event.validationDate
    }

    // ====================
    // BUSINESS RULE VALIDATION METHODS
    // ====================

    private fun validateInitialState() {
        // For new aggregates, this is always valid
    }

    private fun validateState(expectedState: MasterThesisStatus, operation: String) {
        if (this.currentState != expectedState) {
            throw InvalidThesisStateException(
                "$operation is only allowed in $expectedState state, but current state is $currentState"
            )
        }
    }

    private fun validateMentorAuthorization(commandMentorId: ProfessorId) {
        if (this.mentorId != commandMentorId) {
            throw UnauthorizedThesisOperationException(
                "Only the assigned mentor can perform this operation"
            )
        }
    }

    private fun validateRegistrationData(command: SubmitThesisRegistration) {
        if (command.title.value.isBlank()) {
            throw InvalidRegistrationDataException("Thesis title cannot be empty")
        }
        if (command.shortDescription.value.isBlank()) {
            throw InvalidRegistrationDataException("Thesis description cannot be empty")
        }
        if (command.requiredDocuments.isEmpty()) {
            throw InvalidRegistrationDataException("At least one document must be submitted")
        }
    }

    private fun validateCommissionTimeout(validationDate: LocalDateTime) {
        administrationValidationDate?.let { adminDate ->
            val daysPassed = java.time.Duration.between(adminDate, validationDate).toDays()
            if (daysPassed > 5) {
                throw ValidationTimeoutException("Commission validation period has expired (5 days)")
            }
        }
    }

    private fun validateAutoApprovalTimeout(autoApprovalDate: LocalDateTime) {
        administrationValidationDate?.let { adminDate ->
            val daysPassed = java.time.Duration.between(adminDate, autoApprovalDate).toDays()
            if (daysPassed < 5) {
                throw ValidationTimeoutException("Auto-approval can only occur after 5 days")
            }
        }
    }

    private fun validateSecondCommissionTimeout(validationDate: LocalDateTime) {
        secondSecretaryValidationDate?.let { secretaryDate ->
            val daysPassed = java.time.Duration.between(secretaryDate, validationDate).toDays()
            if (daysPassed > 5) {
                throw ValidationTimeoutException("Second commission validation period has expired (5 days)")
            }
        }
    }

    private fun validateSecondAutoApprovalTimeout(autoApprovalDate: LocalDateTime) {
        secondSecretaryValidationDate?.let { secretaryDate ->
            val daysPassed = java.time.Duration.between(secretaryDate, autoApprovalDate).toDays()
            if (daysPassed < 5) {
                throw ValidationTimeoutException("Second auto-approval can only occur after 5 days")
            }
        }
    }

    private fun validateArchiveNumber(archiveNumber: ArchiveNumber) {
        // Validate archive number format (implementation depends on your business rules)
        if (archiveNumber.value.isBlank()) {
            throw InvalidArchiveNumberException("Archive number cannot be empty")
        }
    }

    private fun validateDocument(document: DocumentInfo) {
        if (document.fileName.isBlank()) {
            throw InvalidDocumentException("Document filename cannot be empty")
        }
        if (document.fileSize <= 0) {
            throw InvalidDocumentException("Document file size must be greater than 0")
        }
    }
}

data class SecretaryValidation(
    val secretaryId: ExternalUserId,
    val archiveNumber: ArchiveNumber,
    val validationDate: LocalDateTime,
    val phase: Int
)
