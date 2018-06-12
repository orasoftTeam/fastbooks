/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DELL
 */
public class Conexion {

    protected @Getter @Setter Connection cn = null;
    
    public void conectar() {
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            cn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.41:1551:dbprod1", "fastbooks", "fastbooks1979");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("com.fastbooks.util.Conexion.conectar()");
            e.printStackTrace();
        }
        
        
    }
    
    public void desconectar(){
        try {
            if (cn != null) {
                cn.close();
            }
        } catch (SQLException e) {
            System.out.println("com.fastbooks.util.Conexion.desconectar()");
            e.printStackTrace();
        }
    }
    
   
}
