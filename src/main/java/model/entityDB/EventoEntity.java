package model.entityDB;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "Evento", schema = "trip_broker")
public class EventoEntity extends OffertaEntity {
    //private int id;
    private int numeroPosto;
    private int oraInizio;
    private int oraFine;
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

        //if (id != that.id) return false;
        if (numeroPosto != that.numeroPosto) return false;
        if (oraInizio != that.oraInizio) return false;
        if (oraFine != that.oraFine) return false;
        if (luogo != null ? !luogo.equals(that.luogo) : that.luogo != null) return false;

        return true;
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
