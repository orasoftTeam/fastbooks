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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

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
    @Inject
    UserData userData;
    
    
    private @Getter @Setter List<FbCustomer> custL = new ArrayList<>();
    
    private @Getter @Setter String firstName;
    private @Getter @Setter String phone;
    private @Getter @Setter String OpenBalance;
    private @Getter @Setter String title;
    private @Getter @Setter String midName;
    private @Getter @Setter String lastName;
    private @Getter @Setter String email;
    private @Getter @Setter String street;
    private @Getter @Setter String city;
    private @Getter @Setter String state;
    private @Getter @Setter String country;
    private @Getter @Setter String postalCode;
    private @Getter @Setter String suffix;
    private @Getter @Setter String company;
    private @Getter @Setter String fax;
    private @Getter @Setter String mobile;
    private @Getter @Setter String displayName;
    private @Getter @Setter String website;
     private @Getter @Setter String streetS;
    private @Getter @Setter String cityS;
    private @Getter @Setter String stateS;
    private @Getter @Setter String countryS;
    private @Getter @Setter String postalCodeS;
    private @Getter @Setter String attach;
    private @Getter @Setter String note;
    
    private @Getter @Setter FbCustomer cust;
  

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
        //getCustomerList();
        
    }
    
    //Instanciando objeto para prepararlo para que reciba la informacion
    public void newCustomer(){
        customer = new FbCustomer();
    }
    
    
    //Adding a new customer
     public void registerC() {
         System.out.println("ingresando a tomar la misma direccion y agregando valores");
        if(sameSHA){
                    customer.setStreetS(customer.getStreet());
                    customer.setCityS(customer.getCity());
                    customer.setEstateS(customer.getEstate());
                    customer.setPostalCodeS(customer.getPostalCode());
                    customer.setCountryS(customer.getCountry());
                  
                    FbCompania com = new FbCompania();
                    com.setIdCia(BigDecimal.ZERO);
                    customer.setIdCia(new FbCompania(userData.getCurrentCia().getIdCia()));
                    customer.setIdCust(new BigDecimal("0"));
                    customer.setNote("Prueba");
                    customer.setAtachment("Prueba2");
                    String res;
                    res = custF.actCustomer(customer,  "A");
                    System.out.println("Resultado esperado en el controlador: " + res);
                    customer = new FbCustomer(); //limpiando formulario de add customer..

                    System.out.println("obteniendo valores de la shipping same as billing" + customer);
                    
                }
         /*
            System.out.println("Ingresando al registro de nuevo customer");
            FbCompania com = new FbCompania();
            com.setIdCia(BigDecimal.ZERO);
            customer.setIdCia(new FbCompania(userData.getCurrentCia().getIdCia()));
            customer.setIdCust(new BigDecimal("0"));
            //customer.setIdCia(com);
            String res = custF.actCustomer(customer,  "A");
            System.out.println("Resultado esperado en el controlador: " + res);
       */ 
    }
     
   
      public void init(){ 
          System.out.println("Obteniendo lista clientes controller");
          try {
              custL = custF.getCustomer(this.userData.getCurrentCia().getIdCia().toString());
        if (!this.userData.getUses().equals("0")) {
            this.validationBean.lanzarMensaje("info", this.userData.getUses(), "blank");
            this.userData.setUses("0");
        }
              
          } catch (Exception e) {
              System.out.println("error obteniendo lista");
              e.printStackTrace();
          }
        
        }
     
      //Updating customer
      public void editCustomer(FbCustomer cu, String op){
       System.out.println("updating customer" + cu);
        this.cust = cu;
        if (op.equals("U")) {
            this.title = cu.getTitle();
            this.firstName = cu.getFirstname();
            this.midName =  cu.getMiddlename();
            this.lastName = cu.getLastname();
            this.suffix =  cu.getSuffixx();
            this.company = cu.getCompany();
            this.displayName = cu.getDisplayName();
            this.email = cu.getEmail();
            this.phone = cu.getPhone();
            this.mobile = cu.getMobile();
            this.fax = cu.getFax();
            this.website = cu.getWebside();
            this.street = cu.getStreet();
            this.state = cu.getEstate();
            this.postalCode = cu.getPostalCode();
            this.country = cu.getCountry();
            this.streetS = cu.getStreetS();
            this.stateS = cu.getEstateS();
            this.postalCodeS = cu.getPostalCodeS();
            this.countryS = cu.getCountryS();
            this.attach = cu.getAtachment();
            this.note = cu.getNote();
            this.validationBean.ejecutarJavascript("$('.modalPseudoClass2').modal();");
        }else{
            this.validationBean.ejecutarJavascript("$('.modalPseudoClass3').modal();");
        }
          
      }
      
      
      public void edit() {
            String res = "";
            System.out.println("getting cust" + cust);
            try {
                cust.setIdCia(new FbCompania(userData.getCurrentCia().getIdCia()));
                cust.setIdCust(new BigDecimal("0"));
                cust.setTitle(title);
                cust.setFirstname(firstName);
                cust.setMiddlename(midName);
                cust.setLastname(lastName);
                cust.setSuffixx(suffix);
                cust.setCompany(company);
                cust.setDisplayName(displayName);
                cust.setEmail(email);
                cust.setPhone(phone);
                cust.setMobile(mobile);
                cust.setFax(fax);
                cust.setWebside(website);
                cust.setStreet(street);
                cust.setEstate(state);
                cust.setCity(city);
                cust.setPostalCode(postalCode);
                cust.setCountry(country);
                cust.setCityS(cityS);
                cust.setEstateS(stateS);
                cust.setCityS(cityS);
                cust.setPostalCodeS(postalCodeS);
                cust.setCountryS(countryS);
                cust.setAtachment("prueba");
                cust.setNote("prueba2");
                res = custF.actCustomer(cust, "U");
                System.out.println("resultado update customer" + res);
                if (res.equals("0")) {
                    validationBean.lanzarMensaje("info", "catEditSuccess", "blank");
                } else if (res.equals("-1")) {
                    validationBean.lanzarMensaje("error", "catRepeatFail", "blank");
                } else if (res.equals("-2")) {
                    validationBean.lanzarMensaje("error", "unexpectedError", "blank");
                }
                //limpiar();
            } catch (Exception e) {
                System.out.println("com.fastbooks.managedbeans.CategoryController.add()");
                e.printStackTrace();
                res = "-2";
            }

        
    }
      
      
      
        //Deleting customer
          public void deleteCustomer() {
              System.out.println("Ingresando a eliminar registros");
        
            String res = "";
            try {
                res = custF.actCustomer(cust, "D");
                System.out.println("Resultado controlador" + res);
                if (res.equals("0")) {
                    validationBean.lanzarMensaje("info", "catDelSuccess", "blank");
                } else if (res.equals("-1")) {
                    validationBean.lanzarMensaje("error", "catRepeatFail", "blank");
                } else if (res.equals("-2")) {
                    validationBean.lanzarMensaje("error", "unexpectedError", "blank");
                }
               // limpiar();
            } catch (Exception e) {
                System.out.println("com.fastbooks.managedbeans.CategoryController.add()");
                e.printStackTrace();
                res = "-2";
            }

        
    }

      
     
    public boolean regVal(){
        boolean flag = false;
        int c = 0;
       /* 
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
         */ 
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
    
    
    
    
    
    
 

