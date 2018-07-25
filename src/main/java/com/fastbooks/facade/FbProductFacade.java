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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class FbProductFacade extends AbstractFacade<FbProduct> {

    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FbProductFacade() {
        super(FbProduct.class);
    }

    public List<FbProduct> getProductsByIdCia(String idCia) {
        List<FbProduct> list = new ArrayList<>();
        try {
            String sql = "select * from fb_product where id_cia = ? and status = 'A' order by name asc";
            Query q = em.createNativeQuery(sql, FbProduct.class);
            q.setParameter(1, idCia);
            list = q.getResultList();

        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }

        return list;
    }

    public List<FbProduct> getProductsByIdCia(String idCia, String filter) {
        List<FbProduct> list = new ArrayList<>();
        try {
            String sql = "select * from fb_product where id_cia = ? and status = 'A' order by name asc";
            if (!filter.isEmpty()) {
                if (filter.equals("LS")) {
                    sql = "select * from fb_product prod where prod.id_cia = ? and prod.TYPE = 'IN' and prod.INIT_QUANT between 1 and 20 and prod.status = 'A'";
                } else if (filter.equals("OS")) {
                    sql = "select *  from fb_product prod where prod.id_cia = ? and prod.TYPE = 'IN' and prod.INIT_QUANT <=0 and prod.status = 'A'";
                }
            }
            Query q = em.createNativeQuery(sql, FbProduct.class);
            q.setParameter(1, idCia);
            list = q.getResultList();

        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }

        return list;
    }
    
       public List<FbProduct> getProductsByFilter(String idCia, String status, String type, String idCat, String stock) {
        List<FbProduct> list = new ArrayList<>();
        try {
            String sql = "select * from fb_product where id_cia = ?"; //and status = 'A' order by name asc";
            if (!stock.equals("0")) {
                type = "IN";
            }
            if (status.equals("A")) {
                sql += " and status = 'A'";
            }else if(status.equals("I")){
                sql += " and status = 'I'";
            }
            
            if (type.equals("IN")) {
                sql += " and type = 'IN'";
            }else if (type.equals("NI")) {
                sql += " and type = 'NI'";
            }else if (type.equals("BU")) {
                sql += " and type = 'BU'";
            }if (type.equals("SE")) {
                sql += " and type = 'SE'";
            }
            
            if (idCat.equals("UN")) {
                sql += " and id_cat is null";
            }else if (!idCat.equals("0") && !idCat.equals("UN")) {
                sql += " and id_cat =" + idCat;
            }
            
            
            if (stock.equals("LS") && type.equals("IN")) {
                sql += " and INIT_QUANT between 1 and 20";
            }else if(stock.equals("OS") && type.equals("IN")){
                sql += " and INIT_QUANT <=0";
            }
            sql += " order by name asc";
            Query q = em.createNativeQuery(sql, FbProduct.class);
            q.setParameter(1, idCia);
            list = q.getResultList();

        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }

        return list;
    }
    

    public List<FbProduct> getProductsByIdCiaWithoutBundle(String idCia) {
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

    public String actProd(FbProduct prod, String op) {
        String res = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs = cn.prepareCall("{call FASTBOOKS.PROCS_FASTBOOKS.PR_ACT_PRODUCT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
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
            cs.setDouble(11, Double.parseDouble(prod.getProdCost().toString()));
            //cs.setFloat(10, Float.parseFloat(prod.getPrice().toString()));
            cs.setInt(12, Integer.parseInt(prod.getIncTax().toString()));
            cs.setInt(13, Integer.parseInt(prod.getIdTax().getIdTax().toString()));
            cs.setString(14, op);
            cs.registerOutParameter(15, Types.VARCHAR);
            cs.execute();
            res = cs.getString(15);
            cs.close();

        } catch (Exception e) {
            res = "-2";
            e.printStackTrace();
        }
        System.out.println("Facade Resultado de operacion: " + res);
        return res;
    }

    public String actBundle(FbProduct prod, String op) {
        String res = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs = cn.prepareCall("{call FASTBOOKS.PROCS_FASTBOOKS.PR_ACT_BUNDLE (?,?,?,?,?,?,?,?,?,?,?,?)}");
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

    public Integer getPanelInfo(String idCia, String op) {
        Integer res = 0;
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            String sql = "";
            if (op.equals("LS")) {
                sql = "select count(*) CANTIDAD from fb_product prod where prod.id_cia = ? and prod.TYPE = 'IN' and prod.INIT_QUANT between 1 and 20 and prod.status = 'A'";
            } else if (op.equals("OS")) {
                sql = "select count(*) CANTIDAD from fb_product prod where prod.id_cia = ? and prod.TYPE = 'IN' and prod.INIT_QUANT <=0 and prod.status = 'A'";
            }
            if (!sql.isEmpty()) {
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idCia));
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    res = rs.getInt("CANTIDAD");
                }
                rs.close();
                ps.close();
                cn.close();
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getPanelInfo()");
            e.printStackTrace();
        }

        return res;
    }

}
