/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.espaceclient.managedbean;

import com.crm.model.Commande;
import com.crm.model.LigneCommande;
import com.crm.model.Notification;
import com.crm.model.Produit;
import com.crm.sessionbean.ClientFacade;
import com.crm.sessionbean.CommandeFacade;
import com.crm.sessionbean.NotificationFacade;
import com.espaceclient.util.JsfUtil;
import com.espaceclient.util.ObjetPanier;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Haithem
 */
@Named
@SessionScoped
public class CartManager implements Serializable {
    @EJB
    private ClientFacade clientFacade;
    @EJB
    private CommandeFacade commandeFacade;
    
     @EJB
    private NotificationFacade notificationFacade;
     
     
     
    
    private List<ObjetPanier> cady=new ArrayList<ObjetPanier>();
    private int nbrArticle;
      private boolean skip; 
       private static Logger logger = Logger.getLogger(CartManager.class.getName());  
      private ObjetPanier selectedObj;
      
      private Commande currentCmd=new Commande();
      
      @Inject
    private LoginManager loginManager;
      
      
      
    

    /**
     * Creates a new instance of CartManager
     */
    public CartManager() {
        nbrArticle=0;
    }
    
    
    
    public double getTotalPrix(){
        int somme=0;
        for (ObjetPanier objetPanier : cady) {
            somme+=objetPanier.getPrix();
        }
        return somme;
    }

    public List<ObjetPanier> getCady() {
        return cady;
    }

    public void setCady(List<ObjetPanier> cady) {
        this.cady = cady;
    }
    
     public void AddItem(ActionListener event,Produit selected)
    {
        boolean test=false;
        int i=0;
        FacesContext context= FacesContext.getCurrentInstance();
        FacesMessage msg;
        //Jouet selected=(Jouet) FacesContext.getCurrentInstance().getApplication().createValueBinding("#{jouetManagedbean.selectedJouet}").getValue(context);
       
       nbrArticle++;
         
        while(!test && i<cady.size())
        {
             if(cady.get(i).getProduit().equals(selected))
            {
                test=true;
                int old_qt=cady.get(i).getQte();
                cady.get(i).setQte(cady.get(i).getQte()+1);
            }
             i++;
        }
        if(test==false)
        {
            ObjetPanier obj=new ObjetPanier(selected, 1);
             cady.add(obj);
        }
       
        msg=new FacesMessage(FacesMessage.SEVERITY_INFO, "Jouet Ajoutee", selected.getLibelle()+"jouet ajoutee dans votre panier");
        context.addMessage(null, msg);
    }
     
     public void spinnerQte(){
          nbrArticle++;
     }
     
     public void deleteProduct(ActionEvent event){
         nbrArticle-=selectedObj.getQte();
        cady.remove(selectedObj);
         JsfUtil.addSuccessMessage("Le produit a été supprimé de votre panier");
     }

     
    
  
    public int getNbrArticle() {
        return nbrArticle;
    }

    public void setNbrArticle(int nbrArticle) {
        this.nbrArticle = nbrArticle;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }
    
     
      public ObjetPanier getSelectedObj() {
        return selectedObj;
    }

    public void setSelectedObj(ObjetPanier selectedObj) {
        this.selectedObj = selectedObj;
    }
    
    
  
    public void save(ActionEvent actionEvent) {
        //Persist user
         
        FacesMessage msg = new FacesMessage("Successful", "Welcome :" );
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
 
     
    public String onFlowProcess(FlowEvent event) {
        logger.info("Current wizard step:" + event.getOldStep());
        logger.info("Next step:" + event.getNewStep());
         
        if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            if(getTotalPrix()!=0)
            return event.getNewStep();
            else{
                JsfUtil.addErrorMessage("Vous devez ajouter au moins un article dans votre panier !");
                return event.getOldStep();
            }
        }
    }
    
    
    public void validate(ActionEvent event){
        if(loginManager.getCurrentClient()==null){
            RequestContext.getCurrentInstance().execute("PF('loginPanier').show()");
        }
        else{
            
            List<LigneCommande> allLigCmd=new ArrayList<LigneCommande>();
              currentCmd.setClient(loginManager.getCurrentClient());
             currentCmd.setMontant(getTotalPrix());
             for(ObjetPanier p:cady){
                 LigneCommande lc=new LigneCommande();
                 lc.setProduit(p.getProduit());
                 lc.setQte(p.getQte());
                 lc.setCommande(currentCmd);
                 allLigCmd.add(lc);
             }
           
             currentCmd.setLigneCommandeCollection(allLigCmd);
             currentCmd.setEtat("en cours de traitement");
             //commandeFacade.create(currentCmd);
             //loginManager.getCurrentClient().getCommandeCollection().add(currentCmd);
             loginManager.setCurrentClient(clientFacade.addNewCommande(loginManager.getCurrentClient().getIdclient(), currentCmd));
             Notification notif=new Notification();
notif.setDateNotif(new Date());
 if(currentCmd.getType())
     notif.setSujet("Nouvelle Commande");
             else
     notif.setSujet("Demande de Devis");
notif.setContenu("Le client "+currentCmd.getClient()+" vient de passer une commande de produits");
notif.setNewNotif(Boolean.TRUE);
notificationFacade.create(notif);
             currentCmd=new Commande();
             cady=new ArrayList<ObjetPanier>();
             nbrArticle=0;
             RequestContext.getCurrentInstance().execute("PF('validation').show()");
        }
    }

    public Commande getCurrentCmd() {
        return currentCmd;
    }

    public void setCurrentCmd(Commande currentCmd) {
        this.currentCmd = currentCmd;
    }
    
     
    
}
