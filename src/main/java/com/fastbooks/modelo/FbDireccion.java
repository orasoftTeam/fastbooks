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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author dell
 */
@Entity
@Table(name = "FB_DIRECCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbDireccion.findAll", query = "SELECT f FROM FbDireccion f"),
    @NamedQuery(name = "FbDireccion.findByIdDireccion", query = "SELECT f FROM FbDireccion f WHERE f.idDireccion = :idDireccion"),
    @NamedQuery(name = "FbDireccion.findByUrbanizacion", query = "SELECT f FROM FbDireccion f WHERE f.urbanizacion = :urbanizacion"),
    @NamedQuery(name = "FbDireccion.findByAvenida", query = "SELECT f FROM FbDireccion f WHERE f.avenida = :avenida"),
    @NamedQuery(name = "FbDireccion.findByPiso", query = "SELECT f FROM FbDireccion f WHERE f.piso = :piso"),
    @NamedQuery(name = "FbDireccion.findByApartamento", query = "SELECT f FROM FbDireccion f WHERE f.apartamento = :apartamento"),
    @NamedQuery(name = "FbDireccion.findByFechaCreacion", query = "SELECT f FROM FbDireccion f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbDireccion implements Serializable {

    @OneToMany(mappedBy = "idDireccion")
    private List<FbUsuario> fbUsuarioList;

    @OneToMany(mappedBy = "idDireccion")
    private List<FbCustomer> fbCustomerList;
    @OneToMany(mappedBy = "idDirShip")
    private List<FbCustomer> fbCustomerList1;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DIRECCION")
    private BigDecimal idDireccion;
    @Size(max = 50)
    @Column(name = "URBANIZACION")
    private String urbanizacion;
    @Size(max = 50)
    @Column(name = "AVENIDA")
    private String avenida;
    @Size(max = 10)
    @Column(name = "PISO")
    private String piso;
    @Size(max = 50)
    @Column(name = "APARTAMENTO")
    private String apartamento;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_CIUDAD", referencedColumnName = "ID_CIUDAD")
    @ManyToOne(optional = false)
    private FbCiudad idCiudad;
    @OneToMany(mappedBy = "idDireccion")
    private List<FbCompania> fbCompaniaList;

    public FbDireccion() {
    }

    public FbDireccion(BigDecimal idDireccion) {
        this.idDireccion = idDireccion;
    }

    public BigDecimal getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(BigDecimal idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getUrbanizacion() {
        return urbanizacion;
    }

    public void setUrbanizacion(String urbanizacion) {
        this.urbanizacion = urbanizacion;
    }

    public String getAvenida() {
        return avenida;
    }

    public void setAvenida(String avenida) {
        this.avenida = avenida;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public FbCiudad getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(FbCiudad idCiudad) {
        this.idCiudad = idCiudad;
    }

    @XmlTransient
    public List<FbCompania> getFbCompaniaList() {
        return fbCompaniaList;
    }

    public void setFbCompaniaList(List<FbCompania> fbCompaniaList) {
        this.fbCompaniaList = fbCompaniaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDireccion != null ? idDireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbDireccion)) {
            return false;
        }
        FbDireccion other = (FbDireccion) object;
        if ((this.idDireccion == null && other.idDireccion != null) || (this.idDireccion != null && !this.idDireccion.equals(other.idDireccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbDireccion[ idDireccion=" + idDireccion + " ]";
    }

    @XmlTransient
    public List<FbCustomer> getFbCustomerList() {
        return fbCustomerList;
    }

    public void setFbCustomerList(List<FbCustomer> fbCustomerList) {
        this.fbCustomerList = fbCustomerList;
    }

    @XmlTransient
    public List<FbCustomer> getFbCustomerList1() {
        return fbCustomerList1;
    }

    public void setFbCustomerList1(List<FbCustomer> fbCustomerList1) {
        this.fbCustomerList1 = fbCustomerList1;
    }

    @XmlTransient
    public List<FbUsuario> getFbUsuarioList() {
        return fbUsuarioList;
    }

    public void setFbUsuarioList(List<FbUsuario> fbUsuarioList) {
        this.fbUsuarioList = fbUsuarioList;
    }
    
}
