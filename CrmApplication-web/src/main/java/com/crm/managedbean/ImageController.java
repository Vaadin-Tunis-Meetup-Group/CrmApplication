/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crm.managedbean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Haithem
 */
@Named
@ViewScoped
public class ImageController {

    private List<String> allImage;

    /**
     * Creates a new instance of ImageController
     */
    public ImageController() {
    }

    @PostConstruct
    public void init() {
        allImage = Arrays.asList("nokia", "nexus","note3","iphone - 5s","alcatel","blackberry",
                "samsung-gravity","samsung-s4","lg","galaxy-exhibit","xperia"
                
                
        );
    }

    public List<String> getAllImage() {
        return allImage;
    }

    public void setAllImage(List<String> allImage) {
        this.allImage = allImage;
    }
    
    
    
}
