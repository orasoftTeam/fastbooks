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
import com.fastbooks.modelo.FbStmtDetail;
import com.fastbooks.util.ValidationBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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
public class StatementController implements Serializable {

    private @Getter
    @Setter
    String title;
    private @Getter
    @Setter
    String idCust;
    private @Getter
    @Setter
    FbCustomer currentCust;
    private @Getter
    @Setter
    String idCia;
    private @Getter
    @Setter
    String stmtType;
    private @Getter
    @Setter
    String stmtDate;
    private @Getter
    @Setter
    String startDate;
    private @Getter
    @Setter
    String endDate;
    private @Getter
    @Setter
    String idStmt;

    private @Getter
    @Setter
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private @Getter
    @Setter
    DateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    @EJB
    private @Getter
    @Setter
    ValidationBean vb;
    @EJB
    FbCustomerFacade cFacade;
    @EJB
    FbInvoiceFacade iFacade;
    @Inject
    UserData userData;
    private @Getter
    @Setter
    List<FbInvoice> tranList = new ArrayList<>();

    private @Getter
    @Setter
    FbStatement statement = null;

    private @Getter
    @Setter
    String thirdColumn;

    private @Getter
    @Setter
    boolean showTbl = false;

    private @Getter
    @Setter
    String stmtPdf = "0";
    
    private @Getter
    @Setter
    String dir = "0";

    private @Getter
    @Setter
    boolean alreadyCreated = false;

    private @Getter
    @Setter
    boolean view = false;

    /**
     * Creates a new instance of StatementController
     */
    @PostConstruct
    public void init() {
        this.title = this.vb.getMsgBundle("lblCreateStatement");
        this.stmtType = "BF";
        Calendar cal = Calendar.getInstance();
        this.stmtDate = sdf.format(cal.getTime());
        this.endDate = sdf.format(cal.getTime());
        HttpServletRequest req = (HttpServletRequest) vb.getRequestContext();
        this.dir = req.getParameter("dir");

        cal.add(Calendar.MONTH, -1);
        this.startDate = sdf.format(cal.getTime());
        this.setCustomer();
        this.setStatementView();
    }

    public void setCustomer() {
        HttpServletRequest req = (HttpServletRequest) vb.getRequestContext();
        int c = 0;
        this.idCust = req.getParameter("id");
        if (userData != null && userData.getCurrentCia() != null) {
            this.idCia = this.userData.getCurrentCia().getIdCia().toString();
            if (this.idCust != null && !this.idCust.isEmpty()) {
                this.currentCust = cFacade.getCustomerByIdCust(idCust, this.idCia);
                if (this.currentCust != null) {

                } else {
                    this.regresar();
                }
            } else {
                this.regresar();
            }
        } else {
            this.regresar();
        }

    }

    public void setStatementView() {
        HttpServletRequest req = (HttpServletRequest) vb.getRequestContext();
        this.idStmt = req.getParameter("stmt");
        if (this.idStmt != null) {
            this.statement = this.iFacade.getStmtByIdStmt(this.idCia, this.idStmt, this.idCust);
            if (this.statement != null) {
                List<FbStmtDetail> listaDet = this.iFacade.getStmtDetailByIdStmt(this.idStmt);
                this.statement.setFbStmtDetailList(listaDet);
                this.view = true;
                this.stmtType = this.statement.getType();
                this.startDate = this.statement.getStartDate();
                this.endDate = this.statement.getEndDate();
                this.stmtDate = this.statement.getStmtDate();
                switch (this.stmtType) {
                    case "BF":

                        this.thirdColumn = this.vb.getMsgBundle("lblStmtBFBalance");
                        this.showTbl = true;
                        break;

                    case "OI":

                        this.showTbl = true;
                        this.vb.ejecutarJavascript("$('.startEnd').hide();");
                        break;

                    case "TS":

                        this.thirdColumn = this.vb.getMsgBundle("lblStmtTSRecieved");
                        this.showTbl = true;
                        break;
                }
                this.vb.ejecutarJavascript("$('.selectMenu').hide()");
                this.vb.ejecutarJavascript("$('.dBalance').show();");
            }
        }
    }

    public StatementController() {
    }

    public void setTranList() {
        this.tranList = this.iFacade.getTransactionsForStmt(idCia, idCust, startDate, endDate, stmtType);

    }

    public void regresar() {
        
        if (this.dir == null) {
           this.vb.redirecionar("/view/sales/customer/customerDetail.xhtml?id=" + this.idCust); 
        }else{
            this.vb.redirecionar("/view/sales/sales.xhtml"); 
        }
        
    }

