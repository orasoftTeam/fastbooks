/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbTaxFacade;
import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbTax;
import com.fastbooks.util.ValidationBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Guadalupe
 */
@Named(value = "taxController")
@ViewScoped
public class TaxController implements Serializable {

    @EJB
    ValidationBean validationBean;
    @Inject
    UserData userData;
    @EJB
    FbTaxFacade tFacade;

    private @Getter
    @Setter
    List<FbTax> taxList = new ArrayList<>();
    private @Getter
    @Setter
    FbTax tx;
    private @Getter
    @Setter
    FbTax tax;

    private @Getter
    @Setter
    boolean showForm = false;
    private @Getter
    @Setter
    String modalTitle = "Add a new tax";
    private @Getter
    @Setter
    String type;
    private @Getter
    @Setter
    String taxName;
    private @Getter
    @Setter
    String descripcion;
    private @Getter
    @Setter
    String rate;

    /**
     * Creates a new instance of TaxController
     */
    public TaxController() {
        tx = new FbTax();
    }

    public void newTax() {
        tx = new FbTax();
    }

    //Adding a new  tax
    public void addNewTax() {
        System.out.println("Ingresando a agregar nuevo tax" + tx);
        if (valCampos()) {
            FbCompania com = new FbCompania();
            com.setIdCia(BigDecimal.ZERO);
            this.tx.setIdCia(new FbCompania(userData.getCurrentCia().getIdCia()));
            this.tx.setIdCia(this.userData.getCurrentCia());
            this.tx.setIdTax(new BigDecimal("0"));
            if (this.tx.getRate().length() == 1) {
                this.tx.setRate("0.0" + this.tx.getRate());

            } else if (this.tx.getRate().length() > 1) {
                this.tx.setRate("0." + this.tx.getRate());

            }

            String res = tFacade.actTax(tx, "A");
            System.out.println("Res: " + res);
            if (res.equals("0")) {
                validationBean.lanzarMensaje("info", "taxAdded", "blank");
            } else if (res.equals("-1")) {
                validationBean.lanzarMensaje("error", "taxRepeatFail", "blank");
            } else if (res.equals("-2")) {
                validationBean.lanzarMensaje("error", "unexpectedError", "blank");
            }

            tx = new FbTax(); //limpiando..
        }
    }

    //getting tax list 
    public void init() {
        System.out.println("Obteniendo lista taxes" + taxList);
        try {
            taxList = tFacade.getTaxByIdCia(this.userData.getCurrentCia().getIdCia().toString());
            System.out.println("Obteniendo lista taxes" + taxList);
            if (!this.userData.getUses().equals("0")) {
                this.validationBean.lanzarMensaje("info", this.userData.getUses(), "blank");
                this.userData.setUses("0");
            }

        } catch (Exception e) {
            System.out.println("error obteniendo lista");
            e.printStackTrace();
        }

    }

    //edit&&delete
    public void action(FbTax t, String op) {

        this.tax = t;
        System.out.println("obteniendo objeto action " + tx);

        if (op.equals("U")) {
            this.validationBean.ejecutarJavascript("$('.modalPseudoClass2').modal();");
        } else {
            this.validationBean.ejecutarJavascript("$('.modalPseudoClass3').modal();");
        }

    }

    //updating tax
    public void editTax() {
        System.out.println("Ingresando a actualizar tax");
        String res = "";
       // if (valCampos()) {
                if (this.tax.getRate().length() == 1) {
                this.tax.setRate("0.0" + this.tax.getRate());

            } else if (this.tax.getRate().length() > 1) {
                this.tax.setRate("0." + this.tax.getRate());

            }

            try {
                res = tFacade.actTax(tax, "U");
                if (res.equals("0")) {
                    validationBean.lanzarMensaje("info", "taxUpdated", "blank");
                } else if (res.equals("-1")) {
                    validationBean.lanzarMensaje("error", "taxRepeatFail", "blank");
                } else if (res.equals("-2")) {
                    validationBean.lanzarMensaje("error", "unexpectedError", "blank");
                }
                tax = new FbTax(); //limpiando

            } catch (Exception e) {
                System.out.println("com.fastbooks.managedbeans.TaxController.editTax()");
                e.printStackTrace();
            //}
        }
    }

    public void deleteTax() {
        System.out.println("Ingresando a eliminar tax");
        String res = "";
        try {
            res = tFacade.actTax(tax, "D");
            System.out.println("resultado delete tax" + res);
            if (res.equals("0")) {
                validationBean.lanzarMensaje("info", "taxDeleted", "blank");
            } else if (res.equals("-1")) {
                validationBean.lanzarMensaje("error", "taxRepeatFail", "blank");
            } else if (res.equals("-2")) {
                validationBean.lanzarMensaje("error", "unexpectedError", "blank");
            }
            tax = new FbTax(); //limpiando

        } catch (Exception e) {
            e.printStackTrace();
            res = "-2";
        }

    }

    public boolean valCampos() {
        boolean flag = false;

        int c = 0;

        if (!(validationBean.validarCampoVacio(this.tx.getName(), "warn", "valErr", "reqNameTax")
                && validationBean.validarSoloLetras(this.tx.getName(), "warn", "valErr", "reqNameTax"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.tx.getDescrip(), "warn", "valErr", "reqDescTax")
                && validationBean.validarSoloLetras(this.tx.getDescrip(), "warn", "valErr", "reqDescTax"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.tx.getRate(), "warn", "valErr", "reqRateTax")
                && validationBean.validarSoloNumeros(this.tx.getRate(), "warn", "valErr", "reqRateTax"))) {

            c++;
        }
        if (this.tx.getRate().equals("00") || this.tx.getRate().equals("0")) {
            System.out.println("Obteniendo valor rate" + tx.getRate());
            c++;
        }

        if (c == 0) {
            flag = true;
        }

        return flag;

    }

    public void selectType(String type) {
        System.out.println("Ingresa a seleccionar panel");
        this.type = type;

        this.modalTitle = "Add a new tax";//this.validationBean.getMsgBundle("lblProdServiceInfo");
        this.showForm = true;
        if (type.equals("TX")) {
            this.modalTitle = "Add a new tax";//this.validationBean.getMsgBundle("lblBundleInfo");
        }
        System.out.println("Levantar nuevo panel.");
        this.validationBean.updateComponent("prodForm:modalContent");
    }

    public void limpiar() {
        this.taxName = "";
        this.descripcion = "";
        this.rate = "";
        this.showForm = false; 

    }
}
