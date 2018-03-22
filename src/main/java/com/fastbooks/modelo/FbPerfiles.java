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
@Table(name = "FB_PERFILES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbPerfiles.findAll", query = "SELECT f FROM FbPerfiles f"),
    @NamedQuery(name = "FbPerfiles.findByIdPerfil", query = "SELECT f FROM FbPerfiles f WHERE f.idPerfil = :idPerfil"),
    @NamedQuery(name = "FbPerfiles.findByNombre", query = "SELECT f FROM FbPerfiles f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "FbPerfiles.findByDescripcion", query = "SELECT f FROM FbPerfiles f WHERE f.descripcion = :descripcion"),
    @NamedQuery(name = "FbPerfiles.findByEstado", query = "SELECT f FROM FbPerfiles f WHERE f.estado = :estado"),
    @NamedQuery(name = "FbPerfiles.findByFechaCreacion", query = "SELECT f FROM FbPerfiles f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbPerfiles implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PERFIL")
    private BigDecimal idPerfil;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 20)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 50)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fbPerfiles", fetch = FetchType.EAGER)
    private List<FbPerfilXUsuario> fbPerfilXUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fbPerfiles", fetch = FetchType.EAGER)
    private List<FbModuloXPerfil> fbModuloXPerfilList;

    public FbPerfiles() {
    }

    public FbPerfiles(BigDecimal idPerfil) {
        this.idPerfil = idPerfil;
    }

    public FbPerfiles(BigDecimal idPerfil, String nombre) {
        this.idPerfil = idPerfil;
        this.nombre = nombre;
    }

    public BigDecimal getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(BigDecimal idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @XmlTransient
    public List<FbPerfilXUsuario> getFbPerfilXUsuarioList() {
        return fbPerfilXUsuarioList;
    }

    public void setFbPerfilXUsuarioList(List<FbPerfilXUsuario> fbPerfilXUsuarioList) {
        this.fbPerfilXUsuarioList = fbPerfilXUsuarioList;
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
        hash += (idPerfil != null ? idPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbPerfiles)) {
            return false;
        }
        FbPerfiles other = (FbPerfiles) object;
        if ((this.idPerfil == null && other.idPerfil != null) || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbPerfiles[ idPerfil=" + idPerfil + " ]";
    }
    
}
