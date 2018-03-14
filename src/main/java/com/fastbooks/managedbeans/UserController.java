/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbUsuarioFacade;
import com.fastbooks.modelo.FbUsuario;
import com.fastbooks.util.ValidationBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
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
    @Inject
    private UserData userData;
    

    FbUsuario usuario = new FbUsuario();
    
    List<FbUsuario> usuarioL;
    
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
    
    
    /**
     * Creates a new instance of UserController
     */
    public UserController() {
        
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
    }
    
    /*@PostConstruct
    public void init() {
    
    setValues();
    }*/
}
