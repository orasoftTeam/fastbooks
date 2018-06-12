/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.modelo.FbCustomer;
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
 * @author DELL
 */
@Stateless
public class FbCustomerFacade extends AbstractFacade<FbCustomer> {

    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FbCustomerFacade() {
        super(FbCustomer.class);
    }

    public List<FbCustomer> getCustomersByIdCia(String idCust) {
        List<FbCustomer> listC = new ArrayList<>();
        try {
            String sql = "select * from fb_customer where id_cia = ? and status = 'A'";
            //tambien q muestre el perfil
            Query q = em.createNativeQuery(sql, FbCustomer.class);
            q.setParameter(1, idCust);
            listC = q.getResultList();
            
            for (FbCustomer fbCustomer : listC) {
                em.refresh(fbCustomer);
            }

        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbUsuarioFacade.getUserbyCia()");
            e.printStackTrace();

        }
        return listC;
    }
    //Lista para obtener customer por compania

    public List<FbCustomer> getCustomer(String idCia) {
        List<FbCustomer> listC = new ArrayList<>();
        try {
            String sql = "select * from fb_customer \n"
                    + " where id_cia = ?"
                    + " and status = 'A'";
            Query q = em.createNativeQuery(sql, FbCustomer.class);
            q.setParameter(1, idCia);
            listC = q.getResultList();
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbCustomerFacade.getCustomer()");
            e.printStackTrace();

        }
        return listC;
    }

    /* (pIdCia IN INT,pIdCust IN INT,pTitle IN VARCHAR2,pfirstName IN VARCHAR2,
		pMidName IN VARCHAR2,pLastName IN VARCHAR2,pSuffix IN VARCHAR2,pEmail IN VARCHAR2,
		pCompany IN VARCHAR2,pPhone IN VARCHAR2,pMobile IN VARCHAR2,pFax IN VARCHAR2,
                pDisplayName IN VARCHAR2 pWebsite IN VARCHAR2,pStreet IN VARCHAR2,pCity IN VARCHAR2,
                pState IN VARCHAR2,pPostalCode IN VARCHAR2,pCountry IN VARCHAR2,pStreet_S IN VARCHAR2,
                pCity_S IN VARCHAR2,pState_S IN VARCHAR2,pPostalCode_S IN VARCHAR2,
		pCountry_S IN VARCHAR2,op IN VARCHAR2,res OUT VARCHAR2,pAttachment  
                IN VARCHAR2,pNotes IN VARCHAR2)*/
    public String actCustomer(FbCustomer cust, String op) {
        String res = "";
        try {
            //Abrimos la conexion al Entity Manager
            Connection cn = em.unwrap(java.sql.Connection.class);//Conn EM
            CallableStatement cs = cn.prepareCall("{call FASTBOOKS.PROCS_FASTBOOKS.PR_ACT_CUSTOMER (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"); //33 elementos
            cs.setInt(1, Integer.parseInt(String.valueOf(cust.getIdCia().getIdCia())));
            cs.setInt(2, Integer.parseInt(String.valueOf(cust.getIdCust())));
            cs.setString(3, cust.getTitle());
            cs.setString(4, cust.getFirstname());
            cs.setString(5, cust.getMiddlename());
            cs.setString(6, cust.getLastname());
            cs.setString(7, cust.getSuffixx());
            cs.setString(8, cust.getEmail());
            cs.setString(9, cust.getCompany());
            cs.setString(10, cust.getPhone());
            cs.setString(11, cust.getMobile());
            cs.setString(12, cust.getFax());
            cs.setString(13, cust.getDisplayName());
            cs.setString(14, cust.getWebside());
            cs.setString(15, cust.getStreet());
            cs.setString(16, cust.getCity());
            cs.setString(17, cust.getEstate());
            cs.setString(18, cust.getPostalCode());
            cs.setString(19, cust.getCountry());
            cs.setString(20, cust.getStreetS());
            cs.setString(21, cust.getCityS());
            cs.setString(22, cust.getEstateS());
            cs.setString(23, cust.getPostalCodeS());
            cs.setString(24, cust.getCountryS());
            cs.setString(25, cust.getAtachment());
            cs.setString(26, cust.getNote());
            cs.setString(27, cust.getTaxregno());
            cs.setString(28, cust.getPaymentMethod());
            cs.setString(29, cust.getDeliveryMethod());
            cs.setString(30, cust.getTerms());
            cs.setString(31, cust.getAsOf());
            cs.setString(32, op);
            cs.registerOutParameter(33, Types.VARCHAR);//setea parametro de salida --res
            cs.execute();
            res = cs.getString(33);
            cs.close();

        } catch (Exception e) {
            res = "-2";
            e.printStackTrace();
        }
        System.out.println("Resultado de la operacion res : " + res);

        return res;
    }

}
