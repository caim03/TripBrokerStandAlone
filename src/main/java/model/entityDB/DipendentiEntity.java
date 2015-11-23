package model.entityDB;

import javafx.scene.Scene;
import model.Amministratore;
import model.Designer;
import model.Ruolo;
import model.Scout;

import javax.persistence.*;

@Entity
@Table(name = "Dipendenti", schema = "trip_broker")
public class DipendentiEntity extends AbstractEntity {
    private int id;
    private String nome;
    private String cognome;
    private String passwordLogin;
    private String mail;
    private Ruolo job;

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
    @Column(name = "cognome")
    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Basic
    @Column(name = "ruolo")
    public String getRuolo() {
        if (job != null) return job.getRole();
        else return null;
    }

    public void setRuolo(String ruolo) {

        if ("Amministratore".equals(ruolo)) job = new Amministratore();
        else if ("Designer".equals(ruolo)) job = new Designer();
        else if ("Scout".equals(ruolo)) job = new Scout();
        else {
            //TODO DEFAULT
        }
    }

    @Basic
    @Column(name = "password_login")
    public String getPasswordLogin() {
        return passwordLogin;
    }

    public void setPasswordLogin(String passwordLogin) {
        this.passwordLogin = passwordLogin;
    }

    @Basic
    @Column(name = "mail")
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DipendentiEntity that = (DipendentiEntity) o;

        if (id != that.id) return false;
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        if (cognome != null ? !cognome.equals(that.cognome) : that.cognome != null) return false;
        if (job != null ? !job.equals(that.job) : that.job != null) return false;
        if (passwordLogin != null ? !passwordLogin.equals(that.passwordLogin) : that.passwordLogin != null)
            return false;
        if (mail != null ? !mail.equals(that.mail) : that.mail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (cognome != null ? cognome.hashCode() : 0);
        result = 31 * result + (job != null ? job.getRole().hashCode() : 0);
        result = 31 * result + (passwordLogin != null ? passwordLogin.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        return result;
    }

    public Scene generateView() {

        return this.job.generateView();
    }
}
