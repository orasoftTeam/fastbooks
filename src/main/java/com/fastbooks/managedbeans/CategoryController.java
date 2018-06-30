/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbCatProdFacade;
import com.fastbooks.modelo.FbCatProd;
import com.fastbooks.modelo.FbCompania;
import com.fastbooks.util.ValidationBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author dell
 */
@Named(value = "categoryController")
@ViewScoped
public class CategoryController implements Serializable {

    /**
     * Creates a new instance of CategoryController
     */
    private @Getter
    @Setter
    FbCatProd cat;
    private @Getter
    @Setter
    String name;

    private @Getter
    @Setter
    List<FbCatProd> list = new ArrayList<>();

    @EJB
    ValidationBean vb;

    @EJB
    FbCatProdFacade cpFacade;

    @Inject
    UserData userData;

    public CategoryController() {
    }

    public void add() {
        if (valAdd()) {
            String res = "";
            try {
                cat = new FbCatProd();
                cat.setName(name);
                cat.setIdCat(BigDecimal.ZERO);
                cat.setIdCia(new FbCompania(userData.getCurrentCia().getIdCia()));
                cat.setUserCreacion(new BigInteger(userData.getLoggedUser().getIdUsuario().toString()));
                res = cpFacade.actCat(cat, "A");
                if (res.equals("0")) {
                    vb.lanzarMensaje("info", "catAddSuccess", "blank");
                    this.refresh();
                } else if (res.equals("-1")) {
                    vb.lanzarMensaje("error", "catRepeatFail", "blank");
                } else if (res.equals("-2")) {
                    vb.lanzarMensaje("error", "unexpectedError", "blank");
                }
                limpiar();
            } catch (Exception e) {
                System.out.println("com.fastbooks.managedbeans.CategoryController.add()");
                e.printStackTrace();
                res = "-2";
            }

        }
    }

    public boolean valAdd() {

        boolean flag = false;
        if (vb.validarCampoVacio(this.name, "warn", "valErr", "reqCatName")
                && vb.validarSoloLetras(this.name, "warn", "valErr", "reqCatName")) {
            flag = true;
        }

        return flag;
    }

    @PostConstruct
    public void init() {
        System.out.println("com.fastbooks.managedbeans.CategoryController.init()");
        try {
            list = cpFacade.getCatsByIdCia(userData.getCurrentCia().getIdCia().toString());
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.CategoryController.init()");
            e.printStackTrace();
        }
    }

    public void assign(FbCatProd cate, String op) {
        System.out.println(cate);
        this.cat = cate;
        if (op.equals("U")) {
            this.name = cate.getName();
            this.vb.ejecutarJavascript("$('.modalPseudoClass2').modal();");
        }else{
            this.vb.ejecutarJavascript("$('.modalPseudoClass3').modal();");
        }
    }

    public void limpiar() {
        this.name = "";
        this.cat = new FbCatProd();
    }

    public void edit() {
        if (valAdd()) {
            String res = "";
            try {

                cat.setName(name);
                res = cpFacade.actCat(cat, "U");
                if (res.equals("0")) {
                    vb.lanzarMensaje("info", "catEditSuccess", "blank");
                    this.refresh();
                } else if (res.equals("-1")) {
                    vb.lanzarMensaje("error", "catRepeatFail", "blank");
                } else if (res.equals("-2")) {
                    vb.lanzarMensaje("error", "unexpectedError", "blank");
                }
                limpiar();
            } catch (Exception e) {
                System.out.println("com.fastbooks.managedbeans.CategoryController.add()");
                e.printStackTrace();
                res = "-2";
            }

        }
    }
    
    public void delete() {
        
            String res = "";
            try {

                
                res = cpFacade.actCat(cat, "D");
                if (res.equals("0")) {
                    vb.lanzarMensaje("info", "catDelSuccess", "blank");
                    this.refresh();
                } else if (res.equals("-1")) {
                    vb.lanzarMensaje("error", "catRepeatFail", "blank");
                } else if (res.equals("-2")) {
                    vb.lanzarMensaje("error", "unexpectedError", "blank");
                }
                limpiar();
            } catch (Exception e) {
                System.out.println("com.fastbooks.managedbeans.CategoryController.add()");
                e.printStackTrace();
                res = "-2";
            }

        
    }
    
     public void refresh(){
        list = cpFacade.getCatsByIdCia(userData.getCurrentCia().getIdCia().toString());
        this.vb.updateComponent("catForm:tblCat");
    }

}
