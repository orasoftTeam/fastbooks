/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.modelo.FbGlCatalogo;
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
public class FbGlCatalogoFacade extends AbstractFacade<FbGlCatalogo>{
    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public FbGlCatalogoFacade () {
        super(FbGlCatalogo.class);
    }
    
    public List<FbGlCatalogo> getCatalogosByCia(String idCia){
    List<FbGlCatalogo> lista = new ArrayList<>();
        try {
            String sql = "select * from fb_gl_catalogo where id_cia = ?";
            Query q = em.createNativeQuery(sql,FbGlCatalogo.class);
            q.setParameter(1, idCia);
            lista = q.getResultList();
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbGlCatalogoFacade.getCatalogosByCia()");
            e.printStackTrace();
        }
        
      return lista;
    }
}
