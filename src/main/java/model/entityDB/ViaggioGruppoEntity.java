package model.entityDB;

import javax.persistence.*;

/*** This class represents the table of group trips in DataBase, and it is used to retrieve,
 *   save, delete or update group trips.
 *   It contains four private attributes that represent the columns of table in DataBase:
 *      1. 'min'
 *      2. 'max'
 *      3. 'prenotazioni'
 *      4. 'creatore'
 *
 *   The attribute 'min' indicates the minimum bookings that the group trip should have
 *
 *   The attribute 'max' indicates the maximum bookings that the group trip should have;
 *   usually coincides with the number of the lowest bid with the lowest number of availability
 *
 *   The attribute 'prenotazioni' indicates the number of booking for the group trip
 *
 *   The attribute 'creatore' indicates the identifier of the dependent (agent)
 *   that has created the group trip ***/

@Entity
@Table(name = "Viaggio_Gruppo", schema = "trip_broker")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public class ViaggioGruppoEntity extends PacchettoEntity {

    private int min, max, prenotazioni, acquisti;

    @Basic
    @Column(name = "min")
    public int getMin() {
        /** @result int; return the minimum number of booking that the group trip should have **/
        return min;
    }

    public void setMin(int min) {
        /** @param int; set the minimum number of booking that the group trip should have **/
        this.min = min;
    }

    @Basic
    @Column(name = "max")
    public int getMax() {
        /** @result int; return the maximum number of booking that the group trip should have **/
        return this.max;
    }

    public void setMax(int max) {
        /** @param int; set the maximum number of booking that the group trip should have **/
        this.max = max;
    }


    @Basic
    @Column(name = "prenotazioni")
    public int getPrenotazioni() {
        /** @result int; return the current number of booking of the group trip **/
        return this.prenotazioni;
    }

    public void setPrenotazioni(int prenotazioni) {
        /** @param int; set the current number of booking of the group trip **/
        this.prenotazioni = prenotazioni;
    }

    public void addPrenotazione(int add) {
        /** @param int; refresh the current number of booking of the group trip **/
        if (prenotazioni + add > max || prenotazioni + add < 0) return;
        this.prenotazioni += add;
    }

    @Basic
    @Column(name = "acquisti")
    public int getAcquisti() {
        return acquisti;
    }

    public void setAcquisti(int acquisti) {
        this.acquisti = acquisti;
    }

    public void addAcquisti(int add) {
        /** @param int; refresh the current number of booking of the group trip **/
        if (add < 0 || acquisti + add > max) return;
        this.acquisti += add;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViaggioGruppoEntity that = (ViaggioGruppoEntity) o;

        //if (id != that.id) return false;
        return super.equals(o) && min == that.min && max == that.max &&
               prenotazioni == that.prenotazioni && acquisti == that.acquisti;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + min;
        result = 31 * result + max;
        return result;
    }
}
