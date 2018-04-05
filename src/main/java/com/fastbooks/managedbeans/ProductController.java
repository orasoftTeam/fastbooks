/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbProductFacade;
import com.fastbooks.facade.FbTaxFacade;
import com.fastbooks.modelo.FbCatProd;
import com.fastbooks.modelo.FbProduct;
import com.fastbooks.modelo.FbTax;
import com.fastbooks.util.ValidationBean;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author dell
 */
@Named(value = "productController")
@ViewScoped
public class ProductController implements Serializable{

    
    private @Getter @Setter String type;
    private @Getter @Setter String typeImg = "índice.png";
    private @Getter @Setter boolean showScnd = false;
    
    
    private @Getter @Setter String appPath = System.getProperty("user.dir");
    private final String destination = appPath + File.separator + "prodPhoto\\";
    private @Getter @Setter UploadedFile archivo;
    private @Getter @Setter String nameFileFinal;
    private @Getter @Setter String msgFile;
    
    
    private @Getter @Setter String name;
    private @Getter @Setter String sku;
    private @Getter @Setter String photoUrl = "/resources/img/placeholder.png";
    private @Getter @Setter String idCat;
    private @Getter @Setter String initQuant;
    private @Getter @Setter String desc;
    private @Getter @Setter String idTax;
    private @Getter @Setter boolean incTax;
    private @Getter @Setter String price;
    
    private @Getter @Setter boolean showForm = false;
    private @Getter @Setter String modalTitle = "Select a type:";
    
    
    private @Getter @Setter List<FbProduct> pList = new ArrayList<>();
    private @Getter @Setter List<FbTax> tList = new ArrayList<>();
    
    private @Getter @Setter FbProduct product;
    
    @Inject
    UserData userData;
    @EJB
    ValidationBean vb;
    @EJB
    FbProductFacade pFacade;
    @EJB
    FbTaxFacade tFacade;
    /**
     * Creates a new instance of ProductController
     */
    public ProductController() {
    }
    
    public void init(){
    pList = pFacade.getProductsByIdCia(this.userData.getCurrentCia().getIdCia().toString());
    tList = tFacade.getTaxByIdCia(this.userData.getCurrentCia().getIdCia().toString());
    }
    
    public void goCat(){
    vb.redirecionar("/view/sales/categories.xhtml");
    }
    
    public void goProd(){
    vb.redirecionar("/view/sales/producsNServices.xhtml");
    }
    
    
    public void selectType(String type, String typeImg) {
    this.type = type;
    this.typeImg = typeImg;
    //showScnd = true;
    //this.vb.reload();
    /*this.vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide');");
    this.vb.updateComponent("bmodal");
    this.vb.ejecutarJavascript("$('.modalPseudoClass2').modal();");*/
    this.modalTitle = "Product/Service information";
    this.showForm = true;
    this.vb.updateComponent("prodForm:modalContent");
    }
    
    public void limpiar(){
    //this.vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide')");
    this.modalTitle = "Select a type:";
    this.showForm = false;
    photoUrl = "/resources/img/placeholder.png";
    //this.vb.updateComponent("prodForm:modalContent");
        System.out.println("com.fastbooks.managedbeans.ProductController.limpiar()");
    }
    
 
    
    public void handleFileUpload(FileUploadEvent event) {
        String nameF;
        String ciaFolder =  "cia"+userData.getCurrentCia().getIdCia().toString();
        String newDest = destination + ciaFolder+"\\";
        try {
            if (archivo == null) {
                archivo = event.getFile();
                //BufferedImage img = ImageIO.read(archivo.getInputstream());
                nameF = vb.generarRandom(archivo.getFileName());
                File file = new File(destination);
                boolean res = file.mkdir();
                file = new File(newDest);
                boolean result = file.mkdir();
                vb.copyFile(nameF, newDest, archivo.getInputstream());
                //this.msgFile = vb.getMsgBundle("lblFileSuccess");
                this.photoUrl = "/prodPhoto/"+ciaFolder+"/" + nameF;
                //vb.updateComponent("comForm:msgFile");
                System.out.println(this.photoUrl);
                vb.updateComponent("prodForm:showPhoto");
                this.nameFileFinal = nameF;
            } else {
                archivo = event.getFile();
                if (vb.deleteFile(newDest + nameFileFinal)) {
                    nameF = vb.generarRandom(archivo.getFileName());
                    vb.copyFile(nameF, newDest, archivo.getInputstream());
                    this.photoUrl = "/prodPhoto/"+ciaFolder+"/" + nameF;
                    
                    System.out.println(this.photoUrl);
                    vb.updateComponent("prodForm:showPhoto");
                    nameFileFinal = nameF;
                }

            }
        } catch (Exception e) {
            msgFile = vb.getMsgBundle("lblFileUploadError");
            if (archivo != null) {
                if (vb.deleteFile("/prodPhoto/"+ciaFolder+"/" + archivo.getFileName())) {
                    archivo = null;
                }
            }
            this.photoUrl = "";
            
            System.out.println("error"+this.photoUrl);
            vb.updateComponent("prodForm:showPhoto");
            e.printStackTrace();
        }

    }
    
    
    public void addNew(){
    
        if (valNew()) {
            this.product = new FbProduct();
            this.product.setIdCia(this.userData.getCurrentCia());
            this.product.setIdProd(new BigDecimal("0"));
            this.product.setName(name);
            this.product.setSku(sku);
            this.product.setPhoto(photoUrl);
            this.product.setDescrip(desc);
            this.product.setIdCat(new FbCatProd(new BigDecimal(idCat)));
            this.product.setIdTax(new FbTax(new BigDecimal(idTax)));
            this.product.setInitQuant(new BigInteger(initQuant));
            String incTaxx = "0";
            if (incTax) {
                incTaxx = "1";
            }
            this.product.setIncTax(new BigInteger(incTaxx));
            this.product.setType(type);
            String res = pFacade.actProd(product, "A");
            System.out.println("Res: " + res );
            
        }
    }
    
    public boolean valNew(){
    boolean flag = false;
    flag =  true;
    return flag;
    }
    
    
}
