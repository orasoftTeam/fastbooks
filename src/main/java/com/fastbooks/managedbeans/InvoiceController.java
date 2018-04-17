/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbInvoiceFacade;
import com.fastbooks.modelo.FbInvoice;
import com.fastbooks.util.GlobalParameters;
import com.fastbooks.util.SendMails;
import com.fastbooks.util.ValidationBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
    FbInvoice currentIn = new FbInvoice();

    private @Getter
    @Setter
    String invoiceModal = "0";
    private @Getter
    @Setter
    String subjet = "";
    private @Getter
    @Setter
    String body = "";

    public void init() {
        try {
            iList = iFacade.getInvoicesByIdCia(this.userData.getCurrentCia().getIdCia().toString());
            if (!this.userData.getUses().equals("0")) {
                this.validationBean.lanzarMensaje("info", "lblInvoiceAddSuccess", "blank");
            }
            System.out.println("com.fastbooks.managedbeans.InvoiceController.init()");
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
        if (t.equals("IN")) {
            res = this.validationBean.getMsgBundle("lblInvoiceTypeIn");
        }

        return res;
    }

    public String formatStatus(String s) {
        String res = "";
        if (s.equals("OP")) {
            res = this.validationBean.getMsgBundle("lblInvoiceStatus");
        }

        return res;
    }

    public void print(FbInvoice i) {
        this.invoiceModal = this.iFacade.generateInvoice(i,this.userData.getCurrentCia().getLogo());
        //this.userData.setSInvoice(invoiceModal);
        this.currentIn = i;
       // this.validationBean.updateComponent("pdf");
        this.validationBean.ejecutarJavascript("$('.invoiceModal').modal();");
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
            sm.sendSimpleMail(currentIn.getCustEmail(), currentIn.getIdCia().getEmail(), this.subjet, this.body, gp.getAppPath() + this.invoiceModal);
            this.validationBean.lanzarMensajeSinBundle("info", "Enviado con exito", "");
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.sendReminder()");
            e.printStackTrace();
        }
    }
}
