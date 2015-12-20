package model.entityDB;

import javax.persistence.*;

@Entity
@Table(name = "Viaggio_Gruppo", schema = "trip_broker")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public class ViaggioGruppoEntity extends ProdottoEntity {

    private int min;
    private int max;
    private int prenotazioni;
    private int creatore;

    @Basic
    @Column(name = "min")
    public int getMin() {
        return min;
    }
    public void setMin(int min) {
        this.min = min;
    }

    @Basic
    @Column(name = "max")
    public int getMax() {
        return this.max;
    }
    public void setMax(int max) {
        this.max = max;
    }

    @Basic
    @Column(name = "prenotazioni")
    public int getPrenotazioni() {
        return this.prenotazioni;
    }
    public void setPrenotazioni(int prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    @Basic
    @Column(name = "creatore")
    public int getCreatore() {
        return creatore;
    }

    public void setCreatore(int creatore) {
        this.creatore = creatore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViaggioGruppoEntity that = (ViaggioGruppoEntity) o;

        //if (id != that.id) return false;
        return super.equals(o) && min == that.min && max == that.max && creatore == that.creatore;
    }

    @Override
    public int hashCode() {
        int result = super.getId();
        result = 31 * result + min;
        result = 31 * result + max;
        result = 31 * result + creatore;
        return result;
    }

}
