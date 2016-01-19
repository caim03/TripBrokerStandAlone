package model.entityDB;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/*** This class represents the table of packets in DataBase, and it is used to retrieve, save, delete or update
 *   packets.
 *   It contains three private attributes that represent the columns of table in DataBase:
 *      1. 'stato'
 *      2. 'motivazione'
 *      3. 'creatore'
 *
 *   The attribute 'stato' indicates the state of a packet; in particular exist three type of state:
 *      - Packet to be approved (0)
 *      - Approved packet (1)
 *      - Rejected packet (2)
 *
 *   The attribute 'motivazione' indicates the reasons for the rejected;
 *   in this way the designer can make the necessary changes to the packet
 *
 *   The attribute 'creatore' indicates the dependent (a designer) which has created the packet ***/

@Entity
@Table(name = "Crea_Pacchetto", schema = "trip_broker")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class CreaPacchettoEntity extends ProdottoEntity {
    //private int id;
    private int stato;
    private String motivazione;
    private int creatore;

    @Basic
    @Column(name = "stato")
    public int getStato() {
        /** @result int; return the state of packet **/
        return stato;
    }

    public void setStato(int stato) {
        /** @param int; set the state of packet **/
        this.stato = stato;
    }

    @Basic
    @Column(name = "motivazione")
    public String getMotivazione() {
        /** @result String; return the motivation of rejected packet **/
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        /** @param String; set the motivation of rejected packet **/
        this.motivazione = motivazione;
    }

    @Basic
    @Column(name = "creatore")
    public int getCreatore() {
        /** @result int; return the identifier of dependent **/
        return creatore;
    }

    public void setCreatore(int creatore) {
        /** @param int; set the identifier of dependent **/
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
