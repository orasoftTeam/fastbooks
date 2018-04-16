/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.modelo.FbEmployee;
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
 * @author Guadalupe
 */
@Stateless
public class FbEmployeeFacade extends AbstractFacade<FbEmployee> {

    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FbEmployeeFacade() {
        super(FbEmployee.class);

    }

    //Metodo para obtener la lista de empleados
    public List<FbEmployee> getEmployee(String idCia) {

        List<FbEmployee> eList = new ArrayList<>();
        try {
            String sql = " select * from fb_employee where id_cia = ? and status = 'A'";
            Query q = em.createNativeQuery(sql, FbEmployee.class);
            q.setParameter(1, idCia);
            eList = q.getResultList();

        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbEmployeeFacade.getEmployee()");
            e.printStackTrace();
        }
        return eList;
    }

    public String actEmployee(FbEmployee emp, String op) {
        String res = "";

        try {
            Connection cn = em.unwrap(java.sql.Connection.class);//Conn EM
            CallableStatement cs = cn.prepareCall("{call HOLOGRAM.PROCS_FASTBOOKS.PR_ACT_EMPLOYEE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                    + "?,?,?,?,?,?,?,?,?,?,?,?)}"); //26 elementos

            cs.setInt(1, Integer.parseInt(String.valueOf(emp.getIdCia().getIdCia())));
            cs.setInt(2, Integer.parseInt(String.valueOf(emp.getIdEmp())));
            cs.setString(3, emp.getTitle());
            cs.setString(4, emp.getFirstname());
            cs.setString(5, emp.getMiddlename());
            cs.setString(6, emp.getLastname());
            cs.setString(7, emp.getSuffixx());
            cs.setString(8, emp.getEmail());
            cs.setString(9, emp.getPhone());
            cs.setString(10, emp.getMobile());
            cs.setString(11, emp.getGender());
            cs.setString(12, emp.getDisplayName());
            cs.setString(13, emp.getNote());
            cs.setString(14, emp.getStreet());
            cs.setString(15, emp.getCity());
            cs.setString(16, emp.getState());
            cs.setString(17, emp.getPostalCode());
            cs.setString(18, emp.getCountry());
            cs.setString(19, emp.getBillingRate());
            cs.setString(20, emp.getEmployeeIdNo());
            cs.setString(21, emp.getEmployeeId());
            cs.setString(22, emp.getHiredDate());
            cs.setString(23, emp.getReleased());
            cs.setString(24, emp.getDateOfBirth());
            cs.setString(25, op);
            cs.registerOutParameter(26, Types.VARCHAR);//setea parametro de salida --res
            cs.execute();
            res = cs.getString(26);
            System.out.println("Resultado de la operacion res : " + res);
            cs.close();

        } catch (Exception e) {
            res = "-2";
            e.printStackTrace();
        }

        return res;

    }

}
