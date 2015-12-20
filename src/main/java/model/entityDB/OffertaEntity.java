package model.entityDB;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Offerta", schema = "trip_broker")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public class OffertaEntity extends ProdottoEntity {
    //private int id;
    private String città;
    private double prezzoFabbrica;
    private int quantità;
    private int stato;
    private Date dataInizio;

    @Basic
    @Column(name = "città")
    public String getCittà() {
        return città;
    }

    public void setCittà(String città) {
        this.città = città;
    }

    @Basic
    @Column(name = "prezzo_fabbrica")
    public double getPrezzoFabbrica() {
        return prezzoFabbrica;
    }

    public void setPrezzoFabbrica(double prezzoFabbrica) {
        this.prezzoFabbrica = prezzoFabbrica;
    }

    @Basic
    @Column(name = "quantità")
    public int getQuantità() {
        return quantità;
    }

    public void setQuantità(int quantità) {
        this.quantità = quantità;
    }

    @Basic
    @Column(name = "prenotazioni")
    public int getStato() {
        return stato;
    }
    public void setStato(int stato) {
        this.stato = stato;
    }
    public void addPrenotazioni(int add) {
        if (quantità < stato + add) return;
        stato += add;
    }

    @Basic
    @Column(name = "data_inizio")
    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OffertaEntity that = (OffertaEntity) o;

        //if (id != that.id) return false;
        if (Double.compare(that.prezzoFabbrica, prezzoFabbrica) != 0) return false;
        if (quantità != that.quantità) return false;
        if (stato != that.stato) return false;
        if (città != null ? !città.equals(that.città) : that.città != null) return false;
        if (dataInizio != null ? !dataInizio.equals(that.dataInizio) : that.dataInizio != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = super.getId();
        result = 31 * result + (città != null ? città.hashCode() : 0);
        temp = Double.doubleToLongBits(prezzoFabbrica);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + quantità;
        result = 31 * result + (int) stato;
        result = 31 * result + (dataInizio != null ? dataInizio.hashCode() : 0);
        return result;
    }
}
