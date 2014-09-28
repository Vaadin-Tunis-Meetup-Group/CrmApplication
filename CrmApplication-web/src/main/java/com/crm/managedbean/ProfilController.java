package com.crm.managedbean;

import com.crm.model.Profil;
import com.crm.sessionbean.ProfilFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;

@Named(value = "profilController")
@ViewScoped
public class ProfilController extends AbstractController<Profil> implements Serializable {

    @Inject
    private ProfilFacade ejbFacade;

    public ProfilController() {
        super(Profil.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }

}
