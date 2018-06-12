/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.modelo.FbInvoiceDetail;
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
public class FbInvoiceDetailFacade extends AbstractFacade<FbInvoiceDetail> {

    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FbInvoiceDetailFacade() {
        super(FbInvoiceDetail.class);
    }

    public List<FbInvoiceDetail> getInvoiceDetailsByIdInvoice(String idInvoice) {
        List<FbInvoiceDetail> list = new ArrayList<>();
        try {

            String sql = "select * from fb_invoice_detail where id_invoice = ? order by id_detail asc";
            Query q = em.createNativeQuery(sql, FbInvoiceDetail.class);
            q.setParameter(1, idInvoice);
            list = q.getResultList();
            for (FbInvoiceDetail fbInvoice : list) {
                em.refresh(fbInvoice);
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }

        return list;
    }
}
