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
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Table(name = "FB_PAIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbPais.findAll", query = "SELECT f FROM FbPais f"),
    @NamedQuery(name = "FbPais.findByIdPais", query = "SELECT f FROM FbPais f WHERE f.idPais = :idPais"),
    @NamedQuery(name = "FbPais.findByNombre", query = "SELECT f FROM FbPais f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "FbPais.findByFechaCreacion", query = "SELECT f FROM FbPais f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbPais implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PAIS")
    private BigDecimal idPais;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPais", fetch = FetchType.EAGER)
    private List<FbDireccion> fbDireccionList;

    public FbPais() {
    }

    public FbPais(BigDecimal idPais) {
        this.idPais = idPais;
    }

    public FbPais(BigDecimal idPais, String nombre) {
        this.idPais = idPais;
        this.nombre = nombre;
    }

    public BigDecimal getIdPais() {
        return idPais;
    }

    public void setIdPais(BigDecimal idPais) {
        this.idPais = idPais;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPais != null ? idPais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbPais)) {
            return false;
        }
        FbPais other = (FbPais) object;
        if ((this.idPais == null && other.idPais != null) || (this.idPais != null && !this.idPais.equals(other.idPais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbPais[ idPais=" + idPais + " ]";
    }
    
}
