/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.modelo.FbCatProd;
import java.sql.*;
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
public class FbCatProdFacade extends AbstractFacade<FbCatProd>{
     @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FbCatProdFacade() {
        super(FbCatProd.class);
    }
    
    
    public List<FbCatProd> getCatsByIdCia(String idCia){
    List<FbCatProd> list = new ArrayList<>();
        try {
            String sql = "select * from fb_cat_prod where id_cia = ? and status = 'A'";
            Query q = em.createNativeQuery(sql, FbCatProd.class);
            q.setParameter(1, idCia);
            list = q.getResultList();
            
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbCatProdFacade.getCatsByIdCia()");
            e.printStackTrace();
        }
    
    return list;
    }
    
    
    
    public String actCat(FbCatProd cat,String op){
    String res = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs = cn.prepareCall("{call FASTBOOKS.PROCS_FASTBOOKS.PR_ACT_CAT_PROD (?,?,?,?,?,?)}");
            cs.setInt(1, Integer.parseInt(cat.getIdCia().getIdCia().toString()));
            cs.setInt(2, Integer.parseInt(cat.getIdCat().toString()));
            cs.setString(3, cat.getName());
            cs.setInt(4, Integer.parseInt(cat.getUserCreacion().toString()));
            cs.setString(5, op);
            cs.registerOutParameter(6, Types.VARCHAR);
            cs.execute();
            res = cs.getString(6);
            cs.close();
           
        } catch (Exception e) {
            res = "-2";
            e.printStackTrace();
        }
        System.out.println("Facade Resultado de operacion: " + res);
        return res;
    }
    
}
