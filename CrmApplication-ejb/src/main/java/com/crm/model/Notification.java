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
@Table(name = "notification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n ORDER BY n.dateNotif DESC"),
     @NamedQuery(name = "Notification.findAllNew", query = "SELECT n FROM Notification n WHERE n.newNotif = TRUE ORDER BY n.dateNotif DESC"),
    @NamedQuery(name = "Notification.findById", query = "SELECT n FROM Notification n WHERE n.id = :id"),
    @NamedQuery(name = "Notification.findBySujet", query = "SELECT n FROM Notification n WHERE n.sujet = :sujet"),
    @NamedQuery(name = "Notification.findByContenu", query = "SELECT n FROM Notification n WHERE n.contenu = :contenu"),
    @NamedQuery(name = "Notification.findByDateNotif", query = "SELECT n FROM Notification n WHERE n.dateNotif = :dateNotif"),
    @NamedQuery(name = "Notification.findByNew1", query = "SELECT n FROM Notification n WHERE n.newNotif = :new1")})
public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "sujet")
    private String sujet;
    @Size
    @Column(name = "contenu")
    private String contenu;
    @Column(name = "dateNotif")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateNotif;
    @Column(name = "new_notif")
    private Boolean newNotif;
    @JoinColumn(name = "profil", referencedColumnName = "id")
    @ManyToOne
    private Profil profil;

    public Notification() {
    }

    public Notification(Integer id) {
        this.id = id;
    }

    public Notification(Integer id, String sujet) {
        this.id = id;
        this.sujet = sujet;
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

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateNotif() {
        return dateNotif;
    }

    public void setDateNotif(Date dateNotif) {
        this.dateNotif = dateNotif;
    }

    public Boolean isNewNotif() {
        return newNotif;
    }

    public void setNewNotif(Boolean newNotif) {
        this.newNotif = newNotif;
    }

  

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
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
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm.model.Notification[ id=" + id + " ]";
    }
    
}
