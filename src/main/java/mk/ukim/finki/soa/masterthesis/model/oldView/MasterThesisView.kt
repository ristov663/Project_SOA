//package mk.ukim.finki.soa.masterthesis.model.oldView
//
//import jakarta.persistence.*
//import mk.ukim.finki.soa.masterthesis.model.common.LabeledEntity
//import mk.ukim.finki.soa.masterthesis.model.common.Identifier
//import mk.ukim.finki.soa.masterthesis.model.valueObject.*
//import org.hibernate.annotations.Immutable
//import java.time.ZonedDateTime
//
//@Entity
//@Table(name = "masterThesis")
//@Immutable
//data class MasterThesisView(
//    @EmbeddedId
//    @AttributeOverride(name = "value", column = Column(name = "id"))
//    val id: MasterThesisId,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "title"))
//    val title: MasterThesisTitle,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "status"))
//    val status: MasterThesisStatus,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "area"))
//    val area: MasterThesisArea,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "description"))
//    val description: MasterThesisDescription,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "student_index"))
//    val studentIndex: StudentIndex,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "mentor_id"))
//    val mentorId: ProfessorId,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "first_member_id"))
//    val firstMemberId: ProfessorId,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "second_member_id"))
//    val secondMemberId: ProfessorId,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "coordinator_id"))
//    val coordinatorId: ProfessorId,
//
//    @Column(name = "grade")
//    val grade: Int? = null,
//
//    @Column(name = "location")
//    val location: String? = null,
//
//    @Column(name = "presentation_start_time")
//    val presentationStartTime: ZonedDateTime? = null,
//
//    @Column(name = "last_update")
//    val lastUpdate: ZonedDateTime,
//
//    @Column(name = "master_thesis_due_date")
//    val masterThesisDueDate: ZonedDateTime? = null,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "application_archive_number"))
//    val applicationArchiveNumber: ArchiveNumber? = null,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "deadline_extension_archive_number"))
//    val deadlineExtensionArchiveNumber: ArchiveNumber? = null,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "decision_commission_election_archive_number"))
//    val decisionForCommissionElectionArchiveNumber: ArchiveNumber? = null,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "master_thesis_approval_archive_number"))
//    val masterThesisApprovalArchiveNumber: ArchiveNumber? = null
//
//) : LabeledEntity {
//
//    protected constructor() : this(
//        id = MasterThesisId("default-id"),
//        title = MasterThesisTitle("default-title"),
//        status = MasterThesisStatus.STUDENT_THESIS_REGISTRATION,
//        area = MasterThesisArea("default-area"),
//        description = MasterThesisDescription("default-description"),
//        studentIndex = StudentIndex("000000"),
//        mentorId = ProfessorId("mentor-id"),
//        firstMemberId = ProfessorId("first-member-id"),
//        secondMemberId = ProfessorId("second-member-id"),
//        coordinatorId = ProfessorId("coordinator-id"),
//        grade = null,
//        location = null,
//        presentationStartTime = null,
//        lastUpdate = ZonedDateTime.now(),
//        masterThesisDueDate = null,
//        applicationArchiveNumber = null,
//        deadlineExtensionArchiveNumber = null,
//        decisionForCommissionElectionArchiveNumber = null,
//        masterThesisApprovalArchiveNumber = null
//    )
//
//    override fun getId(): Identifier<out Any> = id
//    override fun getLabel(): String = title.toString()
//}
