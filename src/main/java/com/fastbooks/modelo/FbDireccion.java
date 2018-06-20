/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author DELL
 */
@Entity
@Table(name = "FB_DIRECCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbDireccion.findAll", query = "SELECT f FROM FbDireccion f")
    , @NamedQuery(name = "FbDireccion.findByIdDireccion", query = "SELECT f FROM FbDireccion f WHERE f.idDireccion = :idDireccion")
    , @NamedQuery(name = "FbDireccion.findByDireccion", query = "SELECT f FROM FbDireccion f WHERE f.direccion = :direccion")
    , @NamedQuery(name = "FbDireccion.findByFechaCreacion", query = "SELECT f FROM FbDireccion f WHERE f.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "FbDireccion.findByZipCode", query = "SELECT f FROM FbDireccion f WHERE f.zipCode = :zipCode")})
public class FbDireccion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DIRECCION")
    private BigDecimal idDireccion;
    @Size(max = 200)
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 20)
    @Column(name = "ZIP_CODE")
    private String zipCode;
    @JoinColumn(name = "ID_CIUDAD", referencedColumnName = "ID_CIUDAD")
    @ManyToOne
    private FbCiudad idCiudad;

    public FbDireccion() {
    }

    public FbDireccion(BigDecimal idDireccion) {
        this.idDireccion = idDireccion;
    }

    public BigDecimal getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(BigDecimal idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public FbCiudad getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(FbCiudad idCiudad) {
        this.idCiudad = idCiudad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDireccion != null ? idDireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbDireccion)) {
            return false;
        }
        FbDireccion other = (FbDireccion) object;
        if ((this.idDireccion == null && other.idDireccion != null) || (this.idDireccion != null && !this.idDireccion.equals(other.idDireccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbDireccion[ idDireccion=" + idDireccion + " ]";
    }
    
}
