package mk.ukim.finki.soa.masterthesis.model.valueObject

import java.io.Serializable

enum class ProfessorTitle(val isProfessor: Boolean) : Serializable {
    TUTOR(false),
    TEACHING_ASSISTANT(false),
    ASSISTANT_PROFESSOR(true),
    ASSOCIATE_PROFESSOR(true),
    PROFESSOR(true),
    RETIRED(true),
    ELECTED_ASSISTANT_PROFESSOR(true),
    ELECTED_ASSOCIATE_PROFESSOR(true),
    ELECTED_PROFESSOR(true),
    EXTERNAL_EXPERT(false);
}
