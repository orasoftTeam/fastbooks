/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbCustomerFacade;

import com.fastbooks.facade.FbInvoiceFacade;
import com.fastbooks.facade.FbProductFacade;
import com.fastbooks.facade.FbTaxFacade;
import com.fastbooks.modelo.FbBundleItems;
import com.fastbooks.modelo.FbCustomer;
import com.fastbooks.modelo.FbInvoice;
import com.fastbooks.modelo.FbInvoiceDetail;
import com.fastbooks.modelo.FbInvoiceTaxes;
import com.fastbooks.modelo.FbPaymentDetail;
import com.fastbooks.modelo.FbProduct;
import com.fastbooks.modelo.FbTax;
import com.fastbooks.modelo.PaymentMethod;
import com.fastbooks.modelo.Terms;
import com.fastbooks.service.InvoiceService;
import com.fastbooks.util.ValidationBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author dell
 */
@Named(value = "invoiceController")
@ViewScoped
public class InvoiceFormController implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    UserData userData;
    @EJB
    @Getter
    ValidationBean validationBean;
    @EJB
    FbCustomerFacade cFacade;
    @EJB
    FbInvoiceFacade iFacade;
    @EJB
    FbProductFacade pFacade;
    @EJB
    FbTaxFacade taxFacade;

    //InvoiceService invoiceService;
    private @Getter
    @Setter
    String invoiceModal = "0";

    private @Getter
    @Setter
    List<FbCustomer> cList = new ArrayList<>();

    private @Getter
    @Setter
    List<FbPaymentDetail> paymentDetailList = new ArrayList<>();

    private @Getter
    @Setter
    List<Terms> tList = new ArrayList<>();
    private @Getter
    @Setter
    List<FbProduct> pList = new ArrayList<>();
    private @Getter
    @Setter
    List<FbInvoiceDetail> dList = new ArrayList<>();
    private @Getter
    @Setter
    List<FbTax> taxList = new ArrayList<>();
    private @Getter
    @Setter
    List<FbInvoiceTaxes> taxesAmountList = new ArrayList<>();
    private @Getter
    @Setter
    List<FbInvoiceTaxes> taxesModList = new ArrayList<>();

    private @Getter
    @Setter
    List<PaymentMethod> pMethodList = new ArrayList<>();

    private @Getter
    @Setter
    FbCustomer currentCust;
    private @Getter
    @Setter
    String idCust = "0";

    private @Getter
    @Setter
    String idOGCust = "0";

    private @Getter
    @Setter
    String paymentMade = "";

    private @Getter
    @Setter
    String idInvoice = "0";

    private @Getter
    @Setter
    String email;

    private @Getter
    @Setter
    boolean hasTax = true;

    private @Getter
    @Setter
    String invoiceDate;
    private @Getter
    @Setter
    String dueDate;
    private @Getter
    @Setter
    String termDays = "30";
    private @Getter
    @Setter
    String biAddress;
    private @Getter
    @Setter
    String shAddress;
    private @Getter
    @Setter
    String shipDate;
    private @Getter
    @Setter
    String shVia;
    private @Getter
    @Setter
    String trackNo;
    private @Getter
    @Setter
    String pQuant = "1";
    private @Getter
    @Setter
    String idProd = "0";
    private @Getter
    @Setter
    String dBalance = "0.00";
    private @Getter
    @Setter
    BigDecimal rBalance = new BigDecimal(BigInteger.ZERO);
    private @Getter
    @Setter
    BigDecimal rTaxTotal = new BigDecimal(BigInteger.ZERO);

    private @Getter
    @Setter
    String dSubTotal = "0.00";
    private @Getter
    @Setter
    String InNo = "";
    private @Getter
    @Setter
    String messageInvoice = "";
    private @Getter
    @Setter
    String attach = "";
    private @Getter
    @Setter
    String dTaxTotal = "0.00";
    private @Getter
    @Setter
    BigDecimal rSubTotal = new BigDecimal(BigInteger.ZERO);

    private @Getter
    @Setter
    String dTotal = "0.00";
    private @Getter
    @Setter
    BigDecimal rTotal = new BigDecimal(BigInteger.ZERO);

    private @Getter
    @Setter
    String shCost = "0.00";
    private @Getter
    @Setter
    String shCostIdTax = "0";
    private @Getter
    @Setter
    String dShCostTaxAmount = "0.00";
    private @Getter
    @Setter
    String dShCostTaxName;
    private @Getter
    @Setter
    BigDecimal rShCostTaxAmount = new BigDecimal(BigInteger.ZERO);

    private @Getter
    @Setter
    boolean mod = false;
    private @Getter
    @Setter
    boolean modStay = false;
    private @Getter
    @Setter
    boolean copy = false;
    private @Getter
    @Setter
    boolean isFormTouched = false;
    private @Getter
    @Setter
    String type;
    private @Getter
    @Setter
    String title;
    private @Getter
    @Setter
    String estimateStatus;
    private @Getter
    @Setter
    String AccBy;
    private @Getter
    @Setter
    String AccDate;
    private @Getter
    @Setter
    String invoiceStatus = "OP";

    private @Getter
    @Setter
    Double totalPayment = 0.00;

    private @Getter
    @Setter
    boolean linked = false;

    private @Getter
    @Setter
    FbInvoice editInvoice;

    public InvoiceFormController() {
    }

    public boolean showSegunType(String tipo) {
        boolean flag = false;
        if (tipo.equals(type)) {
            flag = true;
        }

        return flag;
    }

    public void init() {
        try {//this.userData.getCurrentCia().getIdCia().toString()
            type = this.userData.getInvoiceTypeForm();
            switch (type) {
                case "IN":
                    title = this.validationBean.getMsgBundle("lblInvoiceTypeIn");
                    break;
                case "ES":
                    title = this.validationBean.getMsgBundle("lblEstimate");
                    break;
                case "SR":
                    title = this.validationBean.getMsgBundle("salesR");
                    if (this.pMethodList.isEmpty()) {
                        this.pMethodList.add(new PaymentMethod("1", "", this.validationBean.getMsgBundle("lblCash")));
                        this.pMethodList.add(new PaymentMethod("2", "", this.validationBean.getMsgBundle("lblCreditCard")));
                        this.pMethodList.add(new PaymentMethod("3", "", this.validationBean.getMsgBundle("lblDirectDebit")));
                        this.pMethodList.add(new PaymentMethod("4", "", this.validationBean.getMsgBundle("lblCheque")));
                    }
                    break;
                case "CN":
                    title = this.validationBean.getMsgBundle("creditN");
                    break;
                default:
                    break;
            }
            cList = cFacade.getCustomersByIdCia(this.userData.getCurrentCia().getIdCia().toString());
            pList = pFacade.getProductsByIdCia(this.userData.getCurrentCia().getIdCia().toString());
            taxList = taxFacade.getTaxByIdCia(this.userData.getCurrentCia().getIdCia().toString());
            if (taxList.isEmpty()) {
                hasTax = false;
            }
            if (this.tList.isEmpty()) {
                this.tList.add(new Terms("1", "30", "Credits at 30 days"));
                this.tList.add(new Terms("2", "0", "Due on receipt"));
                this.tList.add(new Terms("3", "15", "Net 15"));
                this.tList.add(new Terms("4", "30", "Net 30"));
                this.tList.add(new Terms("5", "60", "Net 60"));
            }

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Calendar cal = Calendar.getInstance();
            invoiceDate = dateFormat.format(cal.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(this.invoiceDate));
            c.add(Calendar.DATE, Integer.parseInt(this.termDays));
            //dt.plusDays(Integer.parseInt(this.termDays));
            this.dueDate = sdf.format(c.getTime());

        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.init()");
            e.printStackTrace();
        }

    }

    public void changeCust() {
        try {
            if (!this.idCust.equals("0")) {
                for (FbCustomer fbCust : cList) {
                    if (fbCust.getIdCust().toString().equals(idCust)) {
                        this.currentCust = fbCust;
                        email = currentCust.getEmail();
                        biAddress = "";
                        if (currentCust.getStreet() != null) {
                            biAddress += currentCust.getStreet() + " ";
                        }
                        if (currentCust.getPostalCode() != null) {
                            biAddress += currentCust.getPostalCode() + " ";
                        }
                        if (currentCust.getCity() != null) {
                            biAddress += currentCust.getCity() + " ";
                        }
                        if (currentCust.getEstate() != null) {
                            biAddress += currentCust.getEstate() + " ";
                        }
                        if (currentCust.getCountry() != null) {
                            biAddress += currentCust.getCountry() + ".";
                        }

                        shAddress = "";
                        if (currentCust.getStreetS() != null) {
                            shAddress += currentCust.getStreetS() + " ";
                        }
                        if (currentCust.getPostalCodeS() != null) {
                            shAddress += currentCust.getPostalCodeS() + " ";
                        }
                        if (currentCust.getCityS() != null) {
                            shAddress += currentCust.getCityS() + " ";
                        }
                        if (currentCust.getEstateS() != null) {
                            shAddress += currentCust.getEstateS() + " ";
                        }
                        if (currentCust.getCountryS() != null) {
                            shAddress += currentCust.getCountryS() + ".";
                        }

                    }
                }
                if (modStay) {
                    this.setPagos(editInvoice);
                }

                this.didUserTouchForm();
            } else {
                this.idCust = "0";
                currentCust = null;
                email = null;
                biAddress = null;
                shAddress = null;
            }

        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.changeCust()");
            e.printStackTrace();
        }
    }

    public void updateDate() {
        try {
            //DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
            // DateTime dt = formatter.parseDateTime(this.termDays);

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Calendar c = Calendar.getInstance();
            c.setTime(sd.parse(this.invoiceDate));
            c.add(Calendar.DATE, Integer.parseInt(this.termDays));
            //dt.plusDays(Integer.parseInt(this.termDays));
            this.dueDate = sdf.format(c.getTime());
            this.invoiceDate = sdf.format(sd.parse(this.invoiceDate));
            if (this.shipDate != null) {
                this.shipDate = sdf.format(sd.parse(this.shipDate));
            }
            System.out.println("invoice date:" + this.invoiceDate + " :: days to add :" + this.termDays + ":: due date: " + this.dueDate);
            this.didUserTouchForm();
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.updateDate()");
            e.printStackTrace();
        }
    }

    public void parseDates() {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            if (!this.shipDate.isEmpty()) {
                this.shipDate = sdf.format(sd.parse(this.shipDate));
            }
            this.invoiceDate = sdf.format(sd.parse(this.invoiceDate));
            this.dueDate = sdf.format(sd.parse(this.dueDate));

        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.parseDates()");
            //e.printStackTrace();
        }
    }

    public boolean isNotInDetList(String id) {
        int c = 0;
        boolean flag = false;
        for (FbInvoiceDetail det : dList) {
            if (id.equals(det.getIdProd().getIdProd().toString())) {
                c++;
            }
        }
        if (c == 0) {
            flag = true;
        }

        return flag;
    }

    public void addDetail() {
        try {
            if (this.dList.size() < 7) {
                if (!this.idProd.equals("0")) {

                    /* int c = 0;
                for (FbInvoiceDetail det : dList) {
                    if (this.idProd.equals(det.getIdProd().getIdProd().toString())) {
                        c++;
                    }
                }*/
                    if (isNotInDetList(idProd)) {
                        FbProduct prod = new FbProduct();
                        for (FbProduct fbProd : pList) {
                            if (this.idProd.equals(fbProd.getIdProd().toString())) {
                                prod = fbProd;
                            }
                        }

                        if (prod.getType().equals("BU")) {
                            FbProduct prodBundle = new FbProduct();
                            int c = 0;
                            int i = 0;
                            for (FbBundleItems bi : prod.getFbBundleItemsList()) {
                                prodBundle = bi.getIdProd();

                                if (!(isNotInDetList(prodBundle.getIdProd().toString()))) {
                                    c++;
                                }

                                if (!checkIfInvHasQuant(prodBundle, Integer.parseInt(bi.getQuant().toString()))) {
                                    i++;
                                }
                            }

                            if (c == 0 && i == 0) {

                                for (FbBundleItems bi : prod.getFbBundleItemsList()) {
                                    prodBundle = bi.getIdProd();

                                    addItemInDetailList(prodBundle, bi.getQuant().toString());

                                }

                            } else if (c != 0) {
                                this.validationBean.lanzarMensaje("error", "lblBundleInDetail", "blank");
                            } else if (i != 0) {
                                this.validationBean.lanzarMensaje("error", "lblBundNoQuant", "blank");
                            } else {
                                System.out.println("It should never go here");
                            }

                            /**/
                        } else {

                            if (checkIfInvHasQuant(prod, 1)) {
                                addItemInDetailList(prod, "1");
                            } else {
                                this.validationBean.lanzarMensaje("error", "lblProdNoQuant", "blank");
                            }

                        }
                        this.updateTotal();

                    } else {
                        this.validationBean.lanzarMensaje("error", "lblAddDetailRepeat", "blank");
                    }

                } else {
                    this.validationBean.lanzarMensaje("error", "lblAddDetailFail", "blank");
                }
            } else {
                this.validationBean.lanzarMensaje("error", "lblMaxDetInvoice", "blank");
            }

        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.addDetail()");
            e.printStackTrace();
        }

    }

    public void addItemInDetailList(FbProduct prod, String quantity) {
        FbInvoiceDetail id = new FbInvoiceDetail(BigDecimal.ZERO);
        id.setIdProd(prod);
        id.setItemName(prod.getName());
        id.setItemDesc(prod.getDescrip());
        id.setItemSku(prod.getSku());
        id.setItemRate(prod.getPrice());
        if (prod.getIdTax() != null) {
            id.setItemTax(prod.getIdTax().getIdTax().toString());
        }
        id.setItemQuant(new BigInteger(quantity));

        Double price = Double.parseDouble(prod.getPrice().toString());
        Integer quant = Integer.parseInt(id.getItemQuant().toString());

        id.setItemAmount(new BigDecimal(String.valueOf(price * quant)));
        dList.add(id);
    }

    public boolean checkIfInvHasQuant(FbProduct prod, Integer q) {
        boolean flag = true;
        Integer prodQ = Integer.parseInt(prod.getInitQuant().toString());
        Integer res = 0;
        if (prod.getType().equals("IN")) {

            res = prodQ - q;

            if (res < 0) {
                flag = false;
            }

        }

        if (mod) {
            flag = true;
        }

        return flag;
    }

    public void removeDetail(FbInvoiceDetail det) {
        try {
            this.dList.remove(det);
            this.updateTotal();
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.removeDetail()");
            e.printStackTrace();
        }
    }

    public void updateTotal() {
        Double acum = 0.00;
        Double value = 0.00;
        int q = 0;
        Double price = 0.00;
        int c = 0;

        try {

            for (FbInvoiceDetail det : dList) {
                if (det.getItemQuant() == null) {
                    det.setItemQuant(BigInteger.ONE);
                } else if (det.getItemQuant().equals(BigInteger.ZERO)) {
                    det.setItemQuant(BigInteger.ONE);
                } else if (!checkIfInvHasQuant(det.getIdProd(), Integer.valueOf(det.getItemQuant().toString()))) {
                    c++;
                    det.setItemQuant(BigInteger.ONE);
                }

                if (c == 0) {
                    q = Integer.parseInt(det.getItemQuant().toString());
                    price = Double.parseDouble(det.getItemRate().toString());
                    det.setItemAmount(new BigDecimal(String.valueOf(q * price)));
                    value = Double.parseDouble(det.getItemAmount().toString());

                    acum += value;
                }
            }
            if (c == 0) {
                actTaxes();
                this.dSubTotal = String.format("%.2f", acum);
                this.rSubTotal = new BigDecimal(acum);
                Double ship = 0.00;
                Double tax = 0.00;

                tax = Double.parseDouble(this.rTaxTotal.toString());
                Double t = 0.00;
                Double shTaxAmount = 0.00;
                if (!this.shCost.isEmpty()) {
                    try {
                        t = Double.parseDouble(shCost);
                        boolean result = t <= 0.00;
                        if (result) {
                            t = 0.00;
                            shCost = "0.00";
                        } else {
                            if (!this.shCostIdTax.equals("0")) {
                                for (FbTax fbTax : taxList) {
                                    if (fbTax.getIdTax().toString().equals(this.shCostIdTax)) {
                                        shTaxAmount = t * Double.parseDouble(fbTax.getRate());
                                        this.dShCostTaxName = fbTax.getName() + "(" + fbTax.getRate() + "%)";
                                    }
                                }
                            }
                        }

                    } catch (Exception e) {
                        t = 0.00;
                        shCost = "0.00";
                    }

                    //if (StringUtils.isNumeric(shCost)) {
                    ship = t;
                    // } else {
                    //  shCost = "0.00";
                    // ship = 0.00;
                    //}

                } else {
                    this.shCost = "0.00";
                }

                this.dBalance = String.format("%.2f", (acum + ship + tax + shTaxAmount));
                //Double balanceDue = acum + ship;
                this.dShCostTaxAmount = String.format("%.2f", shTaxAmount);
                this.rShCostTaxAmount = new BigDecimal(shTaxAmount);
                this.rBalance = new BigDecimal((acum + ship + tax + shTaxAmount));
                this.dTotal = String.format("%.2f", (acum + ship + tax + shTaxAmount));
                //Double balanceDue = acum + ship;

                this.rTotal = new BigDecimal((acum + ship + tax + shTaxAmount));

                /* if (modStay && this.paymentDetailList.size() != 0) {
                    this.rBalance = new BigDecimal(this.rBalance.doubleValue() - this.totalPayment);
                }*/
                this.didUserTouchForm();
            } else {
                this.validationBean.lanzarMensaje("error", "lblProdNoQuant", "blank");

            }

        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.ProductController.updateBundleTotal()");
            e.printStackTrace();
        }
    }

    public String tooltipQuant(FbProduct prod, String quantity) {
        String res = "";
        if (prod.getType().equals("IN")) {
            res = this.validationBean.getMsgBundle("lblQuantIs");
            res += " " + (Integer.parseInt(prod.getInitQuant().toString()) - Integer.parseInt(quantity));
        } else {
            res = this.validationBean.getMsgBundle("lblQuant");
        }

        return res;
    }

    public String formatProdDisplay(FbProduct p) {
        String res = "";

        switch (p.getType()) {
            case "IN":
                res = p.getName() + " (" + validationBean.getMsgBundle("lblPrice") + ":"
                        + p.getPrice().toString() + ", " + validationBean.getMsgBundle("lblQuant")
                        + ":" + p.getInitQuant().toString();

                break;
            case "BU":
                res = p.getName() + " (" + validationBean.getMsgBundle("lblPrice") + ":"
                        + p.getTotalBundle().toString();

                break;
            default:
                res = p.getName() + " (" + validationBean.getMsgBundle("lblPrice") + ":"
                        + p.getPrice().toString();
                break;

        }

        if (p.getDescrip() != null) {
            res += ", " + p.getDescrip();
        }
        res += ")";
        return res;
    }

    public void actTaxes() {

        if (hasTax) {
            int a = 0;
            int c = 0;
            List<String> idTaxes = new ArrayList<>();

            taxesAmountList = new ArrayList<>();

            FbInvoiceTaxes it = new FbInvoiceTaxes();
            Double rate = 0.00;
            Double amount = 0.00;

            for (FbInvoiceDetail det : dList) {
                if (det.getItemTax() == null) {
                    det.setItemTax(this.taxList.get(0).getIdTax().toString());
                }

            }

            for (FbInvoiceDetail det : dList) {
                c = 0;

                for (FbInvoiceDetail deta : dList) {
                    if (deta.getItemTax().equals(det.getItemTax())) {
                        c++;
                    }

                }
                //System.out.println(c);
                if (c == 1) { //solo esta una vez
                    idTaxes.add(det.getItemTax());
                } else if (c > 1) {//dos o mas veces
                    for (String idTaxe : idTaxes) {
                        if (idTaxe.equals(det.getItemTax())) {
                            a++;
                        }
                    }

                    if (a == 0) {
                        idTaxes.add(det.getItemTax());
                    }

                }
            }

            for (String t : idTaxes) {
                a = 0;
                for (FbInvoiceDetail det : dList) {
                    if (det.getItemTax().equals(t)) {
                        if (a == 0) {
                            it = new FbInvoiceTaxes();
                            for (FbTax fbTax : this.taxList) {
                                if (det.getItemTax().equals(fbTax.getIdTax().toString())) {
                                    it.setIdTax(fbTax);
                                }
                            }
                            it.setFromAmount(det.getItemAmount());
                            it.setIdProds(det.getIdProd().getIdProd().toString());
                            rate = Double.parseDouble(it.getIdTax().getRate());
                            amount = Double.parseDouble(det.getItemAmount().toString());
                            it.setTaxAmount(new BigDecimal(String.valueOf(amount * rate)));

                            this.taxesAmountList.add(it);
                            a++;
                        } else {

                            for (FbInvoiceTaxes inTax : taxesAmountList) {

                                if (inTax.getIdTax().getIdTax().toString().equals(det.getItemTax())) {

                                    amount = Double.parseDouble(inTax.getFromAmount().toString());
                                    inTax.setFromAmount(new BigDecimal(String.valueOf(amount + Double.parseDouble(det.getItemAmount().toString()))));
                                    inTax.setIdProds(inTax.getIdProds() + ":" + det.getIdProd().getIdProd());
                                    inTax.setTaxAmount(new BigDecimal(String.valueOf(
                                            Double.parseDouble(inTax.getFromAmount().toString())
                                            * Double.parseDouble(inTax.getIdTax().getRate()))));

                                }
                            }
                        }
                    }

                }
            }
            Double acum = 0.00;
            for (FbInvoiceTaxes fbInvoiceTaxes : taxesAmountList) {
                acum += Double.parseDouble(fbInvoiceTaxes.getTaxAmount().toString());
            }
            this.rTaxTotal = new BigDecimal(acum.toString());
            this.dTaxTotal = rTaxTotal.toString();
            System.out.println(taxesAmountList.size());
        } else {
            taxesAmountList = new ArrayList<>();
        }

    }

    public String formatOutput(BigDecimal input) {
        return this.validationBean.formatDouble(input);

    }

 
    public void save(String op) {
        try {
            if (this.validationBean.validarSoloNumerosConPunto(this.shCost, "error", "lblInShCostFail", "blank")) {
                if (this.currentCust != null || type.equals("SR")) {

                    if (this.currentCust == null) {
                        currentCust = new FbCustomer();
                        currentCust.setIdCust(BigDecimal.ZERO);
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    DateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                    if (!this.dList.isEmpty()) {

                        updateTotal();
                        FbInvoice in = new FbInvoice();
                        in.setIdCia(this.userData.getCurrentCia());
                        in.setIdInvoice(new BigDecimal(idInvoice));
                        in.setIdCust(currentCust);
                        in.setType(type);
                        in.setNoDot(this.InNo);
                        in.setCustEmail(this.currentCust.getEmail());
                        //in.setInDate(sdf.format(sd.parse(this.invoiceDate)));
                        try {
                            in.setInDate(sdf.format(sd.parse(this.invoiceDate)));
                        } catch (Exception e) {
                            in.setInDate(this.invoiceDate);
                        }

                        try {
                            in.setDueDate(sdf.format(sd.parse(this.dueDate)));
                        } catch (Exception e) {
                            in.setDueDate(this.dueDate);
                        }

                        //in.setDueDate(sdf.format(sd.parse(this.dueDate)));
                        Double resultado = 0.00;
                        if (modStay && this.paymentDetailList.size() != 0) {
                            resultado = this.rBalance.doubleValue() - this.totalPayment;
                            in.setActualBalance(new BigDecimal(resultado));
                        } else {
                            in.setActualBalance(this.rBalance);
                        }

                        in.setSubTotal(rSubTotal);
                        in.setTotal(rTotal);
                        in.setTaxTotal(rTaxTotal);

                        switch (type) {
                            case "IN":
                                Date date = null;
                                try {
                                    date = sdf.parse(this.dueDate);
                                } catch (Exception e) {
                                    date = sd.parse(this.dueDate);
                                }
                                if (date.before(new Date())) {
                                    this.invoiceStatus = "OV";
                                } else {
                                    this.invoiceStatus = "OP";
                                }

                                if (modStay && this.paymentDetailList.size() != 0) {
                                    Double result = this.rBalance.doubleValue() - this.totalPayment;
                                    if (result == 0 || result < 0) {
                                        this.invoiceStatus = "PD";
                                    } else if (result > 0) {
                                        this.invoiceStatus = "PA";
                                    }
                                }
                                in.setStatus(this.invoiceStatus);//aqui 
                                break;
                            case "ES":
                                in.setStatus(this.estimateStatus);//aqui 
                                if (this.estimateStatus.equals("PE")) {
                                    in.setEsAccby("");
                                    in.setEsAccdate("");
                                } else {
                                    in.setEsAccby(AccBy);

                                    try {
                                        in.setEsAccdate(sdf.format(sd.parse(AccDate)));
                                    } catch (Exception e) {
                                        in.setEsAccdate("");

                                    }

                                }
                                break;
                            case "SR":
                                in.setStatus("PD");
                                in.setDueDate(" ");
                                break;
                            default:
                                break;
                        }

                        in.setBiAddress(biAddress);
                        in.setShAddress(shAddress);
                        in.setTerms(termDays);
                        in.setTrackNum(trackNo);
                        in.setShipVia(shVia);
                        in.setShCost(new BigDecimal(this.shCost));
                        if (shipDate != null) {
                            in.setShDate(shipDate);
                            // in.setShDate(sdf.format(sd.parse(shipDate)));
                        }

                        in.setMessageInvoice(messageInvoice);
                        in.setAttachment(attach);

                        in.setFbInvoiceDetailList(dList);
                        in.setFbInvoiceTaxesList(taxesAmountList);

                        in.setShcostTaxAmount(rShCostTaxAmount);
                        in.setShcostTaxName(dShCostTaxName);

                        for (FbTax fbTax : taxList) {
                            if (fbTax.getIdTax().toString().equals(this.shCostIdTax)) {
                                in.setIdShcostTax(fbTax);
                            }
                        }
                        String res = "def";
                        res = iFacade.actInvoice(in, op);
                        System.out.println("result: " + res);
                        if (res.equals("0")) {
                            String message = "unexpectedError";
                            if (op.equals("A")) {
                                if (type.equals("IN")) {
                                    message = "lblInvoiceAddSuccess";
                                } else if (type.equals("ES")) {
                                    message = "lblEstimateAddSuccess";
                                } else if (type.equals("SR")) {
                                    message = "lblSalesRAddSuccess";
                                }

                            } else if (op.equals("U")) {
                                if (type.equals("IN")) {
                                    message = "lblInUpdateSuccess";
                                } else if (type.equals("ES")) {
                                    message = "lblEsUpdateSuccess";
                                } else if (type.equals("SR")) {
                                    message = "lblSalesRUpdateSuccess";
                                }
                            } else if (op.equals("D")) {
                                if (type.equals("IN")) {
                                    message = "lblInvoiceDeleted";
                                } else if (type.equals("ES")) {
                                    message = "lblEstimateDeleted";
                                } else if (type.equals("SR")) {
                                    message = "lblSalesReceiptDeleted";
                                }
                            }

                            this.userData.setUses(message);
                            this.validationBean.redirecionar("/view/sales/sales.xhtml");
                        } else {
                            //this.validationBean.lanzarMensajeSinBundle("error", res, " ");
                        }

                    } else {
                        this.validationBean.lanzarMensaje("error", "lblMinDetInvoice", "blank");
                    }

                } else {

                    this.validationBean.lanzarMensaje("error", "lblSelectCust", "blank");
                }
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.save()");
            // this.validationBean.lanzarMensajeSinBundle("error", e.toString(), " ");
            e.printStackTrace();
        }

    }

    public void assign() {
        FbInvoice in = this.userData.getFbInvoice();

        HttpServletRequest req = (HttpServletRequest) validationBean.getRequestContext();
        System.out.println("id:" + req.getParameter("id"));

        try {

            if (req.getParameter("id") != null && in == null) {
                in = iFacade.getInvoiceByIdInvoice(req.getParameter("id"));
            }

            if (in != null) {
                this.type = in.getType();
                this.currentCust = in.getIdCust();
                //invoiceService = new InvoiceService();
                //in.setFbInvoiceDetailList(invoiceService.getFbInvoiceDetailByIdInvoice(in.getIdInvoice()));
                //in.setFbInvoiceTaxesList(invoiceService.getFbInvoiceTaxesByIdInvoice(in.getIdInvoice()));

                this.editInvoice = in;

                in.setFbInvoiceDetailList(this.iFacade.getInvoiceDetailsByIdInvoice(in.getIdInvoice().toString()));
                in.setFbInvoiceTaxesList(this.iFacade.getInvoiceTaxesByIdInvoice(in.getIdInvoice().toString()));
                if (in.getIdCust() != null) {
                    this.idOGCust = in.getIdCust().getIdCust().toString();
                    this.idCust = in.getIdCust().getIdCust().toString();
                    this.email = in.getIdCust().getEmail();
                    biAddress = "";
                    if (currentCust.getStreet() != null) {
                        biAddress += currentCust.getStreet() + " ";
                    }
                    if (currentCust.getPostalCode() != null) {
                        biAddress += currentCust.getPostalCode() + " ";
                    }
                    if (currentCust.getCity() != null) {
                        biAddress += currentCust.getCity() + " ";
                    }
                    if (currentCust.getEstate() != null) {
                        biAddress += currentCust.getEstate() + " ";
                    }
                    if (currentCust.getCountry() != null) {
                        biAddress += currentCust.getCountry() + ".";
                    }

                    shAddress = "";
                    if (currentCust.getStreetS() != null) {
                        shAddress += currentCust.getStreetS() + " ";
                    }
                    if (currentCust.getPostalCodeS() != null) {
                        shAddress += currentCust.getPostalCodeS() + " ";
                    }
                    if (currentCust.getCityS() != null) {
                        shAddress += currentCust.getCityS() + " ";
                    }
                    if (currentCust.getEstateS() != null) {
                        shAddress += currentCust.getEstateS() + " ";
                    }
                    if (currentCust.getCountryS() != null) {
                        shAddress += currentCust.getCountryS() + ".";
                    }

                    this.setPagos(in);
                    if (!this.paymentDetailList.isEmpty()) {
                        linked = true;
                    }
                } else {
                    this.idCust = "0";
                }

                this.InNo = in.getNoDot();

                termDays = in.getTerms();
                invoiceDate = in.getInDate();
                dueDate = in.getDueDate();
                shVia = in.getShipVia();
                shipDate = in.getShDate();
                trackNo = in.getTrackNum();
                shCost = in.getShCost().toString();
                idInvoice = in.getIdInvoice().toString();
                this.dList = in.getFbInvoiceDetailList();
                if (in.getIdShcostTax() != null) {
                    this.shCostIdTax = in.getIdShcostTax().getIdTax().toString();
                }
                if (in.getShcostTaxAmount() != null) {
                    this.dShCostTaxAmount = in.getShcostTaxAmount().toString();
                    this.dShCostTaxName = in.getShcostTaxName();
                    this.rShCostTaxAmount = in.getShcostTaxAmount();
                } else {
                    this.dShCostTaxAmount = "0.00";
                    this.dShCostTaxName = "";
                    this.rShCostTaxAmount = new BigDecimal(BigInteger.ZERO);
                }

                if (in.getFbInvoiceTaxesList().isEmpty()) {
                    hasTax = false;
                } else {
                    this.taxesModList = in.getFbInvoiceTaxesList();
                }

                for (FbInvoiceDetail det : dList) {

                    for (FbInvoiceTaxes taz : taxesModList) {
                        String[] split = taz.getIdProds().split(":");
                        for (String string : split) {
                            if (det.getIdProd().getIdProd().toString().equals(string)) {
                                det.setItemTax(taz.getIdTax().getIdTax().toString());
                            }
                        }
                    }

                }
                Integer initQuant = 0;
                Integer itemQuant = 0;
                for (FbInvoiceDetail det : dList) {
                    initQuant = Integer.parseInt(det.getIdProd().getInitQuant().toString());
                    itemQuant = Integer.parseInt(det.getItemQuant().toString());
                    det.getIdProd().setInitQuant(new BigInteger(String.valueOf(initQuant + itemQuant)));

                    for (FbProduct fbProduct : pList) {
                        if (fbProduct.getIdProd().toString().equals(det.getIdProd().getIdProd().toString())) {
                            initQuant = Integer.parseInt(fbProduct.getInitQuant().toString());
                            fbProduct.setInitQuant(new BigInteger(String.valueOf(initQuant + itemQuant)));
                        }

                        if (fbProduct.getType().equals("BU")) {
                            for (FbBundleItems fbBundleItems : fbProduct.getFbBundleItemsList()) {
                                if (fbBundleItems.getIdProd().getIdProd().toString().equals(det.getIdProd().getIdProd().toString())) {
                                    initQuant = Integer.parseInt(fbBundleItems.getIdProd().getInitQuant().toString());
                                    fbBundleItems.getIdProd().setInitQuant(new BigInteger(String.valueOf(initQuant + itemQuant)));
                                }
                            }
                        }
                    }
                }

                if (in.getType().equals("ES")) {
                    this.estimateStatus = in.getStatus();
                    if (!this.estimateStatus.equals("PE")) {
                        this.AccBy = in.getEsAccby();
                        this.AccDate = in.getEsAccdate();
                        this.validationBean.ejecutarJavascript("show('" + in.getStatus() + "');");

                    }
                } else if (in.getType().equals("IN")) {
                    this.invoiceStatus = in.getStatus();
                    System.out.println("status: " + this.invoiceStatus);
                }
                mod = true;
                if (!in.getNoDot().equals("copy")) {

                    modStay = true;

                } else {
                    copy = true;
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Calendar cal = Calendar.getInstance();
                    invoiceDate = dateFormat.format(cal.getTime());
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                    Calendar c = Calendar.getInstance();
                    c.setTime(sdf.parse(this.invoiceDate));
                    c.add(Calendar.DATE, Integer.parseInt(this.termDays));
                    //dt.plusDays(Integer.parseInt(this.termDays));
                    this.dueDate = sdf.format(c.getTime());
                }

                updateTotal();
                this.userData.setFbInvoice(null);
                mod = false;
            }

        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceFormController.assign()");
            e.printStackTrace();
        }

    }

    public void refreshCombo() {
        if (!this.userData.getFormInProdId().equals("0")) {
            pList = pFacade.getProductsByIdCia(this.userData.getCurrentCia().getIdCia().toString());
            for (FbProduct p : pList) {
                if (p.getName().equals(this.userData.getFormInProdId())) {
                    this.idProd = p.getIdProd().toString();
                }
            }
            this.userData.setFormInProdId("0");

            this.validationBean.updateComponent("invoiceForm:prods");
            this.validationBean.lanzarMensaje("info", "lblAddProdSuccess", "blank");
        }

    }

    public void refreshComboCust() {
        if (!this.userData.getFormInCustId().equals("0")) {
            cList = cFacade.getCustomersByIdCia(this.userData.getCurrentCia().getIdCia().toString());
            for (FbCustomer c : cList) {
                if (c.getEmail().equals(this.userData.getFormInCustId())) {
                    idCust = c.getIdCust().toString();
                }
            }
            this.userData.setFormInCustId("0");

            this.validationBean.updateComponent("invoiceForm:custs");
            this.changeCust();
            this.validationBean.updateComponent("invoiceForm:custs");
            // validationBean.lanzarMensaje("info", "custAdded", "blank");
        }

    }

    public void didUserTouchForm() {
        if (!this.isFormTouched) {
            this.isFormTouched = true;
            this.validationBean.updateComponent("clear");
        }

    }

    public void exit() {

        if (this.isFormTouched) {
            //confirm
            this.validationBean.ejecutarJavascript("PF('dlg5').show();");
        } else {
            // exit
            validationBean.redirecionar("/view/sales/sales.xhtml");
        }

    }

    public boolean showShTax() {
        boolean flag = false;
        if (!this.dShCostTaxAmount.equals("0.00")) {

            flag = true;
        }
        return flag;

    }

    public void pagar() {
        this.validationBean.redirecionar("/view/sales/payments/paymentForm.xhtml");
    }

    public boolean showReceivePayment() {
        boolean flag = false;
        if (modStay) {
            if (this.type.equals("IN")) {
                if (this.invoiceStatus.equals("OV") || this.invoiceStatus.equals("PA") || this.invoiceStatus.equals("OP")) {
                    if (this.idCust.equals(idOGCust)) {
                        flag = true;
                    }

                }
            }
        }

        return flag;
    }

    public String showBalanceDue() {
        String res = "";
        Double dob = 0.00;
        if (modStay) {
            //res = String.format("%.2f", (this.rBalance.doubleValue() - totalPayment));
            dob = this.rBalance.doubleValue() - totalPayment;

            if (dob == 0) {
                res = this.validationBean.getMsgBundle("lblPaid");
            } else if (dob != 0) {
                res = String.format("%.2f", dob);
            }
        } else {
            res = this.dBalance;
        }
        return res;
    }

    public void setPagos(FbInvoice in) {
        if (this.idCust.equals(idOGCust)) {
            if (in.getType().equals("IN")) {
                System.out.println("idInvoice:" + in.getIdInvoice().toString());
                this.paymentDetailList = this.iFacade.getPaymentDetailsByIdInvoice(in.getIdInvoice().toString());
                if (!this.paymentDetailList.isEmpty()) {
                    System.out.println("TIENE PAGOS");
                    this.totalPayment = 0.00;
                    for (FbPaymentDetail pd : this.paymentDetailList) {
                        System.out.println("id:" + pd.getIdDetail().toString() + "  date: " + pd.getIdPayment().getInDate() + " amount: " + pd.getPayment().toString());
                        this.totalPayment += pd.getPayment().doubleValue();

                    }
                    System.out.println("total pago: " + this.totalPayment);

                    //Formateando mensaje de salida
                    String cadena = "";
                    if (this.paymentDetailList.size() > 1) {
                        cadena = this.validationBean.getMsgBundle("lblPaymentPlural");
                    } else {
                        cadena = this.validationBean.getMsgBundle("lblPaymentLower");
                    }
                    this.paymentMade = "<a onclick='myFunction();' class='dropLi' >" + this.paymentDetailList.size() + " " + cadena + "</a> " + this.validationBean.getMsgBundle("lblPaymentMade")
                            + " " + this.paymentDetailList.get(this.paymentDetailList.size() - 1).getIdPayment().getInDate();

                } else {
                    System.out.println("NO TIENE PAGOS");
                }
            }
        } else {
            this.totalPayment = 0.00;
            this.paymentDetailList = new ArrayList<>();
        }
    }

    public void print(FbInvoice i, HttpServletRequest req) {
        try {
            String jasperFile = i.getIdCust() == null ? "salesReceiptSinCust" : "report1";

            this.invoiceModal = this.iFacade.generateInvoice(i, this.userData.getCurrentCia().getLogo(), this.iFacade.getCompiledFile(jasperFile, req), this.validationBean.formatType(i.getType()));
            
            this.validationBean.updateComponent("pdf");
            this.validationBean.ejecutarJavascript("$('.invoiceModal').modal();");
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.print()");
        }
    }
    
        public void saveForPrint(String op) {
        try {
            if (this.validationBean.validarSoloNumerosConPunto(this.shCost, "error", "lblInShCostFail", "blank")) {
                if (this.currentCust != null || type.equals("SR")) {

                    if (this.currentCust == null) {
                        currentCust = new FbCustomer();
                        currentCust.setIdCust(BigDecimal.ZERO);
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    DateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                    if (!this.dList.isEmpty()) {

                        updateTotal();
                        FbInvoice in = new FbInvoice();
                        in.setIdCia(this.userData.getCurrentCia());
                        in.setIdInvoice(new BigDecimal(idInvoice));
                        in.setIdCust(currentCust);
                        in.setType(type);
                        in.setNoDot(this.InNo);
                        in.setCustEmail(this.currentCust.getEmail());
                        //in.setInDate(sdf.format(sd.parse(this.invoiceDate)));
                        try {
                            in.setInDate(sdf.format(sd.parse(this.invoiceDate)));
                        } catch (Exception e) {
                            in.setInDate(this.invoiceDate);
                        }

                        try {
                            in.setDueDate(sdf.format(sd.parse(this.dueDate)));
                        } catch (Exception e) {
                            in.setDueDate(this.dueDate);
                        }

                        //in.setDueDate(sdf.format(sd.parse(this.dueDate)));
                        Double resultado = 0.00;
                        if (modStay && this.paymentDetailList.size() != 0) {
                            resultado = this.rBalance.doubleValue() - this.totalPayment;
                            in.setActualBalance(new BigDecimal(resultado));
                        } else {
                            in.setActualBalance(this.rBalance);
                        }

                        in.setSubTotal(rSubTotal);
                        in.setTotal(rTotal);
                        in.setTaxTotal(rTaxTotal);

                        switch (type) {
                            case "IN":
                                Date date = null;
                                try {
                                    date = sdf.parse(this.dueDate);
                                } catch (Exception e) {
                                    date = sd.parse(this.dueDate);
                                }
                                if (date.before(new Date())) {
                                    this.invoiceStatus = "OV";
                                } else {
                                    this.invoiceStatus = "OP";
                                }

                                if (modStay && this.paymentDetailList.size() != 0) {
                                    Double result = this.rBalance.doubleValue() - this.totalPayment;
                                    if (result == 0 || result < 0) {
                                        this.invoiceStatus = "PD";
                                    } else if (result > 0) {
                                        this.invoiceStatus = "PA";
                                    }
                                }
                                in.setStatus(this.invoiceStatus);//aqui 
                                break;
                            case "ES":
                                in.setStatus(this.estimateStatus);//aqui 
                                if (this.estimateStatus.equals("PE")) {
                                    in.setEsAccby("");
                                    in.setEsAccdate("");
                                } else {
                                    in.setEsAccby(AccBy);

                                    try {
                                        in.setEsAccdate(sdf.format(sd.parse(AccDate)));
                                    } catch (Exception e) {
                                        in.setEsAccdate("");

                                    }

                                }
                                break;
                            case "SR":
                                in.setStatus("PD");
                                in.setDueDate(" ");
                                break;
                            default:
                                break;
                        }

                        in.setBiAddress(biAddress);
                        in.setShAddress(shAddress);
                        in.setTerms(termDays);
                        in.setTrackNum(trackNo);
                        in.setShipVia(shVia);
                        in.setShCost(new BigDecimal(this.shCost));
                        if (shipDate != null) {
                            in.setShDate(shipDate);
                            // in.setShDate(sdf.format(sd.parse(shipDate)));
                        }

                        in.setMessageInvoice(messageInvoice);
                        in.setAttachment(attach);

                        in.setFbInvoiceDetailList(dList);
                        in.setFbInvoiceTaxesList(taxesAmountList);

                        in.setShcostTaxAmount(rShCostTaxAmount);
                        in.setShcostTaxName(dShCostTaxName);

                        for (FbTax fbTax : taxList) {
                            if (fbTax.getIdTax().toString().equals(this.shCostIdTax)) {
                                in.setIdShcostTax(fbTax);
                            }
                        }
                        String res = "def";
                        res = iFacade.actInvoiceWithReturnId(in, op);
                        System.out.println("result: " + res);
                        if (res.equals("0")) {
                            String message = "unexpectedError";
                            if (op.equals("A")) {
                                if (type.equals("IN")) {
                                    message = "lblInvoiceAddSuccess";
                                } else if (type.equals("ES")) {
                                    message = "lblEstimateAddSuccess";
                                } else if (type.equals("SR")) {
                                    message = "lblSalesRAddSuccess";
                                }

                            } else if (op.equals("U")) {
                                if (type.equals("IN")) {
                                    message = "lblInUpdateSuccess";
                                } else if (type.equals("ES")) {
                                    message = "lblEsUpdateSuccess";
                                } else if (type.equals("SR")) {
                                    message = "lblSalesRUpdateSuccess";
                                }
                            } else if (op.equals("D")) {
                                if (type.equals("IN")) {
                                    message = "lblInvoiceDeleted";
                                } else if (type.equals("ES")) {
                                    message = "lblEstimateDeleted";
                                } else if (type.equals("SR")) {
                                    message = "lblSalesReceiptDeleted";
                                }
                            }
                            this.validationBean.lanzarMensaje("info", message, "blank");
                            this.print(iFacade.getInvoiceByIdInvoice(res), this.validationBean.getRequestContext());
                            //this.userData.setUses(message);
                            //this.validationBean.redirecionar("/view/sales/sales.xhtml");
                        } else {
                            //this.validationBean.lanzarMensajeSinBundle("error", res, " ");
                        }

                    } else {
                        this.validationBean.lanzarMensaje("error", "lblMinDetInvoice", "blank");
                    }

                } else {

                    this.validationBean.lanzarMensaje("error", "lblSelectCust", "blank");
                }
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.save()");
            // this.validationBean.lanzarMensajeSinBundle("error", e.toString(), " ");
            e.printStackTrace();
        }

    }

}
