package com.crm.managedbean;

import com.crm.managedbean.util.JsfUtil;
import com.crm.model.Client;
import com.crm.sessionbean.ClientFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

@Named(value = "clientController")
@ViewScoped
public class ClientController extends AbstractController<Client> implements Serializable {

    @Inject
    private ClientFacade ejbFacade;

    public ClientController() {
        super(Client.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
    
    
    public void activeCompte(ActionEvent event){
        this.getSelected().setEtatCompte(true);
         ejbFacade.edit(this.getSelected());
         
        JsfUtil.sendMaill(getSelected().getEmail(), "Activation de compte ",getSelected());
        JsfUtil.addSuccessMessage("Compte Client Activé ");
       
    }

}
