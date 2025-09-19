package mk.ukim.finki.soa.masterthesis.repository

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import mk.ukim.finki.soa.masterthesis.model.view.MasterThesisView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MasterThesisViewRepository : JpaRepository<MasterThesisView, MasterThesisId> {
    fun findByStudentId(studentId: StudentId): List<MasterThesisView>
}
