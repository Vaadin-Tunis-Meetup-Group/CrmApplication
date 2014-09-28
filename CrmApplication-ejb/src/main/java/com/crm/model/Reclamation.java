/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Haithem
 */
@Entity
@Table(name = "reclamation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reclamation.findAll", query = "SELECT r FROM Reclamation r"),
    @NamedQuery(name = "Reclamation.findAllHisto", query = "SELECT r FROM Reclamation r where r.etat = TRUE"),
        @NamedQuery(name = "Reclamation.findAllRecl", query = "SELECT r FROM Reclamation r where r.etat = FALSE"),
    @NamedQuery(name = "Reclamation.findById", query = "SELECT r FROM Reclamation r WHERE r.id = :id"),
    @NamedQuery(name = "Reclamation.findByDateRecl", query = "SELECT r FROM Reclamation r WHERE r.dateRecl = :dateRecl"),
    @NamedQuery(name = "Reclamation.findByDateRep", query = "SELECT r FROM Reclamation r WHERE r.dateRep = :dateRep"),
    @NamedQuery(name = "Reclamation.findByEtat", query = "SELECT r FROM Reclamation r WHERE r.etat = :etat"),
    @NamedQuery(name = "Reclamation.findByNew1", query = "SELECT r FROM Reclamation r WHERE r.newRecl = :new1")})
public class Reclamation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "sujet")
    private String sujet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateRecl")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRecl;
    @Column(name = "dateRep")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRep;
    @Lob
    @Size(max = 65535)
    @Column(name = "reponse")
    private String reponse;
    @Column(name = "etat")
    private Boolean etat;
    @Column(name = "new_recl")
    private Boolean newRecl;
    @JoinColumn(name = "client", referencedColumnName = "idclient")
    @ManyToOne(optional = false)
    private Client client;
    @JoinColumn(name = "commande", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Commande commande;

    public Reclamation() {
    }

    public Reclamation(Integer id) {
        this.id = id;
    }

    public Reclamation(Integer id, String sujet, Date dateRecl) {
        this.id = id;
        this.sujet = sujet;
        this.dateRecl = dateRecl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Date getDateRecl() {
        return dateRecl;
    }

    public void setDateRecl(Date dateRecl) {
        this.dateRecl = dateRecl;
    }

    public Date getDateRep() {
        return dateRep;
    }

    public void setDateRep(Date dateRep) {
        this.dateRep = dateRep;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Boolean isNewRecl() {
        return newRecl;
    }

    public void setNewRecl(Boolean newRecl) {
        this.newRecl = newRecl;
    }

  
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reclamation)) {
            return false;
        }
        Reclamation other = (Reclamation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm.model.Reclamation[ id=" + id + " ]";
    }
    
}
