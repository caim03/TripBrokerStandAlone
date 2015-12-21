package model.entityDB;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "Evento", schema = "trip_broker")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class EventoEntity extends OffertaEntity {

    private int numeroPosto;
    private int oraInizio;
    private int oraFine;
    private String luogo;

    @Basic
    @Column(name = "numero_posto")
    public int getNumeroPosto() {
        return numeroPosto;
    }

    public void setNumeroPosto(int numeroPosto) {
        this.numeroPosto = numeroPosto;
    }

    @Basic
    @Column(name = "ora_inizio")
    public int getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(int oraInizio) {
        this.oraInizio = oraInizio;
    }

    @Basic
    @Column(name = "ora_fine")
    public int getOraFine() {
        return oraFine;
    }

    public void setOraFine(int oraFine) {
        this.oraFine = oraFine;
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

        EventoEntity that = (EventoEntity) o;

        return super.equals(o) && numeroPosto == that.numeroPosto && oraInizio == that.oraInizio && oraFine == that.oraFine
                && ((luogo != null && luogo.equals(that.luogo)) || (luogo == null && that.luogo == null));
    }

    @Override
    public int hashCode() {
        int result = super.getId();
        result = 31 * result + numeroPosto;
        result = 31 * result + oraInizio;
        result = 31 * result + oraFine;
        result = 31 * result + (luogo != null ? luogo.hashCode() : 0);
        return result;
    }
}
