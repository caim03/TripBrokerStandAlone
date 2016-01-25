package model.entityDB;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/*** This class represents the table of travels in DataBase, and it is used to retrieve,
 *   save, delete or update travels.
 *   It contains six private attributes that represent the columns of table in DataBase:
 *      1. 'destinazione'
 *      2. 'mezzo'
 *      3. 'classe'
 *      4. 'stazionePartenza'
 *      5. 'stazioneArrivo'
 *      6. 'dataArrivo'
 *
 *   The attribute 'destinazione' indicates the arrival city of the travel
 *
 *   The attribute 'mezzo' indicates the vehicle with which the travel happens
 *
 *   The attribute 'classe' indicates the class of the vehicle with which the travel happens
 *
 *   The attribute 'stazionePartenza' indicates the departure station of the travel
 *
 *   The attribute 'stazioneArrivo' indicates the arrival station of the travel
 *
 *   The attribute 'dataArrivo' indicates the arrival date as timestamp ***/

@Entity
@Table(name = "Viaggio", schema = "trip_broker")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class ViaggioEntity extends OffertaEntity {

    private String destinazione;
    private String mezzo;
    private String classe;
    private Timestamp dataArrivo;

    @Basic
    @Column(name = "destinazione")
    public String getDestinazione() {
        /** @result String; return the arrival city of the travel **/
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        /** @param String; set the arrival city of the travel **/
        this.destinazione = destinazione;
    }

    @Basic
    @Column(name = "mezzo")
    public String getMezzo() {
        /** @result String; return the vehicle of the travel **/
        return mezzo;
    }

    public void setMezzo(String mezzo) {
        /** @param String; set the vehicle of the travel **/
        this.mezzo = mezzo;
    }

    @Basic
    @Column(name = "classe")
    public String getClasse() {
        /** @result String; return the class of the vehicle with which the travel happens **/
        return classe;
    }

    public void setClasse(String classe) {
        /** @param String; set the class of the vehicle with which the travel happens **/
        this.classe = classe;
    }

    @Basic
    @Column (name = "data_arrivo")
    public Timestamp getDataArrivo() {
        /** @result Timestamp; return the date as timestamp of the arrival date **/
        return dataArrivo;
    }

    public void setDataArrivo(Timestamp dataArrivo) {
        /** @param Timestamp; set the date as timestamp of the arrival date **/
        this.dataArrivo = dataArrivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViaggioEntity that = (ViaggioEntity) o;

        //if (super.getId() != that.id) return false;
        if (destinazione != null ? !destinazione.equals(that.destinazione) : that.destinazione != null) return false;
        if (mezzo != null ? !mezzo.equals(that.mezzo) : that.mezzo != null) return false;
        if (classe != null ? !classe.equals(that.classe) : that.classe != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.getId();
        result = 31 * result + (destinazione != null ? destinazione.hashCode() : 0);
        result = 31 * result + (dataArrivo != null ? dataArrivo.hashCode() : 0);
        result = 31 * result + (mezzo != null ? mezzo.hashCode() : 0);
        result = 31 * result + (classe != null ? classe.hashCode() : 0);
        return result;
    }

}
