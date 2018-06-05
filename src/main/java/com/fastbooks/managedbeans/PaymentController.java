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
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DELL
 */
public class PaymentController implements Serializable {

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
    
    private @Getter
    @Setter
    String pMethod;
    
    
    private @Getter
    @Setter
    String pReferenceNo;

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

    private @Getter
    @Setter
    boolean formTouched = false;
    
    private DateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

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

    public void changeCust() {

        try {
            this.payDetailList = new ArrayList<>();
            this.limpiarTodo();

            //System.out.println("com.fastbooks.managedbeans.PaymentController.changeCust()");
            if (!this.idCust.equals("0")) {

                for (FbCustomer fbCustomer : cList) {
                    if (fbCustomer.getIdCust().toString().equals(idCust)) {
                        this.email = fbCustomer.getEmail();
                        System.out.println("email: " + this.email);
                    }
                }

                this.transactionList = this.iFacade.getInvoicesForPayment(this.userData.getCurrentCia().getIdCia().toString(), idCust);
                for (FbInvoice in : this.transactionList) {
                    FbPaymentDetail pd = new FbPaymentDetail();
                    pd.setDescrip(this.vb.getMsgBundle("invoice") + "# " + in.getNoDot() + " (" + in.getInDate() + ")");
                    pd.setDueDate(in.getDueDate());
                    pd.setOriginalAmount(in.getTotal());
                    pd.setOpenBalance(in.getActualBalance());
                    pd.setIdInvoice(in);
                    pd.setCheckbox(false);
                    this.payDetailList.add(pd);
                }

                //vb.ejecutarJavascript("$('#TblTransactions').css('display','block')");
                //vb.updateComponent("paymentForm:TblTransactions");
                this.formTouched = true;
            } else {
                this.payDetailList = new ArrayList<>();
                this.email = "";
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.PaymentController.changeCust()");
            e.printStackTrace();
        }

    }

    public boolean renderMaster(String op) {
        boolean res = false;
        switch (op) {
            case "TBL":
                if (!this.payDetailList.isEmpty() && !this.idCust.equals("0") || !this.DAmount.equals("0.00")) {
                    res = true;
                }
                break;
            case "NO_DATA":
                if (this.payDetailList.isEmpty() && !this.idCust.equals("0")) {
                    res = true;
                }
                break;

            default:
                System.out.println("com.fastbooks.managedbeans.PaymentController.renderMaster(default)");
                break;
        }

        return res;
    }

    public String formatDoubleMaster(BigDecimal value) {
        String res = "";
        res = String.format("%.2f", value);

        return res;
    }

    public void limpiarTodo() {
        DAmount = "0.00";
        DAmountApply = "0.00";
        DAmountCredit = "0.00";
    }

    public void calcularPagos() {
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
        } else {
            // aplicar pagos Y LIMPIAR PAGOS

            for (FbPaymentDetail pd : payDetailList) {
                pd.setPaymentString("");
                pd.setPayment(null);
                pd.setCheckbox(false);
            }

            Double remanent = 0.00;
            Double lastValue = amount;
            for (FbPaymentDetail pd : payDetailList) {

                remanent = lastValue - Double.parseDouble(pd.getOpenBalance().toString());

                if (remanent > 0.00) {

                    pd.setPaymentString(pd.getOpenBalance().toString());
                    pd.setPayment(pd.getOpenBalance());
                    pd.setCheckbox(true);
                    lastValue = remanent;
                } else if (remanent <= 0.00) {

                    pd.setPaymentString(this.formatDoubleMaster(new BigDecimal(lastValue.toString())));
                    pd.setPayment(new BigDecimal(lastValue));
                    pd.setCheckbox(true);
                    break;

                }

            }

        }

        if (amount == 0) {
            this.limpiarTodo();
        }
        this.formTouched = true;
        actualizarResults();
        //System.out.println("com.fastbooks.managedbeans.PaymentController.calcularPagos() : " + amount);
        //this.vb.updateComponent("paymentForm:TblTransactions");
    }

