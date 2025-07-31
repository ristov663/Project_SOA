//package mk.ukim.finki.soa.masterthesis.model.view
//
//import jakarta.persistence.*
//import mk.ukim.finki.soa.masterthesis.model.common.Identifier
//import mk.ukim.finki.soa.masterthesis.model.common.LabeledEntity
//import mk.ukim.finki.soa.masterthesis.model.valueObject.*
//import java.time.LocalDateTime
//import javax.annotation.concurrent.Immutable
//
//@Entity
//@Table(name = "thesis_registration")
//@Immutable
//data class ThesisRegistrationView(
//    @EmbeddedId
//    @AttributeOverride(name = "value", column = Column(name = "id"))
//    val id: MasterThesisId,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "student_id"))
//    val studentId: StudentId,
//
//    @Embedded
//    @AttributeOverride(name = "value", column = Column(name = "mentor_id"))
//    val mentorId: ProfessorId,
//
//    @Embedded
//    val title: MasterThesisTitle,
//
//    @Embedded
//    val shortDescription: MasterThesisDescription,
//
//    val submissionDate: LocalDateTime,
//
//    @Enumerated(EnumType.STRING)
//    val status: MasterThesisStatus = MasterThesisStatus.STUDENT_THESIS_REGISTRATION,
//
//    @Column(columnDefinition = "TEXT")
//    val requiredDocumentsJson: String? = null
//) : LabeledEntity {
//
//    constructor() : this(
//        MasterThesisId(""),
//        StudentId(""),
//        ProfessorId(""),
//        MasterThesisTitle(""),
//        MasterThesisDescription(""),
//        LocalDateTime.MIN,
//        MasterThesisStatus.STUDENT_THESIS_REGISTRATION,
//        null
//    )
//
//    override fun getId(): Identifier<out Any> = id
//    override fun getLabel(): String = title.value
//}
