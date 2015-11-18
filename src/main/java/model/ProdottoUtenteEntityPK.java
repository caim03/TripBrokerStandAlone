package model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Christian on 18/11/2015.
 */
public class ProdottoUtenteEntityPK implements Serializable {
    private int idProdotto;
    private int idUtente;

    @Column(name = "id_prodotto")
    @Id
    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    @Column(name = "id_utente")
    @Id
    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProdottoUtenteEntityPK that = (ProdottoUtenteEntityPK) o;

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
