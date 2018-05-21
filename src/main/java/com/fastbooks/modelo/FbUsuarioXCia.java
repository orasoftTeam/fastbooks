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
@Table(name = "FB_USUARIO_X_CIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbUsuarioXCia.findAll", query = "SELECT f FROM FbUsuarioXCia f"),
    @NamedQuery(name = "FbUsuarioXCia.findByIdUsuario", query = "SELECT f FROM FbUsuarioXCia f WHERE f.fbUsuarioXCiaPK.idUsuario = :idUsuario"),
    @NamedQuery(name = "FbUsuarioXCia.findByIdCia", query = "SELECT f FROM FbUsuarioXCia f WHERE f.fbUsuarioXCiaPK.idCia = :idCia"),
    @NamedQuery(name = "FbUsuarioXCia.findByEstado", query = "SELECT f FROM FbUsuarioXCia f WHERE f.estado = :estado"),
    @NamedQuery(name = "FbUsuarioXCia.findByFechaCreacion", query = "SELECT f FROM FbUsuarioXCia f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbUsuarioXCia implements Serializable {

    @Column(name = "IS_OWNER")
    private Integer isOwner;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FbUsuarioXCiaPK fbUsuarioXCiaPK;
    @Size(max = 50)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private FbUsuario fbUsuario;
    @JoinColumn(name = "ID_CIA", referencedColumnName = "ID_CIA", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private FbCompania fbCompania;

    public FbUsuarioXCia() {
    }

    public FbUsuarioXCia(FbUsuarioXCiaPK fbUsuarioXCiaPK) {
        this.fbUsuarioXCiaPK = fbUsuarioXCiaPK;
    }

    public FbUsuarioXCia(BigInteger idUsuario, BigInteger idCia) {
        this.fbUsuarioXCiaPK = new FbUsuarioXCiaPK(idUsuario, idCia);
    }

    public FbUsuarioXCiaPK getFbUsuarioXCiaPK() {
        return fbUsuarioXCiaPK;
    }

    public void setFbUsuarioXCiaPK(FbUsuarioXCiaPK fbUsuarioXCiaPK) {
        this.fbUsuarioXCiaPK = fbUsuarioXCiaPK;
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

    public FbCompania getFbCompania() {
        return fbCompania;
    }

    public void setFbCompania(FbCompania fbCompania) {
        this.fbCompania = fbCompania;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fbUsuarioXCiaPK != null ? fbUsuarioXCiaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbUsuarioXCia)) {
            return false;
        }
        FbUsuarioXCia other = (FbUsuarioXCia) object;
        if ((this.fbUsuarioXCiaPK == null && other.fbUsuarioXCiaPK != null) || (this.fbUsuarioXCiaPK != null && !this.fbUsuarioXCiaPK.equals(other.fbUsuarioXCiaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbUsuarioXCia[ fbUsuarioXCiaPK=" + fbUsuarioXCiaPK + " ]";
    }

    public Integer getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Integer isOwner) {
        this.isOwner = isOwner;
    }
    
}
