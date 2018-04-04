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
 * @author dell
 */
@Entity
@Table(name = "FB_PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbProduct.findAll", query = "SELECT f FROM FbProduct f"),
    @NamedQuery(name = "FbProduct.findByIdProd", query = "SELECT f FROM FbProduct f WHERE f.idProd = :idProd"),
    @NamedQuery(name = "FbProduct.findByName", query = "SELECT f FROM FbProduct f WHERE f.name = :name"),
    @NamedQuery(name = "FbProduct.findBySku", query = "SELECT f FROM FbProduct f WHERE f.sku = :sku"),
    @NamedQuery(name = "FbProduct.findByPhoto", query = "SELECT f FROM FbProduct f WHERE f.photo = :photo"),
    @NamedQuery(name = "FbProduct.findByType", query = "SELECT f FROM FbProduct f WHERE f.type = :type"),
    @NamedQuery(name = "FbProduct.findByInitQuant", query = "SELECT f FROM FbProduct f WHERE f.initQuant = :initQuant"),
    @NamedQuery(name = "FbProduct.findByPrice", query = "SELECT f FROM FbProduct f WHERE f.price = :price"),
    @NamedQuery(name = "FbProduct.findByIncTax", query = "SELECT f FROM FbProduct f WHERE f.incTax = :incTax"),
    @NamedQuery(name = "FbProduct.findByFechaCreacion", query = "SELECT f FROM FbProduct f WHERE f.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "FbProduct.findByUserCreacion", query = "SELECT f FROM FbProduct f WHERE f.userCreacion = :userCreacion")})
public class FbProduct implements Serializable {

    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROD")
    private BigDecimal idProd;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    @Size(max = 50)
    @Column(name = "SKU")
    private String sku;
    @Size(max = 100)
    @Column(name = "PHOTO")
    private String photo;
    @Size(max = 50)
    @Column(name = "TYPE")
    private String type;
    @Column(name = "INIT_QUANT")
    private BigInteger initQuant;
    @Lob
    @Column(name = "DESCRIP")
    private String descrip;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "INC_TAX")
    private BigInteger incTax;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USER_CREACION")
    private BigInteger userCreacion;
    @JoinColumn(name = "ID_TAX", referencedColumnName = "ID_TAX")
    @ManyToOne
    private FbTax idTax;
    @JoinColumn(name = "ID_CIA", referencedColumnName = "ID_CIA")
    @ManyToOne
    private FbCompania idCia;
    @JoinColumn(name = "ID_CAT", referencedColumnName = "ID_CAT")
    @ManyToOne
    private FbCatProd idCat;

    public FbProduct() {
    }

    public FbProduct(BigDecimal idProd) {
        this.idProd = idProd;
    }

    public BigDecimal getIdProd() {
        return idProd;
    }

    public void setIdProd(BigDecimal idProd) {
        this.idProd = idProd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigInteger getInitQuant() {
        return initQuant;
    }

    public void setInitQuant(BigInteger initQuant) {
        this.initQuant = initQuant;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigInteger getIncTax() {
        return incTax;
    }

    public void setIncTax(BigInteger incTax) {
        this.incTax = incTax;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigInteger getUserCreacion() {
        return userCreacion;
    }

    public void setUserCreacion(BigInteger userCreacion) {
        this.userCreacion = userCreacion;
    }

    public FbTax getIdTax() {
        return idTax;
    }

    public void setIdTax(FbTax idTax) {
        this.idTax = idTax;
    }

    public FbCompania getIdCia() {
        return idCia;
    }

    public void setIdCia(FbCompania idCia) {
        this.idCia = idCia;
    }

    public FbCatProd getIdCat() {
        return idCat;
    }

    public void setIdCat(FbCatProd idCat) {
        this.idCat = idCat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProd != null ? idProd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbProduct)) {
            return false;
        }
        FbProduct other = (FbProduct) object;
        if ((this.idProd == null && other.idProd != null) || (this.idProd != null && !this.idProd.equals(other.idProd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbProduct[ idProd=" + idProd + " ]";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
