/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Table(name = "FB_USUARIO_PREF")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbUsuarioPref.findAll", query = "SELECT f FROM FbUsuarioPref f"),
    @NamedQuery(name = "FbUsuarioPref.findByIdPref", query = "SELECT f FROM FbUsuarioPref f WHERE f.idPref = :idPref"),
    @NamedQuery(name = "FbUsuarioPref.findByLocale", query = "SELECT f FROM FbUsuarioPref f WHERE f.locale = :locale"),
    @NamedQuery(name = "FbUsuarioPref.findByIdioma", query = "SELECT f FROM FbUsuarioPref f WHERE f.idioma = :idioma"),
    @NamedQuery(name = "FbUsuarioPref.findByTheme", query = "SELECT f FROM FbUsuarioPref f WHERE f.theme = :theme"),
    @NamedQuery(name = "FbUsuarioPref.findByFechaCreacion", query = "SELECT f FROM FbUsuarioPref f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbUsuarioPref implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PREF")
    private BigDecimal idPref;
    @Size(max = 10)
    @Column(name = "LOCALE")
    private String locale;
    @Size(max = 20)
    @Column(name = "IDIOMA")
    private String idioma;
    @Size(max = 50)
    @Column(name = "THEME")
    private String theme;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(fetch = FetchType.EAGER)
    private FbUsuario idUsuario;

    public FbUsuarioPref() {
    }

    public FbUsuarioPref(BigDecimal idPref) {
        this.idPref = idPref;
    }

    public BigDecimal getIdPref() {
        return idPref;
    }

    public void setIdPref(BigDecimal idPref) {
        this.idPref = idPref;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public FbUsuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(FbUsuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPref != null ? idPref.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbUsuarioPref)) {
            return false;
        }
        FbUsuarioPref other = (FbUsuarioPref) object;
        if ((this.idPref == null && other.idPref != null) || (this.idPref != null && !this.idPref.equals(other.idPref))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbUsuarioPref[ idPref=" + idPref + " ]";
    }
    
}
