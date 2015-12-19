package model.entityDB;

import javax.persistence.*;

@Entity
@Table(name = "Pacchetto_Offerta", schema = "trip_broker", catalog = "")
@IdClass(PacchettoOffertaEntityPK.class)
public class PacchettoOffertaEntity extends AbstractEntity {

    private int idPacchetto;
    private int idOfferta;

    @Id
    @Column(name = "id_pacchetto")
    public int getIdPacchetto() {
        return idPacchetto;
    }

    public void setIdPacchetto(int idPacchetto) {
        this.idPacchetto = idPacchetto;
    }

    @Id
    @Column(name = "id_offerta")
    public int getIdOfferta() {
        return idOfferta;
    }

    public void setIdOfferta(int idOfferta) {
        this.idOfferta = idOfferta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PacchettoOffertaEntity that = (PacchettoOffertaEntity) o;

        if (idPacchetto != that.idPacchetto) return false;
        if (idOfferta != that.idOfferta) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPacchetto;
        result = 31 * result + idOfferta;
        return result;
    }
}
