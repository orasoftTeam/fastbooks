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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import lombok.Getter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

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

    @Getter String appPath = System.getProperty("user.dir");
    private final String destination = appPath + File.separator + "logo\\";
    private UploadedFile archivo;
    private String nameFileFinal;
    private String msgFile;
    private FbCompania company;
    private boolean naturalPerson;
    private String companyComName;
    private String fName;
    private String lName;
    private String pass;
    private String rPass;
    private String email;
    private String companyLegName;
    private String giro;
    private String tel;
    private String website;
    private String logourl;

    public UploadedFile getArchivo() {
        return archivo;
    }

    public String getMsgFile() {
        return msgFile;
    }

    public void setMsgFile(String msgFile) {
        this.msgFile = msgFile;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

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

    public String getCompanyLegName() {
        return companyLegName;
    }

    public void setCompanyLegName(String companyLegName) {
        this.companyLegName = companyLegName;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogourl() {
        return logourl;
    }

    public void setLogourl(String logourl) {
        this.logourl = logourl;
    }

    /**
     * Creates a new instance of CompanyController
     */
    public CompanyController() {
        System.out.println(destination);
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
            com.setIdCia(BigDecimal.ZERO);
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
            } else if (res.equals("-2"))  {
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

    public void setValues() {
        try {
            this.company = userData.getCurrentCia();
            this.companyComName = userData.getCurrentCia().getNomCom();
            this.companyLegName = userData.getCurrentCia().getNomLeg();
            this.email = userData.getCurrentCia().getEmail();
            this.giro = userData.getCurrentCia().getGiro();
            this.logourl = userData.getCurrentCia().getLogo();
            this.tel = userData.getCurrentCia().getTelefono();
            this.website = userData.getCurrentCia().getWebsite();

        } catch (Exception e) {
            System.out.println("not logged in");
            e.printStackTrace();
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        String name;
        try {
            if (archivo == null) {
                archivo = event.getFile();
                //BufferedImage img = ImageIO.read(archivo.getInputstream());
                name = validationBean.generarRandom(archivo.getFileName());
                File file = new File(destination);
                file.mkdir();
                validationBean.copyFile(name, destination, archivo.getInputstream());
                this.msgFile = validationBean.getMsgBundle("lblFileSuccess");
                this.logourl = "/logo/" + name;
                validationBean.updateComponent("comForm:msgFile");
                System.out.println(this.logourl);
                validationBean.updateComponent("comForm:showLogo");
                this.nameFileFinal = name;
            } else {
                archivo = event.getFile();
                if (validationBean.deleteFile(destination + nameFileFinal)) {
                    name = validationBean.generarRandom(archivo.getFileName());
                    validationBean.copyFile(name, destination, archivo.getInputstream());
                    this.logourl = "/logo/" + name;
                    validationBean.updateComponent("comForm:msgFile");
                    System.out.println(this.logourl);
                    validationBean.updateComponent("comForm:showLogo");
                    nameFileFinal = name;
                }

            }
        } catch (Exception e) {
            msgFile = validationBean.getMsgBundle("lblFileUploadError");
            if (archivo != null) {
                if (validationBean.deleteFile("/logo/" + archivo.getFileName())) {
                    archivo = null;
                }
            }
            this.logourl = "";
            validationBean.updateComponent("comForm:msgFile");
            System.out.println("error"+this.logourl);
            validationBean.updateComponent("comForm:showLogo");
            e.printStackTrace();
        }

    }
    
    
    public boolean editVal(){
        boolean flag = false;
        int c = 0;
        if (!(validationBean.validarCampoVacio(this.email, "error", "valErr", "reqEmail")
                && validationBean.validarEmail(this.email, "error", "valErr", "reqEmail")
                && validationBean.validarLongitudCampo(this.email, 8, 80, "error", "valErr", "reqEmail"))) {
            c++;
        }
        if (!(validationBean.validarCampoVacio(this.companyComName, "error", "valErr", "reqNomCom")
                && validationBean.validarLongitudCampo(this.companyComName, 5, 50, "error", "valErr", "reqNomCom"))) {
            c++;
        }
        
        if (!(validationBean.validarCampoVacio(this.companyLegName, "error", "valErr", "reqNomLeg")
                && validationBean.validarLongitudCampo(this.companyLegName, 5, 50, "error", "valErr", "reqNomLeg"))) {
            c++;
        }
        
        

        if (c == 0) {
            flag = true;
        }
        this.naturalPerson = false;
        return flag;
    
    
    }
    
    public void save(){
        if (editVal()) {
            
            this.company.setEmail(this.email);
            this.company.setGiro(giro);
            this.company.setLogo(this.logourl);
            this.company.setNomCom(this.companyComName);
            this.company.setNomLeg(this.companyLegName);
            this.company.setTelefono(tel);
            this.company.setWebsite(website);
            String res = comFacade.actCompany(company, new FbUsuario(),"U");
            System.out.println("Resultado controller: " + res);
            if (res.equals("0")) {
                validationBean.lanzarMensaje("info", "modComSuccess", "blank");
                System.out.println("Exito!");
            }else if(res.equals("-1")){
                validationBean.lanzarMensaje("warn", "modComFailRepeat", "blank");
             System.out.println("Repetido!");
            }else if(res.equals("-2")){
                validationBean.lanzarMensaje("error", "unexpectedError", "blank");
            System.out.println("Error!");
            }
        }else{
        System.out.println("validation fail");
        }
    
    }

}
