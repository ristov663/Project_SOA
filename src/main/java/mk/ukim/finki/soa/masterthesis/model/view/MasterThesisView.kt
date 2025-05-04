package mk.ukim.finki.soa.masterthesis.model.view


import jakarta.persistence.*
import mk.ukim.finki.soa.masterthesis.model.valueObject.MasterThesisId
import org.hibernate.annotations.Immutable
import java.time.ZonedDateTime

@Entity
@Table(name = "masterThesis")
@Immutable
data class MasterThesisView(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    val id: MasterThesisId,
    val title: String,
    val amount: Money,
    val date: ZonedDateTime,
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "vendor_id"))
    val vendorId: VendorId,
    @Embedded
    @Column(name = "account_id", nullable = false)
    val accountId: AccountId,

    ) : LabeledEntity {
    override fun getId(): Identifier<out Any> {
        return id
    }

    override fun getLabel(): String {
        return title
    }
}
