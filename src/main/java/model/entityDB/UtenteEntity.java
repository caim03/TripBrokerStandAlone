package model.entityDB;

import javax.persistence.*;

/*** This class represents the table of users in DataBase, and it is used to retrieve,
 *   save, delete or update users.
 *   It contains seven private attributes that represent the columns of table in DataBase:
 *      1. 'id'
 *      2. 'nome'
 *      3. 'cognome'
 *      4. 'mail'
 *      5. 'passwordLogin'
 *      6. 'codiceFiscale'
 *      7. 'numeroCarta'
 *
 *   The attribute 'id' indicates the identifier of the user;
 *   each user has him distinct identifier
 *
 *   The attribute 'nome' indicates the name of the user
 *
 *   The attribute 'cognome' indicates the surname of the user
 *
 *   The attribute 'mail' indicates the mail address of the user
 *
 *   The attribute 'passwordLogin' indicates the password with which the user accesses the site
 *
 *   The attribute 'codiceFiscale' indicates the fiscal code of the user
 *
 *   The attribute 'numeroCarta' indicates the number of credit card with which the user buys products ***/

@Entity
@Table(name = "Utente", schema = "trip_broker")
public class UtenteEntity extends AbstractEntity {
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
        /** @result int; return the identifier of the user **/
        return id;
    }

    public void setId(int id) {
        /** @param int; set the identifier of the user **/
        this.id = id;
    }

    @Basic
    @Column(name = "nome")
    public String getNome() {
        /** @result String; return the name of the user **/
        return nome;
    }

    public void setNome(String nome) {
        /** @param String; set the name of the user **/
        this.nome = nome;
    }

    @Basic
    @Column(name = "cognome")
    public String getCognome() {
        /** @result String; return the surname of the user **/
        return cognome;
    }

    public void setCognome(String cognome) {
        /** @param String; set the surname of the user **/
        this.cognome = cognome;
    }

    @Basic
    @Column(name = "mail")
    public String getMail() {
        /** @result String; return the mail of the user **/
        return mail;
    }

    public void setMail(String mail) {
        /** @param String; set the mail of the user **/
        this.mail = mail;
    }

    @Basic
    @Column(name = "password_login")
    public String getPasswordLogin() {
        /** @result String; return the password of the user **/
        return passwordLogin;
    }

    public void setPasswordLogin(String passwordLogin) {
        /** @param String; set the password of the user **/
        this.passwordLogin = passwordLogin;
    }

    @Basic
    @Column(name = "codice_fiscale")
    public String getCodiceFiscale() {
        /** @result String; return the fiscal code of the user **/
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        /** @param String; set the fiscal code of the user **/
        this.codiceFiscale = codiceFiscale;
    }

    @Basic
    @Column(name = "numero_carta")
    public String getNumeroCarta() {
        /** @result String; return the number of credit card of user as a string **/
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        /** @param String; set the number of credit card of user as a string **/
        this.numeroCarta = numeroCarta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UtenteEntity that = (UtenteEntity) o;

        return id == that.id &&
              (nome != null ? nome.equals(that.nome) : that.nome == null) &&
              (cognome != null ? cognome.equals(that.cognome) : that.cognome == null) &&
              (mail != null ? mail.equals(that.mail) : that.mail == null) &&
              (passwordLogin != null ? passwordLogin.equals(that.passwordLogin) : that.passwordLogin == null) &&
              (codiceFiscale != null ? codiceFiscale.equals(that.codiceFiscale) : that.codiceFiscale == null) &&
              (numeroCarta != null ? numeroCarta.equals(that.numeroCarta) : that.numeroCarta == null);

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
