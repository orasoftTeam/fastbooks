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
import com.fastbooks.util.GlobalParameters;
import com.fastbooks.util.PanelesVentas;
import com.fastbooks.util.SendMails;
import com.fastbooks.util.ValidationBean;
import com.fastbooks.util.WriteXMLFile;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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
    List<FbStatement> stmtList = new ArrayList<>();
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
    String idCia = "0";
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

    private @Getter
    @Setter
    BigDecimal totalBalance = new BigDecimal(BigInteger.ZERO);
    private @Getter
    @Setter
    BigDecimal totalTotal = new BigDecimal(BigInteger.ZERO);

    /*Panel stuff*/
    private @Getter
    @Setter
    int noEstimates = 0;
    private @Getter
    @Setter
    int noUnbilled = 0;
    private @Getter
    @Setter
    int noOverdue = 0;
    private @Getter
    @Setter
    int noOpen = 0;
    private @Getter
    @Setter
    int noPaid = 0;

    private @Getter
    @Setter
    int panelFlag = 0;

    private @Getter
    @Setter
    BigDecimal totalEstimates = new BigDecimal(BigInteger.ZERO);
    private @Getter
    @Setter
    BigDecimal totalUnbilled = new BigDecimal(BigInteger.ZERO);
    private @Getter
    @Setter
    BigDecimal totalOverdue = new BigDecimal(BigInteger.ZERO);
    private @Getter
    @Setter
    BigDecimal totalOpen = new BigDecimal(BigInteger.ZERO);
    private @Getter
    @Setter
    BigDecimal totalPaid = new BigDecimal(BigInteger.ZERO);

    private @Getter
    @Setter
    PanelesVentas panelVentas;
    private @Getter
    @Setter
    boolean hasPanelFilter = false;
    
    private @Getter @Setter String stmtPdf = "0";

    /*panel stuff end */
    public void setPanelData() {

        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -31);
            Date dateBefore30Days = cal.getTime();

            Double acumEstimates = 0.0;
            Double acumUnbilled = 0.0;
            Double acumOverdue = 0.0;
            Double acumOpen = 0.0;
            Double acumPaid = 0.0;

            for (FbInvoice fbInvoice : iList) {
                if (fbInvoice.getType().equals("ES")) {

                    noEstimates++;
                    acumEstimates += fbInvoice.getTotal().doubleValue();
                }

                if (fbInvoice.getType().equals("SR")) {
                    noUnbilled++;
                    acumUnbilled += fbInvoice.getTotal().doubleValue();
                }

                if (fbInvoice.getType().equals("IN")) {

                    if (fbInvoice.getStatus().equals("OV")) {
                        noOverdue++;
                        acumOverdue += fbInvoice.getActualBalance().doubleValue();
                    }

                    if (fbInvoice.getStatus().equals("PA") || fbInvoice.getStatus().equals("OP")) {
                        noOpen++;
                        acumOpen += fbInvoice.getActualBalance().doubleValue();
                    }

                    if (fbInvoice.getStatus().equals("PA") || fbInvoice.getStatus().equals("PD")) {
                        Date invoiceDate = sdf.parse(fbInvoice.getInDate());
                        if (invoiceDate.after(dateBefore30Days)) {
                            noPaid++;
                            if (fbInvoice.getStatus().equals("PA")) {
                                acumPaid += fbInvoice.getTotal().doubleValue() - fbInvoice.getActualBalance().doubleValue();
                            } else {
                                acumPaid += fbInvoice.getTotal().doubleValue();
                            }
                        }

                    }

                }

            }

            this.totalEstimates = new BigDecimal(acumEstimates).setScale(2, BigDecimal.ROUND_HALF_UP);
            this.totalUnbilled = new BigDecimal(acumUnbilled).setScale(2, BigDecimal.ROUND_HALF_UP);
            this.totalOverdue = new BigDecimal(acumOverdue).setScale(2, BigDecimal.ROUND_HALF_UP);
            this.totalOpen = new BigDecimal(acumOpen).setScale(2, BigDecimal.ROUND_HALF_UP);
            this.totalPaid = new BigDecimal(acumPaid).setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (ParseException e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.setPanelData()");
            e.printStackTrace();
        }
    }

    /*End Panel stuff*/
 /* @PostConstruct
    public void post(){
            testList = iFacade.getInvoicesByIdCia("1");
       }*/
    public void setType(String tipo, String url) {

        this.userData.setInvoiceTypeForm(tipo);

        this.validationBean.redirecionar("/view/sales/invoiceForm.xhtml");
    }

    public int setPanelSelected() {
        HttpServletRequest req = (HttpServletRequest) this.validationBean.getRequestContext();
        String pv = req.getParameter("pv");
        String js = "";
        int r = 5;
        if (pv != null) {
            switch (pv) {
                case "0":
                    js = "$('#estimates').addClass('selected');";
                    this.hasPanelFilter = true;
                    r = 0;
                    break;
                case "1":
                    js = "$('#unbilled').addClass('selected');";
                    this.hasPanelFilter = true;
                    r = 1;
                    break;
                case "2":
                    js = "$('#overdue').addClass('selected');";
                    this.hasPanelFilter = true;
                    r = 2;
                    this.panelFlag = 1;
                    break;
                case "3":
                    js = "$('#unpaid').addClass('selected');";
                    this.hasPanelFilter = true;
                    r = 3;
                    this.panelFlag = 2;
                    break;
                case "4":
                    js = "$('#paid').addClass('selected');";
                    this.hasPanelFilter = true;
                    r = 4;
                    break;
                default:
                    js = "$('.box').removeClass('selected');";
                    this.hasPanelFilter = false;
                    break;
            }
            this.validationBean.ejecutarJavascript(js);
        }

        return r;
    }

    @PostConstruct
    public void init() {
        try {
            System.out.println("INIT INVOICES!!!!");
            this.idCia = this.userData.getCurrentCia().getIdCia().toString();

            this.cList = cFacade.getCustomersByIdCia(this.idCia);

            this.panelVentas = iFacade.getPanelesInfo(this.idCia);
            this.cargarLista();

            Double acumBalance = 0.0;
            Double acumTotal = 0.0;
            for (FbInvoice fbInvoice : iList) {
                if (fbInvoice.getActualBalance() != null && !fbInvoice.getType().equals("PA")) {
                    acumBalance += fbInvoice.getActualBalance().doubleValue();
                } else if (fbInvoice.getActualBalance() != null && fbInvoice.getType().equals("PA")) {
                    acumBalance -= fbInvoice.getActualBalance().doubleValue();
                }
                if (fbInvoice.getTotal() != null && !fbInvoice.getType().equals("PA")) {
                    acumTotal += fbInvoice.getTotal().doubleValue();
                }

            }
            this.totalBalance = new BigDecimal(acumBalance).setScale(2, BigDecimal.ROUND_HALF_UP);
            this.totalTotal = new BigDecimal(acumTotal).setScale(2, BigDecimal.ROUND_HALF_UP);

            //System.out.println("com.fastbooks.managedbeans.InvoiceController.init()");
            this.showInvoice();

        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.init()");
            e.printStackTrace();
        }
    }

    public void showAlert() {
        String uses = this.userData.getUses();
        if (!uses.equals("0")) {
            this.validationBean.lanzarMensaje("info", this.userData.getUses(), "blank");
            this.userData.setUses("0");
            this.validationBean.updateComponent("tblInvoiceForm:messages");

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

            this.invoiceModal = this.iFacade.generateInvoice(i, this.userData.getCurrentCia().getLogo(), this.iFacade.getCompiledFile(jasperFile, req), formatType(i.getType()), this.userData.formatMaster(i.getActualBalance().toString()));

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

    public void cargarLista() {
        HttpServletRequest req = (HttpServletRequest) this.validationBean.getRequestContext();
        String pv = req.getParameter("pv");

        if (pv != null) {
            this.resetFilter();
        }

        if (this.validarFilter()) {

            if (this.userData.getInvoiceFilterType().equals("ST")) {
                this.stmtList = this.iFacade.getStmtFilter(this.idCia, this.userData.getInvoiceFilterFrom(), this.userData.getInvoiceFilterTo(), this.userData.getInvoiceFilterIdCust());
            } else {
                this.iList = this.iFacade.applyFilter(this.idCia, this.userData.getInvoiceFilterType(), this.userData.getInvoiceFilterStatus(), this.userData.getInvoiceFilterSh(), this.userData.getInvoiceFilterFrom(),
                        this.userData.getInvoiceFilterTo(), this.userData.getInvoiceFilterIdCust());
            }

        } else {
            this.iList = iFacade.getInvoicesByIdCia(this.idCia, this.setPanelSelected());

        }
    }

    public void processFilter() {

        this.userData.setInvoiceFilterType(this.fType);
        this.userData.setInvoiceFilterStatus(this.fStatus);
        this.userData.setInvoiceFilterSh(this.fShiVia);
        this.userData.setInvoiceFilterFrom(this.fFrom);
        this.userData.setInvoiceFilterTo(this.fTo);
        this.userData.setInvoiceFilterIdCust(this.fIdCust);
        this.validationBean.redirecionar("/view/sales/sales.xhtml");

    }

    public boolean validarFilter() {
        //HttpServletRequest req = (HttpServletRequest) this.validationBean.getRequestContext();
        boolean flag = false;
        int c = 0;
        try {
            if (!this.userData.getInvoiceFilterType().equals("0")) {
                c++;
            }
            if (!this.userData.getInvoiceFilterStatus().equals("0")) {
                c++;
            }
            if (!this.userData.getInvoiceFilterSh().equals("0")) {
                c++;
            }
            if (!this.userData.getInvoiceFilterFrom().equals("0")) {
                c++;
            }
            if (!this.userData.getInvoiceFilterTo().equals("0")) {
                c++;
            }
            if (!this.userData.getInvoiceFilterIdCust().equals("0")) {
                c++;
            }

            if (c != 0) {
                flag = true;
            }

        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.aplicarFilter()");
            e.printStackTrace();
        }

        return flag;

    }

    public void resetFilter() {
        this.userData.setInvoiceFilterType("0");
        this.userData.setInvoiceFilterStatus("0");
        this.userData.setInvoiceFilterSh("0");
        this.userData.setInvoiceFilterFrom("0");
        this.userData.setInvoiceFilterTo("0");
        this.userData.setInvoiceFilterIdCust("0");
        //this.validationBean.redirecionar("/view/sales/sales.xhtml");
    }

    public void resetFilterBtn() {
        this.userData.setInvoiceFilterType("0");
        this.userData.setInvoiceFilterStatus("0");
        this.userData.setInvoiceFilterSh("0");
        this.userData.setInvoiceFilterFrom("0");
        this.userData.setInvoiceFilterTo("0");
        this.userData.setInvoiceFilterIdCust("0");
        this.validationBean.redirecionar("/view/sales/sales.xhtml");
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
        this.validationBean.redirecionar("/view/sales/payments/paymentForm.xhtml?idc=" + idCust + "&idi=" + idInvoice);
    }

    public String formatStmtType(String type) {
        String res = "";
        switch (type) {
            case "BF":
                res = this.validationBean.getMsgBundle("lblStatementType1");
                break;
            case "OI":
                res = this.validationBean.getMsgBundle("lblStatementType2");
                break;

            case "TS":
                res = this.validationBean.getMsgBundle("lblStatementType3");
                break;

        }
        return res;
    }
    
    
    public void viewStmt(String idCust, String idStmt){
        this.validationBean.redirecionar("/view/sales/customer/statements.xhtml?id="+idCust+"&stmt="+idStmt);
    }
    
    public void generateStmt(FbStatement statement){
        try {
            String res = this.iFacade.generateStmt(statement, statement.getIdCia().getLogo(), this.iFacade.getCompiledFile("statement", this.validationBean.getRequestContext()), this.userData.formatMaster(statement.getIdCust().getBalance().toString()));
            this.stmtPdf = res;
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.generateStmt()");
            e.printStackTrace();
        }
    
    
    }

}
