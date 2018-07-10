/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbCustomerFacade;
import com.fastbooks.modelo.FbCustomer;
import com.fastbooks.util.ValidationBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DELL
 */
public class StatementController {

    
    private @Getter @Setter String title;
    private @Getter @Setter String idCust;
    private @Getter @Setter FbCustomer currentCust;
    private @Getter @Setter String idCia;
    private @Getter @Setter String stmtType;
    private @Getter @Setter String stmtDate;
    private @Getter @Setter String startDate;
    private @Getter @Setter String endDate;
            
    private        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private DateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    @EJB
    ValidationBean vb; 
    @EJB
    FbCustomerFacade cFacade;
    @Inject
    UserData userData;
    /**
     * Creates a new instance of StatementController
     */
    
    @PostConstruct
    public void init(){
    this.title = this.vb.getMsgBundle("lblCreateStatement");
    this.stmtType = "BF";
    Calendar cal = Calendar.getInstance();
    this.stmtDate = sdf.format(cal.getTime());
    this.endDate = sdf.format(cal.getTime());
    
    cal.add(Calendar.MONTH, -1);
    this.startDate = sdf.format(cal.getTime());
    this.setCustomer();
    }
    
    public void setCustomer(){
        HttpServletRequest req = (HttpServletRequest) vb.getRequestContext();
        int c = 0;
        this.idCust = req.getParameter("id");
        if (this.idCust != null && !this.idCust.isEmpty()) {
            this.currentCust = cFacade.getCustomerByIdCust(idCust,this.userData.getCurrentCia().getIdCia().toString());
            if (this.currentCust != null) {
                if (userData != null && userData.getCurrentCia() != null) {
                    
                }
            }
        }
    }
    
    public StatementController() {
    }
    
}
