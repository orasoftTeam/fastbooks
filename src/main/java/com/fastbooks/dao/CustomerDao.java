/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.dao;

import com.fastbooks.modelo.FbCustomer;
import com.fastbooks.util.Conexion;
import java.util.List;

/**
 *
 * @author DELL
 */
public class CustomerDao extends Conexion{
    
    public List<FbCustomer> getCustomersByIdCia(String idCia){
    
        this.conectar();
        String sql = "select * from fb_customer where id_cia = ?";
    
        
        return null;
    }
    
}
