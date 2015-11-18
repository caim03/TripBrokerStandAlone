package model;

import javax.persistence.*;

/**
 * Created by Christian on 18/11/2015.
 */
@Entity
@Table(name = "Prodotto", schema = "trip_broker", catalog = "")
public class ProdottoEntity {
    private int id;
    private String nome;
    private double prezzo;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Basic
    @Column(name = "prezzo")
    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProdottoEntity that = (ProdottoEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.prezzo, prezzo) != 0) return false;
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;

        return true;
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
