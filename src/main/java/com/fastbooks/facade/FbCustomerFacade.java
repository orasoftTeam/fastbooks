/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.modelo.FbCustomer;
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
public class FbCustomerFacade extends AbstractFacade<FbCustomer>{
    
    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FbCustomerFacade() {
        super(FbCustomer.class);
    }
    
     //Lista para obtener usuarios por compania
    public List<FbCustomer> getCustomer (String idCust){
        List<FbCustomer> listC = new ArrayList<>();
        try {
            String sql = "select * from fb_customer u\n" +
                    " inner join fb_usuario_x_cia uc\n" +
                    " on u.id_usuario = uc.id_usuario\n" +
                    " where uc.id_cia = ?";
            //tambien q muestre el perfil
            Query q = em.createNativeQuery(sql, FbCustomer.class);
            q.setParameter(1, idCust);
            listC = q.getResultList();
            
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbUsuarioFacade.getUserbyCia()");
            e.printStackTrace();
           
        }
        return listC; 
    }
    
    
}
