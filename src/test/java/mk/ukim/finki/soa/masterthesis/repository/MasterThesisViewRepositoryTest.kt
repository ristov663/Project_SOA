package mk.ukim.finki.soa.masterthesis.repository

import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisTitle
import mk.ukim.finki.soa.masterthesis.model.valueObject.StudentId
import mk.ukim.finki.soa.masterthesis.model.view.MasterThesisView
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.UUID

@DataJpaTest
class MasterThesisViewRepositoryTest @Autowired constructor(
    val repository: MasterThesisViewRepository
) {

    @Test
    fun `should find thesis by student id`() {
        val title = MasterThesisTitle("Test Thesis")
        val studentId = StudentId(UUID.randomUUID().toString())
        val thesisView = MasterThesisView(
            thesisId = MasterThesisId(UUID.randomUUID().toString()),
            studentId = studentId,
            title = title
        )
        repository.save(thesisView)
        val found = repository.findByStudentId(studentId)
        assertThat(found).hasSize(1)
        assertThat(found.first().title)
            .isEqualTo(MasterThesisTitle("Test Thesis"))

    }
}
