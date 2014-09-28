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
@Table(name = "produit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produit.findAll", query = "SELECT p FROM Produit p"),
     @NamedQuery(name = "Produit.findAllByCateg", query = "SELECT p FROM Produit p WHERE p.categorie IN :categories"),
        @NamedQuery(name = "Produit.findAllByMarque", query = "SELECT p FROM Produit p WHERE p.marque IN :marques"),
       @NamedQuery(name = "Produit.findAllBetweenPU", query = "SELECT p FROM Produit p WHERE p.pu > :val1 AND p.pu < :val2"),
       @NamedQuery(name = "Produit.findAllInfPu", query = "SELECT p FROM Produit p WHERE p.pu < :prix"),
      @NamedQuery(name = "Produit.findAllSupPu", query = "SELECT p FROM Produit p WHERE p.pu > :prix"),
     @NamedQuery(name = "Produit.findByIdproduit", query = "SELECT p FROM Produit p WHERE p.idproduit = :idproduit"),
    @NamedQuery(name = "Produit.findByLibelle", query = "SELECT p FROM Produit p WHERE p.libelle = :libelle"),
    @NamedQuery(name = "Produit.findByModel", query = "SELECT p FROM Produit p WHERE p.model = :model"),
    @NamedQuery(name = "Produit.findByPu", query = "SELECT p FROM Produit p WHERE p.pu = :pu"),
    @NamedQuery(name = "Produit.findByCodeBarre", query = "SELECT p FROM Produit p WHERE p.codeBarre = :codeBarre"),
    @NamedQuery(name = "Produit.findByQuantite", query = "SELECT p FROM Produit p WHERE p.quantite = :quantite"),
    @NamedQuery(name = "Produit.findByCouleur", query = "SELECT p FROM Produit p WHERE p.couleur = :couleur"),
    @NamedQuery(name = "Produit.findByImage", query = "SELECT p FROM Produit p WHERE p.image = :image"),
    @NamedQuery(name = "Produit.findByDateAjout", query = "SELECT p FROM Produit p WHERE p.dateAjout = :dateAjout")})
public class Produit implements Serializable {
   
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproduit")
    private Integer idproduit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "libelle")
    private String libelle;
    @Size(max = 45)
    @Column(name = "model")
    private String model;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pu")
    private double pu;
    @Size(max = 100)
    @Column(name = "code_barre")
    private String codeBarre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantite")
    private int quantite;
    @Size(max = 45)
    @Column(name = "couleur")
    private String couleur;
    @Size(max = 45)
    @Column(name = "image")
    private String image;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_ajout")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAjout;
    @JoinColumn(name = "marque", referencedColumnName = "idmarque")
    @ManyToOne
    private Marque marque;
    @JoinColumn(name = "categorie", referencedColumnName = "idcategorie")
    @ManyToOne
    private Categorie categorie;
    
     @OneToMany(cascade = CascadeType.ALL, mappedBy = "produit")
    private Collection<LigneCommande> ligneCommandeCollection;

    public Produit() {
    }

    public Produit(Integer idproduit) {
        this.idproduit = idproduit;
    }

    public Produit(Integer idproduit, String libelle, double pu, int quantite, Date dateAjout) {
        this.idproduit = idproduit;
        this.libelle = libelle;
        this.pu = pu;
        this.quantite = quantite;
        this.dateAjout = dateAjout;
    }

    public Integer getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(Integer idproduit) {
        this.idproduit = idproduit;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPu() {
        return pu;
    }

    public void setPu(double pu) {
        this.pu = pu;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproduit != null ? idproduit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produit)) {
            return false;
        }
        Produit other = (Produit) object;
        if ((this.idproduit == null && other.idproduit != null) || (this.idproduit != null && !this.idproduit.equals(other.idproduit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return libelle;
    }
    
    @PrePersist
    public void beforeAdd(){
        dateAjout=new Date();
    }

    @XmlTransient
    public Collection<LigneCommande> getLigneCommandeCollection() {
        return ligneCommandeCollection;
    }

    public void setLigneCommandeCollection(Collection<LigneCommande> ligneCommandeCollection) {
        this.ligneCommandeCollection = ligneCommandeCollection;
    }
    
}
