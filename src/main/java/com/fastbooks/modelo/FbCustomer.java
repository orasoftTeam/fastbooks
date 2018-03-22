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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "FB_CUSTOMER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbCustomer.findAll", query = "SELECT f FROM FbCustomer f"),
    @NamedQuery(name = "FbCustomer.findByIdCust", query = "SELECT f FROM FbCustomer f WHERE f.idCust = :idCust"),
    @NamedQuery(name = "FbCustomer.findByTitle", query = "SELECT f FROM FbCustomer f WHERE f.title = :title"),
    @NamedQuery(name = "FbCustomer.findByFirstname", query = "SELECT f FROM FbCustomer f WHERE f.firstname = :firstname"),
    @NamedQuery(name = "FbCustomer.findByMiddlename", query = "SELECT f FROM FbCustomer f WHERE f.middlename = :middlename"),
    @NamedQuery(name = "FbCustomer.findByLastname", query = "SELECT f FROM FbCustomer f WHERE f.lastname = :lastname"),
    @NamedQuery(name = "FbCustomer.findBySuffixx", query = "SELECT f FROM FbCustomer f WHERE f.suffixx = :suffixx"),
    @NamedQuery(name = "FbCustomer.findByEmail", query = "SELECT f FROM FbCustomer f WHERE f.email = :email"),
    @NamedQuery(name = "FbCustomer.findByCompany", query = "SELECT f FROM FbCustomer f WHERE f.company = :company"),
    @NamedQuery(name = "FbCustomer.findByPhone", query = "SELECT f FROM FbCustomer f WHERE f.phone = :phone"),
    @NamedQuery(name = "FbCustomer.findByMobile", query = "SELECT f FROM FbCustomer f WHERE f.mobile = :mobile"),
    @NamedQuery(name = "FbCustomer.findByFax", query = "SELECT f FROM FbCustomer f WHERE f.fax = :fax"),
    @NamedQuery(name = "FbCustomer.findByDisplayName", query = "SELECT f FROM FbCustomer f WHERE f.displayName = :displayName"),
    @NamedQuery(name = "FbCustomer.findByWebside", query = "SELECT f FROM FbCustomer f WHERE f.webside = :webside"),
    @NamedQuery(name = "FbCustomer.findByAtachment", query = "SELECT f FROM FbCustomer f WHERE f.atachment = :atachment")})
public class FbCustomer implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CUST")
    private BigDecimal idCust;
    @Size(max = 50)
    @Column(name = "TITLE")
    private String title;
    @Size(max = 50)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Size(max = 50)
    @Column(name = "MIDDLENAME")
    private String middlename;
    @Size(max = 50)
    @Column(name = "LASTNAME")
    private String lastname;
    @Size(max = 50)
    @Column(name = "SUFFIXX")
    private String suffixx;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 50)
    @Column(name = "COMPANY")
    private String company;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "PHONE")
    private String phone;
    @Size(max = 20)
    @Column(name = "MOBILE")
    private String mobile;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "FAX")
    private String fax;
    @Size(max = 50)
    @Column(name = "DISPLAY_NAME")
    private String displayName;
    @Size(max = 100)
    @Column(name = "WEBSIDE")
    private String webside;
    @Lob
    @Column(name = "NOTE")
    private String note;
    @Size(max = 100)
    @Column(name = "ATACHMENT")
    private String atachment;
    @JoinColumn(name = "ID_CIA", referencedColumnName = "ID_CIA")
    @ManyToOne
    private FbCompania idCia;
    @JoinColumn(name = "ID_DIR_SHIP", referencedColumnName = "ID_DIRECCION")
    @ManyToOne
    private FbDireccion idDirShip;
    @JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID_DIRECCION")
    @ManyToOne
    private FbDireccion idDireccion;

    public FbCustomer() {
    }

    public FbCustomer(BigDecimal idCust) {
        this.idCust = idCust;
    }

    public BigDecimal getIdCust() {
        return idCust;
    }

    public void setIdCust(BigDecimal idCust) {
        this.idCust = idCust;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSuffixx() {
        return suffixx;
    }

    public void setSuffixx(String suffixx) {
        this.suffixx = suffixx;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getWebside() {
        return webside;
    }

    public void setWebside(String webside) {
        this.webside = webside;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAtachment() {
        return atachment;
    }

    public void setAtachment(String atachment) {
        this.atachment = atachment;
    }

    public FbCompania getIdCia() {
        return idCia;
    }

    public void setIdCia(FbCompania idCia) {
        this.idCia = idCia;
    }

    public FbDireccion getIdDirShip() {
        return idDirShip;
    }

    public void setIdDirShip(FbDireccion idDirShip) {
        this.idDirShip = idDirShip;
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
        hash += (idCust != null ? idCust.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbCustomer)) {
            return false;
        }
        FbCustomer other = (FbCustomer) object;
        if ((this.idCust == null && other.idCust != null) || (this.idCust != null && !this.idCust.equals(other.idCust))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbCustomer[ idCust=" + idCust + " ]";
    }
    
}
