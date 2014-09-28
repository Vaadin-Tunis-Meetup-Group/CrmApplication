/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm.sessionbean;

import com.crm.model.Client;
import com.crm.model.Commande;
import com.crm.model.Profil;
import com.crm.model.RendezVous;
import com.crm.model.Utilisateur;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Haithem
 */
@Stateless
public class UtilisateurFacade extends AbstractFacade<Utilisateur> {
    @PersistenceContext(unitName = "com.crm_CrmApplication-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }
    
      public Utilisateur findByLoginAndPassword(String login,String password,Profil profil){
        Query query=em.createNamedQuery("Utilisateur.findByLogAndPwd")
                .setParameter("login", login)
                .setParameter("password", password)
                .setParameter("profil", profil);
        try{
            return (Utilisateur) query.getSingleResult();
        }catch(NoResultException ex)
        {
            return null;
        }
        
    }
      
      public List<Utilisateur> findAllOnLineUser(){
                  Query query=em.createNamedQuery("Utilisateur.findAllConnectedUser");
                  return query.getResultList();

      }
      
      public Utilisateur editUser(Utilisateur u){
          Utilisateur user=em.find(Utilisateur.class, u.getId());
          user.setConnected(Boolean.TRUE);
          user.setLastConnection(new Date());
          return user;
      }
      
         public Utilisateur addNewRendezVous(Integer id,RendezVous rv){
         Utilisateur u=em.find(Utilisateur.class, id);
         u.getRendezVousCollection().add(rv);
         return u;
     }
     
      
     
}
