/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis Eduardo Valdez
 */
@Entity
@Table(name = "FB_INVOICE_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbInvoiceDetail.findAll", query = "SELECT f FROM FbInvoiceDetail f")
    , @NamedQuery(name = "FbInvoiceDetail.findByIdDetail", query = "SELECT f FROM FbInvoiceDetail f WHERE f.idDetail = :idDetail")
    , @NamedQuery(name = "FbInvoiceDetail.findByItemName", query = "SELECT f FROM FbInvoiceDetail f WHERE f.itemName = :itemName")
    , @NamedQuery(name = "FbInvoiceDetail.findByItemSku", query = "SELECT f FROM FbInvoiceDetail f WHERE f.itemSku = :itemSku")
    , @NamedQuery(name = "FbInvoiceDetail.findByItemQuant", query = "SELECT f FROM FbInvoiceDetail f WHERE f.itemQuant = :itemQuant")
    , @NamedQuery(name = "FbInvoiceDetail.findByItemRate", query = "SELECT f FROM FbInvoiceDetail f WHERE f.itemRate = :itemRate")
    , @NamedQuery(name = "FbInvoiceDetail.findByItemAmount", query = "SELECT f FROM FbInvoiceDetail f WHERE f.itemAmount = :itemAmount")
    , @NamedQuery(name = "FbInvoiceDetail.findByItemTax", query = "SELECT f FROM FbInvoiceDetail f WHERE f.itemTax = :itemTax")
    , @NamedQuery(name = "FbInvoiceDetail.findByFechaCreacion", query = "SELECT f FROM FbInvoiceDetail f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbInvoiceDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DETAIL")
    private BigDecimal idDetail;
    @Size(max = 100)
    @Column(name = "ITEM_NAME")
    private String itemName;
    @Size(max = 100)
    @Column(name = "ITEM_SKU")
    private String itemSku;
    @Lob
    @Column(name = "ITEM_DESC")
    private String itemDesc;
    @Column(name = "ITEM_QUANT")
    private BigInteger itemQuant;
    @Column(name = "ITEM_RATE")
    private BigDecimal itemRate;
    @Column(name = "ITEM_AMOUNT")
    private BigDecimal itemAmount;
    @Size(max = 100)
    @Column(name = "ITEM_TAX")
    private String itemTax;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_INVOICE", referencedColumnName = "ID_INVOICE")
    @ManyToOne
    private FbInvoice idInvoice;
    @JoinColumn(name = "ID_PROD", referencedColumnName = "ID_PROD")
    @ManyToOne
    private FbProduct idProd;

    public FbInvoiceDetail() {
    }

    public FbInvoiceDetail(BigDecimal idDetail) {
        this.idDetail = idDetail;
    }

    public BigDecimal getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(BigDecimal idDetail) {
        this.idDetail = idDetail;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSku() {
        return itemSku;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public BigInteger getItemQuant() {
        return itemQuant;
    }

    public void setItemQuant(BigInteger itemQuant) {
        this.itemQuant = itemQuant;
    }

    public BigDecimal getItemRate() {
        return itemRate;
    }

    public void setItemRate(BigDecimal itemRate) {
        this.itemRate = itemRate;
    }

    public BigDecimal getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(BigDecimal itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getItemTax() {
        return itemTax;
    }

    public void setItemTax(String itemTax) {
        this.itemTax = itemTax;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public FbInvoice getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(FbInvoice idInvoice) {
        this.idInvoice = idInvoice;
    }

    public FbProduct getIdProd() {
        return idProd;
    }

    public void setIdProd(FbProduct idProd) {
        this.idProd = idProd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetail != null ? idDetail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbInvoiceDetail)) {
            return false;
        }
        FbInvoiceDetail other = (FbInvoiceDetail) object;
        if ((this.idDetail == null && other.idDetail != null) || (this.idDetail != null && !this.idDetail.equals(other.idDetail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbInvoiceDetail[ idDetail=" + idDetail + " ]";
    }
    
}
