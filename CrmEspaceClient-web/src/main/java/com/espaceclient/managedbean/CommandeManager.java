/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.espaceclient.managedbean;

import com.crm.model.Commande;
import com.crm.sessionbean.ClientFacade;
import com.crm.sessionbean.CommandeFacade;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Haithem
 */
@Named
@ViewScoped
public class CommandeManager {
    @EJB
    private ClientFacade clientFacade;
    
    private List<Commande> mesCommandes;
    
    @Inject
    private LoginManager loginCtrl;
    
    @PostConstruct
    public void init(){
        mesCommandes=clientFacade.getMesCommandes(loginCtrl.getCurrentClient().getIdclient());
    }

    public List<Commande> getMesCommandes() {
        return mesCommandes;
    }

    public void setMesCommandes(List<Commande> mesCommandes) {
        this.mesCommandes = mesCommandes;
    }
    
    
}
