/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dell
 */
@Embeddable
public class FbPerfilXUsuarioPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PERFIL")
    private BigInteger idPerfil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_USUARIO")
    private BigInteger idUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CIA")
    private BigInteger idCia;

    public FbPerfilXUsuarioPK() {
    }

    public FbPerfilXUsuarioPK(BigInteger idPerfil, BigInteger idUsuario, BigInteger idCia) {
        this.idPerfil = idPerfil;
        this.idUsuario = idUsuario;
        this.idCia = idCia;
    }

    public BigInteger getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(BigInteger idPerfil) {
        this.idPerfil = idPerfil;
    }

    public BigInteger getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigInteger idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigInteger getIdCia() {
        return idCia;
    }

    public void setIdCia(BigInteger idCia) {
        this.idCia = idCia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerfil != null ? idPerfil.hashCode() : 0);
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        hash += (idCia != null ? idCia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbPerfilXUsuarioPK)) {
            return false;
        }
        FbPerfilXUsuarioPK other = (FbPerfilXUsuarioPK) object;
        if ((this.idPerfil == null && other.idPerfil != null) || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        if ((this.idCia == null && other.idCia != null) || (this.idCia != null && !this.idCia.equals(other.idCia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbPerfilXUsuarioPK[ idPerfil=" + idPerfil + ", idUsuario=" + idUsuario + ", idCia=" + idCia + " ]";
    }
    
}
