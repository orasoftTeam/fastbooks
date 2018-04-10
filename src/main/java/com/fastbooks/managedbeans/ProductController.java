/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbProductFacade;
import com.fastbooks.facade.FbTaxFacade;
import com.fastbooks.modelo.FbBundleItems;
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
import org.primefaces.context.RequestContext;
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
    private @Getter @Setter List<FbProduct> itemBundleList = new ArrayList<>();
    private @Getter @Setter List<FbBundleItems> bundleItems = new ArrayList<>();
    private @Getter @Setter List<FbTax> tList = new ArrayList<>();
    
    private @Getter @Setter FbProduct product;
    private @Getter @Setter String operation = "A";
    private @Getter @Setter String idProd = "0";
    private @Getter @Setter boolean isBundle = false;
    private @Getter @Setter FbProduct productBundle;
    private @Getter @Setter String bundleTotal = "0.00"; 
    private @Getter @Setter String bundleQuant = "1"; 
    
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
        if (!this.userData.getUses().equals("0")) {
            this.vb.lanzarMensaje("info",this.userData.getUses() , "blank");
            this.userData.setUses("0");
        }
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
    this.modalTitle = this.vb.getMsgBundle("lblProdServiceInfo");
    this.showForm = true;
        if (type.equals("BU")) {
           itemBundleList = pFacade.getProductsByIdCiaWithoutBundle(this.userData.getCurrentCia().getIdCia().toString());
           this.modalTitle = this.vb.getMsgBundle("lblBundleInfo"); 
           this.isBundle = true;
        }
    this.vb.updateComponent("prodForm:modalContent");
    }
    
    public void limpiar(){
    //this.vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide')");
    this.modalTitle = "Select a type:";
    this.showForm = false;
    photoUrl = "/resources/img/placeholder.png";
    this.archivo = null;
    this.desc = "";
    this.idCat = "";
    this.idTax = "";
    this.incTax = false;
    this.initQuant = "";
    this.name = "";
    this.nameFileFinal = "";
    this.price = "";
    this.product = null;
    this.sku = "";
    this.type = "";
    this.operation = "A";
    this.idProd = "0";
    this.bundleTotal = "0.00";
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
            this.product.setIdProd(new BigDecimal(idProd));
            this.product.setName(name);
            this.product.setSku(sku);
            this.product.setPhoto(photoUrl);
            this.product.setDescrip(desc);
            this.product.setIdCat(new FbCatProd(new BigDecimal(idCat)));
            this.product.setIdTax(new FbTax(new BigDecimal(idTax)));
            
            if (type.equals("IN")) {
                this.product.setInitQuant(new BigInteger(initQuant));
            }else{
            this.product.setInitQuant(new BigInteger("0"));
            }
            
            
            BigDecimal precio = new BigDecimal(price);
            this.product.setPrice(precio);
            String incTaxx = "0";
            if (incTax) {
                incTaxx = "1";
            }
            this.product.setIncTax(new BigInteger(incTaxx));
            this.product.setType(type);
           
            String res = pFacade.actProd(product, this.operation);
            
            switch(res){
                case "0":
                    String message = this.operation.equals("A")? "lblAddProdSuccess" : "lblUpdateProdSuccess";
                    this.userData.setUses(message);
                    this.vb.reload();
                    /*String message = this.operation.equals("A")? "lblAddProdSuccess" : "lblUpdateProdSuccess";
                    this.vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide');");
                    this.vb.lanzarMensaje("info",message , "blank");
                    this.init();
                    this.vb.updateComponent("tableForm");*/
                    this.limpiar();
                    break;
                case "-1":
                    this.vb.lanzarMensaje("error","lblRepeatProductFail", "blank");
                    break;
                case "-2":
                    this.vb.lanzarMensaje("error","unexpectedError", "blank");
                    break;
            
            }
            
            System.out.println("Res: " + res );
            
        }
    }
    
    public boolean valNew(){
    boolean flag = false;
    int c = 0;
        if (this.vb.validarCampoVacio(this.name, "error", "valErr", "lblReqProdName")
                && this.vb.validarCampoVacio(this.price, "error", "valErr", "lblReqProdPrice")
                && this.vb.validarSoloNumerosConPunto(this.price, "error", "valErr", "lblFormatProdPrice")) {
            
            if (this.type.equals("IN")) {
                if (this.vb.validarCampoVacio(this.initQuant, "error", "valErr", "lblReqInitQuant")
                        && this.vb.validarSoloNumerosConPunto(this.price, "error", "valErr", "lblFormatInitQuant")) {
                    flag =  true;
                }
            }else{
            flag =  true;
            }
            
            
            
        }
    
        /*if (!flag) {
            RequestContext.getCurrentInstance().scrollTo("prodForm:msg");
        }*/
    
    return flag;
    }
    
    
    public String convertType(String op){
        String res = "";
    switch(op){
        case "IN":
            res = this.vb.getMsgBundle("lblInventory");
            break;
        case "NI":
            res = this.vb.getMsgBundle("lblNonInventory");
            break;
        case "SE":
            res = this.vb.getMsgBundle("lblService");
            break;
        case "BU":
            res = this.vb.getMsgBundle("lblBundle");
            break;
        default:
            res = "NaN";
            break;    
           
    
    }
    
    return res;
    }
    
    
    public void assign(FbProduct prod, String op){
        if (op.equals("U")) {
            this.name = prod.getName();
            this.photoUrl = prod.getPhoto() != null ? prod.getPhoto() : "/resources/img/placeholder.png";
            this.price = prod.getPrice().toString();
            this.showForm = true;
            this.type = prod.getType();
            this.desc = prod.getDescrip();
            this.idCat = prod.getIdCat() != null ?  prod.getIdCat().getIdCat().toString() : "0";
            this.idTax = prod.getIdTax() != null ? prod.getIdTax().getIdTax().toString() : "0";
            this.initQuant = prod.getInitQuant() != null ? prod.getInitQuant().toString() : "0";
            if (prod.getIncTax().toString().equals("0")) {
            this.incTax = false;    
            }else{
            this.incTax = true;
            }
            this.sku = prod.getSku();
            this.idProd = prod.getIdProd().toString();
            switch(this.type){
            case "IN":
            this.typeImg = "inv.png";
            break;
        case "NI":
            this.typeImg = "noninv.png";
            break;
        case "SE":
            this.typeImg = "serv.png";
            break;
        case "BU":
            this.typeImg = "bundle.png";
            break;
        default:
            this.typeImg = "NaN";
            break;
            
            }
            
            this.operation = "U";
            this.vb.ejecutarJavascript("$('.modalPseudoClass').modal();");
        }else {
        this.product = prod;
        this.vb.ejecutarJavascript("$('.modalPseudoClass2').modal();");
        }
    }
    
    
    public void del(){
    product.setIdCat(new FbCatProd(new BigDecimal("0")));
    product.setIdTax(new FbTax(BigDecimal.ZERO));
    product.setInitQuant(BigInteger.ZERO);
    String res=this.pFacade.actProd(product, "D");
    
    switch(res){
        case "0":
            this.userData.setUses("lblDelProdSuccess");
            this.vb.reload();
            this.limpiar();
            break;
        case "-1":
            
            break;
        case "-2":
            this.userData.setUses("unexpectedError");
            this.vb.reload();
            this.limpiar();
            break;    
    }
    }
    
    public void addItemBundle(){
    
        try {
            System.out.println(this.productBundle);
            FbBundleItems e = new FbBundleItems(BigDecimal.ZERO);
            e.setItemName(this.productBundle.getName());
            e.setIdProd(this.productBundle);
            e.setItemPrice(this.productBundle.getPrice());
            //this.bundleTotal = String.valueOf(new BigDecimal(this.bundleTotal).add(this.productBundle.getPrice()));
            
            bundleItems.add(e);
            this.updateBundleTotal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void removeItemBundle(FbBundleItems bi){
        try {
            bundleItems.remove(bi);
            //Double priceI = Double.parseDouble(bi.getItemPrice().toString());
            //this.bundleTotal = String.valueOf(Double.parseDouble(this.bundleTotal) - priceI);
            this.updateBundleTotal();
            this.vb.updateComponent("prodForm:tblBundle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    
    }
    
    public void updateBundleTotal(){
        Double acum = 0.00;
        Double value = 0.00;
        try {
            for (FbBundleItems bi : bundleItems) {
                value = Double.parseDouble(bi.getItemPrice().toString());
                acum += value;
            }
            this.bundleTotal = String.valueOf(acum);
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.ProductController.updateBundleTotal()");
            e.printStackTrace();
        }
    }
    
}
