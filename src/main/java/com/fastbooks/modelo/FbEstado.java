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
@Table(name = "FB_ESTADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbEstado.findAll", query = "SELECT f FROM FbEstado f"),
    @NamedQuery(name = "FbEstado.findByIdEstado", query = "SELECT f FROM FbEstado f WHERE f.idEstado = :idEstado"),
    @NamedQuery(name = "FbEstado.findByNombre", query = "SELECT f FROM FbEstado f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "FbEstado.findByFechaCreacion", query = "SELECT f FROM FbEstado f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbEstado implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTADO")
    private BigDecimal idEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_PAIS", referencedColumnName = "ID_PAIS")
    @ManyToOne(optional = false)
    private FbPais idPais;
    @OneToMany(mappedBy = "idEstado")
    private List<FbCiudad> fbCiudadList;

    public FbEstado() {
    }

    public FbEstado(BigDecimal idEstado) {
        this.idEstado = idEstado;
    }

    public FbEstado(BigDecimal idEstado, String nombre) {
        this.idEstado = idEstado;
        this.nombre = nombre;
    }

    public BigDecimal getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(BigDecimal idEstado) {
        this.idEstado = idEstado;
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

    public FbPais getIdPais() {
        return idPais;
    }

    public void setIdPais(FbPais idPais) {
        this.idPais = idPais;
    }

    @XmlTransient
    public List<FbCiudad> getFbCiudadList() {
        return fbCiudadList;
    }

    public void setFbCiudadList(List<FbCiudad> fbCiudadList) {
        this.fbCiudadList = fbCiudadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstado != null ? idEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbEstado)) {
            return false;
        }
        FbEstado other = (FbEstado) object;
        if ((this.idEstado == null && other.idEstado != null) || (this.idEstado != null && !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbEstado[ idEstado=" + idEstado + " ]";
    }
    
}
