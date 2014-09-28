package com.crm.managedbean;

import com.crm.managedbean.util.JsfUtil;
import com.crm.model.Produit;
import com.crm.sessionbean.ProduitFacade;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "produitController")
@ViewScoped
public class ProduitController extends AbstractController<Produit> implements Serializable {

    @Inject
    private ProduitFacade ejbFacade;
    
    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    
    
  
   
    
    public ProduitController() {
        super(Produit.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
    
    public void upload(FileUploadEvent event) { 
        this.getSelected().setImage(event.getFile().getFileName());
} 
    
      public void saveData(ActionEvent event) {
        this.getSelected().setImage(file.getFileName());
        ejbFacade.create(this.getSelected());
          JsfUtil.addSuccessMessage("Produit ajoutée");
        
      }

}
