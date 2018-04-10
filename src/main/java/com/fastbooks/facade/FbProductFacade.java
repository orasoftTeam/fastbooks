/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.modelo.FbBundleItems;
import com.fastbooks.modelo.FbProduct;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
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
public class FbProductFacade extends AbstractFacade<FbProduct>{
    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FbProductFacade() {
        super(FbProduct.class);
    }
    
    public List<FbProduct> getProductsByIdCia(String idCia){
    List<FbProduct> list = new ArrayList<>();
        try {
            String sql = "select * from fb_product where id_cia = ? and status = 'A'";
            Query q = em.createNativeQuery(sql, FbProduct.class);
            q.setParameter(1, idCia);
            list = q.getResultList();
            
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }
    
    return list;
    }
    
    public List<FbProduct> getProductsByIdCiaWithoutBundle(String idCia){
    List<FbProduct> list = new ArrayList<>();
        try {
            String sql = "select * from fb_product where id_cia = ? and status = 'A' and type != 'BU'";
            Query q = em.createNativeQuery(sql, FbProduct.class);
            q.setParameter(1, idCia);
            list = q.getResultList();
            
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }
    
    return list;
    }
    
    
    
  public String actProd(FbProduct prod,String op){
    String res = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs = cn.prepareCall("{call HOLOGRAM.PROCS_FASTBOOKS.PR_ACT_PRODUCT (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, Integer.parseInt(prod.getIdCia().getIdCia().toString()));
            cs.setInt(2, Integer.parseInt(prod.getIdProd().toString()));
            cs.setString(3, prod.getName());
            cs.setString(4, prod.getSku());
            cs.setString(5, prod.getPhoto());
            cs.setInt(6, Integer.parseInt(prod.getIdCat().getIdCat().toString()));
            cs.setString(7, prod.getType());
            cs.setInt(8, Integer.parseInt(prod.getInitQuant().toString()));
            cs.setString(9, prod.getDescrip());
            cs.setDouble(10, Double.parseDouble(prod.getPrice().toString()));
            //cs.setFloat(10, Float.parseFloat(prod.getPrice().toString()));
            cs.setInt(11, Integer.parseInt(prod.getIncTax().toString()));
            cs.setInt(12, Integer.parseInt(prod.getIdTax().getIdTax().toString()));
            cs.setString(13, op);
            cs.registerOutParameter(14, Types.VARCHAR);
            cs.execute();
            res = cs.getString(14);
            cs.close();
           
        } catch (Exception e) {
            res = "-2";
            e.printStackTrace();
        }
        System.out.println("Facade Resultado de operacion: " + res);
        return res;
    }  
  
  
  
  public String actBundle(FbProduct prod,String op){
    String res = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs = cn.prepareCall("{call HOLOGRAM.PROCS_FASTBOOKS.PR_ACT_BUNDLE (?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, Integer.parseInt(prod.getIdCia().getIdCia().toString()));
            cs.setInt(2, Integer.parseInt(prod.getIdProd().toString()));
            cs.setString(3, prod.getName());
            cs.setString(4, prod.getSku());
            cs.setString(5, prod.getPhoto());
            cs.setString(7, prod.getType());
            cs.setString(6, prod.getDescrip());
            cs.setDouble(8, Double.parseDouble(prod.getTotalBundle().toString()));
            String pProdIds = "";
            for (FbBundleItems obj : prod.getFbBundleItemsList1()) {
                pProdIds += obj.getIdProd().getIdProd().toString() + ",";
            }
            cs.setString(9, pProdIds);
            String pQuants = "";
            for (FbBundleItems obj : prod.getFbBundleItemsList1()) {
                pQuants += obj.getQuant().toString() + ",";
            }
            cs.setString(10, pQuants);
            cs.setString(11, op);
            cs.registerOutParameter(12, Types.VARCHAR);
            cs.execute();
            res = cs.getString(12);
            cs.close();
           
        } catch (Exception e) {
            res = "-2";
            e.printStackTrace();
        }
        System.out.println("Facade Resultado de operacion: " + res);
        return res;
    } 
    
    
    
    
}
