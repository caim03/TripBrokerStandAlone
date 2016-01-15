package model.entityDB;

import javax.persistence.*;

/*** This class represents the table of relations between
 *   'OffertaEntity' and 'ViaggioGruppoEntity' in DataBase.
 *   It contains two private attributes which reference the primary key of tables ***/

@Entity
@Table(name = "Gruppo_Offerta", schema = "trip_broker")
@IdClass(GruppoOffertaEntityPK.class)
public class GruppoOffertaEntity extends AbstractEntity {

    private int idGruppo;
    private int idOfferta;

    public GruppoOffertaEntity() {}

    @Id
    @Column(name = "id_gruppo")
    public int getIdGruppo() {
        return idGruppo;
    }
    public void setIdGruppo(int idGruppo) {
        this.idGruppo = idGruppo;
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

        GruppoOffertaEntityPK that = (GruppoOffertaEntityPK) o;

        return super.equals(o) && idGruppo == that.getIdGruppo() && idOfferta == that.getIdOfferta();
    }

    @Override
    public int hashCode() {
        int result = idGruppo;
        result = 31 * result + idOfferta;
        return result;
    }
}
