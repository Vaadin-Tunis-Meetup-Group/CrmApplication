/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm.managedbean.util;

import java.util.Date;

/**
 *
 * @author Haithem
 */
public class ClientStat {
    private int id;
    private String name;
    private int nbrCmd;
    private int nbrDev;
    private double pourcentage;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbrCmd() {
        return nbrCmd;
    }

    public void setNbrCmd(int nbrCmd) {
        this.nbrCmd = nbrCmd;
    }

    public int getNbrDev() {
        return nbrDev;
    }

    public void setNbrDev(int nbrDev) {
        this.nbrDev = nbrDev;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
}
