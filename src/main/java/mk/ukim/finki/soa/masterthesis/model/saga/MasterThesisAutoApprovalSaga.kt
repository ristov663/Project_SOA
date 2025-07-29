package mk.ukim.finki.soa.masterthesis.model.saga

import mk.ukim.finki.soa.masterthesis.model.command.AutoApproveCommissionValidation
import mk.ukim.finki.soa.masterthesis.model.command.AutoApproveSecondCommissionValidation
import mk.ukim.finki.soa.masterthesis.model.event.*
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.deadline.DeadlineManager
import org.axonframework.deadline.annotation.DeadlineHandler
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.modelling.saga.SagaLifecycle
import org.axonframework.spring.stereotype.Saga
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.time.Duration
import java.time.LocalDateTime

@Saga
class MasterThesisAutoApprovalSaga {

    companion object {
        private val logger = LoggerFactory.getLogger(MasterThesisAutoApprovalSaga::class.java)
        private const val COMMISSION_AUTO_APPROVAL_DEADLINE = "COMMISSION_AUTO_APPROVAL_DEADLINE"
        private const val SECOND_COMMISSION_AUTO_APPROVAL_DEADLINE = "SECOND_COMMISSION_AUTO_APPROVAL_DEADLINE"
        private const val AUTO_APPROVAL_TIMEOUT_DAYS = 5L
    }

    @Transient
    @Autowired
    private lateinit var commandGateway: CommandGateway

    @Transient
    @Autowired
    private lateinit var deadlineManager: DeadlineManager

    private lateinit var thesisId: MasterThesisId
    private var firstCommissionValidationPending = false
    private var secondCommissionValidationPending = false
    private var administrationValidationDate: LocalDateTime? = null
    private var secondSecretaryValidationDate: LocalDateTime? = null

    // ====================
    // FIRST COMMISSION AUTO-APPROVAL SAGA
    // ====================

    /**
     * Starts the saga when administration validates the thesis.
     * Sets up a 5-day deadline for commission validation.
     */
    @StartSaga
    @SagaEventHandler(associationProperty = "thesisId")
    fun handle(event: ThesisValidatedByAdministration) {
        if (!event.approved) {
            // If administration rejected, no need for commission validation
            logger.info("Administration rejected thesis ${event.thesisId}, ending saga")
            SagaLifecycle.end()
            return
        }

        logger.info("Starting auto-approval saga for thesis ${event.thesisId}")

        this.thesisId = event.thesisId
        this.firstCommissionValidationPending = true
        this.administrationValidationDate = event.validationDate

        // Schedule deadline for auto-approval after 5 days
        val deadline = deadlineManager.schedule(
            Duration.ofDays(AUTO_APPROVAL_TIMEOUT_DAYS),
            COMMISSION_AUTO_APPROVAL_DEADLINE,
            event.thesisId
        )

        logger.info("Scheduled commission auto-approval deadline for thesis ${event.thesisId} in $AUTO_APPROVAL_TIMEOUT_DAYS days")
    }

    /**
     * Handles manual commission validation.
     * Cancels the auto-approval deadline.
     */
    @SagaEventHandler(associationProperty = "thesisId")
    fun handle(event: ThesisValidatedByCommission) {
        if (!firstCommissionValidationPending) {
            return
        }

        logger.info("Commission manually validated thesis ${event.thesisId}, cancelling auto-approval deadline")

        // Cancel the scheduled auto-approval
        deadlineManager.cancelAllWithinScope(COMMISSION_AUTO_APPROVAL_DEADLINE)
        this.firstCommissionValidationPending = false

        // Continue saga for potential second commission validation
        logger.info("First commission validation completed for thesis ${event.thesisId}")
    }

    /**
     * Deadline handler for first commission auto-approval.
     * Automatically approves if 5 days have passed without manual validation.
     */
    @DeadlineHandler(deadlineName = COMMISSION_AUTO_APPROVAL_DEADLINE)
    fun handleCommissionAutoApprovalDeadline(thesisId: MasterThesisId) {
        if (!firstCommissionValidationPending) {
            logger.info("Commission validation already completed for thesis $thesisId, ignoring deadline")
            return
        }

        logger.info("Auto-approving commission validation for thesis $thesisId due to 5-day timeout")

        val autoApprovalCommand = AutoApproveCommissionValidation(
            thesisId = thesisId,
            autoApprovalDate = LocalDateTime.now(),
            reason = "Automatic approval due to 5-day timeout from administration validation"
        )

        try {
            commandGateway.sendAndWait<Void>(autoApprovalCommand)
            this.firstCommissionValidationPending = false
            logger.info("Successfully auto-approved commission validation for thesis $thesisId")
        } catch (e: Exception) {
            logger.error("Failed to auto-approve commission validation for thesis $thesisId", e)
            // Optionally retry or handle the error
        }
    }

