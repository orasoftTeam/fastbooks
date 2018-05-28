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
import com.fastbooks.modelo.FbPaymentDetail;
import com.fastbooks.modelo.PaymentMethod;
import com.fastbooks.util.ValidationBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DELL
 */
public class PaymentController implements Serializable{

    @Inject
    UserData userData;

    @EJB
    ValidationBean vb;

    private @Getter
    @Setter
    String title;

    private @Getter
    @Setter
    String idCust;

    private @Getter
    @Setter
    String email;
    
    

    private @Getter
    @Setter
    String DAmount;
    
    private @Getter
    @Setter
    String DAmountApply;
    
    private @Getter
    @Setter
    String DAmountCredit;
    
    private @Getter
    @Setter
    String paymentDate;

    @EJB
    FbCustomerFacade cFacade;
    
    @EJB
    FbInvoiceFacade iFacade;

    private @Getter
    @Setter
    List<FbCustomer> cList = new ArrayList<>();
    
    private @Getter
    @Setter
    List<FbInvoice> transactionList = new ArrayList<>();
    
    private @Getter
    @Setter
    List<FbPaymentDetail> payDetailList = new ArrayList<>();

    private @Getter
    @Setter
    List<PaymentMethod> pMethodList = new ArrayList<>();

    /**
     * Creates a new instance of PaymentController
     */
    public PaymentController() {
    }

    @PostConstruct
    public void init() {
        cList = cFacade.getCustomersByIdCia(this.userData.getCurrentCia().getIdCia().toString());
        this.title = vb.getMsgBundle("lblReceivePayment");
        idCust = "0";
        DAmount = "0.00";
        DAmountApply = "0.00";
        DAmountCredit = "0.00";
        System.out.println("com.fastbooks.managedbeans.PayController.init()");
        if (this.pMethodList.isEmpty()) {
            this.pMethodList.add(new PaymentMethod("1", "", this.vb.getMsgBundle("lblCash")));
            this.pMethodList.add(new PaymentMethod("2", "", this.vb.getMsgBundle("lblCreditCard")));
            this.pMethodList.add(new PaymentMethod("3", "", this.vb.getMsgBundle("lblDirectDebit")));
            this.pMethodList.add(new PaymentMethod("4", "", this.vb.getMsgBundle("lblCheque")));
        }
        
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Calendar cal = Calendar.getInstance();
            paymentDate = dateFormat.format(cal.getTime());
    }
    
    
    public void changeCust(){
    
        try {
            this.payDetailList = new ArrayList<>();
            this.limpiarTodo();
            System.out.println("com.fastbooks.managedbeans.PaymentController.changeCust()");
            if (!this.idCust.equals("0")) {
                this.transactionList = this.iFacade.getInvoicesForPayment(this.userData.getCurrentCia().getIdCia().toString(), idCust);
                for (FbInvoice in : this.transactionList) {
                    FbPaymentDetail pd = new FbPaymentDetail();
                    pd.setDescrip(this.vb.getMsgBundle("invoice") + "# " + in.getNoDot() + " (" + in.getInDate()+")");
                    pd.setDueDate(in.getDueDate());
                    pd.setOriginalAmount(in.getTotal());
                    pd.setOpenBalance(in.getActualBalance());
                    pd.setIdInvoice(in);
                    this.payDetailList.add(pd);
                }
                
                //vb.ejecutarJavascript("$('#TblTransactions').css('display','block')");
                //vb.updateComponent("paymentForm:TblTransactions");
            }else{
            
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.PaymentController.changeCust()");
            e.printStackTrace();
        }
        
    }
    
    public boolean renderMaster(String op){
    boolean res = false;
    switch(op){
        case "TBL":
            if (!this.payDetailList.isEmpty()  && !this.idCust.equals("0")  || !this.DAmount.equals("0.00")) {
                 res = true;   
                }
            break;
        case "NO_DATA":
            if (this.payDetailList.isEmpty()  && !this.idCust.equals("0")) {
                 res = true;   
                }
            break;
            
        default:
            
            break;    
    }
    
    return res;
    }
    
    public String formatDoubleMaster(BigDecimal value){
    String res ="";
    res =String.format("%.2f", value);
    
    return res;
    }
    
    
    public void limpiarTodo(){
        DAmount = "0.00";
        DAmountApply = "0.00";
        DAmountCredit = "0.00";
    }
    
    public void calcularPagos(){
        Double amount = 0.00;
        
        try {
            amount = Double.parseDouble(this.DAmount);
        } catch (NumberFormatException e) {
            System.out.println("Fallo al parsear payment amount, se pasa a 0");
            //e.printStackTrace();
            this.DAmount = this.formatDoubleMaster(new BigDecimal(amount));
        }
        
        
        if (this.payDetailList.isEmpty() && amount != 0.00) {
              this.DAmountApply = amount.toString();
              this.DAmountCredit = amount.toString();
        }else{
        
        }
        
        if (amount == 0) {
            this.limpiarTodo();
        }
        
        //System.out.println("com.fastbooks.managedbeans.PaymentController.calcularPagos() : " + amount);
        //this.vb.updateComponent("paymentForm:TblTransactions");
    }
    
    

}
