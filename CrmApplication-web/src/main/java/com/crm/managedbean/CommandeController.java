/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crm.managedbean;

import com.crm.managedbean.util.Devis;
import com.crm.managedbean.util.DevisData;
import com.crm.managedbean.util.JsfUtil;
import com.crm.model.Commande;
import com.crm.model.LigneCommande;
import com.crm.model.Produit;
import com.crm.sessionbean.CommandeFacade;
import com.crm.sessionbean.ProduitFacade;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;


/**
 *
 * @author Haithem
 */
@Named
@SessionScoped
public class CommandeController implements Serializable {
    @EJB
    private ProduitFacade produitFacade;

    @EJB
    private CommandeFacade commandeFacade;
    
    

    private List<Commande> allCmd;

    private List<Commande> allDev;

    private List<Commande> allHisto;

    private Commande selected;

    /**
     * Creates a new instance of CommandeController
     */
    public CommandeController() {
    }

    @PostConstruct
    public void init() {
        allCmd = commandeFacade.getAllCmd();
        allDev = commandeFacade.getAllDev();
        allHisto = commandeFacade.getAllHisto();
    }

    public List<Commande> getAllCmd() {
        return commandeFacade.getAllCmd();
    }

    public void setAllCmd(List<Commande> allCmd) {
        this.allCmd = allCmd;
    }

    public Commande getSelected() {
        return selected;
    }

    public void setSelected(Commande selected) {
        this.selected = selected;
    }

    public List<Commande> getAllDev() {
        return commandeFacade.getAllDev();
    }

    public void setAllDev(List<Commande> allDev) {
        this.allDev = allDev;
    }

    public void confirmCmd(ActionEvent event) {
        try {
            selected.setEtat("Confirmer");
            selected.setDateReponse(new Date());
            for(LigneCommande l:selected.getLigneCommandeCollection()){
                Produit p=l.getProduit();
                p.setQuantite(p.getQuantite()-l.getQte());
                produitFacade.edit(p);
            }
            commandeFacade.edit(selected);
            allCmd = commandeFacade.getAllCmd();
            allHisto = commandeFacade.getAllHisto();
            String url = JsfUtil.getRequestUrl().replaceFirst("detail", "list");
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(CommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void confirmCmdDevis(ActionEvent event) {
        try {
            selected.setEtat("Confirmer");
            selected.setDateReponse(new Date());
            commandeFacade.edit(selected);
            allDev = commandeFacade.getAllDev();
            allHisto = commandeFacade.getAllHisto();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            DevisData dev;
            List<Devis> allDev = new ArrayList<Devis>();

            List<DevisData> allData = new ArrayList<DevisData>();
             dev = new DevisData();
            for (LigneCommande l : selected.getLigneCommandeCollection()) {
                Devis d = new Devis();
                d.setRef("#000" + l.getProduit().getIdproduit());
                d.setPu("" + l.getProduit().getPu());
                d.setQte("" + l.getQte());
                d.setProduit("" + l.getProduit().getLibelle());
                d.setPrix("" + l.getProduit().getPu() * l.getQte());
                if(l.getQte() > l.getProduit().getQuantite())
                {
                    dev.getCommentaire().append("La quantité demandée pour le produit #000"+l.getProduit().getIdproduit()+" - "+l.getProduit().getLibelle()+" n'est pas disponible pour le moment ! ").append("/n");
                }
                allDev.add(d);
            }
           
            dev.setDevis(allDev);
            dev.setClient("" + selected.getClient().toString());
            dev.setPrixt("" + selected.getMontant());
          JsfUtil.sendMaillDevis(selected.getClient().getEmail(), dev);
            String url = JsfUtil.getRequestUrl().replaceFirst("detail", "listDev");
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(CommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Commande> getAllHisto() {
        return commandeFacade.getAllHisto();
    }

    public void setAllHisto(List<Commande> allHisto) {
        this.allHisto = allHisto;
    }

}
