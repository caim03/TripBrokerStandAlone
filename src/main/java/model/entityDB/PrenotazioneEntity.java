package model.entityDB;

import javax.persistence.*;

/*** This class represents the table of bookings in DataBase, and it is used to retrieve,
 *   save, delete or update bookings.
 *   It contains four private attributes that represent the columns of table in DataBase:
 *      1. 'vid'
 *      2. 'nome'
 *      3. 'cognome'
 *      4. 'quantità'
 *
 *   The attribute 'vid' indicates the identifier of the booking
 *
 *   The attribute 'nome' indicates the name of the person associated to the booking
 *
 *   The attribute 'cognome' indicates the surname of the person associated to the booking
 *
 *   The attribute 'quantità' indicates the amount remained available of the booking***/

@Entity
@Table(name = "Prenotazione", schema = "trip_broker")
@IdClass(PrenotazioneEntityPK.class)
public class PrenotazioneEntity extends AbstractEntity {

    private int vid;
    private String nome;
    private String cognome;
    private int quantità, telefono;

    @Id
    @Column(name = "vid")
    public int getViaggioId() {
        /** @result int; return the identifier of travel associated to booking **/
        return this.vid;
    }

    public void setViaggioId(int vid) {
        /** @param int; set the identifier of travel associated to booking **/
        this.vid = vid;
    }

    @Id
    @Column(name = "nome")
    public String getNome() {
        /** @result String; return the name of the person associated to booking **/
        return this.nome;
    }

    public void setNome(String nome) {
        /** @param String; set the name of the person associated to booking **/
        this.nome = nome;
    }

    @Id
    @Column(name = "cognome")
    public String getCognome() {
        /** @result String; return the surname of the person associated to booking **/
        return this.cognome;
    }

    public void setCognome(String cognome) {
        /** @param String; set the surname of the person associated to booking **/
        this.cognome = cognome;
    }

    @Column(name = "qu")
    public int getQuantità() {
        /** @result int; return the remain quantity of the booking **/
        return this.quantità;
    }

    public void setQuantità(int quantità) {
        /** @param int; set the quantity of the booking **/
        this.quantità = quantità;
    }

    @Column(name = "telefono")
    public int getTelefono() {
        /** @result int; return the remain quantity of the booking **/
        return this.telefono;
    }

    public void setTelefono(int telefono) {
        /** @param int; set the quantity of the booking **/
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrenotazioneEntity that = (PrenotazioneEntity) o;

        return super.equals(o) && vid == that.vid && nome.equals(that.getNome()) && cognome.equals(that.getCognome()) &&
                quantità == that.quantità;
    }

    @Override
    public int hashCode() {
        int result = vid;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (cognome != null ? cognome.hashCode() : 0);
        result = 31 * result + (quantità);
        return result;
    }
}
