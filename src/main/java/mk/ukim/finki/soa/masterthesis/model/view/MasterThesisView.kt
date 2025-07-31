package mk.ukim.finki.soa.masterthesis.model.view

import jakarta.persistence.*
import mk.ukim.finki.soa.masterthesis.model.common.Identifier
import mk.ukim.finki.soa.masterthesis.model.common.LabeledEntity
import mk.ukim.finki.soa.masterthesis.model.valueObject.*
import org.hibernate.annotations.Immutable
import java.time.LocalDateTime


@Entity
@Table(name = "master_thesis_projection")
@Immutable
data class MasterThesisView(

    @EmbeddedId
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "thesis_id"))
    )
    val thesisId: MasterThesisId? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "current_state")
    val currentState: MasterThesisStatus? = null,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "student_id"))
    )
    val studentId: StudentId? = null,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "mentor_id"))
    )
    val mentorId: ProfessorId? = null,

    @Embedded
    val title: MasterThesisTitle? = null,

    @Embedded
    val shortDescription: MasterThesisDescription? = null,

    @Embedded
    val field: MasterThesisArea? = null,

    val submissionDate: LocalDateTime? = null,

    val lastUpdated: LocalDateTime? = null,

    // Validation tracking
    val mentorValidated: Boolean = false,
    val administrationValidated: Boolean = false,
    val commissionValidated: Boolean = false,

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "master_thesis_secretary_validations",
        joinColumns = [JoinColumn(name = "thesis_id")]
    )
    @AttributeOverrides(
        AttributeOverride(name = "secretaryId.value", column = Column(name = "secretary_id")),
        AttributeOverride(name = "archiveNumber.value", column = Column(name = "secretary_archive_number")),
        AttributeOverride(name = "validationDate", column = Column(name = "secretary_validation_date")),
        AttributeOverride(name = "phase", column = Column(name = "secretary_validation_phase"))
    )
    val secretaryValidations: List<SecretaryValidation> = emptyList(),

    // Documents tracking
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "master_thesis_required_documents",
        joinColumns = [JoinColumn(name = "thesis_id")]
    )
    @AttributeOverrides(
        AttributeOverride(name = "documentId", column = Column(name = "required_document_id")),
        AttributeOverride(name = "fileName", column = Column(name = "required_document_file_name")),
        AttributeOverride(name = "fileSize", column = Column(name = "required_document_file_size")),
        AttributeOverride(name = "mimeType", column = Column(name = "required_document_mime_type")),
        AttributeOverride(name = "uploadDate", column = Column(name = "required_document_upload_date")),
        AttributeOverride(name = "uploadedBy.value", column = Column(name = "required_document_uploaded_by")),
        AttributeOverride(name = "documentType", column = Column(name = "required_document_type"))
    )
    val requiredDocuments: List<DocumentInfo> = emptyList(),

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "documentId", column = Column(name = "thesis_draft_document_id")),
        AttributeOverride(name = "fileName", column = Column(name = "thesis_draft_file_name")),
        AttributeOverride(name = "fileSize", column = Column(name = "thesis_draft_file_size")),
        AttributeOverride(name = "mimeType", column = Column(name = "thesis_draft_mime_type")),
        AttributeOverride(name = "uploadDate", column = Column(name = "thesis_draft_upload_date")),
        AttributeOverride(name = "uploadedBy.value", column = Column(name = "thesis_draft_uploaded_by")),
        AttributeOverride(name = "documentType", column = Column(name = "thesis_draft_document_type"))
    )
    val thesisDraft: DocumentInfo? = null,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "documentId", column = Column(name = "revised_draft_document_id")),
        AttributeOverride(name = "fileName", column = Column(name = "revised_draft_file_name")),
        AttributeOverride(name = "fileSize", column = Column(name = "revised_draft_file_size")),
        AttributeOverride(name = "mimeType", column = Column(name = "revised_draft_mime_type")),
        AttributeOverride(name = "uploadDate", column = Column(name = "revised_draft_upload_date")),
        AttributeOverride(name = "uploadedBy.value", column = Column(name = "revised_draft_uploaded_by")),
        AttributeOverride(name = "documentType", column = Column(name = "revised_draft_document_type"))
    )
    val revisedDraft: DocumentInfo? = null,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "documentId", column = Column(name = "commission_report_document_id")),
        AttributeOverride(name = "fileName", column = Column(name = "commission_report_file_name")),
        AttributeOverride(name = "fileSize", column = Column(name = "commission_report_file_size")),
        AttributeOverride(name = "mimeType", column = Column(name = "commission_report_mime_type")),
        AttributeOverride(name = "uploadDate", column = Column(name = "commission_report_upload_date")),
        AttributeOverride(name = "uploadedBy.value", column = Column(name = "commission_report_uploaded_by")),
        AttributeOverride(name = "documentType", column = Column(name = "commission_report_document_type"))
    )
    val commissionReport: DocumentInfo? = null,

    // Commission tracking
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "commission_member_1_id"))
    )
    val commissionMember1Id: ExternalUserId? = null,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "commission_member_2_id"))
    )
    val commissionMember2Id: ExternalUserId? = null,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "commission_coordinator_id"))
    )
    val commissionCoordinatorId: ExternalUserId? = null,

    // Archive tracking
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "master_thesis_archive_numbers",
        joinColumns = [JoinColumn(name = "thesis_id")]
    )
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "archive_number_value")),
        AttributeOverride(name = "archiveDate", column = Column(name = "archive_date"))
    )
    val archiveNumbers: List<ArchiveNumber> = emptyList(),

    // Defense tracking
    val defenseScheduled: Boolean = false,
    val defenseDate: LocalDateTime? = null,
    val roomId: String? = null,
    val defended: Boolean = false,

    @Embedded
    val finalGrade: Grade? = null,

    // Auto-approval tracking
    val administrationValidationDate: LocalDateTime? = null,
    val secondSecretaryValidationDate: LocalDateTime? = null

) : LabeledEntity {

    override fun getId(): Identifier<out Any> {
        return thesisId ?: throw IllegalStateException("thesisId is null in MasterThesisView")
    }

    override fun getLabel(): String {
        return title?.value ?: ""
    }
}
