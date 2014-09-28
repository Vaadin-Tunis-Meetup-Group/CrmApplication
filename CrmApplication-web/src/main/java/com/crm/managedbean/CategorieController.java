package com.crm.managedbean;

import com.crm.model.Categorie;
import com.crm.sessionbean.CategorieFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;

@Named(value = "categorieController")
@ViewScoped
public class CategorieController extends AbstractController<Categorie> implements Serializable {

    @EJB
    private CategorieFacade ejbFacade;

    public CategorieController() {
        super(Categorie.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }

}
