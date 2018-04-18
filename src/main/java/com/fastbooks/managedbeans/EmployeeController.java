/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbEmployeeFacade;
import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbEmployee;
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
@Named(value = "employeeController")
@ViewScoped
public class EmployeeController implements Serializable {

    private FbEmployee emp;

    @EJB
    ValidationBean validationBean;
    @Inject
    UserData userData;
    @EJB
    FbEmployeeFacade eFacade;

    private @Getter
    @Setter
    List<FbEmployee> empL = new ArrayList<>();

    private @Getter
    @Setter
    FbEmployee empleado;

    private @Setter
    @Getter
    String day;
    private @Setter
    @Getter
    String month;
    private @Setter
    @Getter
    String year;
    private @Setter
    @Getter
    String bDay;
    private @Setter
    @Getter
    List<Integer> years = new ArrayList<Integer>();

    public FbEmployee getEmp() {
        return emp;
    }

    public void setEmp(FbEmployee emp) {
        this.emp = emp;
    }

    /**
     * Creates a new instance of EmployeeController
     */
    public EmployeeController() {
        emp = new FbEmployee();

    }

    public void newEmployee() {
        emp = new FbEmployee();
    }

    //Adding a new employee
    public void addEmployee() {
        System.out.println("Ingresando a agregar nuevo employee" + emp);
        if (valCampos()) {
            FbCompania com = new FbCompania();
            com.setIdCia(BigDecimal.ZERO);
            this.emp.setIdCia(new FbCompania(userData.getCurrentCia().getIdCia()));
            this.emp.setIdCia(this.userData.getCurrentCia());
            this.emp.setIdEmp(new BigDecimal("0"));
            this.bDay = this.day + "/"+this.month+"/"+this.year;
            

            
            

            String res = eFacade.actEmployee(emp, "A");
            System.out.println("Res: " + res);
            if (res.equals("0")) {
                validationBean.lanzarMensaje("info", "empAdded", "blank");
            } else if (res.equals("-1")) {
                validationBean.lanzarMensaje("error", "empRepeatFail", "blank");
            } else if (res.equals("-2")) {
                validationBean.lanzarMensaje("error", "unexpectedError", "blank");
            }
            emp = new FbEmployee(); //limpiando..
        }

    }
    //metodo para obtener lista de empleados

    public void init() {
        try {
            empL = eFacade.getEmployee(this.userData.getCurrentCia().getIdCia().toString());
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
    public void action(FbEmployee em, String op) {

        this.empleado = em;
        System.out.println("obteniendo objeto action " + em);

        if (op.equals("U")) {
            this.validationBean.ejecutarJavascript("$('.modalPseudoClass2').modal();");
        } else {
            this.validationBean.ejecutarJavascript("$('.modalPseudoClass3').modal();");
        }

    }

    //Updating customer
    public void editEmployee() {
        System.out.println("Ingresando a actualizar employee");
        String res = "";

        try {
            res = eFacade.actEmployee(empleado, "U");
            if (res.equals("0")) {
                validationBean.lanzarMensaje("info", "empUpdate", "blank");
            } else if (res.equals("-1")) {
                validationBean.lanzarMensaje("error", "empRepeatFail", "blank");
            } else if (res.equals("-2")) {
                validationBean.lanzarMensaje("error", "unexpectedError", "blank");
            }
            empleado = new FbEmployee(); //limpiando

        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.EmployeeController.editEmployee()");
            e.printStackTrace();
            res = "-2";
        }
    }

    public void deleteEmployee() {
        System.out.println("Ingresando a eliminar employee");
        String res = "";
        try {
            res = eFacade.actEmployee(empleado, "D");
            System.out.println("resultado delete employee" + res);
            if (res.equals("0")) {
                validationBean.lanzarMensaje("info", "empDelete", "blank");
            } else if (res.equals("-1")) {
                validationBean.lanzarMensaje("error", "empRepeatFail", "blank");
            } else if (res.equals("-2")) {
                validationBean.lanzarMensaje("error", "unexpectedError", "blank");
            }
            empleado = new FbEmployee(); //limpiando

        } catch (Exception e) {
            e.printStackTrace();
            res = "-2";
        }

    }

    public boolean valCampos() {

        boolean flag = false;
        int c = 0;

        if (!(validationBean.validarCampoVacio(this.emp.getFirstname(), "warn", "valErr", "reqFname")
                && validationBean.validarSoloLetras(this.emp.getFirstname(), "warn", "valErr", "reqFname"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.emp.getLastname(), "warn", "valErr", "reqLname")
                && validationBean.validarSoloLetras(this.emp.getFirstname(), "warn", "valErr", "reqLname"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.emp.getEmail(), "warn", "valErr", "reqEmail")
                && validationBean.validarEmail(this.emp.getEmail(), "warn", "valErr", "reqEmail"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.emp.getGender(), "warn", "valErr", "reqGender")
                && validationBean.validarSoloLetras(this.emp.getGender(), "warn", "valErr", "reqGender"))) {
            c++;
        }
        
        if (!(validationBean.validarCampoVacio(this.emp.getDateOfBirth(), "warn", "valErr", "reqBDay")
                && validationBean.validarSoloNumeros(this.emp.getDateOfBirth(), "warn", "valErr", "reqBDay"))) {
            c++;
        }
        
        if (!(validationBean.validarCampoVacio(this.emp.getPhone(), "warn", "valErr", "reqPhone")
                && validationBean.validarSoloNumeros(this.emp.getPhone(), "warn", "valErr", "reqPhone"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.emp.getStreet(), "warn", "valErr", "reqStreet"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.emp.getCity(), "warn", "valErr", "reqCity")
                && validationBean.validarSoloLetras(this.emp.getCity(), "warn", "valErr", "reqCity"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.emp.getState(), "warn", "valErr", "reqState")
                && validationBean.validarSoloLetras(this.emp.getState(), "warn", "valErr", "reqState"))) {
        }
        if (!(validationBean.validarCampoVacio(this.emp.getPostalCode(), "warn", "valErr", "reqPostalC")
                && validationBean.validarSoloNumeros(this.emp.getPostalCode(), "warn", "valErr", "reqPostalC"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.emp.getCountry(), "warn", "valErr", "reqCountry")
                && validationBean.validarSoloLetras(this.emp.getCountry(), "warn", "valErr", "reqCountry"))) {
            c++;
        }

        
        if (c == 0) {
            flag = true;
        }

        return flag;
    }

 
}
