/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm.managedbean;

import com.crm.managedbean.util.JsfUtil;
import com.crm.model.Reclamation;
import com.crm.sessionbean.ReclamationFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author Haithem
 */
@Named
@SessionScoped
public class ReclamationController implements Serializable {
    @EJB
    private ReclamationFacade reclamationFacade;
    
    private List<Reclamation> allReclamation;
    private List<Reclamation> historique;
    private Reclamation selected;
    
        

    /**
     * Creates a new instance of ReclamationController
     */
    public ReclamationController() {
       
        
    }
    
    @PostConstruct
    public void init(){
         allReclamation=reclamationFacade.getAllRecl();
        historique=reclamationFacade.getAllReclHist();
    }

    public List<Reclamation> getAllReclamation() {
        return allReclamation;
    }

    public void setAllReclamation(List<Reclamation> allReclamation) {
        this.allReclamation = allReclamation;
    }

    public List<Reclamation> getHistorique() {
        return historique;
    }

    public void setHistorique(List<Reclamation> historique) {
        this.historique = historique;
    }

    public Reclamation getSelected() {
        return selected;
    }

    public void setSelected(Reclamation selected) {
        this.selected = selected;
    }
    
    public void sendReponse(ActionEvent event){
        try{
        selected.setDateRep(new Date());
        selected.setEtat(Boolean.TRUE);
          reclamationFacade.edit(selected);

           allReclamation=reclamationFacade.getAllRecl();
        historique=reclamationFacade.getAllReclHist();
          String url=JsfUtil.getRequestUrl().replaceFirst("detail", "list");
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(CommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        
    }
    
}
