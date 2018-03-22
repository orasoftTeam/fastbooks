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
import javax.validation.constraints.Size;

/**
 *
 * @author dell
 */
@Embeddable
public class FbGlCatalogoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CIA")
    private BigInteger idCia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CON_NUMCTA")
    private String conNumcta;

    public FbGlCatalogoPK() {
    }

    public FbGlCatalogoPK(BigInteger idCia, String conNumcta) {
        this.idCia = idCia;
        this.conNumcta = conNumcta;
    }

    public BigInteger getIdCia() {
        return idCia;
    }

    public void setIdCia(BigInteger idCia) {
        this.idCia = idCia;
    }

    public String getConNumcta() {
        return conNumcta;
    }

    public void setConNumcta(String conNumcta) {
        this.conNumcta = conNumcta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCia != null ? idCia.hashCode() : 0);
        hash += (conNumcta != null ? conNumcta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbGlCatalogoPK)) {
            return false;
        }
        FbGlCatalogoPK other = (FbGlCatalogoPK) object;
        if ((this.idCia == null && other.idCia != null) || (this.idCia != null && !this.idCia.equals(other.idCia))) {
            return false;
        }
        if ((this.conNumcta == null && other.conNumcta != null) || (this.conNumcta != null && !this.conNumcta.equals(other.conNumcta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbGlCatalogoPK[ idCia=" + idCia + ", conNumcta=" + conNumcta + " ]";
    }
    
}
