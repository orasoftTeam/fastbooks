/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbCompaniaFacade;
import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbUsuario;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author dell
 */
@Named(value = "companyController")
@ViewScoped
public class CompanyController implements Serializable{
    
    
    @EJB
    FbCompaniaFacade comFacade;
    
    private boolean naturalPerson;
    private String companyComName;
    private String fName;
    private String lName;
    private String pass;
    private String rPass;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isNaturalPerson() {
        return naturalPerson;
    }

    public void setNaturalPerson(boolean naturalPerson) {
        this.naturalPerson = naturalPerson;
    }

    public String getCompanyComName() {
        return companyComName;
    }

    public void setCompanyComName(String companyComName) {
        this.companyComName = companyComName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getrPass() {
        return rPass;
    }

    public void setrPass(String rPass) {
        this.rPass = rPass;
    }
    
    
    
    /**
     * Creates a new instance of CompanyController
     */
    public CompanyController() {
    }
    
    
    public void register(){
        System.out.println("com.fastbooks.managedbeans.CompanyController.register()");
        System.out.println(this.companyComName);
        System.out.println(this.email);
        System.out.println(this.fName);
        System.out.println(this.lName);
        System.out.println(this.naturalPerson);
        System.out.println(this.pass);
        System.out.println(this.rPass);
        
        FbCompania com =  new FbCompania();
        com.setEmail(email);
        com.setNomCom(companyComName);
        com.setNomLeg(companyComName);
        com.setGiro("");
        com.setTelefono("");
        com.setLogo("");
        com.setPerNat(1);
        com.setWebsite("");
        FbUsuario user = new FbUsuario();
        user.setFirstname(fName);
        user.setLastname(lName);
        user.setClave(pass);
        
        String res = comFacade.actCompany(com, user, "A");
        System.out.println("Resultado controller: " + res);
    }
    
    
    public void regVal(){
    
    
    
    }
    
}
