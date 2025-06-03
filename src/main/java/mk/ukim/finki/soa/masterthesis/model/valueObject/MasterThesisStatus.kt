package mk.ukim.finki.soa.masterthesis.model.valueObject

enum class MasterThesisStatus(
    val masterOrder: Double,
    val displayName: String
) {

    STUDENT_THESIS_REGISTRATION(1.0, "Студентот пополнува пријава и прикачува документи."),
    MENTOR_VALIDATION(2.0, "Менторот валидира."),
    ADMINISTRATION_VALIDATION(3.0, "Студентската служба проверува и валидира."),
    COMMISSION_VALIDATION(4.0, "Наставно научна комисија валидира."),
    SECRETARY_VALIDATION(5.0, "Секретарот валидира."),
    STUDENT_DRAFT(6.0, "Студентот подготвува и менторот го прикачува драфт документ."),
    MENTOR_COMMISSION_CHOICE(7.0, "Менторот избира членови на комисија и валидира."),
    SECOND_SECRETARY_VALIDATION(8.0, "Секретарот валидира по изборот на комисија."),
    COMMISSION_CHECK(9.0, "Проверка од ННК."),
    THIRD_SECRETARY_VALIDATION(10.0, "Секретарот валидира по проверката од ННК."),
    DRAFT_CHECK(11.0, "Комисија проверува и остава коментари, студентот корегира, менторот прикачува нов драфт документ."),
    REPORT_VALIDATION(12.0, "Членови на комисијата оставаат коментари, менторот прикачува извештај."),
    FOURTH_SECRETARY_VALIDATION(13.0, "Секретар додава архивски број и валидира."),
    ADMINISTRATION_ARCHIVING(14.0, "Студентска служба архивира и валидира."),
    PROCESS_FINISHED(15.0, "Процесот е завршен и се чека одбрана на трудот."),
    FINISHED(16.0, "Процесот е завршен трудот е одбранет."),
    CANCELLED(30.0, "Откажано."),
    EDITED(31.0, "Направена измена"),
    STATUS_CHANGE(32.0, "Направена измена на статус"),
    COMMENT(33.0, "Коментар");

    fun getNextStatusFromCurrent(): MasterThesisStatus? {
        val statuses = MasterThesisStatus.values()
        for (i in 0 until statuses.size - 1) {
            if (statuses[i].masterOrder == this.masterOrder) {
                return statuses[i + 1]
            }
        }
        return null
    }

    fun getPreviousStatusFromCurrent(): MasterThesisStatus? {
        val statuses = MasterThesisStatus.values()
        for (i in 0 until statuses.size - 1) {
            if (statuses[i].masterOrder == this.masterOrder - 1) {
                return statuses[i]
            }
        }
        return null
    }

    override fun toString(): String {
        return "MasterThesisStatus(masterOrder=$masterOrder, displayName='$displayName')"
    }
}
