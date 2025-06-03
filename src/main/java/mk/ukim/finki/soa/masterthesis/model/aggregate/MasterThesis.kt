package mk.ukim.finki.soa.masterthesis.model.aggregate

import jakarta.persistence.*
import mk.ukim.finki.soa.masterthesis.model.command.student.*
import mk.ukim.finki.soa.masterthesis.model.command.mentor.*
import mk.ukim.finki.soa.masterthesis.model.command.general.*
import mk.ukim.finki.soa.masterthesis.model.command.administration.*
import mk.ukim.finki.soa.masterthesis.model.event.student.*
import mk.ukim.finki.soa.masterthesis.model.event.mentor.*
import mk.ukim.finki.soa.masterthesis.model.event.general.*
import mk.ukim.finki.soa.masterthesis.model.event.administration.*
import mk.ukim.finki.soa.masterthesis.model.common.LabeledEntity
import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import mk.ukim.finki.soa.masterthesis.model.common.Identifier
import mk.ukim.finki.soa.masterthesis.model.exception.StudentNotEligibleException
import mk.ukim.finki.soa.masterthesis.service.StudentService
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.time.ZonedDateTime

@Aggregate(repository = "axonMasterThesisRepository")
@Entity
class MasterThesis : LabeledEntity {
    @AggregateIdentifier
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    private lateinit var id: MasterThesisId

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "title"))
    private lateinit var title: MasterThesisTitle

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "status"))
    private lateinit var status: MasterThesisStatus

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "area"))
    private lateinit var area: MasterThesisArea

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "description"))
    private lateinit var description: MasterThesisDescription

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "student_index"))
    private lateinit var studentIndex: StudentIndex

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "mentor_id"))
    private lateinit var mentorId: ProfessorId

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "first_member_id"))
    private lateinit var firstMemberId: ProfessorId

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "second_member_id"))
    private lateinit var secondMemberId: ProfessorId

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "coordinator_id"))
    private lateinit var coordinatorId: ProfessorId

    private var thesisText: ByteArray? = null

    private var grade: Int? = null

    private var location: String? = null

    @Column(name = "presentation_start_time")
    private var presentationStartTime: ZonedDateTime? = null

    @Column(name = "last_update")
    private lateinit var lastUpdate: ZonedDateTime

    @Column(name = "master_thesis_due_date")
    private var masterThesisDueDate: ZonedDateTime? = null

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "application_archive_number"))
    private var applicationArchiveNumber: ArchiveNumber? = null

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "deadline_extension_archive_number"))
    private var deadlineExtensionArchiveNumber: ArchiveNumber? = null

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "decision_commission_election_archive_number"))
    private var decisionForCommissionElectionArchiveNumber: ArchiveNumber? = null

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "master_thesis_approval_archive_number"))
    private var masterThesisApprovalArchiveNumber: ArchiveNumber? = null

    // Required no-arg constructor for JPA
    constructor()

