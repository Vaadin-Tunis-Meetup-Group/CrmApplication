/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Haithem
 */
@Entity
@Table(name = "client")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
           @NamedQuery(name = "Client.findByLogAndPwd", query = "SELECT c FROM Client c where c.login= :login and c.password = :password"),
    @NamedQuery(name = "Client.findAllInActive", query = "SELECT c FROM Client c where c.etatCompte = FALSE"),
    @NamedQuery(name = "Client.findAllActive", query = "SELECT c FROM Client c where c.etatCompte = TRUE"),
    @NamedQuery(name = "Client.findByIdclient", query = "SELECT c FROM Client c WHERE c.idclient = :idclient"),
    @NamedQuery(name = "Client.findByNom", query = "SELECT c FROM Client c WHERE c.nom = :nom"),
    @NamedQuery(name = "Client.findByPrenom", query = "SELECT c FROM Client c WHERE c.prenom = :prenom"),
    @NamedQuery(name = "Client.findByCin", query = "SELECT c FROM Client c WHERE c.cin = :cin"),
    @NamedQuery(name = "Client.findByEmail", query = "SELECT c FROM Client c WHERE c.email = :email"),
    @NamedQuery(name = "Client.findByPassword", query = "SELECT c FROM Client c WHERE c.password = :password"),
    @NamedQuery(name = "Client.findByLogin", query = "SELECT c FROM Client c WHERE c.login = :login"),
    @NamedQuery(name = "Client.findByVille", query = "SELECT c FROM Client c WHERE c.ville = :ville"),
    @NamedQuery(name = "Client.findByGouvernourat", query = "SELECT c FROM Client c WHERE c.gouvernourat = :gouvernourat"),
    @NamedQuery(name = "Client.findByTelephone", query = "SELECT c FROM Client c WHERE c.telephone = :telephone"),
    @NamedQuery(name = "Client.findByMobile", query = "SELECT c FROM Client c WHERE c.mobile = :mobile"),
    @NamedQuery(name = "Client.findByCompany", query = "SELECT c FROM Client c WHERE c.company = :company"),
    @NamedQuery(name = "Client.findByDateNaissance", query = "SELECT c FROM Client c WHERE c.dateNaissance = :dateNaissance"),
    @NamedQuery(name = "Client.findByDateAjout", query = "SELECT c FROM Client c WHERE c.dateAjout = :dateAjout")})
public class Client implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "cin")
    private int cin;
    @OneToMany(mappedBy = "client")
    private Collection<RendezVous> rendezVousCollection;
    @Column(name = "connected")
    private Boolean connected;
    @Column(name = "etat_compte")
    private Boolean etatCompte;
    @OneToMany(mappedBy = "toClient")
    private Collection<Message> messageRecu;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "fromCl")
    private Collection<Message> messageEnvoye;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Collection<Reclamation> reclamationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Collection<Commande> commandeCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idclient")
    private Integer idclient;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "prenom")
    private String prenom;
    @Lob
    @Size(max = 65535)
    @Column(name = "adresse")
    private String adresse;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "password")
    private String password;
    @Size(max = 45)
    @Column(name = "login")
    private String login;
    @Size(max = 45)
    @Column(name = "ville")
    private String ville;
    @Size(max = 45)
    @Column(name = "gouvernourat")
    private String gouvernourat;
    @Size(max = 45)
    @Column(name = "telephone")
    private String telephone;
    @Size(max = 45)
    @Column(name = "mobile")
    private String mobile;
    @Size(max = 45)
    @Column(name = "company")
    private String company;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_naissance")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_ajout")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAjout;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;

    public Client() {
    }

    public Client(Integer idclient) {
        this.idclient = idclient;
    }

    public Client(Integer idclient, String nom, String prenom, Integer cin, String email, String password, Date dateNaissance, Date dateAjout) {
        this.idclient = idclient;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.email = email;
        this.password = password;
        this.dateNaissance = dateNaissance;
        this.dateAjout = dateAjout;
    }

    public Integer getIdclient() {
        return idclient;
    }

    public void setIdclient(Integer idclient) {
        this.idclient = idclient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getGouvernourat() {
        return gouvernourat;
    }

    public void setGouvernourat(String gouvernourat) {
        this.gouvernourat = gouvernourat;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idclient != null ? idclient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.idclient == null && other.idclient != null) || (this.idclient != null && !this.idclient.equals(other.idclient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nom+" "+prenom;
    }
    
    @PrePersist
    public void beforeAdd(){
        dateAjout=new Date();
    }

    public boolean getEtatCompte() {
        return etatCompte;
    }

    public void setEtatCompte(boolean etatCompte) {
        this.etatCompte = etatCompte;
    }

    @XmlTransient
    public Collection<Commande> getCommandeCollection() {
        return commandeCollection;
    }

    public void setCommandeCollection(Collection<Commande> commandeCollection) {
        this.commandeCollection = commandeCollection;
    }

    

   
    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

  

    public void setEtatCompte(Boolean etatCompte) {
        this.etatCompte = etatCompte;
    }

    public Collection<Message> getMessageRecu() {
        return messageRecu;
    }

    public void setMessageRecu(Collection<Message> messageRecu) {
        this.messageRecu = messageRecu;
    }

    public Collection<Message> getMessageEnvoye() {
        return messageEnvoye;
    }

    public void setMessageEnvoye(Collection<Message> messageEnvoye) {
        this.messageEnvoye = messageEnvoye;
    }

   

    @XmlTransient
    public Collection<Reclamation> getReclamationCollection() {
        return reclamationCollection;
    }

    public void setReclamationCollection(Collection<Reclamation> reclamationCollection) {
        this.reclamationCollection = reclamationCollection;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    @XmlTransient
    public Collection<RendezVous> getRendezVousCollection() {
        return rendezVousCollection;
    }

    public void setRendezVousCollection(Collection<RendezVous> rendezVousCollection) {
        this.rendezVousCollection = rendezVousCollection;
    }
}
