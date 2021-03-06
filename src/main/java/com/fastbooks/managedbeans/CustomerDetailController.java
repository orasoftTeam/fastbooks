/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbCustomerFacade;
import com.fastbooks.facade.FbInvoiceFacade;
import com.fastbooks.modelo.FbCustomer;
import com.fastbooks.modelo.FbInvoice;
import com.fastbooks.modelo.FbStatement;
import com.fastbooks.modelo.PaymentMethod;
import com.fastbooks.modelo.Terms;
import com.fastbooks.util.ValidationBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DELL
 */
public class CustomerDetailController implements Serializable {

    /**
     * Creates a new instance of CustomerDetailController
     */
    private @Getter
    @Setter
    String title;
    private @Getter
    @Setter
    String idCia = "";
    private @Getter
    @Setter
    String idCust = "";
    private @Getter
    @Setter
    FbCustomer currentCust = null;
    @EJB
    private ValidationBean vb;
    @EJB
    private FbCustomerFacade cFacade;
    @EJB
    private FbInvoiceFacade iFacade;
    @Inject
    private UserData userData;

    private @Getter
    @Setter
    Double openBalance = 0.00;
    private @Getter
    @Setter
    Double overDue = 0.00;
    private @Getter
    @Setter
    List<Terms> tList = new ArrayList<>();
    private @Getter
    @Setter
    String dOpenBalance = "0.00";
    private @Getter
    @Setter
    String dOverDue = "0.00";

    private @Getter
    @Setter
    String invoiceModal = "0";

    private @Getter
    @Setter
    List<FbInvoice> transactionList = new ArrayList<>();

    private @Getter
    @Setter
    FbInvoice currentIn = new FbInvoice();

    private @Getter
    @Setter
    List<PaymentMethod> pMethodList = new ArrayList<>();

    /*Estimate stuff*/
    private @Getter
    @Setter
    FbInvoice currenInvoice;
    private @Getter
    @Setter
    String estimateStatus;
    private @Getter
    @Setter
    String AccBy;
    private @Getter
    @Setter
    String AccDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private DateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    
    private @Getter @Setter BigDecimal totalBalance = new BigDecimal(BigInteger.ZERO);
    private @Getter @Setter BigDecimal totalTotal = new BigDecimal(BigInteger.ZERO);

    /*End estimate stuff*/
    public @Getter
    @Setter
    boolean sameShipping = false;
    
    
    public @Getter @Setter List<FbStatement> stmtList = new ArrayList<>();

    public CustomerDetailController() {
    }

    @PostConstruct
    public void init() {
        title = this.vb.getMsgBundle("lblCustomerDetail");
        System.out.println("com.fastbooks.managedbeans.CustomerDetailController.init()");
        if (!this.userData.getUses().equals("0")) {
            this.vb.lanzarMensaje("info", this.userData.getUses(), "blank");
            this.vb.updateComponent("CustomerDetailForm:messages");
            this.userData.setUses("0");
        }

        if (this.pMethodList.isEmpty()) {
            this.pMethodList.add(new PaymentMethod("1", "", this.vb.getMsgBundle("lblCash")));
            this.pMethodList.add(new PaymentMethod("2", "", this.vb.getMsgBundle("lblCreditCard")));
            this.pMethodList.add(new PaymentMethod("3", "", this.vb.getMsgBundle("lblDirectDebit")));
            this.pMethodList.add(new PaymentMethod("4", "", this.vb.getMsgBundle("lblCheque")));
        }

        if (this.tList.isEmpty()) {
            this.tList.add(new Terms("1", "30", "Credits at 30 days"));
            this.tList.add(new Terms("2", "0", "Due on receipt"));
            this.tList.add(new Terms("3", "15", "Net 15"));
            this.tList.add(new Terms("4", "30", "Net 30"));
            this.tList.add(new Terms("5", "60", "Net 60"));
        }
        this.setCustomer();
    }

