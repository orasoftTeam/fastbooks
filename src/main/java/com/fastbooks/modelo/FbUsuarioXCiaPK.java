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
public class FbUsuarioXCiaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_USUARIO")
    private BigInteger idUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CIA")
    private BigInteger idCia;

    public FbUsuarioXCiaPK() {
    }

    public FbUsuarioXCiaPK(BigInteger idUsuario, BigInteger idCia) {
        this.idUsuario = idUsuario;
        this.idCia = idCia;
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
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        hash += (idCia != null ? idCia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbUsuarioXCiaPK)) {
            return false;
        }
        FbUsuarioXCiaPK other = (FbUsuarioXCiaPK) object;
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
        return "com.fastbooks.modelo.FbUsuarioXCiaPK[ idUsuario=" + idUsuario + ", idCia=" + idCia + " ]";
    }
    
}
