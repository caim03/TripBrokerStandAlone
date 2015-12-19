package model.entityDB;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

class GruppoOffertaEntityPK extends AbstractEntity implements Serializable {

    private int idGruppo;
    private int idOfferta;

    public GruppoOffertaEntityPK() {}

    @Column(name = "id_gruppo")
    @Id
    public int getIdGruppo() {
        return idGruppo;
    }
    public void setIdGruppo(int idGruppo) {
        this.idGruppo = idGruppo;
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

        GruppoOffertaEntityPK that = (GruppoOffertaEntityPK) o;

        return super.equals(o) && idGruppo == that.idGruppo && idOfferta == that.idOfferta;
    }

    @Override
    public int hashCode() {
        int result = idGruppo;
        result = 31 * result + idOfferta;
        return result;
    }
}
