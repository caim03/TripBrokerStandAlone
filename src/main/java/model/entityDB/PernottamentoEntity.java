package model.entityDB;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Pernottamento", schema = "trip_broker")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class PernottamentoEntity extends OffertaEntity {
    //private int id;
    private Date dataFinale;
    private String servizio;
    private String qualità;
    private String luogo;

    /*@Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    @Basic
    @Column(name = "data_finale")
    public Date getDataFinale() {
        return dataFinale;
    }

    public void setDataFinale(Date dataFinale) {
        this.dataFinale = dataFinale;
    }

    @Basic
    @Column(name = "servizio")
    public String getServizio() {
        return servizio;
    }

    public void setServizio(String servizio) {
        this.servizio = servizio;
    }

    @Basic
    @Column(name = "qualità")
    public String getQualità() {
        return qualità;
    }

    public void setQualità(String qualità) {
        this.qualità = qualità;
    }

    @Basic
    @Column(name = "luogo")
    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PernottamentoEntity that = (PernottamentoEntity) o;

        //if (id != that.id) return false;
        if (dataFinale != null ? !dataFinale.equals(that.dataFinale) : that.dataFinale != null) return false;
        if (servizio != null ? !servizio.equals(that.servizio) : that.servizio != null) return false;
        if (qualità != null ? !qualità.equals(that.qualità) : that.qualità != null) return false;
        if (luogo != null ? !luogo.equals(that.luogo) : that.luogo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.getId();
        result = 31 * result + (dataFinale != null ? dataFinale.hashCode() : 0);
        result = 31 * result + (servizio != null ? servizio.hashCode() : 0);
        result = 31 * result + (qualità != null ? qualità.hashCode() : 0);
        result = 31 * result + (luogo != null ? luogo.hashCode() : 0);
        return result;
    }
}
