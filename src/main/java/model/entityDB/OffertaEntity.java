package model.entityDB;

import javax.persistence.*;
import java.sql.Timestamp;

/*** This class represents the table of offers in DataBase, and it is used to retrieve, save, delete or update
 *   offers.
 *   It contains five private attributes that represent the columns of table in DataBase:
 *      1. 'città'
 *      2. 'prezzoFabbrica'
 *      3. 'quantità'
 *      4. 'prenotazioni'
 *      5. 'dataInizio'
 *
 *   The attribute 'città' indicates the city of the offer;
 *   it can be interpreted in three ways:
 *      - if offer is a travel 'città' indicates the departure city
 *      - if offer is an event 'città' indicates the city where happens
 *      - if offer is a stay 'città' indicates the city where you are staying
 *
 *   The attribute 'prezzoFabbrica' means the price at which the offer was bought
 *
 *   The attribute 'quantità' indicates the quantity of the offer
 *
 *   The attribute 'prenotazioni' indicates the number of the booking associated to the offer
 *
 *   The attribute 'dataInizio' indicates the Timestamp of the initial date;
 *   it can be interpreted in three ways:
 *      - if offer is a travel 'dataInizio' indicates the departure date
 *      - if offer is an event 'dataInizio' indicates the date of start event
 *      - if offer is a stay 'dataInizio' indicates the date of start staying ***/

@Entity
@Table(name = "Offerta", schema = "trip_broker")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public class OffertaEntity extends ProdottoEntity {
    //private int id;
    private String città;
    private double prezzoFabbrica;
    private int quantità;
    private int prenotazioni;
    private Timestamp dataInizio;

    @Basic
    @Column(name = "città")
    public String getCittà() {
        /** @result String; return the city of the offer as string **/
        return città;
    }

    public void setCittà(String città) {
        /** @param String; set the city of the offer **/
        this.città = città;
    }

    @Basic
    @Column(name = "prezzo_fabbrica")
    public double getPrezzoFabbrica() {
        /** @result double; return the the price at which the offer was bought **/
        return prezzoFabbrica;
    }

    public void setPrezzoFabbrica(double prezzoFabbrica) {
        /** @param double; set the price at which the offer was bought **/
        this.prezzoFabbrica = prezzoFabbrica;
    }

    @Basic
    @Column(name = "quantità")
    public int getQuantità() {
        /** @result int; return the current quantity of the offer **/
        return quantità;
    }

    public void setQuantità(int quantità) {
        /** @param int; set the quantity of the offer **/
        this.quantità = quantità;
    }

    @Basic
    @Column(name = "prenotazioni")
    public int getPrenotazioni() {
        /** @result int; return the current number of bookings **/
        return prenotazioni;
    }

    public void setPrenotazioni(int prenotazioni) {
        /** @param int; set the current number of bookings **/
        this.prenotazioni = prenotazioni;
    }

    public void addPrenotazioni(int add) {
        /** @param int; refresh the number of the bookings **/
        if (quantità < prenotazioni + add) return;
        prenotazioni += add;
    }

    @Basic
    @Column(name = "data_inizio")
    public Timestamp getDataInizio() {
        /** @result Timestamp; return the timestamp of the initial date **/
        return dataInizio;
    }

    public void setDataInizio(Timestamp dataInizio) {
        /** @param Timestamp; set the timestammp of the initial date **/
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
        if (prenotazioni != that.prenotazioni) return false;
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
        result = 31 * result + (int) prenotazioni;
        result = 31 * result + (dataInizio != null ? dataInizio.hashCode() : 0);
        return result;
    }
}
