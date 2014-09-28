/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.espaceclient.util;

import com.crm.model.Produit;

/**
 *
 * @author Haithem
 */
public class ObjetPanier {
    private Produit produit;
    private int qte;
    private double prix;

    public ObjetPanier() {
    }

    public ObjetPanier(Produit produit, int qte) {
        this.produit = produit;
        this.qte = qte;
        prix=qte*produit.getPu();
    }
    
    

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public double getPrix() {
        prix=qte*produit.getPu();
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
    
    
}