    public void setCustomer() {
        HttpServletRequest req = (HttpServletRequest) vb.getRequestContext();
        int c = 0;
        this.idCust = req.getParameter("id");
        if (this.idCust != null && !this.idCust.isEmpty()) {
            this.currentCust = cFacade.getCustomerByIdCust(idCust,userData.getCurrentCia().getIdCia().toString());
            if (this.currentCust != null) {
                if (userData != null && userData.getCurrentCia() != null) {
                    this.idCia = userData.getCurrentCia().getIdCia().toString();
                    //transactionList = iFacade.getInvoicesByIdCust(idCust, idCia);
                    this.aplicarFilter();
                    Double acumBalance = 0.0;
                    Double acumTotal = 0.0;
                    for (FbInvoice fbInvoice : transactionList) {
                if (fbInvoice.getActualBalance() != null && !fbInvoice.getType().equals("PA")) {
                    acumBalance += fbInvoice.getActualBalance().doubleValue();
                }else if(fbInvoice.getType().equals("PA")){
                    acumBalance -= fbInvoice.getActualBalance().doubleValue();
                }
                if (fbInvoice.getTotal() != null && !fbInvoice.getType().equals("PA")) {
                    acumTotal += fbInvoice.getTotal().doubleValue();
                }
                
                
            }
                    this.totalBalance = new BigDecimal(acumBalance).setScale(2, BigDecimal.ROUND_HALF_UP);
                    this.totalTotal = new BigDecimal(acumTotal).setScale(2, BigDecimal.ROUND_HALF_UP);
                    if (currentCust.getStreet() == null) {
                        currentCust.setStreet("");
                    }
                    if (currentCust.getStreetS() == null) {
                        currentCust.setStreetS("");
                    }

                    if (currentCust.getStreet().equals(currentCust.getStreetS()) && currentCust.getEstate().equals(currentCust.getEstateS())
                            && currentCust.getPostalCode().equals(currentCust.getPostalCodeS()) && currentCust.getCity().equals(currentCust.getCityS())
                            && currentCust.getCountry().equals(currentCust.getCountryS())) {
                        this.sameShipping = true;
                        this.vb.ejecutarJavascript("customerFormHelper.shippingDir = 2;customerFormHelper.enableShipAdress();");
                    } else {
                        this.sameShipping = false;
                        this.vb.ejecutarJavascript("$('#sameSha').attr('checked',false);customerFormHelper.enableShipAdress()");
                    }
                } else {
                    c++;
                }

            } else {
                c++;
            }
        } else {
            c++;
        }

        if (c != 0) {
            this.vb.redirecionar("/view/sales/sales.xhtml");
        }

        for (FbInvoice in : transactionList) {
            if (!in.getType().equals("PA") && in.getType().equals("IN")) {
                if (in.getStatus().equals("OV")) {
                    overDue += in.getActualBalance().doubleValue();
                } else {
                    openBalance += in.getActualBalance().doubleValue();
                }
            } else if (in.getType().equals("PA")) {
                openBalance -= in.getActualBalance().doubleValue();
            }

        }

        dOpenBalance = openBalance.toString();
        dOverDue = overDue.toString();
    }

    public void setType(String tipo) {

        this.userData.setInvoiceTypeForm(tipo);
        this.userData.setDirCust(idCust);
        this.vb.redirecionar("/view/sales/invoiceForm.xhtml?idc=" + idCust + "&dir=" + idCust);
    }

    public void pagar() {
        this.userData.setDirCust(idCust);
        this.vb.redirecionar("/view/sales/payments/paymentForm.xhtml?idc=" + idCust + "&dir=" + idCust);
    }

    public void edit(FbInvoice in) {
        this.userData.setFbInvoice(in);
        if (!in.getType().equals("PA")) {
            this.userData.setInvoiceTypeForm(in.getType());
            this.vb.redirecionar("/view/sales/invoiceForm.xhtml?id=" + in.getIdInvoice().toString() + "&dir=" + this.idCust);
        } else if (in.getType().equals("PA")) {
            this.vb.redirecionar("/view/sales/payments/paymentForm.xhtml?id=" + in.getIdInvoice().toString() + "&dir=" + this.idCust);
        }
    }

