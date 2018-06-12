/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.service;

import com.fastbooks.dao.FbInvoiceDetailDao;
import com.fastbooks.dao.FbInvoiceTaxesDao;
import com.fastbooks.modelo.FbInvoiceDetail;
import com.fastbooks.modelo.FbInvoiceTaxes;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author DELL
 */
public class InvoiceService {
    
    FbInvoiceDetailDao detailDao = new FbInvoiceDetailDao();
    FbInvoiceTaxesDao taxesDao = new FbInvoiceTaxesDao();
    
    public List<FbInvoiceDetail> getFbInvoiceDetailByIdInvoice(BigDecimal idInvoice){
    return detailDao.getInvoiceDetailsByIdInvoice(idInvoice.intValue());
    }
    
    public List<FbInvoiceTaxes> getFbInvoiceTaxesByIdInvoice(BigDecimal idInvoice){
    return taxesDao.getInvoiceTaxesByIdInvoice(idInvoice.intValue());
    }
    
    
}
