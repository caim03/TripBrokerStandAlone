package model.entityDB;

import javax.persistence.*;

/*** This class represents the table of relations between
 *   'ProdottoEntity' and 'UtenteEntity' in DataBase.
 *   It contains two private attributes which reference the primary key of tables ***/

@Entity
@Table(name = "Prodotto_Utente", schema = "trip_broker")
@IdClass(ProdottoUtenteEntityPK.class)
public class ProdottoUtenteEntity extends AbstractEntity {
    private int idProdotto;
    private int idUtente;

    @Id
    @Column(name = "id_prodotto")
    public int getIdProdotto() {
        /** @result int; return the identifier of the product **/
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        /** @param int; set the identifier of the product **/
        this.idProdotto = idProdotto;
    }

    @Id
    @Column(name = "id_utente")
    public int getIdUtente() {
        /** @result int; return the identifier of the user **/
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        /** @param int; set the identifier of the user **/
        this.idUtente = idUtente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProdottoUtenteEntity that = (ProdottoUtenteEntity) o;

        if (idProdotto != that.idProdotto) return false;
        if (idUtente != that.idUtente) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idProdotto;
        result = 31 * result + idUtente;
        return result;
    }
}
