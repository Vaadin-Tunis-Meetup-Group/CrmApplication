/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crm.sessionbean;

import com.crm.model.Categorie;
import com.crm.model.Categorie_;
import com.crm.model.Marque;
import com.crm.model.Produit;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Haithem
 */
@Stateless
public class ProduitFacade extends AbstractFacade<Produit> {

    @PersistenceContext(unitName = "com.crm_CrmApplication-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProduitFacade() {
        super(Produit.class);
    }

    public List<Produit> findAllByCategs(List<Categorie> categs) {
        Query query = em.createNamedQuery("Produit.findAllByCateg")
                .setParameter("categories", categs);
        return query.getResultList();
    }

    public List<Produit> findAllByMarque(List<Marque> marque) {
        Query query = em.createNamedQuery("Produit.findAllByMarque")
                .setParameter("marques", marque);
        return query.getResultList();
    }

    public List<Produit> findAllBetwwenPU(double minPu, double maxPu) {
        Query query = em.createNamedQuery("Produit.findAllBetweenPU")
                .setParameter("val1 ", minPu)
                .setParameter("val2", maxPu);
        return query.getResultList();
    }

    public List<Produit> findAllInfPu(double maxPu) {
        Query query = em.createNamedQuery("Produit.findAllInfPu")
                .setParameter("prix", maxPu);
        return query.getResultList();
    }

    public List<Produit> findAllSupPu(double minPu) {
        Query query = em.createNamedQuery("Produit.findAllSupPu")
                .setParameter("prix", minPu);
        return query.getResultList();
    }

   /* public List<Produit> searchPhones(List<Categorie> categs, List<Marque> marque, double minPu, double maxPu) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

// Query for a List of objects.
        CriteriaQuery cq = cb.createQuery();
        
        Root e=cq.from(Produit.class);
        cq.where(cb.1
                }
    */

}
