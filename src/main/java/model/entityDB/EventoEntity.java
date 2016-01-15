package model.entityDB;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "Evento", schema = "trip_broker")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class EventoEntity extends OffertaEntity {

    private int numeroPosto;
    private String luogo;
    private Timestamp dataFine;

    @Basic
    @Column(name = "numero_posto")
    public int getNumeroPosto() {
        return numeroPosto;
    }

    public void setNumeroPosto(int numeroPosto) {
        this.numeroPosto = numeroPosto;
    }

    @Basic
    @Column(name = "luogo")
    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }


    @Basic
    @Column(name = "data_fine")
    public Timestamp getDataFine() {
        return this.dataFine;
    }

    public void setDataFine(Timestamp dataFine) {
        this.dataFine = dataFine;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventoEntity that = (EventoEntity) o;

        return super.equals(o) && numeroPosto == that.numeroPosto
                && ((dataFine == null && that.dataFine == null) || (dataFine != null && dataFine.equals(that.dataFine)))
                && ((luogo != null && luogo.equals(that.luogo)) || (luogo == null && that.luogo == null));
    }

    @Override
    public int hashCode() {
        int result = super.getId();
        result = 31 * result + numeroPosto;
        result = 31 * result + (dataFine != null ? dataFine.hashCode() : 0);
        result = 31 * result + (luogo != null ? luogo.hashCode() : 0);
        return result;
    }
}
