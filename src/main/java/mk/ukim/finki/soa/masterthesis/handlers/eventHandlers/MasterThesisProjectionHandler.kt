package mk.ukim.finki.soa.masterthesis.handlers.eventHandlers

import mk.ukim.finki.soa.masterthesis.model.event.administration.*
import mk.ukim.finki.soa.masterthesis.model.event.mentor.*
import mk.ukim.finki.soa.masterthesis.model.event.student.ThesisRegistrationSubmitted
import mk.ukim.finki.soa.masterthesis.model.valueObject.SecretaryValidation
import mk.ukim.finki.soa.masterthesis.model.view.MasterThesisView
import mk.ukim.finki.soa.masterthesis.repository.MasterThesisViewRepository
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MasterThesisProjectionHandler(
    private val masterThesisViewRepository: MasterThesisViewRepository
) {

    // =====================================
    // STUDENT EVENTS
    // =====================================

    // 1
    @EventHandler
    @Transactional
    fun on(event: ThesisRegistrationSubmitted) {
        val view = MasterThesisView(
            thesisId = event.thesisId,
            currentState = event.newState,
            studentId = event.studentId,
            mentorId = event.mentorId,
            title = event.title,
            shortDescription = event.shortDescription,
            submissionDate = event.submissionDate,
            lastUpdated = event.submissionDate,
            requiredDocuments = event.requiredDocuments.toMutableList()
        )

        masterThesisViewRepository.save(view)
    }

    // =====================================
    // MENTOR EVENTS
    // =====================================

    // 2
    @EventHandler
    @Transactional
    fun on(event: ThesisValidatedByMentor) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.mentorValidated = event.approved
        thesisView.field = event.field
        thesisView.lastUpdated = event.validationDate

        masterThesisViewRepository.save(thesisView)
    }

    // 6
    @EventHandler
    @Transactional
    fun on(event: ThesisDraftUploaded) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.thesisDraft = event.draftDocument
        thesisView.lastUpdated = event.uploadDate

        masterThesisViewRepository.save(thesisView)
    }

    // 7
    @EventHandler
    @Transactional
    fun on(event: CommissionMembersSelected) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.commissionMember1Id = event.commissionMember1Id
        thesisView.commissionMember2Id = event.commissionMember2Id
        thesisView.lastUpdated = event.selectionDate

        masterThesisViewRepository.save(thesisView)
    }

    // 11
    @EventHandler
    @Transactional
    fun on(event: RevisedThesisDraftUploaded) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.revisedDraft = event.revisedDraftDocument
        thesisView.lastUpdated = event.uploadDate

        masterThesisViewRepository.save(thesisView)
    }

    // 12
    @EventHandler
    @Transactional
    fun on(event: CommissionReportSubmitted) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.commissionReport = event.reportDocument
        thesisView.lastUpdated = event.submissionDate

        masterThesisViewRepository.save(thesisView)
    }

    // =====================================
    // ADMINISTRATION EVENTS
    // =====================================

    // 3
    @EventHandler
    @Transactional
    fun on(event: ThesisValidatedByAdministration) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.administrationValidated = event.approved
        thesisView.administrationValidationDate = event.validationDate
        thesisView.lastUpdated = event.validationDate

        masterThesisViewRepository.save(thesisView)
    }

    // 4
    @EventHandler
    @Transactional
    fun on(event: ThesisValidatedByCommission) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.commissionValidated = event.approved
        thesisView.commissionCoordinatorId = event.commissionCoordinatorId
        thesisView.lastUpdated = event.validationDate

        masterThesisViewRepository.save(thesisView)
    }

    // 5
    @EventHandler
    @Transactional
    fun on(event: ThesisValidatedBySecretary) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.secretaryValidations.add(
            SecretaryValidation(
                secretaryId = event.secretaryId,
                archiveNumber = event.archiveNumber,
                validationDate = event.validationDate,
                phase = event.newState.name
            )
        )
        thesisView.lastUpdated = event.validationDate

        masterThesisViewRepository.save(thesisView)
    }

    // 8
    @EventHandler
    @Transactional
    fun on(event: SecondSecretaryValidationCompleted) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.secondSecretaryValidationDate = event.validationDate
        thesisView.archiveNumbers.add(event.commissionArchiveNumber)
        thesisView.lastUpdated = event.validationDate

        masterThesisViewRepository.save(thesisView)
    }

    // 9
    @EventHandler
    @Transactional
    fun on(event: SecondaryValidatedByTeachingAndResearchCommission) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.commissionCoordinatorId = event.commissionCoordinatorId
        thesisView.commissionValidated = event.approved
        thesisView.lastUpdated = event.validationDate

        masterThesisViewRepository.save(thesisView)
    }

    // 10
    @EventHandler
    @Transactional
    fun on(event: ThirdSecretaryValidationCompleted) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.secretaryValidations.add(
            SecretaryValidation(
                secretaryId = event.secretaryId,
                archiveNumber = event.archiveNumber,
                validationDate = event.validationDate,
                phase = "THIRD_SECRETARY_VALIDATION"
            )
        )
        thesisView.lastUpdated = event.validationDate

        masterThesisViewRepository.save(thesisView)
    }

    // 13
    @EventHandler
    @Transactional
    fun on(event: FourthSecretaryValidationCompleted) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.secondSecretaryValidationDate = event.validationDate // reuse column for secretary validations
        thesisView.archiveNumbers.add(event.archiveNumber)
        thesisView.lastUpdated = event.validationDate

        masterThesisViewRepository.save(thesisView)
    }

    // 14
    @EventHandler
    @Transactional
    fun on(event: ThesisArchived) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.archiveProcessValidated = event.archiveProcessValidated
        thesisView.archiveDate = event.archiveDate
        thesisView.lastUpdated = event.archiveDate

        masterThesisViewRepository.save(thesisView)
    }

    // 15
    @EventHandler
    @Transactional
    fun on(event: ThesisDefenseScheduled) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.defenseScheduled = true
        thesisView.defenseDate = event.defenseDate
        thesisView.roomId = event.roomId
        thesisView.lastUpdated = event.schedulingDate

        masterThesisViewRepository.save(thesisView)
    }

    // 16
    @EventHandler
    @Transactional
    fun on(event: ThesisMarkedAsDefended) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.currentState = event.newState
        thesisView.defended = event.successful
        thesisView.defenseDate = event.defenseDate
        thesisView.finalGrade = event.finalGrade
        thesisView.lastUpdated = event.defenseDate

        masterThesisViewRepository.save(thesisView)
    }

    // =====================================
    // SYSTEM EVENTS
    // =====================================

}
