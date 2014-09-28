/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espaceclient.managedbean;

import com.crm.model.Categorie;
import com.crm.model.Marque;
import com.crm.model.Produit;
import com.crm.sessionbean.CategorieFacade;
import com.crm.sessionbean.MarqueFacade;
import com.crm.sessionbean.ProduitFacade;
import com.espaceclient.util.ObjetPanier;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author Haithem
 */
@Named
@SessionScoped
public class PhonesManager implements Serializable {

    @EJB
    private MarqueFacade marqueFacade;
    @EJB
    private CategorieFacade categorieFacade;

    @EJB
    private ProduitFacade produitFacade;

    private List<Categorie> allCateg;
    private List<Marque> allMarque;

    private List<Produit> allProduit;
    private Produit selected = new Produit();

    private List<Categorie> selectedCateg;
    private List<Marque> selectedMarque;

    private String selectedPrix;
    
    private Produit topVente;

    /**
     * Creates a new instance of PhonesManager
     */
    @PostConstruct
    public void init() {
        allProduit = produitFacade.findAll();
        selectedCateg = new ArrayList<Categorie>();
        selectedMarque = new ArrayList<Marque>();
        allCateg = categorieFacade.findAll();
        allMarque = marqueFacade.findAll();
        topVente=allProduit.get(0);

    }

    public PhonesManager() {
    }

    public List<Produit> getAllProduit() {
        return allProduit;
    }

    public void setAllProduit(List<Produit> allProduit) {
        this.allProduit = allProduit;
    }

    public Produit getSelected() {
        return selected;
    }

    public void setSelected(Produit selected) {
        this.selected = selected;
    }

    public List<Categorie> getSelectedCateg() {
        return selectedCateg;
    }

    public void setSelectedCateg(List<Categorie> selectedCateg) {
        this.selectedCateg = selectedCateg;
    }

    public List<Categorie> getAllCateg() {
        return allCateg;
    }

    public void setAllCateg(List<Categorie> allCateg) {
        this.allCateg = allCateg;
    }

    public void updatePhones() {
        System.out.println("ksjlsjlqsjqs " + selectedCateg.toString());
        if (!selectedCateg.isEmpty()) {
            allProduit = produitFacade.findAllByCategs(selectedCateg);
            System.out.println("all" + allProduit.toString());
        } else {
            allProduit = produitFacade.findAll();
        }
    }

    public void updatePhonesBMarque() {
        if (!selectedMarque.isEmpty()) {
            allProduit = produitFacade.findAllByMarque(selectedMarque);
        } else {
            allProduit = produitFacade.findAll();
        }
    }

    public void updatePhonesByPu() {
        switch (selectedPrix) {
            case "1":
                allProduit = produitFacade.findAllInfPu(100.00);
                break;
            case "4":
                allProduit = produitFacade.findAllSupPu(1000.00);
                break;
            case "2":
                allProduit = produitFacade.findAllBetwwenPU(100.00, 500.00);
                break;
            case "3":
                allProduit = produitFacade.findAllBetwwenPU(500.00, 1000.00);
                break;
            default:
                allProduit = produitFacade.findAll();
                break;

        }
    }

    public List<Marque> getAllMarque() {
        return allMarque;
    }

    public void setAllMarque(List<Marque> allMarque) {
        this.allMarque = allMarque;
    }

    public List<Marque> getSelectedMarque() {
        return selectedMarque;
    }

    public void setSelectedMarque(List<Marque> selectedMarque) {
        this.selectedMarque = selectedMarque;
    }

    public String getSelectedPrix() {
        return selectedPrix;
    }

    public void setSelectedPrix(String selectedPrix) {
        this.selectedPrix = selectedPrix;
    }

    public Produit getTopVente() {
        return topVente;
    }

    public void setTopVente(Produit topVente) {
        this.topVente = topVente;
    }

    
}
