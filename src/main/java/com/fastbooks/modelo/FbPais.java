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
 * @author DELL
 */
@Entity
@Table(name = "FB_PAIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbPais.findAll", query = "SELECT f FROM FbPais f")
    , @NamedQuery(name = "FbPais.findByIdPais", query = "SELECT f FROM FbPais f WHERE f.idPais = :idPais")
    , @NamedQuery(name = "FbPais.findByNomPais", query = "SELECT f FROM FbPais f WHERE f.nomPais = :nomPais")
    , @NamedQuery(name = "FbPais.findByFechaCreacion", query = "SELECT f FROM FbPais f WHERE f.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "FbPais.findByLocale", query = "SELECT f FROM FbPais f WHERE f.locale = :locale")
    , @NamedQuery(name = "FbPais.findByLang", query = "SELECT f FROM FbPais f WHERE f.lang = :lang")})
public class FbPais implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PAIS")
    private BigDecimal idPais;
    @Size(max = 20)
    @Column(name = "NOM_PAIS")
    private String nomPais;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 10)
    @Column(name = "LOCALE")
    private String locale;
    @Size(max = 10)
    @Column(name = "LANG")
    private String lang;
    @OneToMany(mappedBy = "idPais")
    private List<FbEstado> fbEstadoList;

    public FbPais() {
    }

    public FbPais(BigDecimal idPais) {
        this.idPais = idPais;
    }

    public BigDecimal getIdPais() {
        return idPais;
    }

    public void setIdPais(BigDecimal idPais) {
        this.idPais = idPais;
    }

    public String getNomPais() {
        return nomPais;
    }

    public void setNomPais(String nomPais) {
        this.nomPais = nomPais;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @XmlTransient
    public List<FbEstado> getFbEstadoList() {
        return fbEstadoList;
    }

    public void setFbEstadoList(List<FbEstado> fbEstadoList) {
        this.fbEstadoList = fbEstadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPais != null ? idPais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbPais)) {
            return false;
        }
        FbPais other = (FbPais) object;
        if ((this.idPais == null && other.idPais != null) || (this.idPais != null && !this.idPais.equals(other.idPais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbPais[ idPais=" + idPais + " ]";
    }
    
}
