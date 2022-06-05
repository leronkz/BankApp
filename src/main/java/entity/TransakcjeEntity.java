package entity;

import javax.persistence.*;
import java.math.BigDecimal;
@NamedQuery(
        name = "TransakcjeByID",
        query = "SELECT t from TransakcjeEntity t WHERE t.id=?1"
)
@NamedQuery(
        name = "TransakcjeByPrincipal",
        query = "SELECT t from TransakcjeEntity t WHERE t.idPrincipal=?1"
)
@NamedQuery(
        name = "TransakcjeByRecipent",
        query = "SELECT t from TransakcjeEntity t WHERE t.idRecipent=?1"
)
@NamedQuery(
        name = "TransakcjeByAmount",
        query = "SELECT t FROM TransakcjeEntity t WHERE t.amount=?1"
)
@Entity
@Table(name = "transakcje", schema = "bank")
public class TransakcjeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "id_principal")
    private Integer idPrincipal;
    @Basic
    @Column(name = "id_recipent")
    private Integer idRecipent;
    @Basic
    @Column(name = "amount")
    private BigDecimal amount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_principal", referencedColumnName = "id",insertable = false,updatable = false)
    private KlienciEntity klienciByIdPrincipal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdPrincipal() {
        return idPrincipal;
    }

    public void setIdPrincipal(Integer idPrincipal) {
        this.idPrincipal = idPrincipal;
    }

    public Integer getIdRecipent() {
        return idRecipent;
    }

    public void setIdRecipent(Integer idRecipent) {
        this.idRecipent = idRecipent;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransakcjeEntity that = (TransakcjeEntity) o;

        if (id != that.id) return false;
        if (idPrincipal != null ? !idPrincipal.equals(that.idPrincipal) : that.idPrincipal != null) return false;
        if (idRecipent != null ? !idRecipent.equals(that.idRecipent) : that.idRecipent != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (idPrincipal != null ? idPrincipal.hashCode() : 0);
        result = 31 * result + (idRecipent != null ? idRecipent.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    public KlienciEntity getKlienciByIdPrincipal() {
        return klienciByIdPrincipal;
    }

    public void setKlienciByIdPrincipal(KlienciEntity klienciByIdPrincipal) {
        this.klienciByIdPrincipal = klienciByIdPrincipal;
    }
    public static TypedQuery<TransakcjeEntity> getTransactionsByID(EntityManager man,int id){
        return man.createNamedQuery("TransakcjeByID",TransakcjeEntity.class).setParameter(1,id);
    }
    public static TypedQuery<TransakcjeEntity> getTransactionsByPrincipal(EntityManager man,Integer id_principal){
        return man.createNamedQuery("TransakcjeByPrincipal",TransakcjeEntity.class).setParameter(1,id_principal);
    }
    public static TypedQuery<TransakcjeEntity> getTransactionsByRecipent(EntityManager man,int id_recipent){
        return man.createNamedQuery("TransakcjeByRecipent",TransakcjeEntity.class).setParameter(1,id_recipent);
    }
    public static TypedQuery<TransakcjeEntity> getTransactionsByAmount(EntityManager man,BigDecimal amount){
        return man.createNamedQuery("TransakcjeByAmount",TransakcjeEntity.class).setParameter(1,amount);
    }
}
