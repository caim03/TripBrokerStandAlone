package model.entityDB;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ProdottoUtenteEntityPK extends AbstractEntity implements Serializable {
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

        return idProdotto == that.idProdotto && idUtente == that.idUtente;

    }

    @Override
    public int hashCode() {
        int result = idProdotto;
        result = 31 * result + idUtente;
        return result;
    }
}
