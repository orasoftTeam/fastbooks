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
import com.fastbooks.modelo.FbProduct;
import com.fastbooks.modelo.FbTax;
import com.fastbooks.modelo.Terms;
import com.fastbooks.util.ValidationBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
    ValidationBean validationBean;
    @EJB
    FbCustomerFacade cFacade;
    @EJB
    FbInvoiceFacade iFacade;
    @EJB
    FbProductFacade pFacade;
    @EJB
    FbTaxFacade taxFacade;
    private @Getter
    @Setter
    List<FbCustomer> cList = new ArrayList<>();
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
    FbCustomer currentCust;
    private @Getter
    @Setter
    String idCust = "0";

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

    public InvoiceFormController() {
    }

    public void init() {
        try {//this.userData.getCurrentCia().getIdCia().toString()
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
                        if (currentCust.getStreet() != null) {
                            shAddress += currentCust.getStreetS() + " ";
                        }
                        if (currentCust.getPostalCode() != null) {
                            shAddress += currentCust.getPostalCodeS() + " ";
                        }
                        if (currentCust.getCity() != null) {
                            shAddress += currentCust.getCityS() + " ";
                        }
                        if (currentCust.getEstate() != null) {
                            shAddress += currentCust.getEstateS() + " ";
                        }
                        if (currentCust.getCountry() != null) {
                            shAddress += currentCust.getCountryS() + ".";
                        }

                    }
                }
            } else {
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
            if (!this.shipDate.isEmpty()) {
                this.shipDate = sdf.format(sd.parse(this.shipDate));
            }
            System.out.println("invoice date:" + this.invoiceDate + " :: days to add :" + this.termDays + ":: due date: " + this.dueDate);
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.updateDate()");
            e.printStackTrace();
        }
    }

    public void parseDates() {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            this.invoiceDate = sdf.format(sd.parse(this.invoiceDate));
            this.dueDate = sdf.format(sd.parse(this.dueDate));
            if (!this.shipDate.isEmpty()) {
                this.shipDate = sdf.format(sd.parse(this.shipDate));
            }
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

                            if (checkIfInvHasQuant(prodBundle, Integer.parseInt(bi.getQuant().toString()))) {
                                i++;
                            }
                        }

                        if (c == 0 && i == 0) {

                            for (FbBundleItems bi : prod.getFbBundleItemsList()) {
                                prodBundle = bi.getIdProd();

                                addItemInDetailList(prodBundle);

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
                            addItemInDetailList(prod);
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
            }else{
            this.validationBean.lanzarMensaje("error", "lblMaxDetInvoice", "blank");
            }

        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.addDetail()");
            e.printStackTrace();
        }

    }

    public void addItemInDetailList(FbProduct prod) {
        FbInvoiceDetail id = new FbInvoiceDetail(BigDecimal.ZERO);
        id.setIdProd(prod);
        id.setItemName(prod.getName());
        id.setItemDesc(prod.getDescrip());
        id.setItemSku(prod.getSku());
        id.setItemRate(prod.getPrice());
        if (prod.getIdTax() != null) {
            id.setItemTax(prod.getIdTax().getIdTax().toString());
        }
        id.setItemQuant(new BigInteger("1"));

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
                
                if (!this.shCost.isEmpty()) {
                    if (StringUtils.isNumeric(shCost)) {
                        ship = Double.parseDouble(shCost);
                    }else{
                        shCost = "0.00";
                        ship = 0.00;
                    }
                    
                }
                
                 
                
                this.dBalance = String.format("%.2f", (acum + ship+tax ));
                //Double balanceDue = acum + ship;

                this.rBalance = new BigDecimal((acum + ship + tax));
                this.dTotal = String.format("%.2f", (acum + ship+ tax));
                //Double balanceDue = acum + ship;

                this.rTotal = new BigDecimal((acum + ship+ tax));
            } else {
                this.validationBean.lanzarMensaje("error", "lblProdNoQuant", "blank");

            }
            
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.ProductController.updateBundleTotal()");
            e.printStackTrace();
        }
    }

    public String tooltipQuant(FbProduct prod) {
        String res = "";
        if (prod.getType().equals("IN")) {
            res = this.validationBean.getMsgBundle("lblQuantIs");
            res += " " + prod.getInitQuant().toString();
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
        }else{
        taxesAmountList = new ArrayList<>();
        }
        
    }
    
    
    public String formatOutput(BigDecimal input){
    return this.validationBean.formatDouble(input);
    
    }

    /*public void updateTaxes() {

        int t = 0;
        Double rate = 0.00;
        Double amount = 0.00;
        FbInvoiceTaxes it = new FbInvoiceTaxes();
        taxesAmountList = new ArrayList<>();
        for (FbInvoiceDetail det : dList) {
            if (det.getItemTax() == null) {
                det.setItemTax(this.taxList.get(0).getIdTax().toString());
            }
        }

        if (dList.isEmpty()) {
            taxesAmountList = new ArrayList<>();
        }
        for (FbInvoiceDetail det : dList) {
            t = 0;

            for (FbInvoiceDetail deta : dList) {
                if (det.getItemTax().equals(deta.getItemTax())) {
                    t++;
                }
            }
            if (t == 1 && det.getItemQuant().toString().equals("1")) {
                //newtaxesAmountList
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
            } else if (t > 1) {
                //sumar
                int o = 0;
                for (FbInvoiceTaxes inTax : taxesAmountList) {
                    o = 0;
                    String[] split = inTax.getIdProds().split(",");
                    for (String string : split) {
                        if (string.equals(det.getIdProd().getIdProd().toString())) {
                            o++;
                        }
                    }

                    if (o == 0) {
                        if (inTax.getIdTax().getIdTax().toString().equals(det.getItemTax())) {

                            amount = Double.parseDouble(inTax.getFromAmount().toString());
                            inTax.setFromAmount(new BigDecimal(String.valueOf(amount + Double.parseDouble(det.getItemAmount().toString()))));
                            inTax.setIdProds(inTax.getIdProds() + ", " + det.getIdProd().getIdProd());
                            inTax.setTaxAmount(new BigDecimal(String.valueOf(
                                    Double.parseDouble(inTax.getFromAmount().toString())
                                    * Double.parseDouble(inTax.getIdTax().getRate()))));

                        }
                    }

                }

            } else if (t == 1 && (!det.getItemQuant().toString().equals("1"))) {
                for (int i = 0; i < taxesAmountList.size(); i++) {
                    if (taxesAmountList.get(i).getIdTax().getIdTax().toString().equals(det.getItemTax())) {
                        taxesAmountList.get(i).setFromAmount(new BigDecimal(String.valueOf(Double.parseDouble(taxesAmountList.get(i).getFromAmount().toString()) + Double.parseDouble(det.getItemAmount().toString()))));
                        taxesAmountList.get(i).setTaxAmount(new BigDecimal(String.valueOf(Double.parseDouble(taxesAmountList.get(i).getFromAmount().toString())
                                * Double.parseDouble(taxesAmountList.get(i).getIdTax().getRate()))));
                    }
                }

            }
            int s = 0;
            for (int i = 0; i < taxesAmountList.size(); i++) {

            }

        }
    }*/

    public void save() {
        try {
            if (this.validationBean.validarSoloNumerosConPunto(this.shCost, "error", "lblInShCostFail", "blank")) {
                if (this.currentCust != null) {
                
            
            DateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            if (!this.dList.isEmpty() ) {
                /*
     PROCEDURE PR_ACT_INVOICE
 (pIdCia IN INT, pIdInvoice IN INT, pIdCust IN INT, pType IN VARCHAR2,pNo IN VARCHAR2, pEmail IN VARCHAR2,
    pInDate IN VARCHAR2,pDueDate IN VARCHAR2,pActBalance IN DECIMAL,pSubTotal IN DECIMAL,pTaxTotal IN DECIMAL,
     pTotal IN DECIMAL,pStatus IN VARCHAR2,pBiAdd IN VARCHAR2,pShAdd IN VARCHAR2,pTerms IN VARCHAR2,pTrackNum IN VARCHAR2,
      pShipVia IN VARCHAR2,pShDate IN VARCHAR2,pShCost IN DECIMAL,pMessageInvoice IN VARCHAR2,pAttachment IN VARCHAR2,
      pProdIds IN VARCHAR2,pQuants IN VARCHAR2,pIdTaxes IN VARCHAR2,pFromAmounts IN VARCHAR2,pTaxAmounts IN VARCHAR2,
        pTaxProdIds IN VARCHAR2, op IN VARCHAR2, res OUT VARCHAR2); 
    */
                updateTotal();
                FbInvoice in = new FbInvoice();
                in.setIdCia(this.userData.getCurrentCia());
                in.setIdInvoice(BigDecimal.ZERO);
                in.setIdCust(currentCust);
                in.setType("IN");
                in.setNoDot("");
                in.setCustEmail(this.currentCust.getEmail());
                in.setInDate(sd.parse(this.invoiceDate));
                in.setDueDate(sd.parse(this.dueDate));
                in.setActualBalance(this.rBalance);
                in.setSubTotal(rSubTotal);
                in.setTotal(rTotal);
                in.setTaxTotal(rTaxTotal);
                in.setStatus("OP");
                in.setBiAddress(biAddress);
                in.setShAddress(shAddress);
                in.setTerms(termDays);
                in.setTrackNum(trackNo);
                in.setShipVia(shVia);
                in.setShCost(new BigDecimal(this.shCost));
                if (shipDate != null) {
                    in.setShDate(sd.parse(shipDate));
                }
                
                in.setMessageInvoice(messageInvoice);
                in.setAttachment(attach);
                
                in.setFbInvoiceDetailList(dList);
                in.setFbInvoiceTaxesList(taxesAmountList);
                String res = iFacade.actInvoice(in, "A");
                System.out.println("result: " + res);
                if (res.equals(res)) {
                            
                            this.userData.setUses("lblInvoiceAddSuccess");
                            this.validationBean.redirecionar("/view/sales/sales.xhtml");
                }
                
            }else{
                this.validationBean.lanzarMensaje("error", "lblMinDetInvoice", "blank");
            }
            
            }else{
            
            this.validationBean.lanzarMensaje("error", "lblSelectCust", "blank");
            }
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.save()");
            e.printStackTrace();
        }
        
    }
}
