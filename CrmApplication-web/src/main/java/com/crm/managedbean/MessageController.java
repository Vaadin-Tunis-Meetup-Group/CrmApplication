/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm.managedbean;

import com.crm.managedbean.util.JsfUtil;
import com.crm.model.Client;
import com.crm.model.Message;
import com.crm.model.Notification;
import com.crm.model.Notification_;
import com.crm.sessionbean.ClientFacade;
import com.crm.sessionbean.MessageFacade;
import com.crm.sessionbean.NotificationFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@SessionScoped
public class MessageController implements Serializable {
    @EJB
    private ClientFacade clientFacade;
    @EJB
    private NotificationFacade notificationFacade;
  
    @EJB
    private MessageFacade messageFacade;
    
    private List<Client> allClient=new ArrayList<Client>();
    
        
    
    
    @Inject
    private LoginController loginCtrl;
    
    private Message selectedMessage;
    
    private Message message=new Message();
    
    private List<Message> allNewMsg;
    private List<Message> allMsg;
    
     private List<Notification> allNewNotif;
    private List<Notification> allNotif;
    
    
    private int nbrMsg;
    private int nbrNotif;
    
    
    
    
    
    

    /**
     * Creates a new instance of MessageController
     */
    public MessageController() {
    }
    

    @PostConstruct
    public void init(){
        allNewMsg=messageFacade.getAllNewMessage(loginCtrl.getCurrentUser().getProfil());
        allNewNotif=notificationFacade.getAllNewNotif();
        allMsg=messageFacade.getAllMessage(loginCtrl.getCurrentUser().getProfil());
        allNotif=notificationFacade.findAll();
        nbrMsg=allNewMsg.size();
        nbrNotif=allNewNotif.size();
    }

    public List<Message> getAllNewMsg() {
        return allNewMsg;
    }

    public void setAllNewMsg(List<Message> allNewMsg) {
        this.allNewMsg = allNewMsg;
    }

    public List<Message> getAllMsg() {
        return allMsg;
    }

    public void setAllMsg(List<Message> allMsg) {
        this.allMsg = allMsg;
    }

    public List<Notification> getAllNewNotif() {
        return allNewNotif;
    }

    public void setAllNewNotif(List<Notification> allNewNotif) {
        this.allNewNotif = allNewNotif;
    }

    public List<Notification> getAllNotif() {
        return allNotif;
    }

    public void setAllNotif(List<Notification> allNotif) {
        this.allNotif = allNotif;
    }

    public int getNbrMsg() {
        return nbrMsg;
    }

    public void setNbrMsg(int nbrMsg) {
        this.nbrMsg = nbrMsg;
    }

    public int getNbrNotif() {
        return nbrNotif;
    }

    public void setNbrNotif(int nbrNotif) {
        this.nbrNotif = nbrNotif;
    }
    
    public void updateNotif(){
        if(nbrNotif != notificationFacade.getAllNewNotif().size()){
            allNewNotif=notificationFacade.getAllNewNotif();
            nbrNotif=allNewNotif.size();
            Notification note=allNewNotif.get(nbrNotif-1);
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, note.getSujet(), note.getContenu()));
        }
        
         if(nbrMsg != messageFacade.getAllNewMessage(loginCtrl.getCurrentUser().getProfil()).size()){
            allNewMsg=messageFacade.getAllNewMessage(loginCtrl.getCurrentUser().getProfil());
            nbrMsg=allNewMsg.size();
            Message note=allNewMsg.get(nbrMsg-1);
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Nouveau Message", note.getSujet()));
        }
    }
    
    public String updateNbrNotif(Notification c,String target){
      nbrNotif--;
      c.setNewNotif(Boolean.FALSE);
      notificationFacade.edit(c);
      allNewNotif.remove(c);
      return target+"?faces-redirect=true";
    }
    
     public String updateNbrMsg(Message msg){
      nbrMsg--;
      msg.setNewMsg(Boolean.FALSE);
      messageFacade.edit(msg);
      allNewMsg.remove(msg);

      return "/faces/message/index"
              + "?faces-redirect=true";
    }

    public Message getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(Message selectedMessage) {
        this.selectedMessage = selectedMessage;
    }
    
 

    public List<Client> getAllClient() {
        return clientFacade.getAllActive();
    }

    public void setAllClient(List<Client> allClient) {
        this.allClient = allClient;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
    
    public void sendMessage(ActionEvent event){
        message.setFromUser(loginCtrl.getCurrentUser().getProfil());
        message.setDateEnv(new Date());
        message.setFromCl(null);
        message.setToUser(null);
        message.setType(null);
        message.setNewMsg(Boolean.TRUE);
       messageFacade.create(message);
       message=new Message();
        JsfUtil.addSuccessMessage("Message envoyé ");
    }
    
    public void deleteMsg(ActionEvent event){
        messageFacade.remove(selectedMessage);
        JsfUtil.addSuccessMessage("Message supprimée avec succées");
        selectedMessage=new Message();
    }
    
    
}
