/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "FB_MODULO_X_PERFIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbModuloXPerfil.findAll", query = "SELECT f FROM FbModuloXPerfil f"),
    @NamedQuery(name = "FbModuloXPerfil.findByIdPerfil", query = "SELECT f FROM FbModuloXPerfil f WHERE f.fbModuloXPerfilPK.idPerfil = :idPerfil"),
    @NamedQuery(name = "FbModuloXPerfil.findByIdModulo", query = "SELECT f FROM FbModuloXPerfil f WHERE f.fbModuloXPerfilPK.idModulo = :idModulo"),
    @NamedQuery(name = "FbModuloXPerfil.findByEstado", query = "SELECT f FROM FbModuloXPerfil f WHERE f.estado = :estado"),
    @NamedQuery(name = "FbModuloXPerfil.findByFechaCreacion", query = "SELECT f FROM FbModuloXPerfil f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbModuloXPerfil implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FbModuloXPerfilPK fbModuloXPerfilPK;
    @Size(max = 20)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID_PERFIL", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private FbPerfiles fbPerfiles;
    @JoinColumn(name = "ID_MODULO", referencedColumnName = "ID_MODULO", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private FbModulo fbModulo;

    public FbModuloXPerfil() {
    }

    public FbModuloXPerfil(FbModuloXPerfilPK fbModuloXPerfilPK) {
        this.fbModuloXPerfilPK = fbModuloXPerfilPK;
    }

    public FbModuloXPerfil(BigInteger idPerfil, BigInteger idModulo) {
        this.fbModuloXPerfilPK = new FbModuloXPerfilPK(idPerfil, idModulo);
    }

    public FbModuloXPerfilPK getFbModuloXPerfilPK() {
        return fbModuloXPerfilPK;
    }

    public void setFbModuloXPerfilPK(FbModuloXPerfilPK fbModuloXPerfilPK) {
        this.fbModuloXPerfilPK = fbModuloXPerfilPK;
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

    public FbPerfiles getFbPerfiles() {
        return fbPerfiles;
    }

    public void setFbPerfiles(FbPerfiles fbPerfiles) {
        this.fbPerfiles = fbPerfiles;
    }

    public FbModulo getFbModulo() {
        return fbModulo;
    }

    public void setFbModulo(FbModulo fbModulo) {
        this.fbModulo = fbModulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fbModuloXPerfilPK != null ? fbModuloXPerfilPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbModuloXPerfil)) {
            return false;
        }
        FbModuloXPerfil other = (FbModuloXPerfil) object;
        if ((this.fbModuloXPerfilPK == null && other.fbModuloXPerfilPK != null) || (this.fbModuloXPerfilPK != null && !this.fbModuloXPerfilPK.equals(other.fbModuloXPerfilPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbModuloXPerfil[ fbModuloXPerfilPK=" + fbModuloXPerfilPK + " ]";
    }
    
}
