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
@Table(name = "FB_MODULO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbModulo.findAll", query = "SELECT f FROM FbModulo f"),
    @NamedQuery(name = "FbModulo.findByIdModulo", query = "SELECT f FROM FbModulo f WHERE f.idModulo = :idModulo"),
    @NamedQuery(name = "FbModulo.findByNombremodulo", query = "SELECT f FROM FbModulo f WHERE f.nombremodulo = :nombremodulo"),
    @NamedQuery(name = "FbModulo.findByEstadomodulo", query = "SELECT f FROM FbModulo f WHERE f.estadomodulo = :estadomodulo"),
    @NamedQuery(name = "FbModulo.findByFechaCreacion", query = "SELECT f FROM FbModulo f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbModulo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MODULO")
    private BigDecimal idModulo;
    @Size(max = 60)
    @Column(name = "NOMBREMODULO")
    private String nombremodulo;
    @Size(max = 1)
    @Column(name = "ESTADOMODULO")
    private String estadomodulo;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @OneToMany(mappedBy = "idModulo", fetch = FetchType.EAGER)
    private List<FbMenu> fbMenuList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fbModulo", fetch = FetchType.EAGER)
    private List<FbModuloXPerfil> fbModuloXPerfilList;

    public FbModulo() {
    }

    public FbModulo(BigDecimal idModulo) {
        this.idModulo = idModulo;
    }

    public BigDecimal getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(BigDecimal idModulo) {
        this.idModulo = idModulo;
    }

    public String getNombremodulo() {
        return nombremodulo;
    }

    public void setNombremodulo(String nombremodulo) {
        this.nombremodulo = nombremodulo;
    }

    public String getEstadomodulo() {
        return estadomodulo;
    }

    public void setEstadomodulo(String estadomodulo) {
        this.estadomodulo = estadomodulo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @XmlTransient
    public List<FbMenu> getFbMenuList() {
        return fbMenuList;
    }

    public void setFbMenuList(List<FbMenu> fbMenuList) {
        this.fbMenuList = fbMenuList;
    }

    @XmlTransient
    public List<FbModuloXPerfil> getFbModuloXPerfilList() {
        return fbModuloXPerfilList;
    }

    public void setFbModuloXPerfilList(List<FbModuloXPerfil> fbModuloXPerfilList) {
        this.fbModuloXPerfilList = fbModuloXPerfilList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModulo != null ? idModulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbModulo)) {
            return false;
        }
        FbModulo other = (FbModulo) object;
        if ((this.idModulo == null && other.idModulo != null) || (this.idModulo != null && !this.idModulo.equals(other.idModulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbModulo[ idModulo=" + idModulo + " ]";
    }
    
}