    public void aplicarPagoADetalle() {
        for (FbPaymentDetail pd : payDetailList) {
            Double payment = 0.00;
            //System.out.println("pago " + pd.getPaymentString());
            try {
                if (pd.getPaymentString() != null && !pd.getPaymentString().isEmpty()) {
                    payment = Double.parseDouble(pd.getPaymentString());
                    if (payment < 0.00) {
                        payment = 0.00;
                    } else if (payment > Double.parseDouble(pd.getOpenBalance().toString())) {
                        payment = 0.00;
                    } else if (payment > 0.00 && payment <= Double.parseDouble(pd.getOpenBalance().toString())) {
                        //aplicar

                        if (pd.getPayment() != null) {
                            this.DAmount = this.formatDoubleMaster(new BigDecimal(String.valueOf(Double.parseDouble(this.DAmount) - Double.valueOf(pd.getPayment().toString()))));
                        }

                        pd.setPayment(new BigDecimal(payment));
                        pd.setCheckbox(true);
                        this.DAmount = this.formatDoubleMaster(new BigDecimal(String.valueOf(Double.parseDouble(this.DAmount) + Double.valueOf(pd.getPayment().toString()))));
                    }

                }
            } catch (NumberFormatException e) {
                payment = 0.00;
                pd.setPayment(null);
            }
            if (payment != 0.00) {
                pd.setPaymentString(this.formatDoubleMaster(new BigDecimal(payment)));
                pd.setPayment(new BigDecimal(payment));
            } else {
                if (pd.getPayment() != null) {
                    this.DAmount = this.formatDoubleMaster(new BigDecimal(String.valueOf(Double.parseDouble(this.DAmount) - Double.valueOf(pd.getPayment().toString()))));
                }
                pd.setCheckbox(false);
                pd.setPaymentString("");
                pd.setPayment(null);
            }

        }

        actualizarResults();
    }

    public void aplicarPagoCheck(String id) {
        //System.out.println("id: " + id);
        for (FbPaymentDetail pd : payDetailList) {
            if (pd.getIdInvoice().getIdInvoice().toString().equals(id)) {
                if (!pd.isCheckbox()) {
                    this.DAmount = this.formatDoubleMaster(new BigDecimal(String.valueOf(Double.parseDouble(this.DAmount) - Double.valueOf(pd.getPayment().toString()))));
                    pd.setPaymentString("");
                    pd.setPayment(null);

                } else {

                    pd.setPaymentString(pd.getOpenBalance().toString());
                    pd.setPayment(pd.getOriginalAmount());
                    this.DAmount = this.formatDoubleMaster(new BigDecimal(String.valueOf(Double.parseDouble(this.DAmount) + Double.valueOf(pd.getPayment().toString()))));
                }
            }
        }

        actualizarResults();
    }

    public void actualizarResults() {
        Double acum = 0.00;
        for (FbPaymentDetail pd : payDetailList) {
            if (pd.getPayment() != null) {
                acum += Double.parseDouble(pd.getPayment().toString());
            }
        }

        Double amount = Double.parseDouble(this.DAmount) - acum;

        if (amount < 0.00) {

            amount = 0.00;
        }

        this.DAmountApply = this.formatDoubleMaster(new BigDecimal(acum.toString()));
        this.DAmountCredit = this.formatDoubleMaster(new BigDecimal(amount.toString()));

    }

    public void save(String op) {
        try {
            if (!this.idCust.equals("0")) {

                if (!this.DAmount.equals("0.00")) {
                    FbInvoice payment = new FbInvoice();
                    payment.setIdCia(this.userData.getCurrentCia());
                    payment.setIdCust(new FbCustomer(new BigDecimal(this.idCust)));
                    if (op.equals("A")) {
                        payment.setIdInvoice(BigDecimal.ZERO);
                    }
                    payment.setTotal(new BigDecimal(this.formatDoubleMaster(new BigDecimal(this.DAmount))));
                    payment.setPayMethod(this.pMethod);
                    payment.setPayReferenceNo(this.pReferenceNo);
                    payment.setInDate(sdf.format(sd.parse(this.paymentDate)));
                    
                    List<FbPaymentDetail> finalList = new ArrayList<>();
                    
                    for (FbPaymentDetail pd : payDetailList) {
                        if (pd.getPayment() != null) {
                            finalList.add(pd);
                        }
                    }
                    
                    
                    
                    payment.setFbPaymentDetailList(finalList);
                    payment.setCustEmail(email);

                    String res = iFacade.actPayment(payment, op);
                    String message = "lblPaymentSuccess";
                    if (op.equals("A")) {
                        message = "lblPaymentSuccess";
                    }
                    switch(res){
                        case "0":
                            this.vb.redirecionar("/view/sales/sales.xhtml");
                            this.userData.setUses(message);
                            break;
                        case "-1":
                            this.vb.lanzarMensaje("error", "unexpectedError", "unexpectedError");
                            break;
                        case "-2":
                            this.vb.lanzarMensaje("error", "unexpectedError", "unexpectedError");
                            break;
                        default:
                            break;    
                    }
                    
                } else {
                    this.vb.lanzarMensaje("error", "lblPaymentInvalid", "lblPaymentInvalid");

                }

            } else {
                this.vb.lanzarMensaje("error", "lblSelectCust", "lblSelectCust");
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.PaymentController.save()");
            e.printStackTrace();
        }

    }

}
