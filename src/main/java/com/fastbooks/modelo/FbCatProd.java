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
@Table(name = "FB_CAT_PROD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbCatProd.findAll", query = "SELECT f FROM FbCatProd f"),
    @NamedQuery(name = "FbCatProd.findByIdCat", query = "SELECT f FROM FbCatProd f WHERE f.idCat = :idCat"),
    @NamedQuery(name = "FbCatProd.findByName", query = "SELECT f FROM FbCatProd f WHERE f.name = :name"),
    @NamedQuery(name = "FbCatProd.findByFechaCreacion", query = "SELECT f FROM FbCatProd f WHERE f.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "FbCatProd.findByUserCreacion", query = "SELECT f FROM FbCatProd f WHERE f.userCreacion = :userCreacion")})
public class FbCatProd implements Serializable {

    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CAT")
    private BigDecimal idCat;
    @Size(max = 100)
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
    @OneToMany(mappedBy = "idCat")
    private List<FbProduct> fbProductList;

    public FbCatProd() {
    }

    public FbCatProd(BigDecimal idCat) {
        this.idCat = idCat;
    }

    public BigDecimal getIdCat() {
        return idCat;
    }

    public void setIdCat(BigDecimal idCat) {
        this.idCat = idCat;
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
        hash += (idCat != null ? idCat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbCatProd)) {
            return false;
        }
        FbCatProd other = (FbCatProd) object;
        if ((this.idCat == null && other.idCat != null) || (this.idCat != null && !this.idCat.equals(other.idCat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbCatProd[ idCat=" + idCat + " ]";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
