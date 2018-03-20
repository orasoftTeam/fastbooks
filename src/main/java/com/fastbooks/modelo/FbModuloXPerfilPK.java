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
public class FbModuloXPerfilPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PERFIL")
    private BigInteger idPerfil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MODULO")
    private BigInteger idModulo;

    public FbModuloXPerfilPK() {
    }

    public FbModuloXPerfilPK(BigInteger idPerfil, BigInteger idModulo) {
        this.idPerfil = idPerfil;
        this.idModulo = idModulo;
    }

    public BigInteger getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(BigInteger idPerfil) {
        this.idPerfil = idPerfil;
    }

    public BigInteger getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(BigInteger idModulo) {
        this.idModulo = idModulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerfil != null ? idPerfil.hashCode() : 0);
        hash += (idModulo != null ? idModulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbModuloXPerfilPK)) {
            return false;
        }
        FbModuloXPerfilPK other = (FbModuloXPerfilPK) object;
        if ((this.idPerfil == null && other.idPerfil != null) || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }
        if ((this.idModulo == null && other.idModulo != null) || (this.idModulo != null && !this.idModulo.equals(other.idModulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbModuloXPerfilPK[ idPerfil=" + idPerfil + ", idModulo=" + idModulo + " ]";
    }
    
}
