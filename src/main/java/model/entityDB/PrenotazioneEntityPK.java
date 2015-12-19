package model.entityDB;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class PrenotazioneEntityPK extends AbstractEntity implements Serializable {

    private int vid;
    private String nome;
    private String cognome;

    @Column(name = "vid")
    @Id
    public int getViaggioId() {
        return vid;
    }

    public void setViaggioId(int vid) {
        this.vid = vid;
    }

    @Column(name = "nome")
    @Id
    public String getNome() {
        return nome;
    }

    public void setNome(String nome_p) {
        this.nome = nome_p;
    }

    @Column(name = "cognome")
    @Id
    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome_p) {
        this.cognome = cognome_p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrenotazioneEntityPK that = (PrenotazioneEntityPK) o;

        return super.equals(o) && vid == that.vid && nome.equals(that.nome) && cognome.equals(that.cognome);
    }

    @Override
    public int hashCode() {
        int result = vid;
        result = 31 * (result + (nome != null ? nome.hashCode() : 0));
        result = 31 * (result + (cognome != null ? cognome.hashCode() : 0));
        return result;
    }
}
