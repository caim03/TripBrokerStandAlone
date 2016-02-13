package model.entityDB;

import controller.Constants;
import javafx.stage.Stage;
import model.role.*;

import javax.persistence.*;

/*** This class represents the table of dependents in DataBase, and it is used to retrieve,
 *   save, delete or update dependents.
 *   It contains five private attributes that represent the columns of table in DataBase:
 *      1. 'id'
 *      2. 'nome'
 *      3. 'cognome'
 *      4. 'passwordLogin'
 *      5. 'mail'
 *
 *   The attribute 'id' indicates the identifier of a dependent;
 *   each dependent has him distinct identifier
 *
 *   The attribute 'nome' indicates the name of a dependent
 *
 *   The attribute 'cognome' indicates the surname of a dependent
 *
 *   The attribute 'passwordLogin' indicates the password of a dependent;
 *   each dependent has him password and he use it to access on TripBroker StandAlone application
 *
 *   The attribute 'mail' indicates the mail address of a dependent
 *
 *   There is another private attribute Ruolo, but this is not included in DataBase,
 *   because it is used for generating the right view in application after login ***/

@Entity
@Table(name = "Dipendenti", schema = "trip_broker")
public class DipendenteEntity extends AbstractEntity {
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

        if (Constants.admin.equals(ruolo)) job = new Amministratore();
        else if (Constants.desig.equals(ruolo)) job = new Designer();
        else if (Constants.scout.equals(ruolo)) job = new Scout();
        else if (Constants.agent.equals(ruolo)) job = new Agente();
        else job = null;
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

        DipendenteEntity that = (DipendenteEntity) o;

        System.out.println(
                this.id + "\t" + that.id + "\n" +
                this.nome + "\t" + that.nome + "\n" +
                this.cognome + "\t" + that.cognome + "\n" +
                this.job + "\t" + that.job + "\n" +
                this.passwordLogin + "\t" + that.passwordLogin + "\n" +
                this.mail + "\t" + that.mail
        );

        if (id != that.id) return false;
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        if (cognome != null ? !cognome.equals(that.cognome) : that.cognome != null) return false;
        if (getRuolo() != null ? !getRuolo().equals(that.getRuolo()) : that.getRuolo() != null) return false;
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

    public Stage generateView() {

        return this.job.generateView();
    }
}
