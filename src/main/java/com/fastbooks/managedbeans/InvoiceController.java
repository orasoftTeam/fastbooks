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
import com.fastbooks.util.GlobalParameters;
import com.fastbooks.util.SendMails;
import com.fastbooks.util.ValidationBean;
import com.fastbooks.util.WriteXMLFile;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Luis Eduardo Valdez
 */
@Named(value = "invoiceTableController")
@ViewScoped
public class InvoiceController implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    UserData userData;
    @EJB
    ValidationBean validationBean;
    @EJB
    FbInvoiceFacade iFacade;
    private @Getter
    @Setter
    List<FbInvoice> iList = new ArrayList<>();

    private @Getter
    @Setter
    List<FbInvoice> testList = new ArrayList<>();

    private @Getter
    @Setter
    List<FbCustomer> cList = new ArrayList<>();
    @EJB
    FbCustomerFacade cFacade;
    private @Getter
    @Setter
    FbInvoice currentIn = new FbInvoice();
    private @Getter
    @Setter
    String status;
    private @Getter
    @Setter
    String invoiceModal = "0";
    private @Getter
    @Setter
    String subjet = "";
    private @Getter
    @Setter
    String body = "";
    private @Getter
    @Setter
    String fType = "0";
    private @Getter
    @Setter
    String fStatus = "0";
    private @Getter
    @Setter
    String fShiVia = "";
    private @Getter
    @Setter
    String fDays = "";
    private @Getter
    @Setter
    String fFrom = "";
    private @Getter
    @Setter
    String fTo = "";
    private @Getter
    @Setter
    String fIdCust = "0";
    private @Getter
    @Setter
    String fDate = "0";
    private @Getter
    @Setter
    List<FbInvoice> idInvoices = new ArrayList<>();
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

    /* @PostConstruct
    public void post(){
            testList = iFacade.getInvoicesByIdCia("1");
       }*/
    public void setType(String tipo, String url) {

        this.userData.setInvoiceTypeForm(tipo);

        this.validationBean.redirecionar("/view/sales/invoiceForm.xhtml");
    }

    @PostConstruct
    public void init() {
        try {
            System.out.println("INIT INVOICES!!!!");
            if (this.userData.getInvoiceSql().equals("0")) {
                //iList =  iFacade.getInvoicesByIdCiaNonJpa(this.userData.getCurrentCia().getIdCia().toString());
                iList = iFacade.getInvoicesByIdCia(this.userData.getCurrentCia().getIdCia().toString());
                /*WriteXMLFile xml = new WriteXMLFile();
                xml.crearXML(iList);*/
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Calendar cal = Calendar.getInstance();
                this.fFrom = dateFormat.format(cal.getTime());
            } else {
                iList = iFacade.getInvoicesByIdCiaFilter(this.userData.getInvoiceSql());
            }

            cList = cFacade.getCustomersByIdCia(this.userData.getCurrentCia().getIdCia().toString());
            if (!this.userData.getUses().equals("0")) {
                this.validationBean.lanzarMensaje("info", this.userData.getUses(), "blank");
                this.validationBean.updateComponent("tblInvoiceForm:messages");
            }

            System.out.println("com.fastbooks.managedbeans.InvoiceController.init()");
            this.showInvoice();
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.init()");
            e.printStackTrace();
        }
    }

    public String formatDate(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(d);
    }

    public String formatType(String t) {
        String res = "";
        switch (t) {
            case "IN":
                res = this.validationBean.getMsgBundle("lblInvoiceTypeIn");
                break;
            case "ES":
                res = this.validationBean.getMsgBundle("lblEstimate");
                break;
            case "SR":
                res = this.validationBean.getMsgBundle("salesR");
                break;
            case "PA":
                res = this.validationBean.getMsgBundle("payment");
                break;
            default:
                break;
        }

        return res;
    }

    /*
    Pending = PE
        Accepted = AC
        Closed = CL
        Rejected RJ
     */
    public String formatStatus(String s) {
        String res = "";
        switch (s) {
            case "OP":
                res = this.validationBean.getMsgBundle("lblInvoiceStatusOpen");
                break;
            case "CA":
                res = this.validationBean.getMsgBundle("lblInvoiceStatusCancel");
                break;
            case "PA":
                res = this.validationBean.getMsgBundle("lblInvoiceStatusPartial");
                break;
            case "OV":
                res = this.validationBean.getMsgBundle("lblInvoiceStatusOverdue");
                break;
            case "CL":
                res = this.validationBean.getMsgBundle("lblInvoiceStatusClosed");
                break;
            case "PE":
                res = this.validationBean.getMsgBundle("lblESPending");
                break;
            case "AC":
                res = this.validationBean.getMsgBundle("lblAccepted");
                break;
            case "RJ":
                res = this.validationBean.getMsgBundle("lblRejected");
                break;

            case "PD":
                res = this.validationBean.getMsgBundle("lblPaid");
                break;
            case "UN":
                res = this.validationBean.getMsgBundle("lblUnapplied");
                break;

            default:
                break;
        }

        return res;
    }

    public void print(FbInvoice i, HttpServletRequest req) {
        try {
            String jasperFile = i.getIdCust() == null ? "salesReceiptSinCust_1" : "invoice_1";

            this.invoiceModal = this.iFacade.generateInvoice(i, this.userData.getCurrentCia().getLogo(), this.iFacade.getCompiledFile(jasperFile, req), formatType(i.getType()),this.userData.formatMaster(i.getActualBalance().toString()));

            //this.userData.setSInvoice(invoiceModal);
            //this.validationBean.lanzarMensajeSinBundle("error", this.invoiceModal, "");
            this.currentIn = i;
            // this.validationBean.updateComponent("pdf");
            this.validationBean.ejecutarJavascript("$('.invoiceModal').modal();");
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.print()");
        }
    }

    public void showInvoice() {
        if (!this.userData.getSInvoice().equals("0")) {
            this.invoiceModal = this.userData.getSInvoice();
            this.validationBean.ejecutarJavascript("$('.invoiceModal').modal();");
            this.userData.setSInvoice("0");
        }

    }

    public void sendReminder() {
        try {
            SendMails sm = new SendMails();
            GlobalParameters gp = new GlobalParameters();
            sm.sendMailWithAttach(currentIn.getCustEmail(), currentIn.getIdCia().getEmail(), this.subjet, this.body, gp.getAppPath() + this.invoiceModal);
            this.validationBean.lanzarMensajeSinBundle("info", "Enviado con exito", "");
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.sendReminder()");
            e.printStackTrace();
        }
    }

    public void edit(FbInvoice in) {
        this.userData.setFbInvoice(in);
        if (!in.getType().equals("PA")) {
            this.userData.setInvoiceTypeForm(in.getType());
            this.validationBean.redirecionar("/view/sales/invoiceForm.xhtml?id=" + in.getIdInvoice().toString());
        } else if (in.getType().equals("PA")) {
            this.validationBean.redirecionar("/view/sales/payments/paymentForm.xhtml?id=" + in.getIdInvoice().toString());
        }
    }

    public void copy(FbInvoice in) {
        
        in.setNoDot("copy");
        this.userData.setFbInvoice(in);
        this.userData.setInvoiceTypeForm(in.getType());
        this.validationBean.redirecionar("/view/sales/invoiceForm.xhtml");
    }

    public void cancel() {
        String res = this.iFacade.actInvoice(currentIn, "D");
        if (res.equals("0")) {
            this.validationBean.lanzarMensaje("info", "lblInCancelSuccess", "blank");
        } else {
            this.validationBean.lanzarMensajeSinBundle("error", res, "");
        }

    }

    public void assign(FbInvoice in) {
        currentIn = in;
        this.validationBean.ejecutarJavascript("$('.cancelModal').modal()");
    }

    public boolean showOptions(String status, String type, String option) {
        boolean flag = false;

        switch (option) {
            case "PRINT":
                if (!type.equals("PA")) {
                    flag = true;
                }

                break;

            case "EDIT":
                flag = true;
                break;

            case "ESTATUS":
                if (type.equals("ES")) {
                    flag = true;
                }
                break;
            case "COPY":
                if (!type.equals("PA")) {
                    flag = true;
                }
                break;
            case "REPAY":
                if (type.equals("IN")) {
                    if (status.equals("OV") || status.equals("OP") || status.equals("PA")) {
                        flag = true;
                    }
                    
                    
                }
                break;                

            default:
                System.out.println("You do nutin");
                break;

        }

        return flag;
    }



    public void applyFilter() {

        String query = "SELECT * FROM FB_INVOICE WHERE ID_CIA = " + this.userData.getCurrentCia().getIdCia().toString() + " ";

        if (!this.fType.equals("0")) {
            query += "AND TYPE = '" + this.fType + "' ";
        }

        if (!this.fStatus.equals("0")) {
            query += "AND STATUS = '" + this.fStatus + "' ";
        }

        if (!this.fShiVia.isEmpty()) {
            query += "AND SHIP_VIA = '" + this.fShiVia + "' ";
        }

        /*if (!this.fDate.equals("0")) {
            query += "AND to_date(IN_DATE,'MM/dd/yyyy') = to_date('"+ this.fDate+"','MM/dd/yyyy') " ;
        } */
        if (!this.fDate.equals("0")) {
            query += " AND to_date(IN_DATE,'MM/dd/yyyy') BETWEEN sysdate-" + this.fDate + " AND sysdate ";
        }

        if (this.fDate.equals("0")) {
            if (this.fFrom.isEmpty()) {
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Calendar cal = Calendar.getInstance();
                this.fFrom = dateFormat.format(cal.getTime());
            }

            if (this.fTo.isEmpty()) {
                query += " AND to_date(IN_DATE,'MM/dd/yyyy') >= to_date('" + this.fFrom + "','MM/dd/yyyy') ";
            } else {
                query += " AND to_date(IN_DATE,'MM/dd/yyyy') BETWEEN to_date('" + this.fFrom + "','MM/dd/yyyy') AND to_date('" + this.fTo + "','MM/dd/yyyy') ";
            }
        }

        if (!this.fIdCust.equals("0")) {
            query += " AND ID_CUST =  " + this.fIdCust;
        }
           query += " and status != 'DEL'";
        this.userData.setInvoiceSql(query);
        System.out.println("com.fastbooks.managedbeans.InvoiceController.applyFilter()");

    }

    public void resetFilter() {
        this.userData.setInvoiceSql("0");
        fType = "0";
        fStatus = "0";
        fShiVia = "";
        fFrom = "";
        fTo = "";
        fIdCust = "0";
        fDate = "0";
    }

    public boolean showFilters(String sec, String value) {
        /*
        private @Getter
    @Setter
    String fType = "0";
    private @Getter
    @Setter
    String fStatus = "0";
    private @Getter
    @Setter
    String fShiVia = "";
    private @Getter
    @Setter
    String fFrom = "";
    private @Getter
    @Setter
    String fTo = "";
    private @Getter
    @Setter
    String fIdCust = "0";
    private @Getter
    @Setter
    String fDate = "0";
        
         */
        boolean flag = false;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        String tmp = dateFormat.format(cal.getTime());
        if (sec.equals("enq")) {
            if (fType.equals("0") && fStatus.equals("0") && fShiVia.isEmpty() && this.fFrom.equals(tmp) && this.fTo.isEmpty() && fIdCust.equals("0") && fDate.equals("0")) {
                System.out.println("no hay filtro");
            } else {
                System.out.println("si hay filtro");
                flag = true;
            }
        }

        return flag;
    }

    public void addToInvoiceList(FbInvoice idInvoice) {
        int c = 0;
        for (FbInvoice str : idInvoices) {
            if (str.getIdInvoice().toString().equals(idInvoice.getIdInvoice().toString())) {
                c++;
            }
        }

        if (c == 0) {
            idInvoices.add(idInvoice);
        } else {
            for (int i = 0; i < idInvoices.size(); i++) {
                if (idInvoices.get(i).getIdInvoice().toString().equals(idInvoice.getIdInvoice().toString())) {
                    idInvoices.remove(i);
                }
            }

        }
        System.out.println("com.fastbooks.managedbeans.InvoiceController.addToInvoiceList()");
    }

    public void printTransactions(HttpServletRequest req) {
        String res = "";
        try {
            for (int i = 0; i < idInvoices.size(); i++) {
                res += idInvoices.get(i);
                if (i != idInvoices.size() - 1) {
                    res += ",";
                }
            }
            this.invoiceModal = this.iFacade.printTransactions(res, this.userData.getCurrentCia().getLogo(), this.iFacade.getCompiledFile("multipleInvoice", req), this.userData.getCurrentCia().getIdCia().toString());
            //this.userData.setSInvoice(invoiceModal);
            //this.validationBean.lanzarMensajeSinBundle("error", this.invoiceModal, "");
            //this.currentIn = i;
            // this.validationBean.updateComponent("pdf");
            this.validationBean.ejecutarJavascript("$('.invoiceModal').modal();");
            this.idInvoices = new ArrayList<>();
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.print()");
            e.printStackTrace();
        }
    }

    public void packingSlip(HttpServletRequest req) {
        String res = "";
        try {
            for (int i = 0; i < idInvoices.size(); i++) {
                res += idInvoices.get(i);
                if (i != idInvoices.size() - 1) {
                    res += ",";
                }
            }
            this.invoiceModal = this.iFacade.printTransactions(res, this.userData.getCurrentCia().getLogo(), this.iFacade.getCompiledFile("packingSlip", req), this.userData.getCurrentCia().getIdCia().toString());
            //this.userData.setSInvoice(invoiceModal);
            //this.validationBean.lanzarMensajeSinBundle("error", this.invoiceModal, "");
            //this.currentIn = i;
            // this.validationBean.updateComponent("pdf");
            this.validationBean.ejecutarJavascript("$('.invoiceModal').modal();");
            this.idInvoices = new ArrayList<>();
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.print()");
            e.printStackTrace();
        }
    }

    public boolean disableBatch(String op) {
        boolean flag = true;
        /*
        PT = PRINT TRANSACTIONS
        PP = PRINT PACKING SLIP
        ST = SEND TRANSACTIONS
        SR = SEND REMINDER
        
         */
        if (op.equals("PT")) {
            if (!idInvoices.isEmpty()) {
                flag = false;
            }
        } else if (op.equals("PP")) {
            if (!idInvoices.isEmpty()) {
                flag = false;
                for (FbInvoice fbInvoice : idInvoices) {
                    if (fbInvoice.getType().equals("ES")) {
                        flag = true;
                    }
                }
            }

        }

        return flag;
    }

    public void assignEstimate(FbInvoice in) {

        this.currenInvoice = in;
        this.estimateStatus = in.getStatus();
        this.AccBy = in.getEsAccby();
        this.AccDate = in.getEsAccdate();
        String exp = "$('.updateStatusModal').modal();";
        exp += !in.getStatus().equals("PE") ? "$('.updateEstimateStatus').show();" : "";
        this.validationBean.ejecutarJavascript(exp);
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

        // this.validationBean.redirecionar("/view/sales/sales.xhtml");
    }

    public void recievePayment(String idCust, String idInvoice) {
        this.validationBean.redirecionar("/view/sales/payments/paymentForm.xhtml?idc="+idCust+"&idi="+idInvoice);
    }

}
