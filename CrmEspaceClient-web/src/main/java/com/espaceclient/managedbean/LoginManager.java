/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.espaceclient.managedbean;

import com.crm.model.Client;
import com.crm.model.Commande;
import com.crm.sessionbean.ClientFacade;
import com.espaceclient.util.JsfUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Haithem
 */
@Named
@SessionScoped
public class LoginManager implements Serializable {
    
    @EJB
    private ClientFacade clientFacade;
   
 

    private String currentStep="p1";
    private String login;
    private String password;
    private Client CurrentClient=null;

    /**
     * Creates a new instance of LoginManager
     */
    public LoginManager() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Client getCurrentClient() {
        return CurrentClient;
    }

    public void setCurrentClient(Client CurrentClient) {
        this.CurrentClient = CurrentClient;
    }
    
    public List<Commande> mesCommandes(){
        return new ArrayList<Commande>(clientFacade.getMesCommandes(CurrentClient.getIdclient()));
    }
    
    
     public void loginAction(ActionEvent actionEvent) {  
        RequestContext context = RequestContext.getCurrentInstance();  
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        FacesMessage msg = null;  
       
         System.out.println("Login is "+login);
       CurrentClient=clientFacade.findByLoginAndPassword(login, password);
       if(CurrentClient!=null)
       {
          if(CurrentClient.getEtatCompte()){
            try {
                if(JsfUtil.getRequestUrl().endsWith("monPanier.xhtml"))
                    ec.redirect(ec.getApplicationContextPath()+"/monPanier.xhtml");
                else
                    ec.redirect(ec.getApplicationContextPath()+"/phones.xhtml");
            } catch (IOException ex) {
               ex.printStackTrace();
            }
          }else{
               msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Compte non active", "Votre compte sera activé dans les prochaines 24 heures"); 
          FacesContext.getCurrentInstance().addMessage(null, msg);
          }
       }else
       {
           
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error : Identifiant ou mot de passe invalide", "Identifiant ou mot de passe invalide"); 
          FacesContext.getCurrentInstance().addMessage(null, msg);  
       }
        
      
    
    } 
     
     
     
      public String loginActionPanier() {  
        RequestContext context = RequestContext.getCurrentInstance();  
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        FacesMessage msg = null;  
       currentStep="p3";
       CurrentClient=clientFacade.findByLoginAndPassword(login, password);
       if(CurrentClient!=null)
       {
          if(CurrentClient.getEtatCompte()){
           JsfUtil.addSuccessMessage("Bienvenu "+CurrentClient);
           return "monPanier?faces-redirect=true";
          }else{
               msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Compte non active", "Votre compte sera activé dans les prochaines 24 heures"); 
          FacesContext.getCurrentInstance().addMessage(null, msg);
          return "";
          }
       }else
       {
           
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error : Identifiant ou mot de passe invalide", "Identifiant ou mot de passe invalide"); 
          FacesContext.getCurrentInstance().addMessage(null, msg); 
          return "";
       }
        
      
    
    } 
    
    
    public String logOut() throws IOException
    {
        
         ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        System.out.println("I am here");
        // clear session
        ec.invalidateSession();
         ec.redirect(ec.getRequestContextPath());
        return "";
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }
    
    
    
}
