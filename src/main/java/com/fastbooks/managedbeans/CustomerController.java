/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbCustomerFacade;
import com.fastbooks.modelo.BillCustomer;
import com.fastbooks.modelo.DeliveryMethod;
import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbCustomer;
import com.fastbooks.modelo.PaymentMethod;
import com.fastbooks.modelo.Terms;
import com.fastbooks.util.GlobalParameters;
import com.fastbooks.util.ValidationBean;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author DELL
 */
@Named(value = "customerController")
@ViewScoped
public class CustomerController implements Serializable {

    //1.Declarar modelo
    //2.En la vista mapear los campos q se usaran en el modelo
    //3.crear o instanciar el objeto y  Llenar el objeto
    //4.Validar los datos antes de mandar a almacenar
    //5.mandar el objeto al facade para que sea almacenado.
    @EJB
    ValidationBean validationBean;
    @EJB
    FbCustomerFacade custF;
    private FbCustomer customer = new FbCustomer(); //declarar modelo
    private boolean sameSHA = true;
    @Inject
    UserData userData;

    private @Getter
    @Setter
    List<FbCustomer> custL = new ArrayList<>();

    private @Getter
    @Setter
    FbCustomer cust;

    private GlobalParameters gp = new GlobalParameters();
    private @Getter
    @Setter
    String appPath = gp.getAppPath();//System.getProperty("user.dir");
    private final String destination = appPath + File.separator + "logo" + File.separator;
    private @Getter
    @Setter
    UploadedFile archivo;
    private @Getter
    @Setter
    String nameFileFinal;
    private @Getter
    @Setter
    String msgFile;
    private @Getter
    @Setter
    String logourl;

    private @Getter
    @Setter
    boolean selectAllCustomers;

    private @Getter
    @Setter
    List<Terms> tList = new ArrayList<>();

    private @Getter
    @Setter
    List<PaymentMethod> pList = new ArrayList<>();

    private @Getter
    @Setter
    List<DeliveryMethod> dList = new ArrayList<>();
    
    private @Getter
    @Setter
    List<BillCustomer> bList = new ArrayList<>();

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
    public void newCustomer() {
        customer = new FbCustomer();
    }

    //Adding a new customer
    public void registerC() {
        if (valCamposInInvoice()) {
            if (sameSHA) {
                System.out.println("gettin sameSHA" + sameSHA);
                customer.setStreetS(customer.getStreet());
                customer.setCityS(customer.getCity());
                customer.setEstateS(customer.getEstate());
                customer.setPostalCodeS(customer.getPostalCode());
                customer.setCountryS(customer.getCountry());
            }

            FbCompania com = new FbCompania();
            com.setIdCia(BigDecimal.ZERO);
            customer.setIdCia(new FbCompania(userData.getCurrentCia().getIdCia()));
            customer.setIdCust(new BigDecimal("0"));
            customer.setAtachment(this.logourl);
            System.out.println("getting customer first name" + customer.getFirstname());
            String res;
            if (customer.getDisplayName().isEmpty()) {
                customer.setDisplayName(customer.getFirstname() + " " + customer.getLastname());
            }
            res = custF.actCustomer(customer, "A");
            if (res.equals("0")) {
                
                validationBean.ejecutarJavascript("PF('dlg2').hide();");
                custL = custF.getCustomer(this.userData.getCurrentCia().getIdCia().toString());
                validationBean.updateComponent("customerForm");
                validationBean.lanzarMensaje("info", "custAdded", "blank");
            } else if (res.equals("-1")) {
                validationBean.lanzarMensaje("error", "customerRepeatFail", "blank");
            } else if (res.equals("-2")) {
                validationBean.lanzarMensaje("error", "unexpectedError", "blank");
            }
            System.out.println("obteniendo valores de la shipping same as billing" + customer);
            customer = new FbCustomer(); //limpiando formulario de add customer..
        }

    }

