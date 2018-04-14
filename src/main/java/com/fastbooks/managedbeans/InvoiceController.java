/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbCustomerFacade;
import com.fastbooks.facade.FbProductFacade;
import com.fastbooks.modelo.FbCustomer;
import com.fastbooks.modelo.FbInvoiceDetail;
import com.fastbooks.modelo.FbProduct;
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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author dell
 */
@Named(value = "invoiceController")
@ViewScoped
public class InvoiceController implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    UserData userData;
    @EJB
    ValidationBean validationBean;
    @EJB
    FbCustomerFacade cFacade;
    @EJB
    FbProductFacade pFacade;
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
    FbCustomer currentCust;
    private @Getter
    @Setter
    String idCust = "0";

    private @Getter
    @Setter
    String email;
    
    private @Getter
    @Setter
    String invoiceDate;
    private @Getter
    @Setter
    String dueDate;
    private @Getter
    @Setter
    String termDays = "30";
    private @Getter @Setter String biAddress;
    private @Getter @Setter String shAddress;
    private @Getter
    @Setter
    String shipDate;
    private @Getter @Setter String shVia;
    private @Getter @Setter String trackNo;
    private @Getter @Setter String pQuant = "1";
    private @Getter @Setter String idProd = "0";
    private @Getter @Setter String dBalance = "0.00";
    private @Getter @Setter BigDecimal rBalance  = new BigDecimal(BigInteger.ZERO);
    
    private @Getter @Setter String dSubTotal = "0.00";
    private @Getter @Setter BigDecimal rSubTotal  = new BigDecimal(BigInteger.ZERO);
    
    private @Getter @Setter String dTotal = "0.00";
    private @Getter @Setter BigDecimal rTotal  = new BigDecimal(BigInteger.ZERO);
    
    private @Getter @Setter String shCost = "0.00";
    
    

    public InvoiceController() {
    }

    public void init() {
        try {//this.userData.getCurrentCia().getIdCia().toString()
            cList = cFacade.getCustomersByIdCia("1");
            pList = pFacade.getProductsByIdCia("1");
            this.tList.add(new Terms("1", "30", "Credits at 30 days"));
            this.tList.add(new Terms("2", "0", "Due on receipt"));
            this.tList.add(new Terms("3", "15", "Net 15"));
            this.tList.add(new Terms("4", "30", "Net 30"));
            this.tList.add(new Terms("5", "60", "Net 60"));

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
                shAddress=null;
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
            System.out.println("invoice date:" + this.invoiceDate + " :: days to add :" + this.termDays + ":: due date: " + this.dueDate);
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.updateDate()");
            e.printStackTrace();
        }
    }
    
    public void parseDates(){
    
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            this.invoiceDate = sdf.format(sd.parse(this.invoiceDate));
            this.dueDate = sdf.format(sd.parse(this.dueDate));
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.parseDates()");
            //e.printStackTrace();
        }
    }
    
    public void addDetail(){
        try {
            if (!this.idProd.equals("0")) {
                
                int c = 0;
                for (FbInvoiceDetail det : dList) {
                    if (this.idProd.equals(det.getIdProd().getIdProd().toString())) {
                        c++;
                    }
                }
                if (c == 0) {
                    FbProduct prod = new FbProduct();
                for (FbProduct fbProd : pList) {
                    if (this.idProd.equals(fbProd.getIdProd().toString())) {
                        prod = fbProd;
                    }
                }
                
                FbInvoiceDetail id = new FbInvoiceDetail(BigDecimal.ZERO);
                id.setIdProd(prod);
                id.setItemName(prod.getName());
                id.setItemDesc(prod.getDescrip());
                id.setItemSku(prod.getSku());
                id.setItemRate(prod.getPrice());
                
                
                if (!this.pQuant.isEmpty()) {
                    Integer q = Integer.parseInt(this.pQuant);
                    if (q == 0) {
                       this.pQuant ="1"; 
                    }
                    if (prod.getType().equals("SE")) {
                        this.pQuant ="1";
                    }
                }else{
                this.pQuant ="1"; 
                }
                
                id.setItemQuant(new BigInteger(this.pQuant));
                
                Double price = Double.parseDouble(prod.getPrice().toString());
                Integer quant = Integer.parseInt(this.pQuant);
                
                id.setItemAmount(new BigDecimal(String.valueOf(price * quant)));
                dList.add(id);
                this.updateTotal();
                this.pQuant ="1";
                }else{
                this.validationBean.lanzarMensaje("error", "lblAddDetailRepeat", "blank");
                }
                
                
                
            }else{
            this.validationBean.lanzarMensaje("error", "lblAddDetailFail", "blank");
            }
            
            
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.addDetail()");
            e.printStackTrace();
        }
    
    }
    
    public void removeDetail(FbInvoiceDetail det){
        try {
            this.dList.remove(det);
            this.updateTotal();
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.InvoiceController.removeDetail()");
            e.printStackTrace();
        }
    }
    
    public void updateTotal(){
     Double acum = 0.00;
        Double value = 0.00;
        int q = 0;
        try {
            for (FbInvoiceDetail det : dList) {
                value = Double.parseDouble(det.getItemAmount().toString());
                
                acum += value;
            }
            this.dSubTotal = String.format("%.2f", acum);
            this.rSubTotal = new BigDecimal(acum);
            Double ship = 0.00;
            if (!this.shCost.isEmpty()) {
                ship = Double.parseDouble(shCost);
            }
            this.dBalance = String.format("%.2f", (acum + ship));
            //Double balanceDue = acum + ship;
            
            
            this.rBalance = new BigDecimal((acum + ship));
            this.dTotal = String.format("%.2f", (acum + ship));
            //Double balanceDue = acum + ship;
            
            this.rTotal = new BigDecimal((acum + ship));
        } catch (Exception e) {
            System.out.println("com.fastbooks.managedbeans.ProductController.updateBundleTotal()");
            e.printStackTrace();
        }
    }
}
