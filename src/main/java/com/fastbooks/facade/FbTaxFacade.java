/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.modelo.FbTax;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dell
 */
@Stateless
public class FbTaxFacade extends AbstractFacade<FbTax>{
    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FbTaxFacade() {
        super(FbTax.class);
    }
    
    public List<FbTax> getTaxByIdCia(String idCia){
    List<FbTax> list = new ArrayList<>();
        try {
            String sql = "select * from fb_tax where id_cia = ? and status = 'A'";
            Query q = em.createNativeQuery(sql, FbTax.class);
            q.setParameter(1, idCia);
            list = q.getResultList();
            
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbTaxFacade.getTaxByIdCia()");
            e.printStackTrace();
        }
    
    return list;
    }
}
