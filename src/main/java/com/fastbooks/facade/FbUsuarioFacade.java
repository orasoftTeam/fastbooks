/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.forms.CompaniaForm;
import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbUsuario;
import java.math.BigDecimal;
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
public class FbUsuarioFacade extends AbstractFacade<FbUsuario> {

    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FbUsuarioFacade() {
        super(FbUsuario.class);
    }

    public FbUsuario login(String email, String pass) {
        FbUsuario user = new FbUsuario();
        user.setIdUsuario(new BigDecimal(0));
        try {
            String sql = "select * from fb_usuario a "
                    + "where a.email = ? "
                    + "and a.clave = ? "
                    + "and a.estado = 'A'";
            Query q = getEntityManager().createNativeQuery(sql, FbUsuario.class);
            q.setParameter(1, email);
            q.setParameter(2, pass);
            List<FbUsuario> resultList = q.getResultList();
            if (!resultList.isEmpty()) {
              user = resultList.get(0);  
            }
            

        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbUsuarioFacade.login()");
            e.printStackTrace();
        }

        return user;
    }
    
        public FbUsuario login(String email) {
        FbUsuario user = new FbUsuario();
        user.setIdUsuario(new BigDecimal(0));
        try {
            String sql = "select * from fb_usuario a "
                    + "where a.email = ? "
                    
                    + "and a.estado = 'A'";
            Query q = getEntityManager().createNativeQuery(sql, FbUsuario.class);
            q.setParameter(1, email);
            
            List<FbUsuario> resultList = q.getResultList();
            if (!resultList.isEmpty()) {
              user = resultList.get(0);  
            }
            

        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbUsuarioFacade.login()");
            e.printStackTrace();
        }

        return user;
    }

    public List<CompaniaForm> getUserCompaniasById(String id) {
        List<CompaniaForm> list = new ArrayList<>();
        try {
            String sql = "select  c.*,\n"
                    + "(select nombre from fb_perfiles p where p.ID_PERFIL in (select id_perfil from fb_perfil_x_usuario where id_usuario = a.id_usuario and id_cia = c.id_cia)) perfil\n"
                    + "from fb_usuario a\n"
                    + "inner join fb_usuario_x_cia b\n"
                    + "on a.ID_USUARIO = b.id_usuario\n"
                    + "inner join fb_compania c\n"
                    + "on c.id_cia = b.id_cia\n"
                    + "where a.id_usuario = ? \n"
                    + "order by b.IS_OWNER desc";
            Query q = getEntityManager().createNativeQuery(sql, "CompaniaMapping");
            q.setParameter(1, id);
            list = q.getResultList();

        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbUsuarioFacade.getUserCompaniasById()");
            e.printStackTrace();
        }

        return list;
    }
    
    //Lista para obtener usuarios por compania
    public List<FbUsuario> getUserbyCia (String idCia){
        List<FbUsuario> listU = new ArrayList<>();
        try {
            String sql = "select * from fb_usuario u\n" +
"                   inner join fb_usuario_x_cia uc\n" +
"                    on u.id_usuario = uc.id_usuario\n" +
"                   where uc.id_cia = ?";
            //tambien q muestre el perfil
            Query q = em.createNativeQuery(sql, FbUsuario.class);
            q.setParameter(1, idCia);
            listU = q.getResultList();
            
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbUsuarioFacade.getUserbyCia()");
            e.printStackTrace();
           
        }
        return listU; 
    }
    
    /*
    (pIdUser IN INT,pEmail IN VARCHAR2, pIdCia IN INT,
    pFname IN VARCHAR2, pLname IN VARCHAR2,pTel IN VARCHAR2,
            pGenero IN VARCHAR2,pBday IN VARCHAR2,pPass IN VARCHAR2,
                op IN VARCHAR2,res OUT VARCHAR2)
    
    */
    public String actUser(FbCompania com,FbUsuario user,String op){
    String res = "";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);//Conn EM
            CallableStatement cs = cn.prepareCall("{call FASTBOOKS.PROCS_FASTBOOKS.PR_ACT_USUARIO (?,?,?,?,?,?,?,?,?,?,?)}"); //llamada al PR
            cs.setInt(1, Integer.parseInt(String.valueOf(user.getIdUsuario())));
            cs.setString(2, user.getEmail());
            cs.setInt(3, Integer.parseInt(String.valueOf(com.getIdCia())));
            cs.setString(4, user.getFirstname());
            cs.setString(5, user.getLastname());
            cs.setString(6, user.getTelefono());
            cs.setString(7, user.getGenero());
            cs.setString(8, user.getBDay());
            cs.setString(9, user.getClave());
            cs.setString(10, op);
            cs.registerOutParameter(11, Types.VARCHAR);//setea parametro de salida --res
            cs.execute();
            res = cs.getString(11);
            cs.close();
        } catch (Exception e) {
            res = "-2";
            e.printStackTrace();
        }
        System.out.println("Resultado de operacion: " + res);
        return res;
    }

}
