/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.dao;

import com.fastbooks.modelo.FbInvoice;
import com.fastbooks.modelo.FbInvoiceDetail;
import com.fastbooks.modelo.FbProduct;
import com.fastbooks.util.Conexion;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class FbInvoiceDetailDao extends Conexion {

    public List<FbInvoiceDetail> getInvoiceDetailsByIdInvoice(int idInvoice) {
        List<FbInvoiceDetail> list = new ArrayList<>();
        try {
            this.conectar();
            String sql = "select * from fb_invoice_detail where id_invoice = ? order by id_detail asc";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setInt(1, idInvoice);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FbInvoiceDetail id = new FbInvoiceDetail();
                id.setIdDetail(rs.getBigDecimal("ID_DETAIL"));
                id.setIdInvoice(new FbInvoice(rs.getBigDecimal("ID_INVOICE")));
                id.setIdProd(new FbProduct(rs.getBigDecimal("ID_PROD")));
                id.setItemName(rs.getString("ITEM_NAME"));
                id.setItemSku(rs.getString("ITEM_SKU"));
                id.setItemDesc(rs.getString("ITEM_DESC"));
                id.setItemQuant(new BigInteger(rs.getString("ITEM_QUANT")));
                id.setItemRate(rs.getBigDecimal("ITEM_RATE"));
                id.setItemAmount(rs.getBigDecimal("ITEM_AMOUNT"));
                id.setItemTax(rs.getString("ITEM_TAX"));
                list.add(id);
            }
        } catch (SQLException e) {
            System.out.println("com.fastbooks.dao.FbInvoiceDetailDao.getInvoiceDetailsByIdInvoice()");
            e.printStackTrace();
        } finally {
            this.desconectar();
        }
        return list;
    }

}
