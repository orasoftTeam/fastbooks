/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbCustomerFacade;
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
    
    //1.Declarar modelo
    //2.En la vista mapear los campos q se usaran en el modelo
    //3.crear o instanciar el objeto y  Llenar el objeto
    //4.Validar los datos antes de mandar a almacenar
    //5.mandar el objeto al facade para que sea almacenado.
    @EJB
    ValidationBean validationBean;
    @EJB 
    FbCustomerFacade custF;
    private FbCustomer customer; //declarar modelo
    private boolean sameSHA;
    
  

    public FbCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(FbCustomer customer) {
        this.customer = customer;
    }

    public boolean isSameSHA() {
        return sameSHA;
    }

    public void setSameSHA(boolean sameSHA) {
        this.sameSHA = sameSHA;
    }
    
    
    
    
    

    /**
     * Creates a new instance of CustomerController
     */
    public CustomerController() {
        customer = new FbCustomer();
    }
    
    //Instanciando objeto para prepararlo para que reciba la informacion
    public void newCustomer(){
        customer = new FbCustomer();
    }
    
    
    
     public void registerC() {
        if(sameSHA){
                    customer.setStreetS(customer.getStreet());
                    customer.setCityS(customer.getCity());
                    customer.setEstateS(customer.getEstate());
                    customer.setPostalCodeS(customer.getPostalCode());
                    customer.setCountryS(customer.getCountry());
                }
         System.out.println("obteniendo valores" + customer);
        if (regVal()) {
            /*
                if(sameSHA){
                    customer.setStreetS(customer.getStreet());
                    customer.setCityS(customer.getCity());
                    customer.setEstateS(customer.getEstate());
                    customer.setPostalCodeS(customer.getPostalCode());
                    customer.setCountryS(customer.getCountry());
                }
            */
            FbCompania com = new FbCompania(); 
            com.setIdCia(BigDecimal.ZERO);
            customer.setIdCia(com);
            String res = custF.actCustomer(customer, "A");
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
        
        if(!(validationBean.validarCampoVacio(customer.getTitle(), "error", "valErr", "reqTitle")
                && validationBean.validarSoloLetras(customer.getTitle(), "error", "valErr", "reqTitle")
                && validationBean.validarLongitudCampo(customer.getTitle(), 4, 50, "error", "valErr", "reqTitle"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(customer.getFirstname(), "error", "valErr", "reqFName")
                && validationBean.validarSoloLetras(customer.getFirstname(), "error", "valErr", "reqFName")
                && validationBean.validarLongitudCampo(customer.getFirstname(), 4, 50, "error", "valErr", "reqFName"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(customer.getMiddlename(), "error", "valErr", "reqMName")
                && validationBean.validarSoloLetras(customer.getMiddlename(), "error", "valErr", "reqMName")
                && validationBean.validarLongitudCampo(customer.getMiddlename(), 4, 50, "error", "valErr", "reqMName"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(customer.getLastname(), "error", "valErr", "reqLName")
                && validationBean.validarSoloLetras(customer.getLastname(), "error", "valErr", "reqLName")
                && validationBean.validarLongitudCampo(customer.getLastname(), 4, 50, "error", "valErr", "reqLName"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(customer.getSuffixx(), "error", "valErr", "reqSuffix")
                && validationBean.validarSoloLetras(customer.getSuffixx(), "error", "valErr", "reqSuffix")
                && validationBean.validarLongitudCampo(customer.getSuffixx(), 4, 50, "error", "valErr", "reqSuffix"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(customer.getEmail(), "error", "valErr", "reqEmail")
                && validationBean.validarEmail(customer.getEmail(), "error", "valErr", "reqEmail")
                && validationBean.validarLongitudCampo(customer.getEmail(), 4, 50, "error", "valErr", "reqEmail"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(customer.getCompany(), "error", "valErr", "reqCompany")
                && validationBean.validarSoloLetras(customer.getCompany(), "error", "valErr", "reqCompany")
                && validationBean.validarLongitudCampo(customer.getCompany(), 4, 50, "error", "valErr", "reqCompany"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(customer.getPhone(), "error", "valErr", "reqPhone")
                && validationBean.validarSoloNumeros(customer.getPhone(), "error", "valErr", "reqPhone")
                && validationBean.validarLongitudCampo(customer.getPhone(), 4, 50, "error", "valErr", "reqPhone"))){
                c++;
            }
        if(!(validationBean.validarCampoVacio(customer.getMobile(), "error", "valErr", "reqMobile")
                && validationBean.validarSoloNumeros(customer.getMobile(), "error", "valErr", "reqMobile")
                && validationBean.validarLongitudCampo(customer.getMobile(), 4, 50, "error", "valErr", "reqMobile"))){
                c++;
            }
         if(!(validationBean.validarCampoVacio(customer.getFax(), "error", "valErr", "reqFax")
                && validationBean.validarSoloNumeros(customer.getFax(), "error", "valErr", "reqFax")
                && validationBean.validarLongitudCampo(customer.getFax(), 4, 50, "error", "valErr", "reqFax"))){
                c++;
            }
         
          if(!(validationBean.validarCampoVacio(customer.getDisplayName(), "error", "valErr", "reqDisName")
                && validationBean.validarSoloLetras(customer.getDisplayName(), "error", "valErr", "reqDisName")
                && validationBean.validarLongitudCampo(customer.getDisplayName(), 4, 50, "error", "valErr", "reqDisName"))){
                c++;
            }
          
           if (c == 0) {
            flag = true;
        }
        return flag;
    }
    
  
    //crear un objeto de PF para capturar el evento del checkbox
    //luego se crea metodo para capturar la info o evaluar datos (ajax)
    //
    
    public void poblarSA(){
        boolean validarS = true;
            
        if(customer.getStreet()==null || customer.getStreet().isEmpty()){
            validarS = false;
        }
        if(customer.getCity()==null || customer.getCity().isEmpty()){
            validarS = false;
        }
        if(customer.getEstate()==null || customer.getEstate().isEmpty()){
            validarS = false;
        }
        if(customer.getPostalCode()==null || customer.getPostalCode().isEmpty()){
            validarS = false;
        }
        if(customer.getCountry()==null || customer.getCountry().isEmpty()){
            validarS = false;
        }
        
        if(!validarS){
            validationBean.lanzarMensaje("error", "valErr", "reqDisName");
        return;
        }
        
        customer.setStreetS(customer.getStreet());
        customer.setCityS(customer.getCity());
        customer.setEstateS(customer.getEstate());
        customer.setPostalCodeS(customer.getPostalCode());
        customer.setCountryS(customer.getCountry());
      
    }
    
 
}
