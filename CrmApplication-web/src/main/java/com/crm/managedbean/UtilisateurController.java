package com.crm.managedbean;

import com.crm.model.Utilisateur;
import com.crm.sessionbean.UtilisateurFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;

@Named(value = "utilisateurController")
@ViewScoped
public class UtilisateurController extends AbstractController<Utilisateur> implements Serializable {

    @Inject
    private UtilisateurFacade ejbFacade;

    public UtilisateurController() {
        super(Utilisateur.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }

}
