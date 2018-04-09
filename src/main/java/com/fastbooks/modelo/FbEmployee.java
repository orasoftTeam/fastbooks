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
@Table(name = "FB_EMPLOYEE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbEmployee.findAll", query = "SELECT f FROM FbEmployee f"),
    @NamedQuery(name = "FbEmployee.findByIdEmp", query = "SELECT f FROM FbEmployee f WHERE f.idEmp = :idEmp"),
    @NamedQuery(name = "FbEmployee.findByTitle", query = "SELECT f FROM FbEmployee f WHERE f.title = :title"),
    @NamedQuery(name = "FbEmployee.findByFirstname", query = "SELECT f FROM FbEmployee f WHERE f.firstname = :firstname"),
    @NamedQuery(name = "FbEmployee.findByMiddlename", query = "SELECT f FROM FbEmployee f WHERE f.middlename = :middlename"),
    @NamedQuery(name = "FbEmployee.findByLastname", query = "SELECT f FROM FbEmployee f WHERE f.lastname = :lastname"),
    @NamedQuery(name = "FbEmployee.findBySuffixx", query = "SELECT f FROM FbEmployee f WHERE f.suffixx = :suffixx"),
    @NamedQuery(name = "FbEmployee.findByEmail", query = "SELECT f FROM FbEmployee f WHERE f.email = :email"),
    @NamedQuery(name = "FbEmployee.findByPhone", query = "SELECT f FROM FbEmployee f WHERE f.phone = :phone"),
    @NamedQuery(name = "FbEmployee.findByMobile", query = "SELECT f FROM FbEmployee f WHERE f.mobile = :mobile"),
    @NamedQuery(name = "FbEmployee.findByGender", query = "SELECT f FROM FbEmployee f WHERE f.gender = :gender"),
    @NamedQuery(name = "FbEmployee.findByDisplayName", query = "SELECT f FROM FbEmployee f WHERE f.displayName = :displayName"),
    @NamedQuery(name = "FbEmployee.findByStatus", query = "SELECT f FROM FbEmployee f WHERE f.status = :status"),
    @NamedQuery(name = "FbEmployee.findByCity", query = "SELECT f FROM FbEmployee f WHERE f.city = :city"),
    @NamedQuery(name = "FbEmployee.findByState", query = "SELECT f FROM FbEmployee f WHERE f.state = :state"),
    @NamedQuery(name = "FbEmployee.findByPostalCode", query = "SELECT f FROM FbEmployee f WHERE f.postalCode = :postalCode"),
    @NamedQuery(name = "FbEmployee.findByCountry", query = "SELECT f FROM FbEmployee f WHERE f.country = :country"),
    @NamedQuery(name = "FbEmployee.findByBillingRate", query = "SELECT f FROM FbEmployee f WHERE f.billingRate = :billingRate"),
    @NamedQuery(name = "FbEmployee.findByEmployeeIdNo", query = "SELECT f FROM FbEmployee f WHERE f.employeeIdNo = :employeeIdNo"),
    @NamedQuery(name = "FbEmployee.findByEmployeeId", query = "SELECT f FROM FbEmployee f WHERE f.employeeId = :employeeId"),
    @NamedQuery(name = "FbEmployee.findByHiredDate", query = "SELECT f FROM FbEmployee f WHERE f.hiredDate = :hiredDate"),
    @NamedQuery(name = "FbEmployee.findByReleased", query = "SELECT f FROM FbEmployee f WHERE f.released = :released"),
    @NamedQuery(name = "FbEmployee.findByDateOfBirth", query = "SELECT f FROM FbEmployee f WHERE f.dateOfBirth = :dateOfBirth")})
public class FbEmployee implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_EMP")
    private BigDecimal idEmp;
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
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "PHONE")
    private String phone;
    @Size(max = 20)
    @Column(name = "MOBILE")
    private String mobile;
    @Size(max = 20)
    @Column(name = "GENDER")
    private String gender;
    @Size(max = 50)
    @Column(name = "DISPLAY_NAME")
    private String displayName;
    @Lob
    @Column(name = "NOTE")
    private String note;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Lob
    @Column(name = "STREET")
    private String street;
    @Size(max = 50)
    @Column(name = "CITY")
    private String city;
    @Size(max = 50)
    @Column(name = "STATE")
    private String state;
    @Size(max = 50)
    @Column(name = "POSTAL_CODE")
    private String postalCode;
    @Size(max = 50)
    @Column(name = "COUNTRY")
    private String country;
    @Size(max = 5)
    @Column(name = "BILLING_RATE")
    private String billingRate;
    @Size(max = 20)
    @Column(name = "EMPLOYEE_ID_NO")
    private String employeeIdNo;
    @Size(max = 20)
    @Column(name = "EMPLOYEE_ID")
    private String employeeId;
    @Size(max = 20)
    @Column(name = "HIRED_DATE")
    private String hiredDate;
    @Size(max = 20)
    @Column(name = "RELEASED")
    private String released;
    @Size(max = 20)
    @Column(name = "DATE_OF_BIRTH")
    private String dateOfBirth;
    @JoinColumn(name = "ID_CIA", referencedColumnName = "ID_CIA")
    @ManyToOne
    private FbCompania idCia;

    public FbEmployee() {
    }

    public FbEmployee(BigDecimal idEmp) {
        this.idEmp = idEmp;
    }

    public BigDecimal getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(BigDecimal idEmp) {
        this.idEmp = idEmp;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBillingRate() {
        return billingRate;
    }

    public void setBillingRate(String billingRate) {
        this.billingRate = billingRate;
    }

    public String getEmployeeIdNo() {
        return employeeIdNo;
    }

    public void setEmployeeIdNo(String employeeIdNo) {
        this.employeeIdNo = employeeIdNo;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(String hiredDate) {
        this.hiredDate = hiredDate;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public FbCompania getIdCia() {
        return idCia;
    }

    public void setIdCia(FbCompania idCia) {
        this.idCia = idCia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmp != null ? idEmp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbEmployee)) {
            return false;
        }
        FbEmployee other = (FbEmployee) object;
        if ((this.idEmp == null && other.idEmp != null) || (this.idEmp != null && !this.idEmp.equals(other.idEmp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbEmployee[ idEmp=" + idEmp + " ]";
    }
    
}
