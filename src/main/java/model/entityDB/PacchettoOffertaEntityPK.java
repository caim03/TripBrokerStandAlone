package model.entityDB;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Christian on 18/11/2015.
 */
public class PacchettoOffertaEntityPK extends AbstractEntity implements Serializable {
    private int idPacchetto;
    private int idOfferta;

    @Column(name = "id_pacchetto")
    @Id
    public int getIdPacchetto() {
        return idPacchetto;
    }

    public void setIdPacchetto(int idPacchetto) {
        this.idPacchetto = idPacchetto;
    }

    @Column(name = "id_offerta")
    @Id
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

        PacchettoOffertaEntityPK that = (PacchettoOffertaEntityPK) o;

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
