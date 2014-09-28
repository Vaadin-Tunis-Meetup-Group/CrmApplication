/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm.managedbean;

import com.crm.managedbean.util.ClientStat;
import com.crm.model.Categorie;
import com.crm.model.Client;
import com.crm.model.Commande;
import com.crm.model.Marque;
import com.crm.model.Produit;
import com.crm.sessionbean.CategorieFacade;
import com.crm.sessionbean.ClientFacade;
import com.crm.sessionbean.CommandeFacade;
import com.crm.sessionbean.MarqueFacade;
import com.crm.sessionbean.ProduitFacade;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Haithem
 */
@Named
@javax.enterprise.context.RequestScoped
public class StatistiquesController {
    @EJB
    private ClientFacade clientFacade;
    @EJB
    private CommandeFacade commandeFacade;
    @EJB
    private ProduitFacade produitFacade;
    @EJB
    private MarqueFacade marqueFacade;
    @EJB
    private CategorieFacade categorieFacade;
    
    private int countCmd;
    private int countClient;
    
    
    
    
    
      private PieChartModel pieModel1;
    private PieChartModel pieModel2;
    
    private BarChartModel barModel;
    
     private BarChartModel barModelClients;
    
    private LineChartModel lineModel2;
    
    private List<ClientStat> allClStat=new ArrayList<ClientStat>();

    
    
    
    

    /**
     * Creates a new instance of StatistiquesController
     */
    public StatistiquesController() {
    }
    
    @PostConstruct
    public void init(){
        createPieModels();
    }
    
    private void createPieModels() {
        createPieModel1();
        createPieModel2();
        createBarModel();
        createBarModelClient();
        createLineModels();
        for(Client c:clientFacade.findAll()){
            ClientStat cs=new ClientStat();
            cs.setId(c.getIdclient());
            cs.setDate(c.getDateAjout());
            int t1=0;
            int t2=0;
            for(Commande com:c.getCommandeCollection()){
                if(com.getType()){
                    t1++;
                }else{
                    t2++;
                }
            }
            cs.setNbrCmd(t1);
            cs.setNbrDev(t2);
            cs.setPourcentage(t1*100/commandeFacade.count());
            cs.setName(c.toString());
            allClStat.add(cs);
            
        }
        countClient=clientFacade.count();
        countCmd=commandeFacade.count();
                
    }
 
    private void createPieModel1() {
        pieModel1 = new PieChartModel();
        List<Categorie> all=categorieFacade.findAll();
        for(Categorie c:all){
        pieModel1.set(c.getLibelle(), c.getProduitCollection().size());
        }
         
        pieModel1.setTitle("Catégorie des Téléphones");
        pieModel1.setLegendPosition("w");
    }
     
    private void createPieModel2() {
        pieModel2 = new PieChartModel();
         
       List<Marque> all=marqueFacade.findAll();
        for(Marque m:all){
        pieModel2.set(m.getLibelle(), m.getProduitCollection().size());
        }
         
        pieModel2.setTitle("Marque des Téléphones");
        pieModel2.setLegendPosition("e");
        pieModel2.setFill(false);
        pieModel2.setShowDataLabels(true);
        pieModel2.setDiameter(150);
    }
    
     private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries prix = new ChartSeries();
        List<Produit> all=produitFacade.findAll();
        prix.setLabel("Prix");
        int p1=0;
        int p2=0;
        int p3=0;
        int p4=0;
        for(Produit p:all){
          if(p.getPu() < 100){
              p1++;
          }else
              if(p.getPu()>100 && p.getPu() < 500){
                  p2++;
              }
          else
                  if(p.getPu()>500 && p.getPu() < 1000){
                      p3++;
                  }
                  else{
                      p4++;
                  }
        }
        prix.set("Prix <100", p1);
        prix.set("100 < Prix < 500", p2);
        prix.set("500 <¨Prix < 1000",p3);
        prix.set("Prix > 1000", p4);
 
      
 
