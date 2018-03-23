/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbCustomer;
import com.fastbooks.util.ValidationBean;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author DELL
 */
@Named(value = "customerController")
@ViewScoped
public class CustomerController implements Serializable{
    
    
    @EJB
    ValidationBean validationBean;
    
    private String title;
    private FbCustomer customer;
    private String firstName;
    private String midName;
    private String lastName;
    private String suffix;
    private String email;
    private String company;
    private String phone;
    private String mobile;
    private String fax;
    private String displayName;
    private String website;
    private String idDireccion;
    private String idDirShipp;
    private boolean sameSHA;
    private String address;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(String idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getIdDirShipp() {
        return idDirShipp;
    }

    public void setIdDirShipp(String idDirShipp) {
        this.idDirShipp = idDirShipp;
    }

    public boolean isSameSHA() {
        return sameSHA;
    }

    public void setSameSHA(boolean sameSHA) {
        this.sameSHA = sameSHA;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    

    /**
     * Creates a new instance of CustomerController
     */
    public CustomerController() {
    }
    
    
     public void registerC() {
       
        if (regVal()) {
            FbCustomer cust= new FbCustomer();
            cust.setTitle(title);
            cust.setFirstname(firstName);
            cust.setMiddlename(midName);
            cust.setLastname(lastName);
            cust.setSuffixx(suffix);
            cust.setCompany(company);
            cust.setDisplayName(displayName);
            cust.setEmail(email);
            cust.setPhone(phone);
            cust.setMobile("");
            cust.setFax("");
            cust.setWebside(website);
            cust.setStreet(address);
            cust.setCity(address);
            cust.setEstate(address);
            cust.setPostalCode(address);
            cust.setCountry(address);
            FbCompania com = new FbCompania(); 
            com.setIdCia(BigDecimal.ZERO);
            String res = ""; // comFacade.actCompany(com, user, "A");
            System.out.println("Resultado controller: " + res);

            if (res.equals("0")) {
               
            } else if (res.equals("-1")) {
                //lanzar mensaje de registro repetido
                validationBean.lanzarMensaje("warn", "valErr", "repeatedEmail");
            } else if (res.equals("-2")) {
                //lanzar mensaje de ocurrio error inesperado
                validationBean.lanzarMensaje("error", "valErr", "unexpectedError");
            }

            System.out.println("validation good");
        } else {
            System.out.println("validation fail");
        }
    }
    
    
    
    public boolean regVal(){
        boolean flag = false;
        int c = 0;
        
        if(!(validationBean.validarCampoVacio(this.title, "error", "valErr", "reqTitle")
                && validationBean.validarSoloLetras(this.title, "error", "valErr", "reqTitle")
                && validationBean.validarLongitudCampo(this.title, 4, 50, "error", "valErr", "reqTitle"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(this.firstName, "error", "valErr", "reqFName")
                && validationBean.validarSoloLetras(this.firstName, "error", "valErr", "reqFName")
                && validationBean.validarLongitudCampo(this.firstName, 4, 50, "error", "valErr", "reqFName"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(this.midName, "error", "valErr", "reqMName")
                && validationBean.validarSoloLetras(this.midName, "error", "valErr", "reqMName")
                && validationBean.validarLongitudCampo(this.midName, 4, 50, "error", "valErr", "reqMName"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(this.lastName, "error", "valErr", "reqLName")
                && validationBean.validarSoloLetras(this.lastName, "error", "valErr", "reqLName")
                && validationBean.validarLongitudCampo(this.lastName, 4, 50, "error", "valErr", "reqLName"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(this.suffix, "error", "valErr", "reqSuffix")
                && validationBean.validarSoloLetras(this.title, "error", "valErr", "reqSuffix")
                && validationBean.validarLongitudCampo(this.title, 4, 50, "error", "valErr", "reqSuffix"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(this.email, "error", "valErr", "reqEmail")
                && validationBean.validarEmail(this.email, "error", "valErr", "reqEmail")
                && validationBean.validarLongitudCampo(this.email, 4, 50, "error", "valErr", "reqEmail"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(this.company, "error", "valErr", "reqCompany")
                && validationBean.validarSoloLetras(this.company, "error", "valErr", "reqCompany")
                && validationBean.validarLongitudCampo(this.company, 4, 50, "error", "valErr", "reqCompany"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(this.phone, "error", "valErr", "reqPhone")
                && validationBean.validarSoloNumeros(this.phone, "error", "valErr", "reqPhone")
                && validationBean.validarLongitudCampo(this.phone, 4, 50, "error", "valErr", "reqPhone"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(this.mobile, "error", "valErr", "reqMobile")
                && validationBean.validarSoloNumeros(this.mobile, "error", "valErr", "reqMobile")
                && validationBean.validarLongitudCampo(this.mobile, 4, 50, "error", "valErr", "reqMobile"))){
                c++;
            }
         if(!(validationBean.validarCampoVacio(this.fax, "error", "valErr", "reqFax")
                && validationBean.validarSoloNumeros(this.fax, "error", "valErr", "reqFax")
                && validationBean.validarLongitudCampo(this.fax, 4, 50, "error", "valErr", "reqFax"))){
                c++;
            }
         
          if(!(validationBean.validarCampoVacio(this.displayName, "error", "valErr", "reqDisName")
                && validationBean.validarSoloLetras(this.displayName, "error", "valErr", "reqDisName")
                && validationBean.validarLongitudCampo(this.displayName, 4, 50, "error", "valErr", "reqDisName"))){
                c++;
            }
          
          if(!this.sameSHA){
             if (!(validationBean.validarCampoVacio(this.address, "error", "valErr", "reqAddress")
                    && validationBean.validarLongitudCampo(this.address, 4, 50, "error", "valErr", "reqAddress"))) {
                c++;
            }    
                
             }else{
               this.address = this.street.trim() + ' ' + this.state.trim() + ' ' + this.city.trim() + ' ' + this.postalCode.trim() + ' ' + this.country.trim();
          }
          
          
           if (c == 0) {
            flag = true;
        }
           this.sameSHA = false;
        return flag;
    }
    
    
    public void registerCustomer(){
        
        if(regVal()){
            
        this.customer.setTitle(title);
        this.customer.setFirstname(firstName);
        this.customer.setMiddlename(midName);
        this.customer.setLastname(lastName);
        this.customer.setSuffixx(suffix);
        this.customer.setEmail(email);
        this.customer.setCompany(company);
        this.customer.setPhone(phone);
        this.customer.setMobile(mobile);
        this.customer.setFax(fax);
        this.customer.setDisplayName(displayName);
        this.customer.setWebside(website);
        String res = ""; //= comFacade.actCustomer(customer);
         if (res.equals("0")) {
                validationBean.lanzarMensaje("info", "modComSuccess", "blank");
                System.out.println("Exito!");
            }else if(res.equals("-1")){
                validationBean.lanzarMensaje("warn", "modComFailRepeat", "blank");
             System.out.println("Repetido!");
            }else if(res.equals("-2")){
                validationBean.lanzarMensaje("error", "unexpectedError", "blank");
            System.out.println("Error!");
            }
        }else{
        System.out.println("validation fail");
        }
    }
 
}
