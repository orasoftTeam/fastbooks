/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbCatProdFacade;
import com.fastbooks.facade.FbProductFacade;
import com.fastbooks.facade.FbTaxFacade;
import com.fastbooks.modelo.FbBundleItems;
import com.fastbooks.modelo.FbCatProd;
import com.fastbooks.modelo.FbProduct;
import com.fastbooks.modelo.FbTax;
import com.fastbooks.util.GlobalParameters;
import com.fastbooks.util.ValidationBean;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class ProductController implements Serializable {

    private @Getter
    @Setter
    String type;
    private @Getter
    @Setter
    String typeImg = "índice.png";
    private @Getter
    @Setter
    boolean showScnd = false;
    private GlobalParameters gp = new GlobalParameters();
    private @Getter
    @Setter
    String appPath = gp.getAppPath();//System.getProperty("user.dir");
    private final String destination = appPath + File.separator + "prodPhoto" + File.separator;
    private @Getter
    @Setter
    UploadedFile archivo;
    private @Getter
    @Setter
    String nameFileFinal;
    private @Getter
    @Setter
    String msgFile;

    private @Getter
    @Setter
    String name;
    private @Getter
    @Setter
    String sku;
    private @Getter
    @Setter
    String photoUrl = "/resources/img/placeholder.png";
    private @Getter
    @Setter
    String idCat;
    private @Getter
    @Setter
    String initQuant;
    private @Getter
    @Setter
    String desc;
    private @Getter
    @Setter
    String idTax;
    private @Getter
    @Setter
    boolean incTax;
    private @Getter
    @Setter
    String price;

    private @Getter
    @Setter
    boolean showForm = false;
    private @Getter
    @Setter
    String modalTitle = "Select a type:";

    private @Getter
    @Setter
    List<FbProduct> pList = new ArrayList<>();
    private @Getter
    @Setter
    List<FbProduct> itemBundleList = new ArrayList<>();
    private @Getter
    @Setter
    List<FbBundleItems> bundleItems = new ArrayList<>();
    private @Getter
    @Setter
    List<FbTax> tList = new ArrayList<>();

    private @Getter
    @Setter
    FbProduct product;
    private @Getter
    @Setter
    String operation = "A";
    private @Getter
    @Setter
    String idProd = "0";
    private @Getter
    @Setter
    boolean isBundle = false;
    private @Getter
    @Setter
    FbProduct productBundle;
    private @Getter
    @Setter
    String bundleTotal = "0.00";
    private @Getter
    @Setter
    BigDecimal bTotalReal;
    private @Getter
    @Setter
    String bundleQuant = "1";

    @Inject
    UserData userData;
    @EJB
    ValidationBean vb;
    @EJB
    FbProductFacade pFacade;
    @EJB
    FbTaxFacade tFacade;
    @EJB
    FbCatProdFacade catFacade;

    private @Getter
    @Setter
    List<FbCatProd> catList = new ArrayList<>();

    /**
     * Creates a new instance of ProductController
     */
    public ProductController() {
    }

    @PostConstruct
    public void init() {
        System.out.println("INIT PRODUCTS!!!!");
        try {
            pList = pFacade.getProductsByIdCia(this.userData.getCurrentCia().getIdCia().toString());
            tList = tFacade.getTaxByIdCia(this.userData.getCurrentCia().getIdCia().toString());
            catList = catFacade.getCatsByIdCia(this.userData.getCurrentCia().getIdCia().toString());
            if (!this.userData.getUses().equals("0")) {
                this.vb.lanzarMensaje("info", this.userData.getUses(), "blank");
                this.userData.setUses("0");
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.ProductController.init()");
            e.printStackTrace();
        }
    }

    public void goCat() {
        vb.redirecionar("/view/sales/categories.xhtml");
    }

    public void goProd() {
        vb.redirecionar("/view/sales/producsNServices.xhtml");
    }

    public void selectType(String type, String typeImg) {
        this.type = type;
        this.typeImg = typeImg;
        //showScnd = true;
        //this.vb.reload();
        /*this.vb.ejecutarJavascript("$('.modalType').modal('hide');");
    this.vb.updateComponent("bmodal");
    this.vb.ejecutarJavascript("$('.modalAddProd').modal();");*/
        this.modalTitle = this.vb.getMsgBundle("lblProdServiceInfo");
        this.showForm = true;
        if (type.equals("BU")) {
            itemBundleList = pFacade.getProductsByIdCiaWithoutBundle(this.userData.getCurrentCia().getIdCia().toString());
            this.modalTitle = this.vb.getMsgBundle("lblBundleInfo");
            this.isBundle = true;
        }
        this.vb.updateComponent("prodForm:modalContent");
    }

    public void limpiar() {
        //this.vb.ejecutarJavascript("$('.modalType').modal('hide')");
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
        this.isBundle = false;
        this.bundleItems = new ArrayList<>();
        //this.vb.updateComponent("prodForm:modalContent");
        System.out.println("com.fastbooks.managedbeans.ProductController.limpiar()");
    }

    public void handleFileUpload(FileUploadEvent event) {
        String nameF;
        String ciaFolder = "cia" + userData.getCurrentCia().getIdCia().toString();
        String newDest = destination + ciaFolder + File.separator;
        try {
            if (archivo == null) {
                archivo = event.getFile();
                //BufferedImage img = ImageIO.read(archivo.getInputstream());
                nameF = vb.generarRandom(archivo.getFileName());
                File file = new File(destination);
                file.mkdir();

                file = new File(newDest);
                file.mkdir();

                vb.copyFile(nameF, newDest, archivo.getInputstream());
                //this.msgFile = vb.getMsgBundle("lblFileSuccess");
                this.photoUrl = "/prodPhoto/" + ciaFolder + "/" + nameF;

                //vb.updateComponent("comForm:msgFile");
                vb.updateComponent("prodForm:showPhoto");
                this.nameFileFinal = nameF;
            } else {
                archivo = event.getFile();
                if (vb.deleteFile(newDest + nameFileFinal)) {
                    nameF = vb.generarRandom(archivo.getFileName());
                    vb.copyFile(nameF, newDest, archivo.getInputstream());
                    this.photoUrl = "/prodPhoto/" + ciaFolder + "/" + nameF;

                    System.out.println(this.photoUrl);
                    vb.updateComponent("prodForm:showPhoto");
                    nameFileFinal = nameF;
                }

            }
        } catch (Exception e) {
            msgFile = vb.getMsgBundle("lblFileUploadError");
            if (archivo != null) {
                if (vb.deleteFile("/prodPhoto/" + ciaFolder + "/" + archivo.getFileName())) {
                    archivo = null;
                }
            }
            this.photoUrl = "";

            vb.updateComponent("prodForm:showPhoto");
            e.printStackTrace();

        }

    }

    public void addNew(boolean isFormProd) {

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
            } else {
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

            switch (res) {
                case "0":
                    //this.limpiar();
                    if (isFormProd) {
                        String message = this.operation.equals("A") ? "lblAddProdSuccess" : "lblUpdateProdSuccess";

                        this.vb.ejecutarJavascript("$('.modalType').modal('hide')");
                        this.vb.lanzarMensaje("info", message, "blank");
                        this.refrescar();
                        //this.userData.setUses(message);
                        //this.vb.reload();
                    } else {
                        this.vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide')");
                        this.vb.updateComponent("invoiceForm:prods");
                        this.userData.setFormInProdId(product.getName());
                    }
                    this.limpiar();
                    break;
                case "-1":
                    this.vb.lanzarMensaje("error", "lblRepeatProductFail", "blank");
                    break;
                case "-2":
                    this.vb.lanzarMensaje("error", "unexpectedError", "blank");
                    break;

            }

            System.out.println("Res: " + res);

        }
    }

    public boolean valNew() {
        boolean flag = false;
        int c = 0;
        if (this.vb.validarCampoVacio(this.name, "error", "valErr", "lblReqProdName")
                && this.vb.validarCampoVacio(this.price, "error", "valErr", "lblReqProdPrice")
                && this.vb.validarSoloNumerosConPunto(this.price, "error", "valErr", "lblFormatProdPrice")) {

            if (this.type.equals("IN")) {
                if (this.vb.validarCampoVacio(this.initQuant, "error", "valErr", "lblReqInitQuant")
                        && this.vb.validarSoloNumerosConPunto(this.price, "error", "valErr", "lblFormatInitQuant")) {
                    flag = true;
                }
            } else {
                flag = true;
            }

        }

        /*if (!flag) {
            RequestContext.getCurrentInstance().scrollTo("prodForm:msg");
        }*/
        return flag;
    }

    public String convertType(String op) {
        String res = "";
        switch (op) {
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

    public void assign(FbProduct prod, String op) {
        if (op.equals("U") && (!prod.getType().equals("BU"))) {
            this.name = prod.getName();
            this.photoUrl = prod.getPhoto() != null ? prod.getPhoto() : "/resources/img/placeholder.png";
            this.price = prod.getPrice().toString();
            this.showForm = true;
            this.type = prod.getType();
            this.desc = prod.getDescrip();
            this.idCat = prod.getIdCat() != null ? prod.getIdCat().getIdCat().toString() : "0";
            this.idTax = prod.getIdTax() != null ? prod.getIdTax().getIdTax().toString() : "0";
            this.initQuant = prod.getInitQuant() != null ? prod.getInitQuant().toString() : "0";
            if (prod.getIncTax().toString().equals("0")) {
                this.incTax = false;
            } else {
                this.incTax = true;
            }
            this.sku = prod.getSku();
            this.idProd = prod.getIdProd().toString();
            switch (this.type) {
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
            this.vb.ejecutarJavascript("$('.modalType').modal();");
        } else if (op.equals("D")) {
            this.product = prod;
            this.name = prod.getName();
            this.vb.ejecutarJavascript("$('.modalAddProd').modal();");
        } else if (op.equals("U") && prod.getType().equals("BU")) {
            this.operation = "U";
            this.product = prod;
            this.idProd = prod.getIdProd().toString();
            this.name = prod.getName();
            this.sku = prod.getSku();
            this.desc = prod.getDescrip();
            this.bTotalReal = prod.getTotalBundle();

            this.bundleItems = prod.getFbBundleItemsList();
            this.updateBundleTotal();
            for (FbBundleItems item : prod.getFbBundleItemsList()) {
                System.out.println("::" + item.getItemName() + "::" + item.getQuant().toString());
            }
            this.photoUrl = prod.getPhoto();
            this.typeImg = "bundle.png";
            this.type = prod.getType();
            this.showForm = true;
            this.isBundle = true;
            itemBundleList = pFacade.getProductsByIdCiaWithoutBundle(this.userData.getCurrentCia().getIdCia().toString());
            this.modalTitle = this.vb.getMsgBundle("lblBundleInfo");
            this.vb.ejecutarJavascript("$('.modalType').modal();");
        }
    }

    public void del() {
        product.setIdCat(new FbCatProd(new BigDecimal("0")));
        product.setIdTax(new FbTax(BigDecimal.ZERO));
        product.setInitQuant(BigInteger.ZERO);
        String res = this.product.getType().equals("BU") ? this.pFacade.actBundle(product, "D") : this.pFacade.actProd(product, "D");
        String message = "unexpectedError";
        switch (res) {
            case "0":
                //this.userData.setUses("lblDelProdSuccess");
                //this.vb.reload();
                message = "lblDelProdSuccess";

                break;
            case "-1":
                //no puede ser menos uno porqe esta eliminando
                break;
            case "-2":
                //this.userData.setUses("unexpectedError");
                // this.vb.reload();
                //this.limpiar();
                break;
        }
        this.vb.lanzarMensaje("info", message, "blank");
        this.refrescar();
        this.limpiar();
    }

    public void addItemBundle() {
        int c = 0;
        try {

            if (this.bundleItems.size() > 6) {
                this.vb.lanzarMensaje("error", "blank", "lblMaxBundleItem");
                this.vb.updateComponent("prodForm:msg");
            } else {
                for (FbBundleItems bi : bundleItems) {
                    if (bi.getIdProd().getIdProd().toString().equals(this.productBundle.getIdProd().toString())) {
                        c++;
                    }
                }
                if (c == 0) {
                    System.out.println(this.productBundle);
                    FbBundleItems e = new FbBundleItems(BigDecimal.ZERO);
                    e.setItemName(this.productBundle.getName());
                    e.setIdProd(this.productBundle);
                    e.setItemPrice(this.productBundle.getPrice());
                    if (!this.bundleQuant.isEmpty()) {
                        Integer q = Integer.parseInt(this.bundleQuant);
                        if (q > 0) {
                            e.setQuant(new BigInteger(q.toString()));
                        } else {
                            e.setQuant(BigInteger.ONE);
                        }
                    }
                    if (this.productBundle.getType().equals("SE")) {
                        e.setQuant(BigInteger.ONE);
                    }
                    int x = Integer.parseInt(e.getQuant().toString());
                    Double y = Double.parseDouble(e.getItemPrice().toString());
                    e.setTotal(new BigDecimal(x * y).setScale(2, BigDecimal.ROUND_HALF_UP));
                    //this.bundleTotal = String.valueOf(new BigDecimal(this.bundleTotal).add(this.productBundle.getPrice()));

                    bundleItems.add(e);
                    this.updateBundleTotal();
                    this.bundleQuant = "1";
                    this.vb.updateComponent("prodForm:bQuant");
                } else {
                    this.vb.lanzarMensaje("error", "blank", "lblRepeatBundleItem");
                    this.vb.updateComponent("prodForm:msg");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeItemBundle(FbBundleItems bi) {
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

    public void updateBundleTotal() {
        Double acum = 0.00;
        Double value = 0.00;
        int q = 0;
        try {
            for (FbBundleItems bi : bundleItems) {
                value = Double.parseDouble(bi.getItemPrice().toString());
                q = Integer.parseInt(bi.getQuant().toString());
                acum += value * q;
            }
            this.bundleTotal = String.format("%.2f", acum);
            this.bTotalReal = new BigDecimal(acum).setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.ProductController.updateBundleTotal()");
            e.printStackTrace();
        }
    }

    public void addBundle(boolean isFormProd) {
        try {
            if (valBundle()) {
                this.product = new FbProduct();
                this.product.setIdCia(this.userData.getCurrentCia());
                this.product.setIdProd(new BigDecimal(idProd));
                this.product.setName(name);
                this.product.setSku(sku);
                this.product.setPhoto(photoUrl);
                this.product.setDescrip(desc);
                this.product.setTotalBundle(this.bTotalReal);
                this.product.setType(type);
                this.product.setFbBundleItemsList1(bundleItems);
                String res = pFacade.actBundle(product, this.operation);

                switch (res) {
                    case "0":
                        if (isFormProd) {
                            String message = this.operation.equals("A") ? "lblAddBundleSuccess" : "lblUpdateBundleSuccess";
                            //this.userData.setUses(message);
                            //pList = pFacade.getProductsByIdCia(this.userData.getCurrentCia().getIdCia().toString());
                            //this.vb.reload();
                            this.vb.ejecutarJavascript("$('.modalType').modal('hide')");
                            this.vb.lanzarMensaje("info", message, "blank");
                            this.refrescar();
                        } else {
                            this.vb.ejecutarJavascript("$('.modalPseudoClass').modal('hide')");
                            this.vb.updateComponent("invoiceForm:prods");
                            this.userData.setFormInProdId(product.getName());
                        }
                        this.limpiar();
                        this.bundleItems = new ArrayList<>();
                        break;
                    case "-1":
                        this.vb.lanzarMensaje("error", "lblRepeatProductFail", "blank");
                        break;
                    case "-2":
                        this.vb.lanzarMensaje("error", "unexpectedError", "blank");
                        break;

                }

                System.out.println("Res: " + res);

            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.ProductController.addBundle()");
            e.printStackTrace();
        }
    }

    public boolean valBundle() {
        boolean flag = false;
        int c = 0;
        if (this.vb.validarCampoVacio(this.name, "error", "valErr", "lblReqProdName")) {

            if (this.bundleItems.size() >= 2) {
                flag = true;
            } else {
                this.vb.lanzarMensaje("error", "blank", "lblMinBundleItem");
            }

        }

        /*if (!flag) {
            RequestContext.getCurrentInstance().scrollTo("prodForm:msg");
        }*/
        return flag;
    }

    public String formatDouble(BigDecimal num) {
        Double number = Double.parseDouble(num.toString());
        return String.format("%.2f", number);
    }

    public void refrescar() {
        pList = pFacade.getProductsByIdCia(this.userData.getCurrentCia().getIdCia().toString());
        this.vb.updateComponent("tableForm");
    }
}