    public void print(FbInvoice i, HttpServletRequest req) {
        try {
            String jasperFile = i.getIdCust() == null ? "salesReceiptSinCust_1" : "invoice_1";

            this.invoiceModal = this.iFacade.generateInvoice(i, this.userData.getCurrentCia().getLogo(), this.iFacade.getCompiledFile(jasperFile, req), this.userData.formatType(i.getType()), this.userData.formatMaster(i.getActualBalance().toString()));

            //this.userData.setSInvoice(invoiceModal);
            //this.vb.lanzarMensajeSinBundle("error", this.invoiceModal, "");
            this.currentIn = i;
            // this.vb.updateComponent("pdf");
            this.vb.ejecutarJavascript("$('.invoiceModal').modal();");
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.print()");
        }
    }

    public void assignEstimate(FbInvoice in) {

        this.currenInvoice = in;
        this.estimateStatus = in.getStatus();
        this.AccBy = in.getEsAccby();
        this.AccDate = in.getEsAccdate();
        //this.vb.ejecutarJavascript("$('.estimateStatus').val('" + in.getStatus() + "');");
        //this.vb.ejecutarJavascript("$('.estimateAccBy').val('" + in.getEsAccby() + "');");
        //this.vb.ejecutarJavascript("$('.estimateAccDate').val('"+in.getEsAccdate()+"');");
        this.vb.ejecutarJavascript("show('" + in.getStatus() + "');");
    }

    public void copy(FbInvoice in) {
        in.setIdInvoice(BigDecimal.ZERO);
        in.setNoDot("copy");
        this.userData.setFbInvoice(in);
        this.userData.setInvoiceTypeForm(in.getType());
        this.vb.redirecionar("/view/sales/invoiceForm.xhtml?dir=" + this.idCust);
    }

    public void recievePayment(String idCust, String idInvoice) {
        this.vb.redirecionar("/view/sales/payments/paymentForm.xhtml?idc=" + idCust + "&idi=" + idInvoice);
    }

    public void updateEstimateStatus() {

        currenInvoice.setStatus(this.estimateStatus);
        if (this.estimateStatus.equals("PE")) {
            currenInvoice.setEsAccby("");
            currenInvoice.setEsAccdate("");
        } else {
            currenInvoice.setEsAccby(AccBy);

            try {
                currenInvoice.setEsAccdate(sdf.format(sd.parse(AccDate)));
            } catch (Exception e) {
                currenInvoice.setEsAccdate("");

            }

        }

        String res = this.iFacade.actInvoice(currenInvoice, "U");
        if (res.equals("0")) {
            this.userData.setUses("lblEsUpdateSuccess");
        } else {
            this.userData.setUses("unexpectedError");

        }
        this.vb.redirecionar("/view/sales/customer/customerDetail.xhtml?id=" + this.idCust);

    }

    public boolean valCampos() {

        boolean flag = false;

        if (vb.validarEmail(this.currentCust.getEmail(), "warn", "valErr", "reqEmail")
                && vb.validarCampoVacio(this.currentCust.getDisplayName(), "warn", "valErr", "lblReqDisplayName")
                && vb.validarCampoVacio(this.currentCust.getStreet(), "warn", "valErr", "reqStreet")
                && vb.validarCampoVacio(this.currentCust.getCity(), "warn", "valErr", "reqCity")
                && vb.validarSoloLetras(this.currentCust.getCity(), "warn", "valErr", "reqCity")
                && vb.validarCampoVacio(this.currentCust.getEstate(), "warn", "valErr", "reqState")
                && vb.validarSoloLetras(this.currentCust.getEstate(), "warn", "valErr", "reqState")
                && vb.validarCampoVacio(this.currentCust.getPostalCode(), "warn", "valErr", "reqPostalC")
                && vb.validarCampoVacio(this.currentCust.getCountry(), "warn", "valErr", "reqCountry")
                && vb.validarSoloLetras(this.currentCust.getCountry(), "warn", "valErr", "reqCountry")
                && vb.validarCampoVacio(this.currentCust.getFirstname(), "warn", "valErr", "lblReqCustomerName")
                && vb.validarSoloLetras(this.currentCust.getFirstname(), "warn", "valErr", "lblReqCustomerName")
                && vb.validarCampoVacio(this.currentCust.getLastname(), "warn", "valErr", "lblReqCustomerLastName")
                && vb.validarSoloLetras(this.currentCust.getLastname(), "warn", "valErr", "lblReqCustomerLastName")) {
            HttpServletRequest req = (HttpServletRequest) vb.getRequestContext();
            String parameter = req.getParameter("sameSha");
            if (parameter != null) {
                sameShipping = true;
            } else {
                sameShipping = false;
            }

            if (!sameShipping) {

                //validar
                if (vb.validarCampoVacio(this.currentCust.getStreetS(), "warn", "valErr", "reqStreetS")
                        && vb.validarCampoVacio(this.currentCust.getCityS(), "warn", "valErr", "reqCityS")
                        && vb.validarSoloLetras(this.currentCust.getCityS(), "warn", "valErr", "reqCityS")
                        && vb.validarCampoVacio(this.currentCust.getEstateS(), "warn", "valErr", "reqStateS")
                        && vb.validarSoloLetras(this.currentCust.getEstateS(), "warn", "valErr", "reqStateS")
                        && vb.validarCampoVacio(this.currentCust.getPostalCodeS(), "warn", "valErr", "reqPostalCS")
                        && vb.validarCampoVacio(this.currentCust.getCountryS(), "warn", "valErr", "reqCountryS")
                        && vb.validarSoloLetras(this.currentCust.getCountryS(), "warn", "valErr", "reqCountryS")) {
                    flag = true;
                }

            } else {
                flag = true;
            }

        }

        return flag;
    }

