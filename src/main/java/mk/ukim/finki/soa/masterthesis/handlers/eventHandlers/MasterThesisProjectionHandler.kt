package mk.ukim.finki.soa.masterthesis.handlers.eventHandlers

import mk.ukim.finki.soa.masterthesis.model.event.student.ThesisRegistrationSubmitted
import mk.ukim.finki.soa.masterthesis.model.view.MasterThesisView
import mk.ukim.finki.soa.masterthesis.repository.MasterThesisViewRepository
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MasterThesisProjectionHandler(
    private val masterThesisViewRepository: MasterThesisViewRepository
) {

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
}
