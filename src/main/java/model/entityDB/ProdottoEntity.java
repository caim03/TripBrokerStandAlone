package model.entityDB;

import javax.persistence.*;

/*** This class represents the table of products in DataBase, and it is used to retrieve,
 *   save, delete or update products.
 *   It contains four private attributes that represent the columns of table in DataBase:
 *      1. 'id'
 *      2. 'nome'
 *      3. 'prezzo'
 *      4. 'tipo'
 *
 *   The attribute 'id' indicates the identifier of the product
 *
 *   The attribute 'nome' indicates the name of the product
 *
 *   The attribute 'prezzo' indicates the price of the product
 *
 *   The attribute 'tipo' indicates the type of the product:
 *      - Event
 *      - Travel
 *      - Stay
 *      - Packet
 *   ***/

@Entity
@Table(name = "Prodotto", schema = "trip_broker")
@Inheritance(strategy = InheritanceType.JOINED)
public class ProdottoEntity extends AbstractEntity {

    private int id;
    private String nome;
    private double prezzo;
    private String tipo;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getId() {
        /** @result int; return the identifier of the product **/
        return id;
    }

    public void setId(int id) {
        /** @param int; set the identifier of the product **/
        this.id = id;
    }

    @Basic
    @Column(name = "nome")
    public String getNome() {
        /** @result String; return the name of the product **/
        return nome;
    }

    public void setNome(String nome) {
        /** @param String; set the name of the product **/
        this.nome = nome;
    }

    @Basic
    @Column(name = "prezzo")
    public double getPrezzo() {
        /** @result double; return the price of the product **/
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        /** @param double; set the price of the product **/
        this.prezzo = prezzo;
    }

    @Basic
    @Column(name = "tipo")
    public String getTipo() {
        /** @result String; return the type of the product **/
        return tipo;
    }

    public void setTipo(String tipo) {
        /** @param String; set the type of the product **/
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProdottoEntity that = (ProdottoEntity) o;

        return id == that.id &&
               Double.compare(that.prezzo, prezzo) == 0 &&
              (nome != null ? nome.equals(that.nome) : that.nome == null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        temp = Double.doubleToLongBits(prezzo);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
