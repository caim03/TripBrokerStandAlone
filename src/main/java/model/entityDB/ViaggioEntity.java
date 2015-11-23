package model.entityDB;

import javax.persistence.*;

/**
 * Created by Christian on 18/11/2015.
 */
@Entity
@Table(name = "Viaggio", schema = "trip_broker")
public class ViaggioEntity extends OffertaEntity {
    //private int id;
    private String destinazione;
    private int oraPartenza;
    private int oraArrivo;
    private String mezzo;
    private String classe;
    private String stazionePartenza;
    private String stazioneArrivo;

    /*@Id
    @Column(name = "id")
    public int getId() {
        return id;
    }*/

    /*public void setId() {
        this.id = super.getId();
    }*/

    @Basic
    @Column(name = "destinazione")
    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    @Basic
    @Column(name = "ora_partenza")
    public int getOraPartenza() {
        return oraPartenza;
    }

    public void setOraPartenza(int oraPartenza) {
        this.oraPartenza = oraPartenza;
    }

    @Basic
    @Column(name = "ora_arrivo")
    public int getOraArrivo() {
        return oraArrivo;
    }

    public void setOraArrivo(int oraArrivo) {
        this.oraArrivo = oraArrivo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViaggioEntity that = (ViaggioEntity) o;

        //if (super.getId() != that.id) return false;
        if (oraPartenza != that.oraPartenza) return false;
        if (oraArrivo != that.oraArrivo) return false;
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
        result = 31 * result + oraPartenza;
        result = 31 * result + oraArrivo;
        result = 31 * result + (mezzo != null ? mezzo.hashCode() : 0);
        result = 31 * result + (classe != null ? classe.hashCode() : 0);
        result = 31 * result + (stazionePartenza != null ? stazionePartenza.hashCode() : 0);
        result = 31 * result + (stazioneArrivo != null ? stazioneArrivo.hashCode() : 0);
        return result;
    }
}
