package model.entityDB;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Viaggio", schema = "trip_broker")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class ViaggioEntity extends OffertaEntity {

    private String destinazione;
    private String mezzo;
    private String classe;
    private String stazionePartenza;
    private String stazioneArrivo;
    private Date dataArrivo;

    @Basic
    @Column(name = "destinazione")
    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    @Basic
    @Column(name = "mezzo")
    public String getMezzo() {
        return mezzo;
    }

    public void setMezzo(String mezzo) {
        this.mezzo = mezzo;
    }

    @Basic
    @Column(name = "classe")
    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    @Basic
    @Column(name = "stazione_partenza")
    public String getStazionePartenza() {
        return stazionePartenza;
    }

    public void setStazionePartenza(String stazionePartenza) {
        this.stazionePartenza = stazionePartenza;
    }

    @Basic
    @Column(name = "stazione_arrivo")
    public String getStazioneArrivo() {
        return stazioneArrivo;
    }

    public void setStazioneArrivo(String stazioneArrivo) {
        this.stazioneArrivo = stazioneArrivo;
    }


    @Basic
    @Column (name = "data_arrivo")
    public Date getDataArrivo() {
        return dataArrivo;
    }

    public void setDataArrivo(Date dataArrivo) {
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
        if (stazionePartenza != null ? !stazionePartenza.equals(that.stazionePartenza) : that.stazionePartenza != null)
            return false;
        if (stazioneArrivo != null ? !stazioneArrivo.equals(that.stazioneArrivo) : that.stazioneArrivo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.getId();
        result = 31 * result + (destinazione != null ? destinazione.hashCode() : 0);
        result = 31 * result + (dataArrivo != null ? dataArrivo.hashCode() : 0);
        result = 31 * result + (mezzo != null ? mezzo.hashCode() : 0);
        result = 31 * result + (classe != null ? classe.hashCode() : 0);
        result = 31 * result + (stazionePartenza != null ? stazionePartenza.hashCode() : 0);
        result = 31 * result + (stazioneArrivo != null ? stazioneArrivo.hashCode() : 0);
        return result;
    }

}
