/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.dao;

import com.fastbooks.modelo.FbInvoice;
import com.fastbooks.modelo.FbInvoiceTaxes;
import com.fastbooks.modelo.FbTax;
import com.fastbooks.util.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class FbInvoiceTaxesDao extends Conexion {

    public List<FbInvoiceTaxes> getInvoiceTaxesByIdInvoice(int idInvoice) {
        List<FbInvoiceTaxes> list = new ArrayList<FbInvoiceTaxes>();
        try {
            this.conectar();
            String sql = "select * from fb_invoice_taxes where id_invoice = ? order by ID_INVOICE_TAX asc";
            PreparedStatement ps =  this.getCn().prepareStatement(sql);
            ps.setInt(1, idInvoice);
            ResultSet rs =  ps.executeQuery();
            while (rs.next()) {                
                FbInvoiceTaxes it = new FbInvoiceTaxes();
                it.setIdInvoiceTax(rs.getBigDecimal("ID_INVOICE_TAX"));
                it.setIdInvoice(new FbInvoice(rs.getBigDecimal("ID_INVOICE")));
                it.setIdTax(new FbTax(rs.getBigDecimal("ID_TAX")));
                it.setFromAmount(rs.getBigDecimal("FROM_AMOUNT"));
                it.setTaxAmount(rs.getBigDecimal("TAX_AMOUNT"));
                it.setIdProds(rs.getString("ID_PRODS"));
                list.add(it);
            }

        } catch (SQLException e) {
            System.out.println("com.fastbooks.dao.FbInvoiceTaxesDao.getInvoiceTaxesByIdInvoice()");
            e.printStackTrace();
        } finally {
            this.desconectar();
        }

        return list;
    }
}