    public void editCustomer() {
        String res = "";

        try {

            if (valCampos()) {

                if (sameShipping) {
                    currentCust.setStreetS(currentCust.getStreet());
                    currentCust.setCityS(currentCust.getCity());
                    currentCust.setEstateS(currentCust.getEstate());
                    currentCust.setPostalCodeS(currentCust.getPostalCode());
                    currentCust.setCountryS(currentCust.getCountry());
                }
                res = cFacade.actCustomer(currentCust, "U");
                System.out.println("resultado update customer" + res);
                if (res.equals("0")) {
                    //vb.lanzarMensaje("info", "customerUpdate", "blank");
                    this.userData.setUses("customerUpdate");
                    this.vb.redirecionar("/view/sales/customer/customerDetail.xhtml?id=" + this.idCust);
                } else if (res.equals("-1")) {
                    vb.lanzarMensaje("error", "customerRepeatFail", "blank");
                } else if (res.equals("-2")) {
                    vb.lanzarMensaje("error", "unexpectedError", "blank");
                }
            }

        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.CustomerController.edit()");
            e.printStackTrace();
            res = "-2";
        }
        
      

    }
    
      public void statement(){
    this.vb.redirecionar("/view/sales/customer/statements.xhtml?id="+this.idCust);
    }
      
      
      /*
      Filter section
      */
      private @Getter @Setter String fFrom = "0";
      private @Getter @Setter String fTo= "0";
      private @Getter @Setter String fTranType = "0";
      
      public void processFilter(){
      this.userData.setCustomerDetailFilterFrom(this.fFrom);
      this.userData.setCustomerDetailFilterTo(this.fTo);
      this.userData.setCustomerDetailFilterType(this.fTranType);
      this.vb.redirecionar("/view/sales/customer/customerDetail.xhtml?id="+this.idCust);
      }
      
          public String formatStmtType(String type) {
        String res = "";
        switch (type) {
            case "BF":
                res = this.vb.getMsgBundle("lblStatementType1");
                break;
            case "OI":
                res = this.vb.getMsgBundle("lblStatementType2");
                break;

            case "TS":
                res = this.vb.getMsgBundle("lblStatementType3");
                break;

        }
        return res;
    }
      
