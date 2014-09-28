

package com.crm.sessionbean;

import com.crm.model.Client;
import com.crm.model.Commande;
import com.crm.model.Message;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ClientFacade extends AbstractFacade<Client> {
    @PersistenceContext(unitName = "com.crm_CrmApplication-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientFacade() {
        super(Client.class);
    }
    
     public Client findByLoginAndPassword(String login,String password){
        Query query=em.createNamedQuery("Client.findByLogAndPwd")
                .setParameter("login", login)
                .setParameter("password", password);
        try{
            return (Client) query.getSingleResult();
        }catch(NoResultException ex)
        {
            return null;
        }
        
    }
     
     public List<Client> getAllINactive()
     {
         Query q=em.createNamedQuery("Client.findAllInActive");
         return q.getResultList();
     }
     
          public List<Client> getAllActive()
     {
         Query q=em.createNamedQuery("Client.findAllActive");
         return q.getResultList();
     }
     
     public List<Commande> getMesCommandes(int id){
         Client cl=em.find(Client.class, id);
         System.out.println("La taille de mes commandes est : "+cl.getCommandeCollection());
         return (List<Commande>) cl.getCommandeCollection();
                 
     }
     
     public Client findByLogin(String login){
          Query query=em.createNamedQuery("Client.findByLogin")
                .setParameter("login", login);
               
        try{
            return (Client) query.getSingleResult();
        }catch(NoResultException ex)
        {
            return null;
        }
     }
     
     public Client addNewCommande(Integer id,Commande cmd){
         Client client=em.find(Client.class, id);
         client.getCommandeCollection().add(cmd);
         return client;
     }
     
     public Client addNewMessage(Integer id,Message m){
         Client client=em.find(Client.class, id);
         client.getMessageEnvoye().add(m);
         return client;
     }
     
     public List<Client> TopClient(){
         Query query=em.createNamedQuery("Client.findAll");
         List<Client> all=query.getResultList();
         Collections.sort(all, new Comparator<Client>() {

             @Override
             public int compare(Client o1, Client o2) {
                 return countCmd(o2.getCommandeCollection())-countCmd(o1.getCommandeCollection());
             }
         }
         );
        
         return all;
         
     }
     
 
    public int countCmd(Collection<Commande> allCmd){
        int val=0;
        for (Commande commande : allCmd) {
            if(commande.getType())
            val++;
        }
        return val;
   
}
    
}
