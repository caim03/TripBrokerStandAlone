package model;

import javax.persistence.*;

/**
 * Created by Christian on 18/11/2015.
 */
@Entity
@Table(name = "Utente", schema = "trip_broker", catalog = "")
public class UtenteEntity {
    private int id;
    private String nome;
    private String cognome;
    private String mail;
    private String passwordLogin;
    private String codiceFiscale;
    private String numeroCarta;

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
    @Column(name = "mail")
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
    @Column(name = "codice_fiscale")
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    @Basic
    @Column(name = "numero_carta")
    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UtenteEntity that = (UtenteEntity) o;

        if (id != that.id) return false;
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        if (cognome != null ? !cognome.equals(that.cognome) : that.cognome != null) return false;
        if (mail != null ? !mail.equals(that.mail) : that.mail != null) return false;
        if (passwordLogin != null ? !passwordLogin.equals(that.passwordLogin) : that.passwordLogin != null)
            return false;
        if (codiceFiscale != null ? !codiceFiscale.equals(that.codiceFiscale) : that.codiceFiscale != null)
            return false;
        if (numeroCarta != null ? !numeroCarta.equals(that.numeroCarta) : that.numeroCarta != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (cognome != null ? cognome.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (passwordLogin != null ? passwordLogin.hashCode() : 0);
        result = 31 * result + (codiceFiscale != null ? codiceFiscale.hashCode() : 0);
        result = 31 * result + (numeroCarta != null ? numeroCarta.hashCode() : 0);
        return result;
    }
}