    /**
     * Handles the auto-approval event to update saga state.
     */
    @SagaEventHandler(associationProperty = "thesisId")
    fun handle(event: CommissionValidationAutoApproved) {
        logger.info("Commission validation auto-approved for thesis ${event.thesisId}")
        this.firstCommissionValidationPending = false
    }

    // ====================
    // SECOND COMMISSION AUTO-APPROVAL SAGA
    // ====================

    /**
     * Handles second secretary validation completion.
     * Sets up another 5-day deadline for second commission validation.
     */
    @SagaEventHandler(associationProperty = "thesisId")
    fun handle(event: SecondSecretaryValidationCompleted) {
        logger.info("Second secretary validation completed for thesis ${event.thesisId}, starting second commission deadline")

        this.secondCommissionValidationPending = true
        this.secondSecretaryValidationDate = event.validationDate

        // Schedule deadline for second commission auto-approval after 5 days
        val deadline = deadlineManager.schedule(
            Duration.ofDays(AUTO_APPROVAL_TIMEOUT_DAYS),
            SECOND_COMMISSION_AUTO_APPROVAL_DEADLINE,
            event.thesisId
        )

        logger.info("Scheduled second commission auto-approval deadline for thesis ${event.thesisId} in $AUTO_APPROVAL_TIMEOUT_DAYS days")
    }

    /**
     * Handles manual second commission validation.
     * Cancels the second auto-approval deadline.
     */
    @SagaEventHandler(associationProperty = "thesisId")
    fun handle(event: SecondaryValidatedByTeachingAndResearchCommission) {
        if (!secondCommissionValidationPending) {
            return
        }

        logger.info("Second commission manually validated thesis ${event.thesisId}, cancelling auto-approval deadline")

        // Cancel the scheduled auto-approval
        deadlineManager.cancelAllWithinScope(SECOND_COMMISSION_AUTO_APPROVAL_DEADLINE)
        this.secondCommissionValidationPending = false

        logger.info("Second commission validation completed for thesis ${event.thesisId}")
    }

    /**
     * Deadline handler for second commission auto-approval.
     * Automatically approves if 5 days have passed without manual validation.
     */
    @DeadlineHandler(deadlineName = SECOND_COMMISSION_AUTO_APPROVAL_DEADLINE)
    fun handleSecondCommissionAutoApprovalDeadline(thesisId: MasterThesisId) {
        if (!secondCommissionValidationPending) {
            logger.info("Second commission validation already completed for thesis $thesisId, ignoring deadline")
            return
        }

        logger.info("Auto-approving second commission validation for thesis $thesisId due to 5-day timeout")

        val autoApprovalCommand = AutoApproveSecondCommissionValidation(
            thesisId = thesisId,
            autoApprovalDate = LocalDateTime.now(),
            reason = "Automatic approval due to 5-day timeout from second secretary validation"
        )

        try {
            commandGateway.sendAndWait<Void>(autoApprovalCommand)
            this.secondCommissionValidationPending = false
            logger.info("Successfully auto-approved second commission validation for thesis $thesisId")
        } catch (e: Exception) {
            logger.error("Failed to auto-approve second commission validation for thesis $thesisId", e)
            // Optionally retry or handle the error
        }
    }

    /**
     * Handles the second auto-approval event to update saga state.
     */
    @SagaEventHandler(associationProperty = "thesisId")
    fun handle(event: SecondCommissionValidationAutoApproved) {
        logger.info("Second commission validation auto-approved for thesis ${event.thesisId}")
        this.secondCommissionValidationPending = false
    }

    // ====================
    // SAGA COMPLETION
    // ====================

    /**
     * Ends the saga when thesis reaches final state or gets rejected.
     * This could be triggered by various final events.
     */
    @EndSaga
    @SagaEventHandler(associationProperty = "thesisId")
    fun handle(event: ThesisMarkedAsDefended) {
        logger.info("Thesis ${event.thesisId} has been defended, ending auto-approval saga")
    }

    // Optional: End saga if thesis gets rejected at any stage
    @EndSaga
    @SagaEventHandler(associationProperty = "thesisId")
    fun handleRejection(event: ThesisValidatedByAdministration) {
        if (!event.approved) {
            logger.info("Thesis ${event.thesisId} was rejected by administration, ending saga")
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "thesisId")
    fun handleMentorRejection(event: ThesisValidatedByMentor) {
        if (!event.approved) {
            logger.info("Thesis ${event.thesisId} was rejected by mentor, ending saga")
        }
    }
}
