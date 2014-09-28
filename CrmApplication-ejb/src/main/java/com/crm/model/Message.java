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
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m ORDER BY m.dateEnv DESC"),
    @NamedQuery(name = "Message.findAllByProfil", query = "SELECT m FROM Message m WHERE m.toUser = :user ORDER BY m.dateEnv DESC"),
     @NamedQuery(name = "Message.findAllNew", query = "SELECT m FROM Message m WHERE m.newMsg = TRUE and m.toUser=:user ORDER BY m.dateEnv DESC"),
    @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id"),
    @NamedQuery(name = "Message.findBySujet", query = "SELECT m FROM Message m WHERE m.sujet = :sujet"),
    @NamedQuery(name = "Message.findByContenu", query = "SELECT m FROM Message m WHERE m.contenu = :contenu"),
    @NamedQuery(name = "Message.findByDateEnv", query = "SELECT m FROM Message m WHERE m.dateEnv = :dateEnv"),
    @NamedQuery(name = "Message.findByType", query = "SELECT m FROM Message m WHERE m.type = :type"),
    @NamedQuery(name = "Message.findByNew1", query = "SELECT m FROM Message m WHERE m.newMsg = :new1")})
public class Message implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "contenu")
    private String contenu;
    @Column(name = "dateEnv")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnv;
    @Size(max = 45)
    @Column(name = "type")
    private String type;
    @Column(name = "new_msg")
    private Boolean newMsg;
    @JoinColumn(name = "toClient", referencedColumnName = "idclient")
    @ManyToOne
    private Client toClient;
    @JoinColumn(name = "fromCl", referencedColumnName = "idclient")
    @ManyToOne
    private Client fromCl;
    @JoinColumn(name = "fromUser", referencedColumnName = "id")
    @ManyToOne
    private Profil fromUser;
    @JoinColumn(name = "toUser", referencedColumnName = "id")
    @ManyToOne
    private Profil toUser;

    public Message() {
    }

    public Message(Integer id) {
        this.id = id;
    }

    public Message(Integer id, String sujet, String contenu) {
        this.id = id;
        this.sujet = sujet;
        this.contenu = contenu;
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

    public Date getDateEnv() {
        return dateEnv;
    }

    public void setDateEnv(Date dateEnv) {
        this.dateEnv = dateEnv;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isNewMsg() {
        return newMsg;
    }

    public void setNewMsg(Boolean newMsg) {
        this.newMsg = newMsg;
    }

 

    public Client getToClient() {
        return toClient;
    }

    public void setToClient(Client toClient) {
        this.toClient = toClient;
    }

    public Client getFromCl() {
        return fromCl;
    }

    public void setFromCl(Client fromCl) {
        this.fromCl = fromCl;
    }

    public Profil getFromUser() {
        return fromUser;
    }

    public void setFromUser(Profil fromUser) {
        this.fromUser = fromUser;
    }

    public Profil getToUser() {
        return toUser;
    }

    public void setToUser(Profil toUser) {
        this.toUser = toUser;
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
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm.model.Message[ id=" + id + " ]";
    }
    
}
