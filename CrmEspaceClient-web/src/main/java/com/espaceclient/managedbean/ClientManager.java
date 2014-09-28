/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.espaceclient.managedbean;

import com.crm.model.Client;
import com.crm.model.Message;
import com.crm.model.Notification;
import com.crm.sessionbean.ClientFacade;
import com.crm.sessionbean.MessageFacade;
import com.crm.sessionbean.NotificationFacade;
import com.crm.sessionbean.ProfilFacade;
import com.espaceclient.util.JsfUtil;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.faces.webapp.FacesServlet;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

/**
 *
 * @author Haithem
 */
@Named
@ViewScoped
public class ClientManager {
    @EJB
    private MessageFacade messageFacade;
    @EJB
    private ProfilFacade profilFacade;
    @EJB
    private NotificationFacade notificationFacade;
    @EJB
    private ClientFacade clientFacade;
    
    private Message selectedMsg;
    
    
    
    @Inject
    private LoginManager loginManager;
    
    private Message message=new Message();
    private String idProfil;
    
    
    
    private Client client=new Client();
    
        private final static String CHANNEL = "/notify";

    
    

    /**
     * Creates a new instance of ClientManager
     */
    public ClientManager() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    public String doCreate(){
     
        if(clientFacade.findByLogin(client.getLogin())!=null){
            JsfUtil.addErrorMessage("Login existe deja ! vous devez choisir un autre login");
            return "";
        }
        else{
             client.setEtatCompte(false);
        clientFacade.create(client);
          
       //  EventBus eventBus = EventBusFactory.getDefault().eventBus();
    // System.out.println(FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath());
Notification notif=new Notification();
notif.setDateNotif(new Date());
notif.setSujet("Nouveau Client");
notif.setContenu("Le client "+client+" vient de créer un compte");
notif.setNewNotif(Boolean.TRUE);
notificationFacade.create(notif);
       // eventBus.publish("/*", new FacesMessage(FacesMessage.SEVERITY_INFO,"Clien ajoutée","Le client "+client.getNom()+" vient de créer un compte !"));
        return "signin?faces-redirect=true";
        }
    }

    public void sendMessage(ActionEvent event){
        message.setDateEnv(new Date());
        message.setNewMsg(Boolean.TRUE);
        System.out.println(idProfil);
        message.setFromCl(loginManager.getCurrentClient());
        if(idProfil.equals("0"))
          message.setToUser(profilFacade.find(1));
          else
             if(idProfil.equals("1"))
              message.setToUser(profilFacade.find(3));
             else
              message.setToUser(profilFacade.find(2));
        
        
        message.setToClient(null);
        message.setFromUser(null);
        //messageFacade.create(message);
        loginManager.setCurrentClient(clientFacade.addNewMessage(loginManager.getCurrentClient().getIdclient(),message));
        message=new Message();
        idProfil=null;
        JsfUtil.addSuccessMessage("Votre message a été envoyée avec succeés");
        RequestContext.getCurrentInstance().execute("PF('sendMessageDlg').hide()");
        
    }
    
    
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getIdProfil() {
        return idProfil;
    }

    public void setIdProfil(String idProfil) {
        this.idProfil = idProfil;
    }

    public Message getSelectedMsg() {
        return selectedMsg;
    }

    public void setSelectedMsg(Message selectedMsg) {
        this.selectedMsg = selectedMsg;
    }
    
    
    
    
    
}
