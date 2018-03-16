/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbUsuarioFacade;
import com.fastbooks.forms.CompaniaForm;
import com.fastbooks.forms.UserForm;
import com.fastbooks.modelo.Country;
import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbPerfilXUsuario;
import com.fastbooks.modelo.FbPerfiles;
import com.fastbooks.modelo.FbUsuario;
import com.fastbooks.modelo.FbUsuarioXCia;
import com.fastbooks.util.ValidationBean;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author dell
 */
@Named(value = "userData")
@SessionScoped
public class UserData implements Serializable {

    @EJB
    FbUsuarioFacade userFacade;

    @EJB
    ValidationBean validationBean;

    private static final long serialVersionUID = 1L;
    private String locale = "en";
    private Country lenguage;
    private List<Country> list = new ArrayList<Country>();

    private FbUsuario loggedUser;
    private FbCompania currentCia;
    private BigDecimal idcia;
    private FbPerfiles perfil;
    private String email;
    private String pass;

    public String getEmail() {
        return email;
    }

    public BigDecimal getIdcia() {
        return idcia;
    }

    public void setIdcia(BigDecimal idcia) {
        this.idcia = idcia;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public FbCompania getCurrentCia() {
        return currentCia;
    }

    public void setCurrentCia(FbCompania currentCia) {
        this.currentCia = currentCia;
    }

    public FbPerfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(FbPerfiles perfil) {
        this.perfil = perfil;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public FbUsuario getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(FbUsuario loggedUser) {
        this.loggedUser = loggedUser;
    }

    /* private static Map<String,Object> countries;
    static {
      
      countries = new LinkedHashMap<String,Object>();
      countries.put("English", Locale.ENGLISH);
      countries.put("French", Locale.FRENCH);
      countries.put("Spanish", new Locale("es"));
   }*/
    /**
     * Creates a new instance of UserData
     */
    public UserData() {

        
    }

    public List<Country> getList() {
        return list;
    }

    public void setList(List<Country> list) {
        this.list = list;
    }

    public Country getLenguage() {
        return lenguage;
    }

    public void setLenguage(Country lenguage) {
        this.lenguage = lenguage;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    //value change event listener
    public void localeChanged(ValueChangeEvent e) {
        String newLocaleValue = e.getNewValue().toString();
        System.out.println(newLocaleValue);
        for (Country entry : list) {

            if (entry.getLanInitials().equals(newLocaleValue)) {
                System.out.println(entry.getLanInitials());
                FacesContext.getCurrentInstance()
                        .getViewRoot().setLocale((Locale) entry.getLocale());
            }

        }
        initLangs();
    }

    public void login() {
        FbUsuario user = new FbUsuario();
        try {
            /*HttpServletRequest req = (HttpServletRequest) validationBean.getRequestContext();
            this.email = req.getParameter("userid");
            this.pass = req.getParameter("password");*/
            user = userFacade.login(email, pass);
            System.out.println(user.toString());
            if (!String.valueOf(user.getIdUsuario()).equals("0")) {
                /*loggedUser = new UserForm();
           loggedUser.setIdUsuario(String.valueOf(user.getIdUsuario()));
           loggedUser.setEmail(user.getEmail());
           loggedUser.setPassword(user.getClave());
           loggedUser.setFirstName(user.getFirstname());
           loggedUser.setLastName(user.getLastname());
           System.out.println(loggedUser.toString());
           loggedUser.setCias(userFacade.getUserCompaniasById(loggedUser.getIdUsuario()));
           System.err.println("cias: " + loggedUser.getCias().toString());*/
                loggedUser = user;
                this.email = "";
                this.pass = "";

                if (loggedUser.getFbUsuarioXCiaList().size() == 1) {
                    //redirect to dashboard and set id_cia and profile
                    currentCia = loggedUser.getFbUsuarioXCiaList().get(0).getFbCompania();
                    perfil = loggedUser.getFbPerfilXUsuarioList().get(0).getFbPerfiles();
                    validationBean.redirecionar("/view/dashboard.xhtml");
                } else {
                    //redirect to chooseCompany and set id_cia and profile with the selected
                    System.out.println("show com list");
                    validationBean.redirecionar("/view/chooseCompany.xhtml?faces-redirect=true");
                }

            } else {
                System.out.println("Fallo de login");
                validationBean.lanzarMensaje("error", "loginfail", "loginfaildesc");
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.UserData.login()");
            e.printStackTrace();
        }

    }

    public void selectCom() {
        try {
            for (FbUsuarioXCia com : loggedUser.getFbUsuarioXCiaList()) {
                if (com.getFbCompania().getIdCia().equals(this.idcia)) {
                    currentCia = com.getFbCompania();

                }
            }

            for (FbPerfilXUsuario per : loggedUser.getFbPerfilXUsuarioList()) {
                if (per.getFbPerfilXUsuarioPK().getIdCia().equals(new BigInteger(String.valueOf(idcia)))) {
                    perfil = per.getFbPerfiles();
                }
            }

            validationBean.redirecionar("/view/dashboard.xhtml");
        } catch (Exception e) {
            validationBean.lanzarMensaje("error", "mustSelectCom", "loginfaildesc");
        }
    }

    public void logout() {
        loggedUser = null;
        currentCia = null;
        idcia = null;
        perfil = null;
        validationBean.redirecionar("/index.xhtml?faces-redirect=true");
    }

    public void workaround() {
        validationBean.redirecionar("/index.xhtml?faces-redirect=true");
        this.locale = "en";
         FacesContext.getCurrentInstance()
               .getViewRoot().setLocale((Locale)new Locale("en"));
        initLangs();
    }
    
    public void valLogin(){
        try {
            if (this.loggedUser != null) {
                validationBean.redirecionar("/view/dashboard.xhtml");
            }
        } catch (Exception e) {
            System.out.println("not logged");
        }
    }
    
    
    public void initLangs(){
        
        if (this.locale.isEmpty()) {
            this.locale = "en";
         FacesContext.getCurrentInstance()
               .getViewRoot().setLocale((Locale)new Locale("en"));
        }
        
        list = new ArrayList<>();
        list.add(new Country("USA", validationBean.getMsgBundle("lblEnglish"), "en", new Locale("en")));
        //list.add(new Country("France", "French", "fr", new Locale("fr")));
        list.add(new Country("El Salvador", validationBean.getMsgBundle("lblSpanish"), "es", new Locale("es")));
    
    }
    
   
}