    public void formatBalanceFoward() {
        try {
            BigDecimal preDateBalance = new BigDecimal(BigInteger.ZERO);
            preDateBalance = new BigDecimal(this.iFacade.getPreBalance(idCia, idCust, startDate));
            /*Double postDateBalance = 0.00;
            for (FbInvoice fbInvoice : tranList) {
                if (fbInvoice.getType().equals("IN") && (!fbInvoice.getStatus().equals("PD"))) {
                    postDateBalance += fbInvoice.getActualBalance().doubleValue();
                }
            }
            preDateBalance = new BigDecimal(this.currentCust.getBalance().doubleValue() - postDateBalance).setScale(2, BigDecimal.ROUND_HALF_UP);*/
            this.statement = new FbStatement();
            List<FbStmtDetail> stmtList = new ArrayList<>();
            FbStmtDetail fLine = new FbStmtDetail();
            fLine.setTranDate(sdf.parse(startDate));
            fLine.setDescripcion(this.vb.getMsgBundle("lblStatementType1"));
            fLine.setBalance(preDateBalance.setScale(2, BigDecimal.ROUND_HALF_UP));
            fLine.setIdTran(new FbInvoice(BigDecimal.ZERO));
            stmtList.add(fLine);
            for (FbInvoice t : tranList) {
                FbStmtDetail sd = new FbStmtDetail();
                sd.setTranDate(sdf.parse(t.getInDate()));
                String desc = "";
                desc = this.userData.formatType(t.getType());
                if (!t.getType().equals("PA")) {
                    desc += " No." + t.getNoDot();
                }
                sd.setDescripcion(desc);
                BigDecimal amount = new BigDecimal(BigInteger.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP);
                if (t.getType().equals("PA")) {
                    amount = new BigDecimal(t.getTotal().doubleValue() * -1).setScale(2, BigDecimal.ROUND_HALF_UP);
                    preDateBalance = new BigDecimal(preDateBalance.doubleValue() - t.getTotal().doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    amount = t.getTotal().setScale(2, BigDecimal.ROUND_HALF_UP);
                    if (t.getType().equals("IN")) {
                        preDateBalance = new BigDecimal(preDateBalance.doubleValue() + t.getTotal().doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                }
                sd.setAmount(amount);
                sd.setBalance(preDateBalance);
                sd.setIdTran(t);
                stmtList.add(sd);

            }

            for (FbStmtDetail fbStmtDetail : stmtList) {
                System.out.println("Date: " + sdf.format(fbStmtDetail.getTranDate()) + "| DESCRIPTION: " + fbStmtDetail.getDescripcion() + "| Amount: " + fbStmtDetail.getAmount() + "| BALANCE: " + fbStmtDetail.getBalance());
            }
            System.out.println("===============================================================================================================================");
            this.statement.setFbStmtDetailList(stmtList);
        } catch (ParseException e) {
            System.out.println("com.fastbooks.managedbeans.StatementController.formatBalanceFoward()");
            e.printStackTrace();
        }
    }

    public void formatOpenItem() {
        try {
            this.statement = new FbStatement();
            List<FbStmtDetail> stmtList = new ArrayList<>();
            for (FbInvoice t : tranList) {
                FbStmtDetail sd = new FbStmtDetail();
                sd.setTranDate(sdf.parse(t.getInDate()));
                sd.setDescripcion(this.vb.getMsgBundle("lblInvoiceTypeIn") + " No." + t.getNoDot() + " Due " + t.getDueDate());
                sd.setAmount(t.getTotal());
                sd.setBalance(t.getActualBalance());
                sd.setIdTran(t);
                stmtList.add(sd);
            }

            for (FbStmtDetail fbStmtDetail : stmtList) {
                System.out.println("Date: " + sdf.format(fbStmtDetail.getTranDate()) + "| DESCRIPTION: " + fbStmtDetail.getDescripcion() + "| Amount: " + fbStmtDetail.getAmount() + "| BALANCE: " + fbStmtDetail.getBalance());
            }
            System.out.println("===============================================================================================================================");
            this.statement.setFbStmtDetailList(stmtList);
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.StatementController.formatOpenItem()");
            e.printStackTrace();
        }

    }

    public void apply() {
        this.parseDates();
        this.setTranList();
        switch (this.stmtType) {
            case "BF":
                this.formatBalanceFoward();
                this.statement.setStartDate(startDate);
                this.statement.setEndDate(endDate);
                this.thirdColumn = this.vb.getMsgBundle("lblStmtBFBalance");
                this.showTbl = true;
                break;

            case "OI":
                this.formatOpenItem();
                this.thirdColumn = this.vb.getMsgBundle("lblStmtOIOpenAmount");
                this.showTbl = true;
                this.vb.ejecutarJavascript("$('.startEnd').hide();");
                break;

            case "TS":
                this.formatTransactionStatement();
                this.statement.setStartDate(startDate);
                this.statement.setEndDate(endDate);
                this.thirdColumn = this.vb.getMsgBundle("lblStmtTSRecieved");
                this.showTbl = true;
                break;
        }
        this.vb.ejecutarJavascript("$('.selectMenu').hide()");
        this.statement.setStmtDate(stmtDate);

        Double totalAmount = 0.00;
        Double totalBalance = 0.00;

        for (FbStmtDetail d : this.statement.getFbStmtDetailList()) {
            if (d.getAmount() != null) {
                totalAmount += d.getAmount().doubleValue();
            }
            if (d.getBalance() != null) {
                totalBalance += d.getBalance().doubleValue();
            }

        }

        this.statement.setType(this.stmtType);
        this.statement.setTotalAmount(new BigDecimal(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP));
        this.statement.setTotalBalance(new BigDecimal(totalBalance).setScale(2, BigDecimal.ROUND_HALF_UP));
        this.statement.setIdCia(this.userData.getCurrentCia());
        this.statement.setIdCust(this.currentCust);
    }

    public void parseDates() {
        try {
            this.stmtDate = sdf.format(sd.parse(this.stmtDate));
            this.startDate = sdf.format(sd.parse(this.startDate));
            this.endDate = sdf.format(sd.parse(this.endDate));
        } catch (ParseException e) {
            System.out.println("com.fastbooks.managedbeans.StatementController.parseDates()");
            e.printStackTrace();
        }
    }

    public void formatTransactionStatement() {
        try {
            this.statement = new FbStatement();
            List<FbStmtDetail> stmtList = new ArrayList<>();
            for (FbInvoice t : tranList) {
                FbStmtDetail sd = new FbStmtDetail();
                sd.setTranDate(sdf.parse(t.getInDate()));
                sd.setDescripcion(this.userData.formatType(t.getType()) + " No." + t.getNoDot());
                sd.setAmount(t.getTotal());
                sd.setBalance(new BigDecimal(t.getTotal().doubleValue() - t.getActualBalance().doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
                sd.setIdTran(t);
                stmtList.add(sd);
            }

            for (FbStmtDetail fbStmtDetail : stmtList) {
                System.out.println("Date: " + sdf.format(fbStmtDetail.getTranDate()) + "| DESCRIPTION: " + fbStmtDetail.getDescripcion() + "| Amount: " + fbStmtDetail.getAmount() + "| BALANCE: " + fbStmtDetail.getBalance());

            }
            System.out.println("===============================================================================================================================");
            this.statement.setFbStmtDetailList(stmtList);
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.StatementController.formatTransactionStatement()");
            e.printStackTrace();
        }
    }

    public String displayDate(String date) {
        String res = "";
        try {
            res = sdf.format(sd.parse(date));
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.StatementController.displayDate()");
            e.printStackTrace();
        }

        return res;
    }

    public void reset() {
        vb.redirecionar("/view/sales/customer/statements.xhtml?id=" + this.idCust);
    }

    public void save() {
        try {
            this.statement.setIdStmt(BigDecimal.ZERO);
            String res = this.iFacade.actStatement(statement, "A");
            if (!res.equals("-2")) {
                System.out.println("SUCCESS!!!" + res);
                statement.setIdStmt(new BigDecimal(res));
                res = this.iFacade.generateStmt(statement, statement.getIdCia().getLogo(), this.iFacade.getCompiledFile("statement", this.vb.getRequestContext()), this.userData.formatMaster(this.currentCust.getBalance().toString()));
                System.out.println("pdf file!!!" + res);
                this.stmtPdf = res;
                this.alreadyCreated = true;
                /*this.regresar();
                this.userData.setUses("lblStatementAddSuccess");*/
            } else {
                System.out.println("FAIL!!!" + res);
                this.regresar();
                this.userData.setUses("lblStatementFailError");
            }
            System.out.println(this.statement);
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.StatementController.save()");
            e.printStackTrace();
        }
    }

    public void print() {
        try {
            this.statement.setIdStmt(new BigDecimal(this.idStmt));
            this.stmtPdf = this.iFacade.generateStmt(statement, statement.getIdCia().getLogo(), this.iFacade.getCompiledFile("statement", this.vb.getRequestContext()), this.userData.formatMaster(this.currentCust.getBalance().toString()));
            this.alreadyCreated = true;
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.StatementController.print()");
            e.printStackTrace();
        }

    }

    public void delete() {
        try {
            this.statement.setIdStmt(new BigDecimal(this.idStmt));
            String res = this.iFacade.actStatement(this.statement, "D");
            if (!res.equals("-2")) {
                this.userData.setUses("lblStatementDelSuccess");
                this.regresar();
                
            } else {
                System.out.println("FAIL!!!" + res);
                this.userData.setUses("lblStatementFailError");
                this.regresar();
                
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.StatementController.delete()");
            e.printStackTrace();
        }
    }
    
    public void goToTran(String type,String idTran){
        String url = "/view/sales/sales.xhtml";
        if (type.equals("PA")) {
            url = "/view/sales/payments/paymentForm.xhtml?id="+idTran+"&stmt="+this.idStmt;
        }else if(!type.isEmpty()){
        url = "/view/sales/invoiceForm.xhtml?id="+idTran+"&stmt="+this.idStmt;
        
        }
        this.vb.redirecionar(url);
    }

}
