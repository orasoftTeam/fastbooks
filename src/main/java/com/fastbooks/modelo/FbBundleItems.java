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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 * @author dell
 */
@Entity
@Table(name = "FB_BUNDLE_ITEMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbBundleItems.findAll", query = "SELECT f FROM FbBundleItems f"),
    @NamedQuery(name = "FbBundleItems.findByIdItem", query = "SELECT f FROM FbBundleItems f WHERE f.idItem = :idItem"),
    @NamedQuery(name = "FbBundleItems.findByItemName", query = "SELECT f FROM FbBundleItems f WHERE f.itemName = :itemName"),
    @NamedQuery(name = "FbBundleItems.findByItemPrice", query = "SELECT f FROM FbBundleItems f WHERE f.itemPrice = :itemPrice"),
    @NamedQuery(name = "FbBundleItems.findByQuant", query = "SELECT f FROM FbBundleItems f WHERE f.quant = :quant"),
    @NamedQuery(name = "FbBundleItems.findByTotal", query = "SELECT f FROM FbBundleItems f WHERE f.total = :total"),
    @NamedQuery(name = "FbBundleItems.findByFechaCreacion", query = "SELECT f FROM FbBundleItems f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbBundleItems implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ITEM")
    private BigDecimal idItem;
    @Size(max = 100)
    @Column(name = "ITEM_NAME")
    private String itemName;
    @Column(name = "ITEM_PRICE")
    private BigDecimal itemPrice;
    @Column(name = "QUANT")
    private BigInteger quant;
    @Column(name = "TOTAL")
    private BigDecimal total;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_BUNDLE", referencedColumnName = "ID_PROD")
    @ManyToOne
    private FbProduct idBundle;
    @JoinColumn(name = "ID_PROD", referencedColumnName = "ID_PROD")
    @ManyToOne(fetch = FetchType.EAGER)
    private FbProduct idProd;

    public FbBundleItems() {
    }

    public FbBundleItems(BigDecimal idItem) {
        this.idItem = idItem;
    }

    public BigDecimal getIdItem() {
        return idItem;
    }

    public void setIdItem(BigDecimal idItem) {
        this.idItem = idItem;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public BigInteger getQuant() {
        return quant;
    }

    public void setQuant(BigInteger quant) {
        this.quant = quant;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public FbProduct getIdBundle() {
        return idBundle;
    }

    public void setIdBundle(FbProduct idBundle) {
        this.idBundle = idBundle;
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
        hash += (idItem != null ? idItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbBundleItems)) {
            return false;
        }
        FbBundleItems other = (FbBundleItems) object;
        if ((this.idItem == null && other.idItem != null) || (this.idItem != null && !this.idItem.equals(other.idItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbBundleItems[ idItem=" + idItem + " ]";
    }
    
}
