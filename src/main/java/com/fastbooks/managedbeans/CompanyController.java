/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbCompaniaFacade;
import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbUsuario;
import com.fastbooks.util.ValidationBean;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author dell
 */
@Named(value = "companyController")
@ViewScoped
public class CompanyController implements Serializable {

    @EJB
    FbCompaniaFacade comFacade;
    @EJB
    ValidationBean validationBean;
    @Inject
    UserData userData;

    private boolean naturalPerson;
    private String companyComName;
    private String fName;
    private String lName;
    private String pass;
    private String rPass;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isNaturalPerson() {
        return naturalPerson;
    }

    public void setNaturalPerson(boolean naturalPerson) {
        this.naturalPerson = naturalPerson;
    }

    public String getCompanyComName() {
        return companyComName;
    }

    public void setCompanyComName(String companyComName) {
        this.companyComName = companyComName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getrPass() {
        return rPass;
    }

    public void setrPass(String rPass) {
        this.rPass = rPass;
    }

    /**
     * Creates a new instance of CompanyController
     */
    public CompanyController() {
    }

    public void register() {
        /* System.out.println("com.fastbooks.managedbeans.CompanyController.register()");
        System.out.println(this.companyComName);
        System.out.println(this.email);
        System.out.println(this.fName);
        System.out.println(this.lName);
        System.out.println(this.naturalPerson);
        System.out.println(this.pass);
        System.out.println(this.rPass);*/

        if (regVal()) {
            FbCompania com = new FbCompania();
            com.setEmail(email);
            com.setNomCom(companyComName);
            com.setNomLeg(companyComName);
            com.setGiro("");
            com.setTelefono("");
            com.setLogo("");
            com.setPerNat(1);
            com.setWebsite("");
            FbUsuario user = new FbUsuario();
            user.setEmail(email);
            user.setFirstname(fName);
            user.setLastname(lName);
            user.setClave(pass);

            String res = comFacade.actCompany(com, user, "A");
            System.out.println("Resultado controller: " + res);

            if (res.equals("0")) {
                //iniciar session y redireccionar a dashboard
                userData.setEmail(user.getEmail());
                userData.setPass(user.getClave());
                userData.login();
            } else if (res.equals("-1")) {
                //lanzar mensaje de registro repetido
                validationBean.lanzarMensaje("warn", "valErr", "repeatedEmail");
            } else if (res.equals("-2")) {
                //lanzar mensaje de ocurrio error inesperado
                validationBean.lanzarMensaje("error", "valErr", "unexpectedError");
            }

            System.out.println("validation good");
        } else {
            System.out.println("validation fail");
        }
    }

    public boolean regVal() {
        boolean flag = false;
        int c = 0;
        if (!(validationBean.validarCampoVacio(this.email, "error", "valErr", "reqEmail")
                && validationBean.validarEmail(this.email, "error", "valErr", "reqEmail")
                && validationBean.validarLongitudCampo(this.email, 8, 80, "error", "valErr", "reqEmail"))) {
            c++;
        }

        if (!(validationBean.validarCampoVacio(this.fName, "error", "valErr", "reqFname")
                && validationBean.validarSoloLetras(this.fName, "error", "valErr", "reqFname")
                && validationBean.validarLongitudCampo(this.fName, 4, 50, "error", "valErr", "reqFname"))) {
            c++;
        }

        if (!(validationBean.validarCampoVacio(this.lName, "error", "valErr", "reqLname")
                && validationBean.validarSoloLetras(this.lName, "error", "valErr", "reqLname")
                && validationBean.validarLongitudCampo(this.lName, 4, 50, "error", "valErr", "reqLname"))) {
            c++;
        }

        if (!(validationBean.validarCampoVacio(this.pass, "error", "valErr", "reqPass")
                && validationBean.validarLongitudCampo(this.pass, 5, 10, "error", "valErr", "reqPass"))) {
            c++;
        } else if (!this.pass.equals(this.rPass)) {
            c++;
            validationBean.lanzarMensaje("warn", "valErr", "reqRPass");
        }

        if (!this.naturalPerson) {
            if (!(validationBean.validarCampoVacio(this.companyComName, "error", "valErr", "reqNomCom")
                    && validationBean.validarLongitudCampo(this.companyComName, 4, 50, "error", "valErr", "reqNomCom"))) {
                c++;
            }
        } else {
            this.companyComName = this.fName.trim() + ' ' + this.lName.trim();
        }

        if (c == 0) {
            flag = true;
        }
        this.naturalPerson = false;
        return flag;
    }

}
