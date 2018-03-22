/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "FB_PERFIL_X_USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbPerfilXUsuario.findAll", query = "SELECT f FROM FbPerfilXUsuario f"),
    @NamedQuery(name = "FbPerfilXUsuario.findByIdPerfil", query = "SELECT f FROM FbPerfilXUsuario f WHERE f.fbPerfilXUsuarioPK.idPerfil = :idPerfil"),
    @NamedQuery(name = "FbPerfilXUsuario.findByIdUsuario", query = "SELECT f FROM FbPerfilXUsuario f WHERE f.fbPerfilXUsuarioPK.idUsuario = :idUsuario"),
    @NamedQuery(name = "FbPerfilXUsuario.findByIdCia", query = "SELECT f FROM FbPerfilXUsuario f WHERE f.fbPerfilXUsuarioPK.idCia = :idCia"),
    @NamedQuery(name = "FbPerfilXUsuario.findByEstado", query = "SELECT f FROM FbPerfilXUsuario f WHERE f.estado = :estado"),
    @NamedQuery(name = "FbPerfilXUsuario.findByFechaCreacion", query = "SELECT f FROM FbPerfilXUsuario f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbPerfilXUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FbPerfilXUsuarioPK fbPerfilXUsuarioPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private FbUsuario fbUsuario;
    @JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID_PERFIL", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private FbPerfiles fbPerfiles;
    @JoinColumn(name = "ID_CIA", referencedColumnName = "ID_CIA", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private FbCompania fbCompania;

    public FbPerfilXUsuario() {
    }

    public FbPerfilXUsuario(FbPerfilXUsuarioPK fbPerfilXUsuarioPK) {
        this.fbPerfilXUsuarioPK = fbPerfilXUsuarioPK;
    }

    public FbPerfilXUsuario(FbPerfilXUsuarioPK fbPerfilXUsuarioPK, String estado) {
        this.fbPerfilXUsuarioPK = fbPerfilXUsuarioPK;
        this.estado = estado;
    }

    public FbPerfilXUsuario(BigInteger idPerfil, BigInteger idUsuario, BigInteger idCia) {
        this.fbPerfilXUsuarioPK = new FbPerfilXUsuarioPK(idPerfil, idUsuario, idCia);
    }

    public FbPerfilXUsuarioPK getFbPerfilXUsuarioPK() {
        return fbPerfilXUsuarioPK;
    }

    public void setFbPerfilXUsuarioPK(FbPerfilXUsuarioPK fbPerfilXUsuarioPK) {
        this.fbPerfilXUsuarioPK = fbPerfilXUsuarioPK;
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

    public FbUsuario getFbUsuario() {
        return fbUsuario;
    }

    public void setFbUsuario(FbUsuario fbUsuario) {
        this.fbUsuario = fbUsuario;
    }

    public FbPerfiles getFbPerfiles() {
        return fbPerfiles;
    }

    public void setFbPerfiles(FbPerfiles fbPerfiles) {
        this.fbPerfiles = fbPerfiles;
    }

    public FbCompania getFbCompania() {
        return fbCompania;
    }

    public void setFbCompania(FbCompania fbCompania) {
        this.fbCompania = fbCompania;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fbPerfilXUsuarioPK != null ? fbPerfilXUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbPerfilXUsuario)) {
            return false;
        }
        FbPerfilXUsuario other = (FbPerfilXUsuario) object;
        if ((this.fbPerfilXUsuarioPK == null && other.fbPerfilXUsuarioPK != null) || (this.fbPerfilXUsuarioPK != null && !this.fbPerfilXUsuarioPK.equals(other.fbPerfilXUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbPerfilXUsuario[ fbPerfilXUsuarioPK=" + fbPerfilXUsuarioPK + " ]";
    }
    
}
