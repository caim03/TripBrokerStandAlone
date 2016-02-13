package model.entityDB;

import controller.Constants;

import javax.persistence.*;

@Entity
@Table(name = "Politiche", schema = "trip_broker")
public class PoliticaEntity extends AbstractEntity {
    private int id;
    private double valore;
    private String nome;
    private String descrizione;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "valore")
    public double getValore() {
        return valore;
    }
    public void setValore(double valore) {
        this.valore = valore;
    }

    @Basic
    @Column(name = "descrizione")
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Basic
    @Column(name = "nome")
    public String getNome() { return nome; }

    public void setNome(String name) {this.nome = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoliticaEntity that = (PoliticaEntity) o;

        return id == that.id &&
               valore == that.valore &&
               nome.equals(that.getNome()) &&
               descrizione.equals(that.descrizione);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) valore;
        result = 31 * result + (nome == null ? 0 : nome.hashCode());
        result = 31 * result + (descrizione == null ? 0 : descrizione.hashCode());
        return result;
    }

    @Override
    public String toString() {

        switch (id) {
            case Constants.minGroup:
                return String.valueOf((int) valore);
            case Constants.discount:
                return String.valueOf((int) ((1 - valore) * 100));
            default:
                return String.valueOf((int) ((valore - 1) * 100));
        }
    }
}
