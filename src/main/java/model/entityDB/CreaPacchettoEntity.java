package model.entityDB;

import javax.persistence.*;

@Entity
@Table(name = "Crea_Pacchetto", schema = "trip_broker")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class CreaPacchettoEntity extends ProdottoEntity {
    //private int id;
    private int stato;
    private String motivazione;
    private int creatore;

    /*@Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    @Basic
    @Column(name = "stato")
    public int getStato() {
        return stato;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    @Basic
    @Column(name = "motivazione")
    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
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

        CreaPacchettoEntity that = (CreaPacchettoEntity) o;

        //if (id != that.id) return false;
        if (stato != that.stato) return false;
        if (creatore != that.creatore) return false;
        if (motivazione != null ? !motivazione.equals(that.motivazione) : that.motivazione != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.getId();
        result = 31 * result + stato;
        result = 31 * result + (motivazione != null ? motivazione.hashCode() : 0);
        result = 31 * result + creatore;
        return result;
    }
}
