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
 * @author DELL
 */
@Entity
@Table(name = "FB_PAYMENT_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbPaymentDetail.findAll", query = "SELECT f FROM FbPaymentDetail f")
    , @NamedQuery(name = "FbPaymentDetail.findByIdDetail", query = "SELECT f FROM FbPaymentDetail f WHERE f.idDetail = :idDetail")
    , @NamedQuery(name = "FbPaymentDetail.findByDescrip", query = "SELECT f FROM FbPaymentDetail f WHERE f.descrip = :descrip")
    , @NamedQuery(name = "FbPaymentDetail.findByDueDate", query = "SELECT f FROM FbPaymentDetail f WHERE f.dueDate = :dueDate")
    , @NamedQuery(name = "FbPaymentDetail.findByOriginalAmount", query = "SELECT f FROM FbPaymentDetail f WHERE f.originalAmount = :originalAmount")
    , @NamedQuery(name = "FbPaymentDetail.findByOpenBalance", query = "SELECT f FROM FbPaymentDetail f WHERE f.openBalance = :openBalance")
    , @NamedQuery(name = "FbPaymentDetail.findByPayment", query = "SELECT f FROM FbPaymentDetail f WHERE f.payment = :payment")})
public class FbPaymentDetail implements Serializable {

    @Column(name = "REMANENTE")
    private BigDecimal remanente;
    @Column(name = "CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creacion;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DETAIL")
    private BigDecimal idDetail;
    @Size(max = 100)
    @Column(name = "DESCRIP")
    private String descrip;
    @Size(max = 50)
    @Column(name = "DUE_DATE")
    private String dueDate;
    @Column(name = "ORIGINAL_AMOUNT")
    private BigDecimal originalAmount;
    @Column(name = "OPEN_BALANCE")
    private BigDecimal openBalance;
    @Column(name = "PAYMENT")
    private BigDecimal payment;
    @JoinColumn(name = "ID_PAYMENT", referencedColumnName = "ID_INVOICE")
    @ManyToOne
    private FbInvoice idPayment;
    @JoinColumn(name = "ID_INVOICE", referencedColumnName = "ID_INVOICE")
    @ManyToOne
    private FbInvoice idInvoice;

    public FbPaymentDetail() {
    }

    public FbPaymentDetail(BigDecimal idDetail) {
        this.idDetail = idDetail;
    }

    public BigDecimal getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(BigDecimal idDetail) {
        this.idDetail = idDetail;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(BigDecimal openBalance) {
        this.openBalance = openBalance;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public FbInvoice getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(FbInvoice idPayment) {
        this.idPayment = idPayment;
    }

    public FbInvoice getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(FbInvoice idInvoice) {
        this.idInvoice = idInvoice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetail != null ? idDetail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbPaymentDetail)) {
            return false;
        }
        FbPaymentDetail other = (FbPaymentDetail) object;
        if ((this.idDetail == null && other.idDetail != null) || (this.idDetail != null && !this.idDetail.equals(other.idDetail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbPaymentDetail[ idDetail=" + idDetail + " ]";
    }

    public BigDecimal getRemanente() {
        return remanente;
    }

    public void setRemanente(BigDecimal remanente) {
        this.remanente = remanente;
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }
    
}
