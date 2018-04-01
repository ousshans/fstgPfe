package controller;

import bean.Commande;
import bean.LigneLivraison;
import bean.Livraison;
import bean.UserStock;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import service.LigneLivraisonFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import service.LivraisonFacade;

@Named("ligneLivraisonController")
@SessionScoped
public class LigneLivraisonController implements Serializable {

    @EJB
    private service.LigneLivraisonFacade ejbFacade;
    private List<LigneLivraison> items = null;
    private List<LigneLivraison> livraisonItmes = new ArrayList<>();
    @EJB
    private LivraisonFacade livraisonFacade;
    
    private LigneLivraison selected;
    
    private Commande commande;
    private Date daterecep;

    public LigneLivraisonController() {
    }

    public LigneLivraison getSelected() {
        if (selected == null) {
            selected = new LigneLivraison();
        }
        return selected;
    }

    public void setSelected(LigneLivraison selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LigneLivraisonFacade getFacade() {
        return ejbFacade;
    }

    public LigneLivraison prepareCreate() {
        selected = new LigneLivraison();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LigneLivraisonCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LigneLivraisonUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("LigneLivraisonDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<LigneLivraison> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<LigneLivraison> getLivraisonItmes() {
            if (livraisonItmes == null) {
            livraisonItmes = new ArrayList<>();
        }
        return livraisonItmes;
    }

    public void setLivraisonItmes(List<LigneLivraison> livraisonItmes) {
        this.livraisonItmes = livraisonItmes;
    }

    public Commande getCommande() {
        if (commande == null) {
            commande = new Commande();
        }
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Date getDaterecep() {
        if (daterecep == null) {
            daterecep = new Date();
        }
        
        return daterecep;
    }

    public void setDaterecep(Date daterecep) {
        this.daterecep = daterecep;
    }
    
    
        public void creerLigne() {
        System.out.println("azert");
        LigneLivraison cloneLigne = ejbFacade.cloneLigneLivraison(selected);
        livraisonItmes.add(cloneLigne);
        setSelected(null);

    }
    
    
    
    
    
    

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public LigneLivraison getLigneLivraison(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<LigneLivraison> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<LigneLivraison> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = LigneLivraison.class)
    public static class LigneLivraisonControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LigneLivraisonController controller = (LigneLivraisonController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ligneLivraisonController");
            return controller.getLigneLivraison(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof LigneLivraison) {
                LigneLivraison o = (LigneLivraison) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), LigneLivraison.class.getName()});
                return null;
            }
        }

    }
    
        public void info() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Livraison ajouter", "avec succe"));
    }
     public void info1() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Votre recu a été enregstrer avec sucée", ""));
    }
    public void vider() {
        setSelected(null);
        livraisonItmes.clear();
    }
    
    
//    
    
    public void validerLivraison() {
       
            String idSession = ((UserStock) SessionUtil.getConnectedUser()).getId();
            System.out.println("hahowa l id " + idSession);
            Livraison livraison = livraisonFacade.addLivraison(idSession, commande, daterecep);
            System.out.println("ha l9lawii tcrea ");
            
            if (livraison == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Vous devez remplir tout les champs !"));
            } else {
                for (int i = 0; i < livraisonItmes.size(); i++) {
                    LigneLivraison livraisonItem = livraisonItmes.get(i);
                    ejbFacade.createLigneLivraison(livraisonItem.getQuantite(), livraisonItem.getProduit(), livraison.getId());
                }
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Votre Recu a été creer avec succès."));
                vider();
            }
        } 

}
