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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "FB_USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbUsuario.findAll", query = "SELECT f FROM FbUsuario f"),
    @NamedQuery(name = "FbUsuario.findByIdUsuario", query = "SELECT f FROM FbUsuario f WHERE f.idUsuario = :idUsuario"),
    @NamedQuery(name = "FbUsuario.findByEmail", query = "SELECT f FROM FbUsuario f WHERE f.email = :email"),
    @NamedQuery(name = "FbUsuario.findByFirstname", query = "SELECT f FROM FbUsuario f WHERE f.firstname = :firstname"),
    @NamedQuery(name = "FbUsuario.findByMidname", query = "SELECT f FROM FbUsuario f WHERE f.midname = :midname"),
    @NamedQuery(name = "FbUsuario.findByLastname", query = "SELECT f FROM FbUsuario f WHERE f.lastname = :lastname"),
    @NamedQuery(name = "FbUsuario.findByTelefono", query = "SELECT f FROM FbUsuario f WHERE f.telefono = :telefono"),
    @NamedQuery(name = "FbUsuario.findByGenero", query = "SELECT f FROM FbUsuario f WHERE f.genero = :genero"),
    @NamedQuery(name = "FbUsuario.findByBDay", query = "SELECT f FROM FbUsuario f WHERE f.bDay = :bDay"),
    @NamedQuery(name = "FbUsuario.findByClave", query = "SELECT f FROM FbUsuario f WHERE f.clave = :clave"),
    @NamedQuery(name = "FbUsuario.findByFechaCreacion", query = "SELECT f FROM FbUsuario f WHERE f.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "FbUsuario.findByEstado", query = "SELECT f FROM FbUsuario f WHERE f.estado = :estado"),
    @NamedQuery(name = "FbUsuario.findByHasCia", query = "SELECT f FROM FbUsuario f WHERE f.hasCia = :hasCia")})
public class FbUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_USUARIO")
    private BigDecimal idUsuario;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 60)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Size(max = 60)
    @Column(name = "MIDNAME")
    private String midname;
    @Size(max = 60)
    @Column(name = "LASTNAME")
    private String lastname;
    @Size(max = 10)
    @Column(name = "TELEFONO")
    private String telefono;
    @Size(max = 10)
    @Column(name = "GENERO")
    private String genero;
    @Size(max = 10)
    @Column(name = "B_DAY")
    private String bDay;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CLAVE")
    private String clave;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 50)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "HAS_CIA")
    private Integer hasCia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fbUsuario", fetch = FetchType.EAGER)
    private List<FbPerfilXUsuario> fbPerfilXUsuarioList;
    @JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID_DIRECCION")
    @ManyToOne(fetch = FetchType.EAGER)
    private FbDireccion idDireccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fbUsuario", fetch = FetchType.EAGER)
    private List<FbUsuarioXCia> fbUsuarioXCiaList;
    @OneToMany(mappedBy = "idUsuario", fetch = FetchType.EAGER)
    private List<FbUsuarioPref> fbUsuarioPrefList;

    public FbUsuario() {
    }

    public FbUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public FbUsuario(BigDecimal idUsuario, String email, String clave) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.clave = clave;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getBDay() {
        return bDay;
    }

    public void setBDay(String bDay) {
        this.bDay = bDay;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getHasCia() {
        return hasCia;
    }

    public void setHasCia(Integer hasCia) {
        this.hasCia = hasCia;
    }

    @XmlTransient
    public List<FbPerfilXUsuario> getFbPerfilXUsuarioList() {
        return fbPerfilXUsuarioList;
    }

    public void setFbPerfilXUsuarioList(List<FbPerfilXUsuario> fbPerfilXUsuarioList) {
        this.fbPerfilXUsuarioList = fbPerfilXUsuarioList;
    }

    public FbDireccion getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(FbDireccion idDireccion) {
        this.idDireccion = idDireccion;
    }

    @XmlTransient
    public List<FbUsuarioXCia> getFbUsuarioXCiaList() {
        return fbUsuarioXCiaList;
    }

    public void setFbUsuarioXCiaList(List<FbUsuarioXCia> fbUsuarioXCiaList) {
        this.fbUsuarioXCiaList = fbUsuarioXCiaList;
    }

    @XmlTransient
    public List<FbUsuarioPref> getFbUsuarioPrefList() {
        return fbUsuarioPrefList;
    }

    public void setFbUsuarioPrefList(List<FbUsuarioPref> fbUsuarioPrefList) {
        this.fbUsuarioPrefList = fbUsuarioPrefList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbUsuario)) {
            return false;
        }
        FbUsuario other = (FbUsuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FbUsuario{" + "idUsuario=" + idUsuario + ", email=" + email + ", firstname=" + firstname + ", midname=" + midname + ", lastname=" + lastname + ", telefono=" + telefono + ", genero=" + genero + ", bDay=" + bDay + ", clave=" + clave + ", fechaCreacion=" + fechaCreacion + ", estado=" + estado + ", hasCia=" + hasCia + ", fbPerfilXUsuarioList=" + fbPerfilXUsuarioList + ", idDireccion=" + idDireccion + ", fbUsuarioXCiaList=" + fbUsuarioXCiaList + ", fbUsuarioPrefList=" + fbUsuarioPrefList + '}';
    }

    
    
}