//    @CommandHandler
//    constructor(command: InitiateThesisRegistrationCommand, studentService: StudentService) {
//        // Validation
//        if (!studentService.isStudentEligible(command.studentIndex)) {
//            throw StudentNotEligibleException(command.studentIndex)
//        }
//
//        val event = ThesisRegistrationInitiatedEvent(
//            studentIndex = command.studentIndex,
//            initiatedAt = command.timestamp
//        )
//
//        this.on(event)
//        AggregateLifecycle.apply(event)
//    }

    @CommandHandler
    constructor(command: InitiateThesisRegistrationCommand) {
        val event = ThesisRegistrationInitiatedEvent(
            studentIndex = command.studentIndex,
            initiatedAt = command.timestamp
        )
        this.on(event)
        AggregateLifecycle.apply(event)
    }


    @CommandHandler
    fun submitThesisProposal(command: SubmitThesisProposalCommand) {
        val event = ThesisProposalSubmittedEvent(
            studentIndex = command.studentIndex,
            title = command.title,
            area = command.area,
            description = command.description
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    fun submitThesisDraft(command: SubmitThesisDraftCommand) {
        val event = ThesisDraftSubmittedEvent(
            thesisId = id,
            studentIndex = command.studentIndex,
            draftDocumentType = command.draftDocumentType,
            draftVersion = command.draftVersion,
            draftText = command.draftText,
            submittedAt = command.submittedAt
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    fun approveThesisProposal(command: ApproveThesisProposalCommand) {
        val event = ThesisProposalApprovedEvent(
            thesisId = id,
            mentorId = command.mentorId,
            comments = command.comments,
            approvedAt = command.timestamp
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    fun mentorReviewProposal(command: MentorReviewProposalCommand) {
        val event = ThesisProposalReviewedEvent(
            thesisId = id,
            mentorId = command.mentorId,
            comments = command.comments,
            approved = command.approved,
            reviewedAt = command.timestamp
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    fun reviewThesisDraft(command: ReviewThesisDraftCommand) {
        val event = ThesisDraftReviewedEvent(
            thesisId = id,
            reviewerId = command.reviewerId,
            feedback = command.feedback,
            requestRevisions = command.requestRevisions,
            reviewedAt = command.timestamp
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    fun scheduleThesisDefense(command: ScheduleThesisDefenseCommand) {
        val event = ThesisDefenseScheduledEvent(
            thesisId = id,
            defenseDate = command.defenseDate,
            location = command.location,
            panelMembers = command.panelMembers,
            scheduledAt = command.timestamp
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    fun assignMentorToThesis(command: AssignMentorToThesisCommand) {
        val event = MentorAssignedToThesisEvent(
            thesisId = id,
            mentorId = command.mentorId,
            assignedAt = command.timestamp
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    fun completeThesisProcess(command: CompleteThesisProcessCommand) {
        val event = MasterThesisProcessCompletedEvent(
            thesisId = id,
            studentIndex = command.studentIndex,
            completionDate = command.completionDate,
            recordedAt = command.timestamp
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    fun generateThesisDocumentation(command: GenerateThesisDocumentationCommand) {
        val event = ThesisDocumentationGeneratedEvent(
            thesisId = id,
            documentType = command.documentType,
            generatedAt = command.timestamp
        )

//        this.on(event)
        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    fun updateThesisStatus(command: UpdateThesisStatusCommand) {
        val event = ThesisStatusUpdatedEvent(
            thesisId = id,
            newStatus = command.newStatus,
            reason = command.reason,
            updatedBy = command.updatedBy,
            updatedAt = command.timestamp
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    fun approveCompletedThesis(command: ApproveCompletedThesisCommand) {
        val event = CompletedThesisApprovedEvent(
            thesisId = id,
            approveId = command.approveId,
            finalGrade = command.finalGrade,
            approvedAt = command.timestamp
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    fun evaluateThesis(command: EvaluateThesisCommand) {
        val event = ThesisEvaluatedEvent(
            thesisId = id,
            committeeId = command.committeeId,
            evaluation = command.evaluation,
            isApproved = command.isApproved,
            evaluatedAt = command.timestamp,
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    // Event Handlers
    fun on(event: ThesisRegistrationInitiatedEvent) {
        this.studentIndex = event.studentIndex
        this.lastUpdate = ZonedDateTime.now()
    }

    fun on(event: ThesisProposalSubmittedEvent) {
        this.title = event.title
        this.area = event.area
        this.description = event.description
        this.status = MasterThesisStatus.MENTOR_VALIDATION
        this.lastUpdate = ZonedDateTime.now()
    }

    fun on(event: ThesisDraftSubmittedEvent) {
        this.thesisText = event.draftText
        this.status = MasterThesisStatus.MENTOR_VALIDATION
        this.lastUpdate = ZonedDateTime.now()
    }

    fun on(event: ThesisProposalApprovedEvent) {
        this.status = MasterThesisStatus.MENTOR_VALIDATION // TO DO
        this.lastUpdate = ZonedDateTime.now()
    }

    fun on(event: ThesisProposalReviewedEvent) {
        if (event.approved) {
            this.status = MasterThesisStatus.REPORT_VALIDATION
        }
        this.lastUpdate = ZonedDateTime.now()
    }

    fun on(event: ThesisDraftReviewedEvent) {
        this.status = if (event.requestRevisions) {
            MasterThesisStatus.DRAFT_CHECK
        } else {
            MasterThesisStatus.CANCELLED
        }
        this.lastUpdate = ZonedDateTime.now()
    }

    fun on(event: ThesisDefenseScheduledEvent) {
        this.location = event.location
        this.presentationStartTime = event.scheduledAt
        this.status = MasterThesisStatus.PROCESS_FINISHED
        this.lastUpdate = ZonedDateTime.now()
    }

    fun on(event: MentorAssignedToThesisEvent) {
        this.mentorId = event.mentorId
        this.lastUpdate = ZonedDateTime.now()
    }

    fun on(event: MasterThesisProcessCompletedEvent) {
        this.status = MasterThesisStatus.FINISHED
        this.lastUpdate = ZonedDateTime.now()
    }

    fun on(event: ThesisStatusUpdatedEvent) {
        this.status = event.newStatus
        this.lastUpdate = ZonedDateTime.now()
    }

    fun on(event: CompletedThesisApprovedEvent) {
        this.status = MasterThesisStatus.STATUS_CHANGE
        this.grade = event.finalGrade
        this.lastUpdate = ZonedDateTime.now()
    }

    fun on(event: ThesisEvaluatedEvent) {
        this.grade = event.evaluation
        this.lastUpdate = ZonedDateTime.now()
    }

    override fun getId(): Identifier<out Any> {
        return this.id
    }

    override fun getLabel(): String {
        return this.title.toString()
    }
}
