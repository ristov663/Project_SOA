package mk.ukim.finki.soa.masterthesis.handlers.eventHandlers

import mk.ukim.finki.soa.masterthesis.model.event.student.StudentEnrollmentConfirmedOnThesis
import mk.ukim.finki.soa.masterthesis.repository.MasterThesisViewRepository
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class StudentEnrollmentProjectionHandler(
    private val masterThesisViewRepository: MasterThesisViewRepository
) {

    @EventHandler
    @Transactional
    fun on(event: StudentEnrollmentConfirmedOnThesis) {
        val thesisView = masterThesisViewRepository.findById(event.thesisId).orElse(null)
            ?: return

        thesisView.studentEnrollmentValidated = true
        thesisView.studentEnrollmentInfo = event.enrollment
        thesisView.studentEnrollmentValidationDate = event.validationDate
        thesisView.lastUpdated = event.validationDate

        masterThesisViewRepository.save(thesisView)
    }
}
