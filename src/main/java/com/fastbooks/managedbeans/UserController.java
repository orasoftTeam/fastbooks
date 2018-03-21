/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbPerfilXUsuarioFacade;
import com.fastbooks.facade.FbUsuarioFacade;
import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbPerfilXUsuario;
import com.fastbooks.modelo.FbUsuario;
import com.fastbooks.util.ValidationBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Guadalupe
 */
@Named(value = "userController")
@ViewScoped
public class UserController implements Serializable{

    @EJB
    private ValidationBean validationBean;
    @EJB
    private FbUsuarioFacade fbUsuarioFacade;
    @EJB
    private FbPerfilXUsuarioFacade pxuFacade;
    @Inject
    private UserData userData;
    

    FbUsuario usuario = new FbUsuario();
    
    List<FbUsuario> usuarioL;
     @Getter @Setter List<FbUsuario> usuarioPrueba;
    @Getter @Setter List<FbPerfilXUsuario>  pxuList = new ArrayList<>();
    
    String idCiaSearch;
    
    private boolean skip;
    private String profile;
    private String per;
    private List<String> profilesL;
    
    //Atributos tabla
    private String email;
    private String clave;
    private String firstName;
    private String lastName;
    private String bday;
    private String tel;
    private String gender;
    private String rclave;
    private @Setter @Getter String day; 
    private @Setter @Getter String month;
    private @Setter @Getter String year; 
    private @Setter @Getter List<Integer> years = new ArrayList<Integer>();
    
    /**
     * Creates a new instance of UserController
     */
    public UserController() {
        getList();
    }

    public String getRclave() {
        return rclave;
    }

    public void setRclave(String rclave) {
        this.rclave = rclave;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void getUserxCia(){
        usuarioL = new ArrayList<>();
        usuarioL = fbUsuarioFacade.getUserbyCia(idCiaSearch);
            
    }
    //metodo creado para step-menu
     public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
     }
     
     
     public boolean addUsrVal(){
        boolean flag = false;
        
        if(!(validationBean.validarCampoVacio(this.email, "error", "valErr", "reqEmail")
            && validationBean.validarEmail(this.email, "error", "valErr", "reqEmail")
                && validationBean.validarLongitudCampo(this.email, 8, 80, "error", "valErr", "reqEmail"))){
                }
        
        if(!(validationBean.validarCampoVacio(this.lastName, "error", "valErr", "reqLastName")
                && validationBean.validarSoloLetras(this.lastName, "error", "valErr", "reqLastName")
                && validationBean.validarLongitudCampo(this.lastName, 4, 50, "error", "valErr", "reqLastName"))){   
        }
        
        
         return false;
         
         
         
     }
     
   
    
    public FbUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(FbUsuario usuario) {
        this.usuario = usuario;
    }

    public List<FbUsuario> getUsuarioL() {
        return usuarioL;
    }

    public void setUsuarioL(List<FbUsuario> usuarioL) {
        this.usuarioL = usuarioL;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public List<String> getProfilesL() {
        return profilesL;
    }

    public void setProfilesL(List<String> profilesL) {
        this.profilesL = profilesL;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    

    
    
    public void setValues(){
        this.usuario = this.userData.getLoggedUser();
        this.email = this.usuario.getEmail();
        this.clave = this.usuario.getClave();
        this.rclave = this.usuario.getClave();
        this.gender = this.usuario.getGenero();
        this.bday = this.usuario.getBDay();
        this.firstName = this.usuario.getFirstname();
        this.lastName = this.usuario.getLastname();
        this.tel = this.usuario.getTelefono();
        
        
        int cYear = Calendar.getInstance().get(Calendar.YEAR);
        
        for (int i = 80; i > 17; i--) {
            years.add(cYear - i);
        }
        
        try {
            if (!this.usuario.getBDay().isEmpty()) {
                String[] split = this.usuario.getBDay().split("/");
                this.day = split[0];
                this.month = split[1];
                this.year = split[2];
        }
        } catch (Exception e) {
        }
        
    }
    
    /*@PostConstruct
    public void init() {
    
    setValues();
    }*/
    
    
    
    public void editOwnUser(){
        if (valEditOwnUser()) {
            usuario.setEmail(email);
            usuario.setFirstname(firstName);
            usuario.setLastname(lastName);
            usuario.setTelefono(tel);
            usuario.setGenero(gender);
            usuario.setBDay(bday);
            usuario.setClave(clave);
            String res = fbUsuarioFacade.actUser(new FbCompania(BigDecimal.ZERO), usuario, "U");
            
            if (res.equals("0")) {
                validationBean.lanzarMensaje("info", "modUserSuccess", "blank");
                System.out.println("Exito!");
            }else if(res.equals("-1")){
                validationBean.lanzarMensaje("warn", "modUserFailRepeat", "blank");
             System.out.println("Repetido!");
            }else if(res.equals("-2")){
                validationBean.lanzarMensaje("error", "unexpectedError", "blank");
            System.out.println("Error!");
            }
        }
    }
    
    public boolean valEditOwnUser(){
    int c = 0;
    boolean flag = false;
    
    if (!(validationBean.validarCampoVacio(this.email, "error", "valErr", "reqEmail")
                && validationBean.validarEmail(this.email, "error", "valErr", "reqEmail")
                && validationBean.validarLongitudCampo(this.email, 8, 80, "error", "valErr", "reqEmail"))) {
            c++;
        }
    
     if (!(validationBean.validarCampoVacio(this.firstName, "error", "valErr", "reqFname")
                && validationBean.validarSoloLetras(this.firstName, "error", "valErr", "reqFname")
                && validationBean.validarLongitudCampo(this.firstName, 4, 50, "error", "valErr", "reqFname"))) {
            c++;
        }

        if (!(validationBean.validarCampoVacio(this.lastName, "error", "valErr", "reqLname")
                && validationBean.validarSoloLetras(this.lastName, "error", "valErr", "reqLname")
                && validationBean.validarLongitudCampo(this.lastName, 4, 50, "error", "valErr", "reqLname"))) {
            c++;
        }
        
         if (!(validationBean.validarCampoVacio(this.clave, "error", "valErr", "reqPass")
                && validationBean.validarLongitudCampo(this.clave, 5, 10, "error", "valErr", "reqPass"))) {
            c++;
        } else if (!this.clave.equals(this.rclave)) {
            c++;
            validationBean.lanzarMensaje("warn", "valErr", "reqRPass");
        }
         
         if (!(this.day.isEmpty() && this.month.isEmpty())) {
            this.bday = this.day + "/"+this.month+"/"+this.year;
             if (!validationBean.validarFecha(this.bday, "error", "valErr", "reqDate")) {
                 c++;
             }
        }
         
    
    if (c == 0) {
            flag = true;
        }
    return flag;
    }
    
    
    public void initCrud(){
        this.pxuList = this.pxuFacade.getUserbyCia(this.userData.getCurrentCia().getIdCia().toString());
    //usuarioL = this.fbUsuarioFacade.getUserbyCia(this.userData.getCurrentCia().getIdCia().toString());
    // usuarioL = this.fbUsuarioFacade.getUserbyCia("1");
    }
 
     public void getList(){
         usuarioPrueba = new ArrayList<>();
         int i;
         for (i = 0; i < 2; i++) {
             FbUsuario user = new FbUsuario();
             user.setFirstname("Guadalupe");
             user.setTelefono("77455622");
             user.setEmail("lupix.90@gmail.com");
             usuarioPrueba.add(user);
            
        }
    
    
     }  
}