    @PostConstruct
    public void init() {
        try {
            System.out.println("INIT CUSTOMERS!!!!");
            custL = custF.getCustomer(this.userData.getCurrentCia().getIdCia().toString());
            if (!this.userData.getUses().equals("0")) {
                this.validationBean.lanzarMensaje("info", this.userData.getUses(), "blank");
                this.userData.setUses("0");
            }
            System.out.println("Ingresando a terms customer");
            if (this.tList.isEmpty()) {
                this.tList.add(new Terms("1", "30", "Credits at 30 days"));
                this.tList.add(new Terms("2", "0", "Due on receipt"));
                this.tList.add(new Terms("3", "15", "Net 15"));
                this.tList.add(new Terms("4", "30", "Net 30"));
                this.tList.add(new Terms("5", "60", "Net 60"));
            }

            System.out.println("Ingresando a payment method customer");
            if (this.pList.isEmpty()) {
                this.pList.add(new PaymentMethod("1", "C", "Cash"));
                this.pList.add(new PaymentMethod("2", "CQ", "Cheque"));
                this.pList.add(new PaymentMethod("3", "CC", "Credit card"));
                this.pList.add(new PaymentMethod("4", "DD", "Direct debit"));
            }

            System.out.println("Ingresando a delivery method customer");
            if (this.dList.isEmpty()) {
                this.dList.add(new DeliveryMethod("1", "NN", "None"));
                this.dList.add(new DeliveryMethod("2", "PL", "Print Later"));
                this.dList.add(new DeliveryMethod("3", "SL", "Send Later"));
            }
            if (this.bList.isEmpty()) {
                this.bList.add(new BillCustomer("1", "BC", "Bill this customer"));
                this.bList.add(new BillCustomer("2", "BP", "Bill with parent"));
            }

        } catch (Exception e) {
            System.out.println("error obteniendo lista");
            e.printStackTrace();
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
                validationBean.lanzarMensaje("info", "custDelSuccess", "blank");
            } else if (res.equals("-1")) {
                validationBean.lanzarMensaje("error", "customerRepeatFail", "blank");
            } else if (res.equals("-2")) {
                validationBean.lanzarMensaje("error", "unexpectedError", "blank");
            }
            cust = new FbCustomer(); //limpiando formulario
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.CustomerController.deleteCustomer()");
            e.printStackTrace();
            res = "-2";
        }

    }

    public boolean valCampos() {

        boolean flag = false;
        int c = 0;
        /*
        if (!(validationBean.validarCampoVacio(this.customer.getFirstname(), "warn", "valErr", "reqFname")
                && validationBean.validarSoloLetras(this.customer.getFirstname(), "warn", "valErr", "reqFname"))) {
            c++;
        }
         */
        if (!(validationBean.validarCampoVacio(this.customer.getCompany(), "warn", "valErr", "reqComp")
                && validationBean.validarSoloLetras(this.customer.getCompany(), "warn", "valErr", "reqComp"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.customer.getEmail(), "warn", "valErr", "reqEmail")
                && validationBean.validarEmail(this.customer.getEmail(), "warn", "valErr", "reqEmail"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.customer.getPhone(), "warn", "valErr", "reqPhone")
                && validationBean.validarSoloNumeros(this.customer.getPhone(), "warn", "valErr", "reqPhone"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.customer.getWebside(), "warn", "valErr", "reqWebsite"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.customer.getStreet(), "warn", "valErr", "reqStreet"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.customer.getCity(), "warn", "valErr", "reqCity")
                && validationBean.validarSoloLetras(this.customer.getCity(), "warn", "valErr", "reqCity"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.customer.getEstate(), "warn", "valErr", "reqState")
                && validationBean.validarSoloLetras(this.customer.getEstate(), "warn", "valErr", "reqState"))) {
        }
        if (!(validationBean.validarCampoVacio(this.customer.getPostalCode(), "warn", "valErr", "reqPostalC")
                && validationBean.validarSoloNumeros(this.customer.getPostalCode(), "warn", "valErr", "reqPostalC"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.customer.getCountry(), "warn", "valErr", "reqCountry")
                && validationBean.validarSoloLetras(this.customer.getCountry(), "warn", "valErr", "reqCountry"))) {
            c++;
        }

        if (c == 0) {
            flag = true;
        }
        return flag;
    }

    public void handleFileUpload(FileUploadEvent event) {
        String name;
        try {
            if (archivo == null) {
                archivo = event.getFile();
                //BufferedImage img = ImageIO.read(archivo.getInputstream());
                name = validationBean.generarRandom(archivo.getFileName());
                File file = new File(destination);
                file.mkdir();
                validationBean.copyFile(name, destination, archivo.getInputstream());

                this.logourl = "/logo/" + name;
                this.msgFile = validationBean.getMsgBundle("lblFileSuccess");

                validationBean.updateComponent("comForm:msgFile");
                System.out.println(this.logourl);
                validationBean.updateComponent("comForm:showLogo");
                this.nameFileFinal = name;
            } else {
                archivo = event.getFile();
                if (validationBean.deleteFile(destination + nameFileFinal)) {
                    name = validationBean.generarRandom(archivo.getFileName());
                    validationBean.copyFile(name, destination, archivo.getInputstream());
                    this.logourl = "/logo/" + name;
                    this.msgFile = validationBean.getMsgBundle("lblFileSuccess");
                    validationBean.updateComponent("comForm:msgFile");
                    System.out.println(this.logourl);
                    validationBean.updateComponent("comForm:showLogo");
                    nameFileFinal = name;
                }

            }
        } catch (Exception e) {
            msgFile = validationBean.getMsgBundle("lblFileUploadError");
            if (archivo != null) {
                if (validationBean.deleteFile("/logo/" + archivo.getFileName())) {
                    archivo = null;
                }
            }
            this.logourl = "";
            validationBean.updateComponent("comForm:msgFile");

            validationBean.updateComponent("comForm:showLogo");
            e.printStackTrace();
        }

    }

    public void limpiar() {
        sameSHA = false;
        customer = new FbCustomer();
        selectAllCustomers = false;
    }
    
    
        //Adding a new customer in invoice form
    public void registerCustomerInInvoice() {
        if (valCamposInInvoice()) {
            if (sameSHA) {
                System.out.println("gettin sameSHA" + sameSHA);
                customer.setStreetS(customer.getStreet());
                customer.setCityS(customer.getCity());
                customer.setEstateS(customer.getEstate());
                customer.setPostalCodeS(customer.getPostalCode());
                customer.setCountryS(customer.getCountry());
            }
            
            if (customer.getStreetS().isEmpty()) {
                customer.setStreetS(customer.getStreet());
            }
            
            if (customer.getCityS().isEmpty()) {
                customer.setCityS(customer.getCity());
            }
            
            if (customer.getEstateS().isEmpty()) {
                customer.setEstateS(customer.getEstate());
            }
            
            if (customer.getCountryS().isEmpty()) {
                customer.setPostalCodeS(customer.getPostalCode());
            }
            
            if (customer.getPostalCodeS().isEmpty()) {
                customer.setCountryS(customer.getCountry());
            }

            FbCompania com = new FbCompania();
            com.setIdCia(BigDecimal.ZERO);
            customer.setIdCia(new FbCompania(userData.getCurrentCia().getIdCia()));
            customer.setIdCust(new BigDecimal("0"));
            customer.setAtachment(this.logourl);
            System.out.println("getting customer first name" + customer.getFirstname());
            String res;
            if (customer.getDisplayName().isEmpty()) {
                customer.setDisplayName(customer.getFirstname() + " " + customer.getLastname());
            }
            res = custF.actCustomer(customer, "A");
            if (res.equals("0")) {
                //validationBean.lanzarMensaje("info", "custAdded", "blank");
                this.validationBean.ejecutarJavascript("PF('dlg2').hide();");
                this.userData.setFormInCustId(customer.getEmail());
                this.validationBean.updateComponent("invoiceForm:custs");
            } else if (res.equals("-1")) {
                validationBean.lanzarMensaje("error", "customerRepeatFail", "blank");
            } else if (res.equals("-2")) {
                validationBean.lanzarMensaje("error", "unexpectedError", "blank");
            }
            System.out.println("obteniendo valores de la shipping same as billing" + customer);
            customer = new FbCustomer(); //limpiando formulario de add customer..
        }

    }
    
    
        public void registerCustomerInPayment() {
        if (valCamposInInvoice()) {
            if (sameSHA) {
                System.out.println("gettin sameSHA" + sameSHA);
                customer.setStreetS(customer.getStreet());
                customer.setCityS(customer.getCity());
                customer.setEstateS(customer.getEstate());
                customer.setPostalCodeS(customer.getPostalCode());
                customer.setCountryS(customer.getCountry());
            }
            
            if (customer.getStreetS().isEmpty()) {
                customer.setStreetS(customer.getStreet());
            }
            
            if (customer.getCityS().isEmpty()) {
                customer.setCityS(customer.getCity());
            }
            
            if (customer.getEstateS().isEmpty()) {
                customer.setEstateS(customer.getEstate());
            }
            
            if (customer.getCountryS().isEmpty()) {
                customer.setPostalCodeS(customer.getPostalCode());
            }
            
            if (customer.getPostalCodeS().isEmpty()) {
                customer.setCountryS(customer.getCountry());
            }

            FbCompania com = new FbCompania();
            com.setIdCia(BigDecimal.ZERO);
            customer.setIdCia(new FbCompania(userData.getCurrentCia().getIdCia()));
            customer.setIdCust(new BigDecimal("0"));
            customer.setAtachment(this.logourl);
            
            if (customer.getDisplayName().isEmpty()) {
                customer.setDisplayName(customer.getFirstname() + " " + customer.getLastname());
            }
            
            System.out.println("getting customer first name" + customer.getFirstname());
            String res;
            res = custF.actCustomer(customer, "A");
            if (res.equals("0")) {
                //validationBean.lanzarMensaje("info", "custAdded", "blank");
                this.validationBean.ejecutarJavascript("PF('dlg2').hide();");
                this.userData.setFormInCustId(customer.getEmail());
                this.validationBean.updateComponent("paymentForm:custs");
            } else if (res.equals("-1")) {
                validationBean.lanzarMensaje("error", "customerRepeatFail", "blank");
            } else if (res.equals("-2")) {
                validationBean.lanzarMensaje("error", "unexpectedError", "blank");
            }
            System.out.println("obteniendo valores de la shipping same as billing" + customer);
            customer = new FbCustomer(); //limpiando formulario de add customer..
        }

    }
        //Validar campos en invoice form
        public boolean valCamposInInvoice() {

        boolean flag = false;
        
        if (validationBean.validarCampoVacio(this.customer.getCompany(), "warn", "valErr", "reqComp")
               // && validationBean.validarSoloLetras(this.customer.getCompany(), "warn", "valErr", "reqComp")
                //&& validationBean.validarCampoVacio(this.customer.getEmail(), "warn", "valErr", "reqEmail")
                && validationBean.validarEmail(this.customer.getEmail(), "warn", "valErr", "reqEmail")
                && validationBean.validarCampoVacio(this.customer.getStreet(), "warn", "valErr", "reqStreet")
                && validationBean.validarCampoVacio(this.customer.getCity(), "warn", "valErr", "reqCity")
                && validationBean.validarSoloLetras(this.customer.getCity(), "warn", "valErr", "reqCity")
                && validationBean.validarCampoVacio(this.customer.getEstate(), "warn", "valErr", "reqState")
                && validationBean.validarSoloLetras(this.customer.getEstate(), "warn", "valErr", "reqState")
                && validationBean.validarCampoVacio(this.customer.getPostalCode(), "warn", "valErr", "reqPostalC")
                //&& validationBean.validarSoloNumeros(this.customer.getPostalCode(), "warn", "valErr", "reqPostalC")
                && validationBean.validarCampoVacio(this.customer.getCountry(), "warn", "valErr", "reqCountry")
                && validationBean.validarSoloLetras(this.customer.getCountry(), "warn", "valErr", "reqCountry")
                //&& validationBean.validarCampoVacio(this.customer.getDisplayName(), "warn", "valErr", "lblReqCustomerName")
                //&& validationBean.validarSoloLetras(this.customer.getDisplayName(), "warn", "valErr", "lblReqCustomerName")
                && validationBean.validarCampoVacio(this.customer.getFirstname(), "warn", "valErr", "lblReqCustomerName")
                && validationBean.validarSoloLetras(this.customer.getFirstname(), "warn", "valErr", "lblReqCustomerName")
                && validationBean.validarCampoVacio(this.customer.getLastname(), "warn", "valErr", "lblReqCustomerLastName")
                && validationBean.validarSoloLetras(this.customer.getLastname(), "warn", "valErr", "lblReqCustomerLastName")) {
            flag = true;
        }
       
        return flag;
    }
        
        
        public boolean renderOptionMaster(String option,FbCustomer cust){
        boolean flag = false;
        switch(option){
            case "REPAY":
                if (cust.getBalance().doubleValue() > 0) {
                    flag = true;
                }
                break;
            case "INAC":
                if (cust.getBalance().doubleValue() == 0) {
                    flag = true;
                }
                break;
        }
        
        return flag;
        }
        
        
        public void receivePayment(String idCust){
        
        this.validationBean.redirecionar("/view/sales/payments/paymentForm.xhtml?idc=" + idCust);
        }
        
        public void createInvoice(String type,String idCust){
        this.userData.setInvoiceTypeForm(type);
        this.validationBean.redirecionar("/view/sales/invoiceForm.xhtml?idc="+idCust);
        }
        
        
        public void onSelect(String idCust){
            System.out.println("idCust: " +idCust);
        }
        
        
      



}
