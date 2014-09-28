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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "commande")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Commande.findAll", query = "SELECT c FROM Commande c"),
    @NamedQuery(name = "Commande.findAllCmd", query = "SELECT c FROM Commande c where c.type = TRUE and c.etat LIKE 'en%'"),
    @NamedQuery(name = "Commande.findAllCmdHisto", query = "SELECT c FROM Commande c where c.etat LIKE 'Con%'"),
    @NamedQuery(name = "Commande.findAllDev", query = "SELECT c FROM Commande c where c.type = FALSE and c.etat LIKE 'en%'"),
     @NamedQuery(name = "Commande.findById", query = "SELECT c FROM Commande c WHERE c.id = :id"),
    @NamedQuery(name = "Commande.findByType", query = "SELECT c FROM Commande c WHERE c.type = :type"),
    @NamedQuery(name = "Commande.findByMontant", query = "SELECT c FROM Commande c WHERE c.montant = :montant"),
    @NamedQuery(name = "Commande.findByDateCmd", query = "SELECT c FROM Commande c WHERE c.dateCmd = :dateCmd"),
    @NamedQuery(name = "Commande.findByDateReponse", query = "SELECT c FROM Commande c WHERE c.dateReponse = :dateReponse"),
    @NamedQuery(name = "Commande.findByEtat", query = "SELECT c FROM Commande c WHERE c.etat = :etat")})
public class Commande implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commande")
    private Collection<Reclamation> reclamationCollection;
    @Basic(optional = false)
    @NotNull
    @Column(name = "type")
    private boolean type;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montant")
    private Double montant;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_cmd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCmd;
    @Column(name = "date_reponse")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReponse;
    @Size(max = 45)
    @Column(name = "etat")
    private String etat;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "client", referencedColumnName = "idclient")
    @ManyToOne(optional = false)
    private Client client;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commande")
    private Collection<LigneCommande> ligneCommandeCollection;

    public Commande() {
    }

    public Commande(Integer id) {
        this.id = id;
    }

    public Commande(Integer id, boolean type, Date dateCmd) {
        this.id = id;
        this.type = type;
        this.dateCmd = dateCmd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Date getDateCmd() {
        return dateCmd;
    }

    public void setDateCmd(Date dateCmd) {
        this.dateCmd = dateCmd;
    }

    public Date getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(Date dateReponse) {
        this.dateReponse = dateReponse;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @XmlTransient
    public Collection<LigneCommande> getLigneCommandeCollection() {
        return ligneCommandeCollection;
    }

    public void setLigneCommandeCollection(Collection<LigneCommande> ligneCommandeCollection) {
        this.ligneCommandeCollection = ligneCommandeCollection;
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
        if (!(object instanceof Commande)) {
            return false;
        }
        Commande other = (Commande) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm.model.Commande[ id=" + id + " ]";
    }
    
    @PrePersist
    public void beforeAdd(){
        dateCmd=new Date();
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @XmlTransient
    public Collection<Reclamation> getReclamationCollection() {
        return reclamationCollection;
    }

    public void setReclamationCollection(Collection<Reclamation> reclamationCollection) {
        this.reclamationCollection = reclamationCollection;
    }
    
}
