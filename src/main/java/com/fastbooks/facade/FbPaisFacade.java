/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.modelo.FbPais;
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
public class FbPaisFacade extends AbstractFacade<FbPais> {

    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FbPaisFacade() {
        super(FbPais.class);
    }
    
    
    public List<FbPais> getPaises(){
        List<FbPais> list = new ArrayList<>();
        try {
            String sql = "select * from fb_pais";
            Query query = em.createNativeQuery(sql, FbPais.class);
            list = query.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("com.fastbooks.facade.FbPaisFacade.getPaises()");
        }
        return list;
    }
    
}
