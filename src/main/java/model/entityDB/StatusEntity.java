package model.entityDB;

import javax.persistence.*;

@Entity
@Table(name = "Status", schema = "trip_broker")
public class StatusEntity extends AbstractEntity {
    private int id;
    private double valore;

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
    @Column(name = "valore")
    public double getValore() {
        /** @result String; return the name of the product **/
        return valore;
    }

    public void setValore(double valore) {
        /** @param String; set the name of the product **/
        this.valore = valore;
    }

    public void update(double valore) { this.valore += valore; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusEntity that = (StatusEntity) o;
        return id == that.id && Double.compare(that.valore, valore) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(valore);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
