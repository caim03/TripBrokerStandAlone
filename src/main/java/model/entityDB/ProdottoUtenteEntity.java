package model.entityDB;

import javax.persistence.*;

/**
 * Created by Christian on 18/11/2015.
 */
@Entity
@Table(name = "Prodotto_Utente", schema = "trip_broker", catalog = "")
@IdClass(ProdottoUtenteEntityPK.class)
public class ProdottoUtenteEntity extends AbstractEntity {
    private int idProdotto;
    private int idUtente;

    @Id
    @Column(name = "id_prodotto")
    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    @Id
    @Column(name = "id_utente")
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
