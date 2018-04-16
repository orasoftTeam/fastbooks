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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "FB_TAX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbTax.findAll", query = "SELECT f FROM FbTax f"),
    @NamedQuery(name = "FbTax.findByIdTax", query = "SELECT f FROM FbTax f WHERE f.idTax = :idTax"),
    @NamedQuery(name = "FbTax.findByName", query = "SELECT f FROM FbTax f WHERE f.name = :name"),
    @NamedQuery(name = "FbTax.findByRate", query = "SELECT f FROM FbTax f WHERE f.rate = :rate"),
    @NamedQuery(name = "FbTax.findByFechaCreacion", query = "SELECT f FROM FbTax f WHERE f.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "FbTax.findByUserCreacion", query = "SELECT f FROM FbTax f WHERE f.userCreacion = :userCreacion")})
public class FbTax implements Serializable {

    @OneToMany(mappedBy = "idTax")
    private List<FbInvoiceTaxes> fbInvoiceTaxesList;

    @Size(max = 20)
    @Column(name = "RATE")
    private String rate;

    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TAX")
    private BigDecimal idTax;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    @Lob
    @Column(name = "DESCRIP")
    private String descrip;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USER_CREACION")
    private BigInteger userCreacion;
    @JoinColumn(name = "ID_CIA", referencedColumnName = "ID_CIA")
    @ManyToOne
    private FbCompania idCia;
    @OneToMany(mappedBy = "idTax")
    private List<FbProduct> fbProductList;

    public FbTax() {
    }

    public FbTax(BigDecimal idTax) {
        this.idTax = idTax;
    }

    public BigDecimal getIdTax() {
        return idTax;
    }

    public void setIdTax(BigDecimal idTax) {
        this.idTax = idTax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
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

    public FbCompania getIdCia() {
        return idCia;
    }

    public void setIdCia(FbCompania idCia) {
        this.idCia = idCia;
    }

    @XmlTransient
    public List<FbProduct> getFbProductList() {
        return fbProductList;
    }

    public void setFbProductList(List<FbProduct> fbProductList) {
        this.fbProductList = fbProductList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTax != null ? idTax.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbTax)) {
            return false;
        }
        FbTax other = (FbTax) object;
        if ((this.idTax == null && other.idTax != null) || (this.idTax != null && !this.idTax.equals(other.idTax))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbTax[ idTax=" + idTax + " ]";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @XmlTransient
    public List<FbInvoiceTaxes> getFbInvoiceTaxesList() {
        return fbInvoiceTaxesList;
    }

    public void setFbInvoiceTaxesList(List<FbInvoiceTaxes> fbInvoiceTaxesList) {
        this.fbInvoiceTaxesList = fbInvoiceTaxesList;
    }
    
}
