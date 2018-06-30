/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbDireccion;
import com.fastbooks.modelo.FbUsuario;
import java.sql.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dell
 */
@Stateless
public class FbCompaniaFacade extends AbstractFacade<FbCompania> {

    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FbCompaniaFacade() {
        super(FbCompania.class);
    }
    
    
    public String actCompany(FbCompania com,FbDireccion dir,FbUsuario user,String op){
    String res = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs = cn.prepareCall("{call FASTBOOKS.PROCS_FASTBOOKS.PR_ACT_COMPANIA (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, Integer.parseInt(String.valueOf(com.getIdCia())));
            cs.setString(2, com.getNomCom());
            cs.setString(3, com.getNomLeg());
            cs.setString(4, com.getGiro());
            cs.setString(5, com.getTelefono());
            cs.setString(6, com.getLogo());
            cs.setInt(7, com.getPerNat());
            cs.setString(8, com.getEmail());
            cs.setString(9, com.getWebsite());
            cs.setString(10, user.getFirstname());
            cs.setString(11, user.getLastname());
            cs.setString(12, user.getClave());
            cs.setInt(13, dir.getIdDireccion().intValue());
            cs.setString(14, dir.getDireccion());
            cs.setInt(15, dir.getIdCiudad().getIdCiudad().intValue());
            cs.setString(16, dir.getZipCode());
            cs.setString(17, op);
            cs.registerOutParameter(18, Types.VARCHAR);
            cs.execute();
            res = cs.getString(18);
            cs.close();
        } catch (Exception e) {
            res = "-2";
            e.printStackTrace();
        }
        System.out.println("Resultado de operacion: " + res);
        return res;
    }
    
}
