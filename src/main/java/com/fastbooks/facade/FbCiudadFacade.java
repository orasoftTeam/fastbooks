/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.modelo.FbCiudad;
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
public class FbCiudadFacade extends AbstractFacade<FbCiudad> {

    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FbCiudadFacade() {
        super(FbCiudad.class);
    }
    
    public List<FbCiudad> getCiudadesByIdEstado(String idEstado){
    List<FbCiudad> list = new ArrayList<>();
        try {
            String sql= "SELECT * FROM FB_CIUDAD WHERE ID_ESTADO = ?";
            Query q = em.createNativeQuery(sql,FbCiudad.class);
            q.setParameter(1, idEstado);
            list = q.getResultList();
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbCiudadFacade.getCiudadesByIdEstado()");
            e.printStackTrace();
        }
    
    
    return list;
    }
    
}
