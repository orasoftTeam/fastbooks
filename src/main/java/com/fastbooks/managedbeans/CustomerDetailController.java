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
import com.fastbooks.util.ValidationBean;
import java.io.Serializable;
import java.math.BigDecimal;
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

    /*End estimate stuff*/
    public CustomerDetailController() {
    }

    @PostConstruct
    public void init() {
        title = "Customer Detail";
        System.out.println("com.fastbooks.managedbeans.CustomerDetailController.init()");
        if (!this.userData.getUses().equals("0")) {
            this.vb.lanzarMensaje("info", this.userData.getUses(), "blank");
            this.vb.updateComponent("CustomerDetailForm:messages");
        }
        this.setCustomer();
    }

    public void setCustomer() {
        HttpServletRequest req = (HttpServletRequest) vb.getRequestContext();
        int c = 0;
        this.idCust = req.getParameter("id");
        if (this.idCust != null && !this.idCust.isEmpty()) {
            this.currentCust = cFacade.getCustomerByIdCust(idCust);
            if (this.currentCust != null) {
                if (userData != null && userData.getCurrentCia() != null) {
                    this.idCia = userData.getCurrentCia().getIdCia().toString();
                    transactionList = iFacade.getInvoicesByIdCust(idCust, idCia);
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
            //this.validationBean.lanzarMensajeSinBundle("error", this.invoiceModal, "");
            this.currentIn = i;
            // this.validationBean.updateComponent("pdf");
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
        String exp = "$('.updateStatusModal').modal();";
        exp += !in.getStatus().equals("PE") ? "$('.updateEstimateStatus').show();" : "";
        this.vb.ejecutarJavascript(exp);
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

    }

}
