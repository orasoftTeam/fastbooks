/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.modelo.FbPaymentDetail;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author DELL
 */
@Stateless
public class FbPaymentDetailFacade extends AbstractFacade<FbPaymentDetail>{

    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;
    
  

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public FbPaymentDetailFacade(){
    super(FbPaymentDetail.class);
    }

    public List<FbPaymentDetail> getPaymentDetailsByIdPayment(String idPayment) {
        List<FbPaymentDetail> list = new ArrayList<>();
        try {
            
            String sql = "select * from fb_invoice_taxes where id_invoice = ? order by ID_INVOICE_TAX asc";
            Query q = em.createNativeQuery(sql, FbPaymentDetail.class);
            q.setParameter(1, idPayment);
            list = q.getResultList();
            for (FbPaymentDetail fbInvoice : list) {
                em.refresh(fbInvoice);
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }

        return list;
    }    
    
    
}
