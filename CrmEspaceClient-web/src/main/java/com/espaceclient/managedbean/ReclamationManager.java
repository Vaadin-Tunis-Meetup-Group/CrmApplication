/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.espaceclient.managedbean;

import com.crm.model.Commande;
import com.crm.model.Message;
import com.crm.model.Notification;
import com.crm.model.Reclamation;
import com.crm.sessionbean.ClientFacade;
import com.crm.sessionbean.CommandeFacade;
import com.crm.sessionbean.NotificationFacade;
import com.crm.sessionbean.ReclamationFacade;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Haithem
 */
@Named
@SessionScoped
public class ReclamationManager implements Serializable{
    @EJB
    private CommandeFacade commandeFacade;
    @EJB
    private ReclamationFacade reclamationFacade;
    
    
    
    
    private Commande commande;
    @Inject
    private LoginManager loginManager;
    
     @EJB
    private NotificationFacade notificationFacade;
   
    
    private Message selectedMsg;
    
    
  
    
    
    
    private Reclamation reclamation=new Reclamation();

    /**
     * Creates a new instance of ReclamationManager
     */
    public ReclamationManager() {
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }
    
    public String sendReclamation(){
        reclamation.setDateRecl(new Date());
        reclamation.setCommande(commande);
        reclamation.setClient(loginManager.getCurrentClient());
        reclamation.setNewRecl(Boolean.TRUE);
        reclamation.setEtat(Boolean.FALSE);
        Notification notif=new Notification();
notif.setDateNotif(new Date());
notif.setSujet("Réclamation");
notif.setContenu("Le client "+loginManager.getCurrentClient()+" vient de envoyer une réclamation");
notif.setNewNotif(Boolean.TRUE);
notificationFacade.create(notif);
        //reclamationFacade.create(reclamation);
commandeFacade.addNewReclamation(reclamation.getCommande().getId(), reclamation);
        reclamation=new Reclamation();
        return "commandes?faces-redirect=true";
    }

   
    
}
