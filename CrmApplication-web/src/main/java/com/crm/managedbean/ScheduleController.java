/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm.managedbean;

import com.crm.model.RendezVous;
import com.crm.sessionbean.RendezVousFacade;
import com.crm.sessionbean.UtilisateurFacade;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Haithem
 */
@Named
@ViewScoped
public class ScheduleController {
    @EJB
    private UtilisateurFacade utilisateurFacade;
    @EJB
    private RendezVousFacade rendezVousFacade;
    
    @Inject
    private LoginController loginCtrl;
    
    

    private ScheduleModel eventModel;  
      
    private ScheduleEvent event = new DefaultScheduleEvent();  
    
    
  
    public ScheduleController() {  
       
     /*   eventModel.addEvent(new DefaultScheduleEvent("Réunion avec un client", previousDay8Pm(), previousDay11Pm()));  
        eventModel.addEvent(new DefaultScheduleEvent("Passer la commande du client aaa", today1Pm(), today6Pm()));  
        eventModel.addEvent(new DefaultScheduleEvent("réunion avec un fournisseur", nextDay9Am(), nextDay11Am()));  
        eventModel.addEvent(new DefaultScheduleEvent("un evenement", theDayAfter3Pm(), fourDaysLater3pm()));  
   */
    }
    
    @PostConstruct
    public void init(){
         eventModel = new DefaultScheduleModel();  
        for(RendezVous rv:loginCtrl.getCurrentUser().getRendezVousCollection()){
            eventModel.addEvent(new DefaultScheduleEvent(rv.getTitre(), rv.getStart(), rv.getEnd()));
        }
    }
      
    public Date getRandomDate(Date base) {  
        Calendar date = Calendar.getInstance();  
        date.setTime(base);  
        date.add(Calendar.DATE, ((int) (Math.random()*30)) + 1);    //set random day of month  
          
        return date.getTime();  
    }  
      
    public Date getInitialDate() {  
        Calendar calendar = Calendar.getInstance();  
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);  
          
        return calendar.getTime();  
    }  
      
    public ScheduleModel getEventModel() {  
        return eventModel;  
    }  
      
    private Calendar today() {  
        Calendar calendar = Calendar.getInstance();  
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);  
  
        return calendar;  
    }  
      
    private Date previousDay8Pm() {  
        Calendar t = (Calendar) today().clone();  
        t.set(Calendar.AM_PM, Calendar.PM);  
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);  
        t.set(Calendar.HOUR, 8);  
          
        return t.getTime();  
    }  
      
    private Date previousDay11Pm() {  
        Calendar t = (Calendar) today().clone();  
        t.set(Calendar.AM_PM, Calendar.PM);  
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);  
        t.set(Calendar.HOUR, 11);  
          
        return t.getTime();  
    }  
      
    private Date today1Pm() {  
        Calendar t = (Calendar) today().clone();  
        t.set(Calendar.AM_PM, Calendar.PM);  
        t.set(Calendar.HOUR, 1);  
          
        return t.getTime();  
    }  
      
    private Date theDayAfter3Pm() {  
        Calendar t = (Calendar) today().clone();  
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 2);       
        t.set(Calendar.AM_PM, Calendar.PM);  
        t.set(Calendar.HOUR, 3);  
          
        return t.getTime();  
    }  
  
    private Date today6Pm() {  
        Calendar t = (Calendar) today().clone();   
        t.set(Calendar.AM_PM, Calendar.PM);  
        t.set(Calendar.HOUR, 6);  
          
        return t.getTime();  
    }  
      
    private Date nextDay9Am() {  
        Calendar t = (Calendar) today().clone();  
        t.set(Calendar.AM_PM, Calendar.AM);  
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);  
        t.set(Calendar.HOUR, 9);  
          
        return t.getTime();  
    }  
      
    private Date nextDay11Am() {  
        Calendar t = (Calendar) today().clone();  
        t.set(Calendar.AM_PM, Calendar.AM);  
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);  
        t.set(Calendar.HOUR, 11);  
          
        return t.getTime();  
    }  
      
    private Date fourDaysLater3pm() {  
        Calendar t = (Calendar) today().clone();   
        t.set(Calendar.AM_PM, Calendar.PM);  
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 4);  
        t.set(Calendar.HOUR, 3);  
          
        return t.getTime();  
    }  
      
    public ScheduleEvent getEvent() {  
        return event;  
    }  
  
    public void setEvent(ScheduleEvent event) {  
        this.event = event;  
    }  
      
    public void addEvent(ActionEvent actionEvent) {  
        if(event.getId() == null){  
            eventModel.addEvent(event);  
            RendezVous rv=new RendezVous(event.getId());
            rv.setTitre(event.getTitle());
            rv.setStart(event.getStartDate());
            rv.setEnd(event.getEndDate());
            rv.setUser(loginCtrl.getCurrentUser());
            //rendezVousFacade.create(rv);
            loginCtrl.setCurrentUser(utilisateurFacade.addNewRendezVous(loginCtrl.getCurrentUser().getId(), rv));
        }
        else { 
            eventModel.updateEvent(event); 
            RendezVous rv=rendezVousFacade.find(event.getId());
             rv.setTitre(event.getTitle());
            rv.setStart(event.getStartDate());
            rv.setEnd(event.getEndDate());
            rv.setUser(loginCtrl.getCurrentUser());
            rendezVousFacade.edit(rv);
            
            
        }
          
        event = new DefaultScheduleEvent();  
    }  
      
    public void onEventSelect(SelectEvent selectEvent) {  
        event = (ScheduleEvent) selectEvent.getObject();  
    }  
      
    public void onDateSelect(SelectEvent selectEvent) {  
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());  
    }  
      
    public void onEventMove(ScheduleEntryMoveEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());  
          
        addMessage(message);  
    }  
      
    public void onEventResize(ScheduleEntryResizeEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());  
          
        addMessage(message);  
    }  
      
    private void addMessage(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }  
}  

