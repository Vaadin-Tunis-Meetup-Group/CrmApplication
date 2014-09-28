/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm.managedbean.util;

import java.util.List;

/**
 *
 * @author Haithem
 */
public class DevisData {
    
    private String client;
    private String date;
    private String prixt;
    private List<Devis> devis;
    private StringBuilder commentaire;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrixt() {
        return prixt;
    }

    public void setPrixt(String prixt) {
        this.prixt = prixt;
    }

    public List<Devis> getDevis() {
        return devis;
    }

    public void setDevis(List<Devis> devis) {
        this.devis = devis;
    }

    public StringBuilder getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(StringBuilder commentaire) {
        this.commentaire = commentaire;
    }

    
    
}
