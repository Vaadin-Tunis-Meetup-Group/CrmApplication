/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm.sessionbean;

import com.crm.model.Client;
import com.crm.model.Commande;
import com.crm.model.Reclamation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Haithem
 */
@Stateless
public class CommandeFacade extends AbstractFacade<Commande> {
    @PersistenceContext(unitName = "com.crm_CrmApplication-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommandeFacade() {
        super(Commande.class);
    }
    
    public List<Commande> getAllCmd(){
        Query q=em.createNamedQuery("Commande.findAllCmd");
        return (List<Commande>) q.getResultList();
    }
    
     public List<Commande> getAllDev(){
        Query q=em.createNamedQuery("Commande.findAllDev");
        return (List<Commande>) q.getResultList();
    }
     
     public List<Commande> getAllHisto(){
        Query q=em.createNamedQuery("Commande.findAllCmdHisto");
        return (List<Commande>) q.getResultList();
    }
     
       public void addNewReclamation(Integer id,Reclamation recl){
         Commande cmd=em.find(Commande.class, id);
         cmd.getReclamationCollection().add(recl);
     }
    
}
