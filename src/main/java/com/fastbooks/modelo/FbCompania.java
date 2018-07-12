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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "FB_COMPANIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbCompania.findAll", query = "SELECT f FROM FbCompania f"),
    @NamedQuery(name = "FbCompania.findByIdCia", query = "SELECT f FROM FbCompania f WHERE f.idCia = :idCia"),
    @NamedQuery(name = "FbCompania.findByNomCom", query = "SELECT f FROM FbCompania f WHERE f.nomCom = :nomCom"),
    @NamedQuery(name = "FbCompania.findByNomLeg", query = "SELECT f FROM FbCompania f WHERE f.nomLeg = :nomLeg"),
    @NamedQuery(name = "FbCompania.findByGiro", query = "SELECT f FROM FbCompania f WHERE f.giro = :giro"),
    @NamedQuery(name = "FbCompania.findByLogo", query = "SELECT f FROM FbCompania f WHERE f.logo = :logo"),
    @NamedQuery(name = "FbCompania.findByPerNat", query = "SELECT f FROM FbCompania f WHERE f.perNat = :perNat"),
    @NamedQuery(name = "FbCompania.findByEstado", query = "SELECT f FROM FbCompania f WHERE f.estado = :estado"),
    @NamedQuery(name = "FbCompania.findByEmail", query = "SELECT f FROM FbCompania f WHERE f.email = :email"),
    @NamedQuery(name = "FbCompania.findByWebsite", query = "SELECT f FROM FbCompania f WHERE f.website = :website"),
    @NamedQuery(name = "FbCompania.findByFechaCreacion", query = "SELECT f FROM FbCompania f WHERE f.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "FbCompania.findByTelefono", query = "SELECT f FROM FbCompania f WHERE f.telefono = :telefono")})
public class FbCompania implements Serializable {

    @OneToMany(mappedBy = "idCia")
    private List<FbStatement> fbStatementList;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fbCompania")
    private FbCompaniaPref fbCompaniaPref;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fbCompania")
    private List<FbPerfilXUsuario> fbPerfilXUsuarioList;
    @OneToMany(mappedBy = "idCia")
    private List<FbEmployee> fbEmployeeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fbCompania")
    private List<FbUsuarioXCia> fbUsuarioXCiaList;

    @OneToMany(mappedBy = "idCia")
    private List<FbCustomer> fbCustomerList;
    @OneToMany(mappedBy = "idCia")
    private List<FbInvoice> fbInvoiceList;

    @OneToMany(mappedBy = "idCia")
    private List<FbCatProd> fbCatProdList;
    @OneToMany(mappedBy = "idCia")
    private List<FbProduct> fbProductList;

    @OneToMany(mappedBy = "idCia")
    private List<FbTax> fbTaxList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CIA")
    private BigDecimal idCia;
    @Size(max = 100)
    @Column(name = "NOM_COM")
    private String nomCom;
    @Size(max = 100)
    @Column(name = "NOM_LEG")
    private String nomLeg;
    @Size(max = 50)
    @Column(name = "GIRO")
    private String giro;
    @Size(max = 100)
    @Column(name = "LOGO")
    private String logo;
    @Column(name = "PER_NAT")
    private Integer perNat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ESTADO")
    private String estado;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 100)
    @Column(name = "WEBSITE")
    private String website;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 15)
    @Column(name = "TELEFONO")
    private String telefono;
    @JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID_DIRECCION")
    @ManyToOne
    private FbDireccion idDireccion;

    public FbCompania() {
    }

    public FbCompania(BigDecimal idCia) {
        this.idCia = idCia;
    }

    public FbCompania(BigDecimal idCia, String estado) {
        this.idCia = idCia;
        this.estado = estado;
    }

    public BigDecimal getIdCia() {
        return idCia;
    }

    public void setIdCia(BigDecimal idCia) {
        this.idCia = idCia;
    }

    public String getNomCom() {
        return nomCom;
    }

    public void setNomCom(String nomCom) {
        this.nomCom = nomCom;
    }

    public String getNomLeg() {
        return nomLeg;
    }

    public void setNomLeg(String nomLeg) {
        this.nomLeg = nomLeg;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getPerNat() {
        return perNat;
    }

    public void setPerNat(Integer perNat) {
        this.perNat = perNat;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public FbDireccion getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(FbDireccion idDireccion) {
        this.idDireccion = idDireccion;
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
        if (!(object instanceof FbCompania)) {
            return false;
        }
        FbCompania other = (FbCompania) object;
        if ((this.idCia == null && other.idCia != null) || (this.idCia != null && !this.idCia.equals(other.idCia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbCompania[ idCia=" + idCia + " ]";
    }

    @XmlTransient
    public List<FbTax> getFbTaxList() {
        return fbTaxList;
    }

    public void setFbTaxList(List<FbTax> fbTaxList) {
        this.fbTaxList = fbTaxList;
    }

    @XmlTransient
    public List<FbCatProd> getFbCatProdList() {
        return fbCatProdList;
    }

    public void setFbCatProdList(List<FbCatProd> fbCatProdList) {
        this.fbCatProdList = fbCatProdList;
    }

    @XmlTransient
    public List<FbProduct> getFbProductList() {
        return fbProductList;
    }

    public void setFbProductList(List<FbProduct> fbProductList) {
        this.fbProductList = fbProductList;
    }

    @XmlTransient
    public List<FbCustomer> getFbCustomerList() {
        return fbCustomerList;
    }

    public void setFbCustomerList(List<FbCustomer> fbCustomerList) {
        this.fbCustomerList = fbCustomerList;
    }

    @XmlTransient
    public List<FbInvoice> getFbInvoiceList() {
        return fbInvoiceList;
    }

    public void setFbInvoiceList(List<FbInvoice> fbInvoiceList) {
        this.fbInvoiceList = fbInvoiceList;
    }

    @XmlTransient
    public List<FbPerfilXUsuario> getFbPerfilXUsuarioList() {
        return fbPerfilXUsuarioList;
    }

    public void setFbPerfilXUsuarioList(List<FbPerfilXUsuario> fbPerfilXUsuarioList) {
        this.fbPerfilXUsuarioList = fbPerfilXUsuarioList;
    }

    @XmlTransient
    public List<FbEmployee> getFbEmployeeList() {
        return fbEmployeeList;
    }

    public void setFbEmployeeList(List<FbEmployee> fbEmployeeList) {
        this.fbEmployeeList = fbEmployeeList;
    }

    @XmlTransient
    public List<FbUsuarioXCia> getFbUsuarioXCiaList() {
        return fbUsuarioXCiaList;
    }

    public void setFbUsuarioXCiaList(List<FbUsuarioXCia> fbUsuarioXCiaList) {
        this.fbUsuarioXCiaList = fbUsuarioXCiaList;
    }

    public FbCompaniaPref getFbCompaniaPref() {
        return fbCompaniaPref;
    }

    public void setFbCompaniaPref(FbCompaniaPref fbCompaniaPref) {
        this.fbCompaniaPref = fbCompaniaPref;
    }

    @XmlTransient
    public List<FbStatement> getFbStatementList() {
        return fbStatementList;
    }

    public void setFbStatementList(List<FbStatement> fbStatementList) {
        this.fbStatementList = fbStatementList;
    }
    
}
