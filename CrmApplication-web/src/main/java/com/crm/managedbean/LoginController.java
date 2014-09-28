/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm.managedbean;

import com.crm.managedbean.util.JsfUtil;
import com.crm.model.Profil;
import com.crm.model.Utilisateur;
import com.crm.sessionbean.ClientFacade;
import com.crm.sessionbean.ProfilFacade;
import com.crm.sessionbean.UtilisateurFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;


/**
 *
 * @author Haithem
 */
@Named
@SessionScoped
public class LoginController implements Serializable {
 
    @EJB
    private UtilisateurFacade utilisateurFacade;
    @EJB
    private ProfilFacade profilFacade;
    

    private List<Profil> allProfil;
    
    @Inject
    private ChatController chatCtrl;
    
    
    
    private String login;
    private String password;
    private Profil selectedProfil;
    
    private int nbrConnected;
    
    private String msg;
    
    private Utilisateur currentUser;
    
    private List<Utilisateur> connectedUser;
    
    @PostConstruct
    public void init(){
        allProfil=profilFacade.findAll();
        
        
    }

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
    }

    
     
    
     public void login(ActionEvent actionEvent) { 
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        FacesMessage msg = null;  
       
         String url=JsfUtil.getRequestUrl().replaceFirst("/login.jsf", "");
         
       currentUser=utilisateurFacade.findByLoginAndPassword(login, password, selectedProfil);
       if(currentUser!=null)
       {
          
          currentUser=utilisateurFacade.editUser(currentUser);
          nbrConnected=utilisateurFacade.findAllOnLineUser().size();
            try {
                if(currentUser.getProfil().getId()==1)
                   ec.redirect(url+"/index.xhtml");
                else
                     if(currentUser.getProfil().getId()==2)
                          ec.redirect(url+"/index-sav.xhtml");
                     else
                           ec.redirect(url+"/commercial.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
       }else
       {
           
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Identifiant ou mot de passe invalide"); 
          FacesContext.getCurrentInstance().addMessage(null, msg);  
       }
        
      
    
    } 
    
    
    public String logOut() throws IOException
    {
        
         ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        currentUser.setConnected(Boolean.FALSE);
        utilisateurFacade.edit(currentUser);
        // clear session
        ec.invalidateSession();
         ec.redirect(ec.getRequestContextPath());
        return "";
    }
    
    
    public List<Profil> getAllProfil() {
        return allProfil;
    }

    public void setAllProfil(List<Profil> allProfil) {
        this.allProfil = allProfil;
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

    public Profil getSelectedProfil() {
        return selectedProfil;
    }

    public void setSelectedProfil(Profil selectedProfil) {
        this.selectedProfil = selectedProfil;
    }

    public Utilisateur getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Utilisateur currentUser) {
        this.currentUser = currentUser;
    }

    public List<Utilisateur> getConnectedUser() {
        return utilisateurFacade.findAllOnLineUser();
    }

    public void setConnectedUser(List<Utilisateur> connectedUser) {
        this.connectedUser = connectedUser;
    }
    
    
    public void updateChat(){
        if(nbrConnected!=getConnectedUser().size()){
            JsfUtil.addSuccessMessage(getConnectedUser().get(0)+" vient de connecter");
            nbrConnected=getConnectedUser().size();
        }
    }
    
    public void sendMessage(ActionEvent event){
      
        if(nbrConnected>1)
        chatCtrl.addMsg(currentUser.toString(), msg);
        else{
            JsfUtil.addErrorMessage("Il n'y a pas des utilisateurs connectés");
        }
        msg="";
    }
    
  

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    
    
}
