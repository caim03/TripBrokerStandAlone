package model.entityDB;

import javax.persistence.*;

/**
 * Created by Christian on 18/11/2015.
 */
@Entity
@Table(name = "Politiche", schema = "trip_broker", catalog = "")
public class PoliticheEntity extends AbstractEntity {
    private int id;
    private int percentualeMax;
    private int percentualeMin;

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
    public int getPercentualeMax() {
        return percentualeMax;
    }

    public void setPercentualeMax(int percentualeMax) {
        this.percentualeMax = percentualeMax;
    }

    @Basic
    @Column(name = "percentuale_min")
    public int getPercentualeMin() {
        return percentualeMin;
    }

    public void setPercentualeMin(int percentualeMin) {
        this.percentualeMin = percentualeMin;
    }

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
        result = 31 * result + percentualeMax;
        result = 31 * result + percentualeMin;
        return result;
    }
}
