/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm.sessionbean;

import com.crm.model.Commande;
import com.crm.model.Message;
import com.crm.model.Profil;
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
public class MessageFacade extends AbstractFacade<Message> {
    @PersistenceContext(unitName = "com.crm_CrmApplication-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MessageFacade() {
        super(Message.class);
    }
    
    public List<Message> getAllNewMessage(Profil profil){
        Query q=em.createNamedQuery("Message.findAllNew");
        q.setParameter("user", profil);
        return (List<Message>) q.getResultList();
    }
    
     public List<Message> getAllMessage(Profil profil){
        Query q=em.createNamedQuery("Message.findAllByProfil");
        q.setParameter("user", profil);
        return (List<Message>) q.getResultList();
    }
    
}
