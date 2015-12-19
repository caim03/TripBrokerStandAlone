package model.entityDB;

import javax.persistence.*;

@Entity
@Table(name = "Prenotazione", schema = "trip_broker")
@IdClass(PrenotazioneEntityPK.class)
public class PrenotazioneEntity extends AbstractEntity {

    private int vid;
    private String nome;
    private String cognome;
    private int quantità;

    @Id
    @Column(name = "vid")
    public int getViaggioId() { return this.vid; }
    public void setViaggioId(int vid) { this.vid = vid; }

    @Id
    @Column(name = "nome")
    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Id
    @Column(name = "cognome")
    public String getCognome() { return this.cognome; }
    public void setCogome(String cognome) { this.cognome = cognome; }

    @Column(name = "qu")
    public int getQuantità() { return this.quantità; }
    public void setQuantità(int quantità) { this.quantità = quantità; }

}
