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
import com.fastbooks.modelo.FbInvoice;
import com.fastbooks.modelo.FbPerfilXUsuario;
import com.fastbooks.modelo.FbPerfiles;
import com.fastbooks.modelo.FbUsuario;
import com.fastbooks.modelo.FbUsuarioXCia;
import com.fastbooks.util.GlobalParameters;
import com.fastbooks.util.ValidationBean;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

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

    private @Getter
    @Setter
    GlobalParameters gParameters = new GlobalParameters();
    private @Getter
    @Setter
    Integer offset = 2;
    private @Getter
    @Setter
    Integer colmd = 10;
    private @Getter
    @Setter
    String currentPage = "Dashboard";
    private @Getter
    @Setter
    String uses = "0";
    private @Getter
    @Setter
    String sInvoice = "0";
    private @Getter
    @Setter
    FbInvoice fbInvoice = null;
    private @Getter
    @Setter
    int salesIndex = 0;
    private @Getter
    @Setter
    String invoiceSql = "0";
    private @Getter
    @Setter
    String formInProdId = "0";
    private @Getter
    @Setter
    String formInCustId = "0";
    private @Getter
    @Setter
    String invoiceTypeForm = "IN";
    private @Getter
    @Setter
    String autoLocale = "";
    private @Getter
    @Setter
    Locale country;
    private @Getter
    @Setter
    int jsfSucks = 0;
    private @Getter
    @Setter
    String dirCust = "0";

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

    /* @PostConstruct
    public void init(){
       System.out.println("INITIALIZATING SESSION BEAN!!!!");
        Locale requestLocale = new Locale("en", "us");//FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        System.out.println(requestLocale.toString());
        System.out.println(requestLocale.getDisplayName());
        System.out.println(requestLocale.getCountry());
        System.out.println(requestLocale.getLanguage());
        System.out.println(requestLocale.getDisplayLanguage());
        System.out.println(requestLocale.getDisplayCountry());
        
        System.out.println("-------------------------------------------------------");
        BigDecimal doble = new BigDecimal("0");
        java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(requestLocale);
        System.out.println(format.format(doble));
        System.out.println("-------------------------------------------------------");
    }*/
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

    public void changeLocale(String loc) throws IOException {
        for (Country entry : list) {
            if (entry.getLanInitials().equals(loc)) {
                System.out.println(entry.getLanInitials());
                FacesContext.getCurrentInstance()
                        .getViewRoot().setLocale((Locale) entry.getLocale());
                validationBean.reload();
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
                loggedUser = user;
                this.email = "";
                this.pass = "";

                if (loggedUser.getFbUsuarioXCiaList().size() == 1) {
                    //redirect to dashboard and set id_cia and profile
                    currentCia = loggedUser.getFbUsuarioXCiaList().get(0).getFbCompania();
                    perfil = loggedUser.getFbPerfilXUsuarioList().get(0).getFbPerfiles();

                    if (currentCia.getFbCompaniaPref() != null) {
                        country = new Locale(currentCia.getFbCompaniaPref().getLang(), currentCia.getFbCompaniaPref().getCountry());
                    } else {
                        country = new Locale("en", "us");
                    }

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
                .getViewRoot().setLocale((Locale) new Locale("en"));
        initLangs();
    }

    public void valLogin() {
        try {
            if (this.loggedUser != null) {
                validationBean.redirecionar("/view/dashboard.xhtml");
            }
        } catch (Exception e) {
            System.out.println("not logged");
        }
    }

    public void initLangs() {

        if (this.locale.isEmpty()) {
            this.locale = "en";
            FacesContext.getCurrentInstance()
                    .getViewRoot().setLocale((Locale) new Locale("en"));
        }

        list = new ArrayList<>();
        list.add(new Country("USA", validationBean.getMsgBundle("lblEnglish"), "en", new Locale("en")));
        //list.add(new Country("France", "French", "fr", new Locale("fr")));
        list.add(new Country("El Salvador", validationBean.getMsgBundle("lblSpanish"), "es", new Locale("es")));

        System.out.println("INIT LANGUAGES!!");
    }

    public boolean activo(String pag) {

        HttpServletRequest requestContext = validationBean.getRequestContext();
        //System.out.println( requestContext.getRequestURI()+"  :"+pag + " = " + currentPage);
        //return this.currentPage.equals(pag);
        return requestContext.getRequestURI().contains(pag.toLowerCase());
    }

    public String formatMaster(String obj) {
        java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(country);
        //obj = "249.57";
        String res = "";
        if (!obj.isEmpty()) {
            Double dobbs = Double.parseDouble(obj);
            res = format.format(dobbs);
            if (dobbs < 0) {
                //formatear para negativo
                res = res.substring(1, 2) + " -" + res.substring(2, res.length() - 1);

            }

        }

        return res;
    }

    public void changeTab(int index) {
        this.salesIndex = index;
        jsfSucks++;
        if (jsfSucks == 1) {
            this.validationBean.redirecionar("/view/sales/sales.xhtml");
        } else if (jsfSucks > 2) {
            jsfSucks = 0;
        }

    }

    public boolean renderTab(int index) {
        boolean flag = false;
        if (this.salesIndex == index) {
            flag = true;
        }

        return flag;
    }

    public boolean renderSpinner(int index) {
        boolean flag = false;
        if (this.salesIndex != index) {
            flag = true;
        }

        return flag;
    }

    @PreDestroy
    public void destory() {
        this.validationBean.redirecionar("/");
        System.out.println("com.fastbooks.managedbeans.UserData.destory()");
    }

    public String formatStatus(String s) {
        String res = "";
        switch (s) {
            case "OP":
                res = this.validationBean.getMsgBundle("lblInvoiceStatusOpen");
                break;
            case "CA":
                res = this.validationBean.getMsgBundle("lblInvoiceStatusCancel");
                break;
            case "PA":
                res = this.validationBean.getMsgBundle("lblInvoiceStatusPartial");
                break;
            case "OV":
                res = this.validationBean.getMsgBundle("lblInvoiceStatusOverdue");
                break;
            case "CL":
                res = this.validationBean.getMsgBundle("lblInvoiceStatusClosed");
                break;
            case "PE":
                res = this.validationBean.getMsgBundle("lblESPending");
                break;
            case "AC":
                res = this.validationBean.getMsgBundle("lblAccepted");
                break;
            case "RJ":
                res = this.validationBean.getMsgBundle("lblRejected");
                break;

            case "PD":
                res = this.validationBean.getMsgBundle("lblPaid");
                break;
            case "UN":
                res = this.validationBean.getMsgBundle("lblUnapplied");
                break;

            default:
                break;
        }

        return res;
    }

    public String formatType(String t) {
        String res = "";
        switch (t) {
            case "IN":
                res = this.validationBean.getMsgBundle("lblInvoiceTypeIn");
                break;
            case "ES":
                res = this.validationBean.getMsgBundle("lblEstimate");
                break;
            case "SR":
                res = this.validationBean.getMsgBundle("salesR");
                break;
            case "PA":
                res = this.validationBean.getMsgBundle("payment");
                break;
            default:
                break;
        }

        return res;
    }
    
       public boolean showOptions(String status, String type, String option) {
        boolean flag = false;

        switch (option) {
            case "PRINT":
                if (!type.equals("PA")) {
                    flag = true;
                }

                break;

            case "EDIT":
                flag = true;
                break;

            case "ESTATUS":
                if (type.equals("ES")) {
                    flag = true;
                }
                break;
            case "COPY":
                if (!type.equals("PA")) {
                    flag = true;
                }
                break;
            case "REPAY":
                if (type.equals("IN")) {
                    if (status.equals("OV") || status.equals("OP") || status.equals("PA")) {
                        flag = true;
                    }
                    
                    
                }
                break;                

            default:
                System.out.println("You do nutin");
                break;

        }

        return flag;
    }

}
