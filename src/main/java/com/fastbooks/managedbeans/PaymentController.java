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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    
    private @Getter @Setter String dir = "0";

    private @Getter
    @Setter
    String invoiceModal = "0";

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

    private @Getter
    @Setter
    boolean isMod = false;

    private DateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    private @Getter
    @Setter
    String memo;

    private @Getter
    @Setter
    String dMemo;

    private @Getter
    @Setter
    String idPayment = "0";

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

        this.assignEdit();
        this.recievePayment();
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
                            this.DAmount = String.valueOf(Double.parseDouble(this.DAmount) - Double.valueOf(pd.getPayment().toString()));
                        }

                        pd.setPayment(new BigDecimal(payment));
                        pd.setCheckbox(true);
                        this.DAmount = String.valueOf(Double.parseDouble(this.DAmount) + Double.valueOf(pd.getPayment().toString()));
                    }

                }
            } catch (NumberFormatException e) {
                payment = 0.00;
                pd.setPayment(null);
            }
            if (payment != 0.00) {
                pd.setPaymentString(payment.toString());
                pd.setPayment(new BigDecimal(payment));
            } else {
                if (pd.getPayment() != null) {
                    this.DAmount = String.valueOf(Double.parseDouble(this.DAmount) - Double.valueOf(pd.getPayment().toString()));
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
                    this.DAmount = String.valueOf(Double.parseDouble(this.DAmount) - Double.valueOf(pd.getPayment().toString()));
                    pd.setPaymentString("");
                    pd.setPayment(null);

                } else {

                    pd.setPaymentString(pd.getOpenBalance().toString());
                    pd.setPayment(pd.getOpenBalance());
                    this.DAmount = String.valueOf(Double.parseDouble(this.DAmount) + Double.valueOf(pd.getPayment().toString()));
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
                    //this.vb.lanzarMensajeSinBundle("info", this.userData.getCurrentCia().getNomCom(), DAmount);//
                    payment.setIdCust(new FbCustomer(new BigDecimal(this.idCust)));
                    //this.vb.lanzarMensajeSinBundle("info", payment.getIdCust().getIdCust().toString(), DAmount);//
                    //this.vb.lanzarMensajeSinBundle("info", op, DAmount);//
                    if (op.equals("A")) {
                        payment.setIdInvoice(BigDecimal.ZERO);
                    }

                    if (isMod) {
                        payment.setIdInvoice(new BigDecimal(this.idPayment));
                        if (!op.equals("D")) {
                            op = "U";
                        }
                    }

                    //String property = System.getProperty("java.version");
                    payment.setTotal(new BigDecimal(this.DAmount));
                    //this.vb.lanzarMensajeSinBundle("info", op, "386");//
                    payment.setPayMethod(this.pMethod);

                    //this.vb.lanzarMensajeSinBundle("info",this.pMethod, DAmount);//
                    payment.setPayReferenceNo(this.pReferenceNo);

                    //this.vb.lanzarMensajeSinBundle("info", sdf.format(sd.parse(this.paymentDate)), DAmount); //
                    try {
                        payment.setInDate(sdf.format(sd.parse(this.paymentDate)));
                    } catch (Exception e) {
                        payment.setInDate(this.paymentDate);
                    }
                    HttpServletRequest req = (HttpServletRequest) vb.getRequestContext();
                    this.memo = req.getParameter("memo");
                    payment.setMessageInvoice(this.memo);
                    List<FbPaymentDetail> finalList = new ArrayList<>();

                    for (FbPaymentDetail pd : payDetailList) {
                        if (pd.getPayment() != null) {
                            finalList.add(pd);
                        }
                    }

                    payment.setFbPaymentDetailList(finalList);
                    payment.setCustEmail(email);
                    //this.vb.lanzarMensajeSinBundle("info", "entre despues de setear email linea 407", email + " " + op);
                    //this.vb.lanzarMensajeSinBundle("info", finalList.toString(), finalList.toString());//  
                    String res = iFacade.actPayment(payment, op);
                    //this.vb.lanzarMensajeSinBundle("info", "entre despues de facade linea 410", "res: " + res);
                    //this.vb.lanzarMensajeSinBundle("info", res, res); //
                    String message = "lblPaymentSuccess";
                    if (op.equals("A")) {
                        message = "lblPaymentSuccess";
                    } else if (op.equals("U")) {
                        message = "lblEditPaymentSuccess";
                    } else if (op.equals("D")) {
                        message = "lblPaymentDeleted";
                    }
                    switch (res) {
                        case "0":
                            this.userData.setUses(message);
                            this.regresar();
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
            //this.vb.lanzarMensajeSinBundle("error", e.toString(), e.getMessage());
            e.printStackTrace();
        }

    }

    public void refreshComboCust() {
        if (!this.userData.getFormInCustId().equals("0")) {
            cList = cFacade.getCustomersByIdCia(this.userData.getCurrentCia().getIdCia().toString());
            for (FbCustomer c : cList) {
                if (c.getEmail().equals(this.userData.getFormInCustId())) {
                    idCust = c.getIdCust().toString();
                }
            }
            this.userData.setFormInCustId("0");

            this.vb.updateComponent("paymentForm:custs");
            this.changeCust();
            this.vb.updateComponent("paymentForm:custs");
            // validationBean.lanzarMensaje("info", "custAdded", "blank");
        }

    }

    public void assignEdit() {
        FbInvoice in = this.userData.getFbInvoice();

        HttpServletRequest req = (HttpServletRequest) vb.getRequestContext();
        System.out.println("id:" + req.getParameter("id"));

        try {
            
            if (req.getParameter("dir") != null) {
                this.dir = req.getParameter("dir");
            }

            if (req.getParameter("id") != null && in == null) {
                in = iFacade.getInvoiceByIdInvoice(req.getParameter("id"));
            }

            if (in != null) {

                isMod = true;

                title = this.vb.getMsgBundle("lblEditPayment");
                this.userData.setFbInvoice(null);
                this.idPayment = in.getIdInvoice().toString();
                this.memo = in.getMessageInvoice();
                this.dMemo = in.getMessageInvoice();
                this.idCust = in.getIdCust().getIdCust().toString();
                this.changeCust();
                in.setFbPaymentDetailList(this.iFacade.getPaymentDetailsByIdPayment(in.getIdInvoice().toString()));
                this.DAmount = in.getTotal().toString();
                this.paymentDate = in.getInDate();
                this.pReferenceNo = in.getPayReferenceNo();
                this.pMethod = in.getPayMethod();

                int c = 0;
                for (FbPaymentDetail pBaseDetail : in.getFbPaymentDetailList()) {

                    pBaseDetail.setCheckbox(true);
                    pBaseDetail.setPaymentString(pBaseDetail.getPayment().toString());
                    //pBaseDetail.setOpenBalance(pBaseDetail.getIdInvoice().getActualBalance().add(pBaseDetail.getPayment()));
                    Double openBalance = this.iFacade.getInvoiceByIdInvoice(pBaseDetail.getIdInvoice().getIdInvoice().toString()).getActualBalance().doubleValue();
                    Double payment = pBaseDetail.getPayment().doubleValue();
                    pBaseDetail.setOpenBalance(new BigDecimal(openBalance + payment));
                    pBaseDetail.setDescrip(this.vb.getMsgBundle("invoice") + pBaseDetail.getDescrip());

                }

                for (FbPaymentDetail pFormDetail : payDetailList) {
                    c = 0;
                    for (FbPaymentDetail pBaseDetail : in.getFbPaymentDetailList()) {
                        if (pBaseDetail.getIdInvoice().toString().equals(pFormDetail.getIdInvoice().toString())) {
                            c++;

                        }

                    }

                    if (c == 0) {
                        in.getFbPaymentDetailList().add(pFormDetail);
                    }

                }

                this.payDetailList = in.getFbPaymentDetailList();

                this.actualizarResults();

                if (req.getParameter("p") != null) {
                    String message = this.userData.getUses();
                    this.userData.setUses("0");
                    this.vb.lanzarMensaje("info", message, "blank");
                    this.print(in, this.vb.getRequestContext());
                }

            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.PaymentController.assignEdit()");
            e.printStackTrace();
        }
    }

    public void recievePayment() {
        HttpServletRequest req = (HttpServletRequest) vb.getRequestContext();
        System.out.println("idCust:" + req.getParameter("idc"));
        System.out.println("idInvoice:" + req.getParameter("idi"));
        String pIdCust = req.getParameter("idc");
        String pIdInvoice = req.getParameter("idi");
        try {
            if (pIdCust != null && pIdInvoice != null) {
                this.idCust = pIdCust;
                this.changeCust();
                for (FbPaymentDetail pd : this.payDetailList) {
                    if (pd.getIdInvoice().getIdInvoice().toString().equals(pIdInvoice)) {
                        pd.setPaymentString(pd.getOpenBalance().toString());
                        pd.setPayment(pd.getOpenBalance());
                        pd.setCheckbox(true);
                        this.DAmount = String.valueOf(Double.parseDouble(this.DAmount) + Double.valueOf(pd.getPayment().toString()));
                    }
                }
            } else if (pIdCust != null && pIdInvoice == null) {
                this.idCust = pIdCust;
                this.changeCust();
                for (FbPaymentDetail pd : this.payDetailList) {

                    pd.setPaymentString(pd.getOpenBalance().toString());
                    pd.setPayment(pd.getOpenBalance());
                    pd.setCheckbox(true);
                    this.DAmount = String.valueOf(Double.parseDouble(this.DAmount) + Double.valueOf(pd.getPayment().toString()));

                }
            }
            this.actualizarResults();

        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.PaymentController.recievePayment()");
            e.printStackTrace();
        }

    }

    public boolean modifica() {
        return this.isMod;
    }

    public void saveForPrint(String op) {
        try {

            if (!this.idCust.equals("0")) {

                if (!this.DAmount.equals("0.00")) {

                    FbInvoice payment = new FbInvoice();
                    payment.setIdCia(this.userData.getCurrentCia());
                    //this.vb.lanzarMensajeSinBundle("info", this.userData.getCurrentCia().getNomCom(), DAmount);//
                    payment.setIdCust(new FbCustomer(new BigDecimal(this.idCust)));
                    //this.vb.lanzarMensajeSinBundle("info", payment.getIdCust().getIdCust().toString(), DAmount);//
                    //this.vb.lanzarMensajeSinBundle("info", op, DAmount);//
                    if (op.equals("A")) {
                        payment.setIdInvoice(BigDecimal.ZERO);
                    }

                    if (isMod) {
                        payment.setIdInvoice(new BigDecimal(this.idPayment));
                        if (!op.equals("D")) {
                            op = "U";
                        }
                    }

                    //String property = System.getProperty("java.version");
                    payment.setTotal(new BigDecimal(this.DAmount));
                    //this.vb.lanzarMensajeSinBundle("info", op, "386");//
                    payment.setPayMethod(this.pMethod);

                    //this.vb.lanzarMensajeSinBundle("info",this.pMethod, DAmount);//
                    payment.setPayReferenceNo(this.pReferenceNo);

                    //this.vb.lanzarMensajeSinBundle("info", sdf.format(sd.parse(this.paymentDate)), DAmount); //
                    try {
                        payment.setInDate(sdf.format(sd.parse(this.paymentDate)));
                    } catch (Exception e) {
                        payment.setInDate(this.paymentDate);
                    }
                    HttpServletRequest req = (HttpServletRequest) vb.getRequestContext();
                    this.memo = req.getParameter("memo");
                    payment.setMessageInvoice(this.memo);
                    List<FbPaymentDetail> finalList = new ArrayList<>();

                    for (FbPaymentDetail pd : payDetailList) {
                        if (pd.getPayment() != null) {
                            finalList.add(pd);
                        }
                    }

                    payment.setFbPaymentDetailList(finalList);
                    payment.setCustEmail(email);
                    //this.vb.lanzarMensajeSinBundle("info", "entre despues de setear email linea 407", email + " " + op);
                    //this.vb.lanzarMensajeSinBundle("info", finalList.toString(), finalList.toString());//  
                    String res = iFacade.actPaymentWithReturnId(payment, op);
                    //this.vb.lanzarMensajeSinBundle("info", "entre despues de facade linea 410", "res: " + res);
                    //this.vb.lanzarMensajeSinBundle("info", res, res); //
                    String message = "lblPaymentSuccess";
                    if (op.equals("A")) {
                        message = "lblPaymentSuccess";
                    } else if (op.equals("U")) {
                        message = "lblEditPaymentSuccess";
                    } else if (op.equals("D")) {
                        message = "lblPaymentDeleted";
                    }

                    if (!res.equals("def")) {
                        this.userData.setUses(message);
                        this.vb.redirecionar("/view/sales/payments/paymentForm.xhtml?id=" + res + "&p=1");
                    } else {
                        this.vb.lanzarMensaje("error", "unexpectedError", "unexpectedError");
                    }

                } else {
                    this.vb.lanzarMensaje("error", "lblPaymentInvalid", "lblPaymentInvalid");

                }

            } else {
                this.vb.lanzarMensaje("error", "lblSelectCust", "lblSelectCust");
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.PaymentController.save()");
            //this.vb.lanzarMensajeSinBundle("error", e.toString(), e.getMessage());
            e.printStackTrace();
        }

    }

    public void print(FbInvoice i, HttpServletRequest req) {
        try {
            String jasperFile = "payment";

            this.invoiceModal = this.iFacade.generatePayment(i, this.userData.getCurrentCia().getLogo(), this.iFacade.getCompiledFile(jasperFile, req), this.userData.formatMaster(DAmountCredit), this.userData.formatMaster(DAmountApply));

            //this.validationBean.updateComponent("pdf");
            this.vb.ejecutarJavascript("$('.invoiceModal').modal();");
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.print()");
        }
    }

    public void regresar() {
        if (this.dir.equals("0")) {
            this.vb.redirecionar("/view/sales/sales.xhtml");
        } else {
            this.vb.redirecionar("/view/sales/customer/customerDetail.xhtml?id=" + this.dir);
            //this.userData.setDirCust("0");
        }
    }

}