      public void aplicarFilter(){
      this.fFrom = this.userData.getCustomerDetailFilterFrom();
      this.fTo = this.userData.getCustomerDetailFilterTo();
      this.fTranType = this.userData.getCustomerDetailFilterType();
          if (this.validarFilter()) {
              if (this.fTranType.equals("ST")) {
                  this.stmtList = this.iFacade.getStmtFilter(this.idCia, this.fFrom, this.fTo, this.idCust);
              }else if(this.fTranType.equals("PA") || this.fTranType.equals("SR") || this.fTranType.equals("ES")){
                  this.transactionList = this.iFacade.applyFilter(this.idCia, this.fTranType, "0", "", this.fFrom, this.fTo, this.idCust);
              }else if(this.fTranType.equals("AI") ){
                  this.transactionList = this.iFacade.applyFilter(this.idCia, "IN", "0", "", this.fFrom, this.fTo, this.idCust);
              }else if(this.fTranType.equals("0")){
                  this.transactionList = this.iFacade.applyFilter(this.idCia, "0", "0", "", this.fFrom, this.fTo, this.idCust);
              }else{
                  this.transactionList = this.iFacade.applyFilter(this.idCia, "IN", this.fTranType, "", this.fFrom, this.fTo, this.idCust);
              }
          }else{
          this.transactionList = this.iFacade.getInvoicesByIdCust(idCust, idCia);
          
          }
      }
      
      public void resetFilter(){
      this.userData.setCustomerDetailFilterFrom("0");
      this.userData.setCustomerDetailFilterTo("0");
      this.userData.setCustomerDetailFilterType("0");
      this.vb.redirecionar("/view/sales/customer/customerDetail.xhtml?id="+this.idCust);
      }
      
      public boolean validarFilter(){
      boolean flag = false;
      int c = 0;
      
          if (!this.fFrom.equals("0")) {
              c++;
          }
          if (!this.fTo.equals("0")) {
              c++;
          }
          if (!this.fTranType.equals("0")) {
              c++;
          }
          if (c != 0) {
              flag = true;
          }
      
      
      return flag;    
      }
      
      
          public void viewStmt(String idCust, String idStmt){
        this.vb.redirecionar("/view/sales/customer/statements.xhtml?id="+idCust+"&stmt="+idStmt+"&dir=1");
    }
    
         private @Getter @Setter String stmtPdf = "0"; 
          
    public void generateStmt(FbStatement statement){
        try {
            String res = this.iFacade.generateStmt(statement, statement.getIdCia().getLogo(), this.iFacade.getCompiledFile("statement", this.vb.getRequestContext()), this.userData.formatMaster(statement.getIdCust().getBalance().toString()));
            this.stmtPdf = res;
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.generateStmt()");
            e.printStackTrace();
        }
    
    
    }
    
    
    
    
    /*Batch options*/
          public void printTransactions() {
        try {
            String res = "";
            System.out.println("com.fastbooks.managedbeans.InvoiceController.printTransactions()");
            for (FbInvoice fbInvoice : transactionList) {
                if (fbInvoice.isCheckbox()) {
                    if (res.isEmpty()) {
                        res += fbInvoice.getIdInvoice().toString();
                    } else {
                        res += "," + fbInvoice.getIdInvoice().toString();
                    }

                    System.out.println("Transaction: " + fbInvoice.getType() + ", id:" + fbInvoice.getIdInvoice().toString() + " IS CHECKED");
                }
            }
            this.invoiceModal = this.iFacade.printTransactions(res, this.userData.getCurrentCia().getLogo(), this.iFacade.getCompiledFile("transactions", this.vb.getRequestContext()), this.userData.getCurrentCia().getIdCia().toString());
            this.vb.ejecutarJavascript("$('.invoiceModal').modal();");
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.printTransactions()");
            e.printStackTrace();
        }
    }
          
              public void packingSlip() {
        try {
            String res = "";
            System.out.println("com.fastbooks.managedbeans.InvoiceController.printTransactions()");
            for (FbInvoice fbInvoice : transactionList) {
                if (fbInvoice.isCheckbox()) {
                    if (res.isEmpty()) {
                        res += fbInvoice.getIdInvoice().toString();
                    } else {
                        res += "," + fbInvoice.getIdInvoice().toString();
                    }

                    System.out.println("Transaction: " + fbInvoice.getType() + ", id:" + fbInvoice.getIdInvoice().toString() + " IS CHECKED");
                }
            }
            this.invoiceModal = this.iFacade.printTransactions(res, this.userData.getCurrentCia().getLogo(), this.iFacade.getCompiledFile("packingSlip", this.vb.getRequestContext()), this.userData.getCurrentCia().getIdCia().toString());
            this.vb.ejecutarJavascript("$('.invoiceModal').modal();");
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.printTransactions()");
            e.printStackTrace();
        }
    }
     

}
