/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "FB_MENU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbMenu.findAll", query = "SELECT f FROM FbMenu f"),
    @NamedQuery(name = "FbMenu.findByIdMenu", query = "SELECT f FROM FbMenu f WHERE f.idMenu = :idMenu"),
    @NamedQuery(name = "FbMenu.findByNombremenu", query = "SELECT f FROM FbMenu f WHERE f.nombremenu = :nombremenu"),
    @NamedQuery(name = "FbMenu.findByEstadomenu", query = "SELECT f FROM FbMenu f WHERE f.estadomenu = :estadomenu"),
    @NamedQuery(name = "FbMenu.findByNivel", query = "SELECT f FROM FbMenu f WHERE f.nivel = :nivel"),
    @NamedQuery(name = "FbMenu.findBySecuencia", query = "SELECT f FROM FbMenu f WHERE f.secuencia = :secuencia"),
    @NamedQuery(name = "FbMenu.findByOpcion", query = "SELECT f FROM FbMenu f WHERE f.opcion = :opcion"),
    @NamedQuery(name = "FbMenu.findByFechaCreacion", query = "SELECT f FROM FbMenu f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MENU")
    private BigDecimal idMenu;
    @Size(max = 60)
    @Column(name = "NOMBREMENU")
    private String nombremenu;
    @Size(max = 1)
    @Column(name = "ESTADOMENU")
    private String estadomenu;
    @Column(name = "NIVEL")
    private BigInteger nivel;
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Size(max = 100)
    @Column(name = "OPCION")
    private String opcion;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_MODULO", referencedColumnName = "ID_MODULO")
    @ManyToOne(fetch = FetchType.EAGER)
    private FbModulo idModulo;

    public FbMenu() {
    }

    public FbMenu(BigDecimal idMenu) {
        this.idMenu = idMenu;
    }

    public BigDecimal getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(BigDecimal idMenu) {
        this.idMenu = idMenu;
    }

    public String getNombremenu() {
        return nombremenu;
    }

    public void setNombremenu(String nombremenu) {
        this.nombremenu = nombremenu;
    }

    public String getEstadomenu() {
        return estadomenu;
    }

    public void setEstadomenu(String estadomenu) {
        this.estadomenu = estadomenu;
    }

    public BigInteger getNivel() {
        return nivel;
    }

    public void setNivel(BigInteger nivel) {
        this.nivel = nivel;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public FbModulo getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(FbModulo idModulo) {
        this.idModulo = idModulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenu != null ? idMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbMenu)) {
            return false;
        }
        FbMenu other = (FbMenu) object;
        if ((this.idMenu == null && other.idMenu != null) || (this.idMenu != null && !this.idMenu.equals(other.idMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbMenu[ idMenu=" + idMenu + " ]";
    }
    
}
