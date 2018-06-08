/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "FB_COMPANIA_PREF")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbCompaniaPref.findAll", query = "SELECT f FROM FbCompaniaPref f")
    , @NamedQuery(name = "FbCompaniaPref.findByIdCia", query = "SELECT f FROM FbCompaniaPref f WHERE f.idCia = :idCia")
    , @NamedQuery(name = "FbCompaniaPref.findByTheme", query = "SELECT f FROM FbCompaniaPref f WHERE f.theme = :theme")
    , @NamedQuery(name = "FbCompaniaPref.findByCountry", query = "SELECT f FROM FbCompaniaPref f WHERE f.country = :country")
    , @NamedQuery(name = "FbCompaniaPref.findByLang", query = "SELECT f FROM FbCompaniaPref f WHERE f.lang = :lang")})
public class FbCompaniaPref implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CIA")
    private BigDecimal idCia;
    @Size(max = 50)
    @Column(name = "THEME")
    private String theme;
    @Size(max = 50)
    @Column(name = "COUNTRY")
    private String country;
    @Size(max = 50)
    @Column(name = "LANG")
    private String lang;
    @JoinColumn(name = "ID_CIA", referencedColumnName = "ID_CIA", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private FbCompania fbCompania;

    public FbCompaniaPref() {
    }

    public FbCompaniaPref(BigDecimal idCia) {
        this.idCia = idCia;
    }

    public BigDecimal getIdCia() {
        return idCia;
    }

    public void setIdCia(BigDecimal idCia) {
        this.idCia = idCia;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
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
        hash += (idCia != null ? idCia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbCompaniaPref)) {
            return false;
        }
        FbCompaniaPref other = (FbCompaniaPref) object;
        if ((this.idCia == null && other.idCia != null) || (this.idCia != null && !this.idCia.equals(other.idCia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbCompaniaPref[ idCia=" + idCia + " ]";
    }
    
}
