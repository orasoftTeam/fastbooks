/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "FB_CIUDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbCiudad.findAll", query = "SELECT f FROM FbCiudad f"),
    @NamedQuery(name = "FbCiudad.findByIdCiudad", query = "SELECT f FROM FbCiudad f WHERE f.idCiudad = :idCiudad"),
    @NamedQuery(name = "FbCiudad.findByNombre", query = "SELECT f FROM FbCiudad f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "FbCiudad.findByFechaCreacion", query = "SELECT f FROM FbCiudad f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbCiudad implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CIUDAD")
    private BigDecimal idCiudad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCiudad")
    private List<FbDireccion> fbDireccionList;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private FbEstado idEstado;

    public FbCiudad() {
    }

    public FbCiudad(BigDecimal idCiudad) {
        this.idCiudad = idCiudad;
    }

    public FbCiudad(BigDecimal idCiudad, String nombre) {
        this.idCiudad = idCiudad;
        this.nombre = nombre;
    }

    public BigDecimal getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(BigDecimal idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @XmlTransient
    public List<FbDireccion> getFbDireccionList() {
        return fbDireccionList;
    }

    public void setFbDireccionList(List<FbDireccion> fbDireccionList) {
        this.fbDireccionList = fbDireccionList;
    }

    public FbEstado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(FbEstado idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCiudad != null ? idCiudad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbCiudad)) {
            return false;
        }
        FbCiudad other = (FbCiudad) object;
        if ((this.idCiudad == null && other.idCiudad != null) || (this.idCiudad != null && !this.idCiudad.equals(other.idCiudad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbCiudad[ idCiudad=" + idCiudad + " ]";
    }
    
}