        model.addSeries(prix);
         
        return model;
    }
     
      private BarChartModel initBarModelClients() {
        BarChartModel model = new BarChartModel();
        
 
        ChartSeries commande = new ChartSeries();
         ChartSeries devis = new ChartSeries();
        
        
        commande.setLabel("Commande");
        devis.setLabel("Devis");

       
        int i=0;
        List<Client> top=clientFacade.TopClient();
        List<Client> topCl=top.subList(0, top.size()>5?5:top.size());
        for(Client c:topCl){
             commande.set(c.toString(), countCmd(c.getCommandeCollection()));
             devis.set(c.toString(), countDevis(c.getCommandeCollection()));
        }
  
      
 
        model.addSeries(commande);
         model.addSeries(devis);

         
        return model;
    }
     
      public int countCmd(Collection<Commande> allCmd){
        int val=0;
        for (Commande commande : allCmd) {
            if(commande.getType())
            val++;
        }
        return val;
   
}
      
      public int countDevis(Collection<Commande> allCmd){
        int val=0;
        for (Commande commande : allCmd) {
            if(!commande.getType())
            val++;
        }
        return val;
   
}
    
    private void createBarModel() {
        barModel = initBarModel();
         
        barModel.setTitle("Répartition des téléphones par Prix");
        barModel.setLegendPosition("ne");
        
         
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Prix");
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Nombre de téléphones");
        yAxis.setMin(0);
        yAxis.setMax(20);
    }
    
     private void createBarModelClient() {
        barModelClients = initBarModelClients();
         
        barModelClients.setTitle("TOP 5 clients par rapport aux Commandes / Devis");
        barModelClients.setLegendPosition("ne");
        
         
        Axis xAxis = barModelClients.getAxis(AxisType.X);
        xAxis.setLabel("Client");
         
        Axis yAxis = barModelClients.getAxis(AxisType.Y);
        yAxis.setLabel("Nombre Commande / Devis");
        yAxis.setMin(0);
        yAxis.setMax(100);
    }
    
     private void createLineModels() {
      
        lineModel2 = initCategoryModel();
        lineModel2.setTitle("charge des Commandes par mois");
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Mois"));
        Axis yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Nombre de commandes");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }
     private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();
 
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Commandes");
        boys.set("Janvier", 30);
        boys.set("Février", 8);
        boys.set("Mars", 70);
        boys.set("Avril", 60);
        boys.set("Mai", 100);
       
       
        ChartSeries girls = new ChartSeries();
        girls.setLabel("Devis");
        girls.set("Janvier", 50);
        girls.set("Février", 20);
        girls.set("Mars", 120);
        girls.set("Avril", 90);
        girls.set("Mai", 150);
 
        model.addSeries(boys);
        model.addSeries(girls);
         
        return model;
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public void setPieModel1(PieChartModel pieModel1) {
        this.pieModel1 = pieModel1;
    }

    public PieChartModel getPieModel2() {
        return pieModel2;
    }

    public void setPieModel2(PieChartModel pieModel2) {
        this.pieModel2 = pieModel2;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public LineChartModel getLineModel2() {
        return lineModel2;
    }

    public void setLineModel2(LineChartModel lineModel2) {
        this.lineModel2 = lineModel2;
    }

    public List<ClientStat> getAllClStat() {
        return allClStat;
    }

    public void setAllClStat(List<ClientStat> allClStat) {
        this.allClStat = allClStat;
    }

    public int getCountCmd() {
        return countCmd;
    }

    public void setCountCmd(int countCmd) {
        this.countCmd = countCmd;
    }

    public int getCountClient() {
        return countClient;
    }

    public void setCountClient(int countClient) {
        this.countClient = countClient;
    }

    public BarChartModel getBarModelClients() {
        return barModelClients;
    }

    public void setBarModelClients(BarChartModel barModelClients) {
        this.barModelClients = barModelClients;
    }
    
    
    
     
    
}
