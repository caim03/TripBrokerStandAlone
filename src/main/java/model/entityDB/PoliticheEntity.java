package model.entityDB;

import javax.persistence.*;

/**
 * Created by Christian on 18/11/2015.
 */
@Entity
@Table(name = "Politiche", schema = "trip_broker")
public class PoliticheEntity extends AbstractEntity {
    private int id;
    private double percentualeMax;
    private double percentualeMin;
    private String nome;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "percentuale_max")
    public double getPercentualeMax() {
        return percentualeMax;
    }

    public void setPercentualeMax(double percentualeMax) {
        this.percentualeMax = percentualeMax;
    }

    @Basic
    @Column(name = "percentuale_min")
    public double getPercentualeMin() {
        return percentualeMin;
    }

    public void setPercentualeMin(double percentualeMin) {
        this.percentualeMin = percentualeMin;
    }

    @Basic
    @Column(name = "nome")
    public String getNome() { return nome; }

    public void setNome(String name) {this.nome = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoliticheEntity that = (PoliticheEntity) o;

        if (id != that.id) return false;
        if (percentualeMax != that.percentualeMax) return false;
        if (percentualeMin != that.percentualeMin) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int)percentualeMax;
        result = 31 * result + (int)percentualeMin;
        return result;
    }
}
