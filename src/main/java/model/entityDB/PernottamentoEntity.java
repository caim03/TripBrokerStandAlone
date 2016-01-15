package model.entityDB;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/*** This class represents the table of stays in DataBase, and it is used to retrieve,
 *   save, delete or update stays.
 *   It contains four private attributes that represent the columns of table in DataBase:
 *      1. 'dataFinale'
 *      2. 'servizio'
 *      3. 'qualità'
 *      4. 'luogo'
 *
 *   The attribute 'dataFinale' indicates the Timestamp of the final date;
 *   it can be interpreted in three ways:
 *      - if offer is a travel 'dataFinale' indicates the arrival date
 *      - if offer is an event 'dataFinale' indicates the date of end event
 *      - if offer is a stay 'dataFinale' indicates the date of end staying
 *
 *   The attribute 'servizio' indicates the service of the stay:
 *      - half pension
 *      - full board
 *
 *   The attribute 'qualità' indicates the number of the start of the hotel
 *
 *   The attribute 'luogo' indicates the place of the staying ***/

@Entity
@Table(name = "Pernottamento", schema = "trip_broker")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class PernottamentoEntity extends OffertaEntity {
    //private int id;
    private Timestamp dataFinale;
    private String servizio;
    private String qualità;
    private String luogo;

    @Basic
    @Column(name = "data_finale")
    public Timestamp getDataFinale() {
        /** @result Timestamp; return the timestamp of the final date **/
        return dataFinale;
    }

    public void setDataFinale(Timestamp dataFinale) {
        /** @param Timestamp; set the timestamp of the final date **/
        this.dataFinale = dataFinale;
    }

    @Basic
    @Column(name = "servizio")
    public String getServizio() {
        /** @result String; return the service as string **/
        return servizio;
    }

    public void setServizio(String servizio) {
        /** @param String; set the service of the stay **/
        this.servizio = servizio;
    }

    @Basic
    @Column(name = "qualità")
    public String getQualità() {
        /** @result String; return the quality as string **/
        return qualità;
    }

    public void setQualità(String qualità) {
        /** @param String; set the quality of the stay **/
        this.qualità = qualità;
    }

    @Basic
    @Column(name = "luogo")
    public String getLuogo() {
        /** @result String; return the place of stay **/
        return luogo;
    }

    public void setLuogo(String luogo) {
        /** @param String; set the place of the stay **/
        this.luogo = luogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PernottamentoEntity that = (PernottamentoEntity) o;

        //if (id != that.id) return false;
        if (dataFinale != null ? !dataFinale.equals(that.dataFinale) : that.dataFinale != null) return false;
        if (servizio != null ? !servizio.equals(that.servizio) : that.servizio != null) return false;
        if (qualità != null ? !qualità.equals(that.qualità) : that.qualità != null) return false;
        if (luogo != null ? !luogo.equals(that.luogo) : that.luogo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.getId();
        result = 31 * result + (dataFinale != null ? dataFinale.hashCode() : 0);
        result = 31 * result + (servizio != null ? servizio.hashCode() : 0);
        result = 31 * result + (qualità != null ? qualità.hashCode() : 0);
        result = 31 * result + (luogo != null ? luogo.hashCode() : 0);
        return result;
    }
}
