package model.entityDB;

import javax.persistence.*;

/*** This class represents the table of relations between
 *   'OffertaEntity' and 'CreaPacchettoEntity' in DataBase.
 *   It contains two private attributes which reference the primary key of tables;
 *   it also contains the attribute 'posizione', referring to the right order of offers into the packet ***/

@Entity
@Table(name = "Pacchetto_Offerta", schema = "trip_broker", catalog = "")
@IdClass(PacchettoOffertaEntityPK.class)
public class PacchettoOffertaEntity extends AbstractEntity {

    private int idPacchetto;
    private int idOfferta;
    private int posizione;

    @Id
    @Column(name = "id_pacchetto")
    public int getIdPacchetto() {
        /** @result int; return the identifier of the packet **/
        return idPacchetto;
    }

    public void setIdPacchetto(int idPacchetto) {
        /** @param int; set the identifier of the packet **/
        this.idPacchetto = idPacchetto;
    }

    @Id
    @Column(name = "id_offerta")
    public int getIdOfferta() {
        /** @result int; return the identifier of the offer included into packet **/
        return idOfferta;
    }

    public void setIdOfferta(int idOfferta) {
        /** @param int; set the identifier of the packet **/
        this.idOfferta = idOfferta;
    }

    @Column(name = "posizione")
    public int getPosizione() {
        /** @return int; return the position of an offer into packet **/
        return posizione;
    }

    public void setPosizione(int posizione) {
        /** @param int; set the position of an offer into the packet **/
        this.posizione = posizione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PacchettoOffertaEntity that = (PacchettoOffertaEntity) o;

        return idPacchetto == that.idPacchetto && idOfferta == that.idOfferta && posizione == that.posizione;
    }

    @Override
    public int hashCode() {
        int result = idPacchetto;
        result = 31 * result + idOfferta;
        return result;
    }
}
