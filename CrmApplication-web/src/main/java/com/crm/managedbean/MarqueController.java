package com.crm.managedbean;

import com.crm.model.Marque;
import com.crm.sessionbean.MarqueFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;

@Named(value = "marqueController")
@ViewScoped
public class MarqueController extends AbstractController<Marque> implements Serializable {

    @Inject
    private MarqueFacade ejbFacade;

    public MarqueController() {
        super(Marque.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }

}
