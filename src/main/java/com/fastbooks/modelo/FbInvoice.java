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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Luis Eduardo Valdez
 */
@Entity
@Table(name = "FB_INVOICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbInvoice.findAll", query = "SELECT f FROM FbInvoice f")
    , @NamedQuery(name = "FbInvoice.findByIdInvoice", query = "SELECT f FROM FbInvoice f WHERE f.idInvoice = :idInvoice")
    , @NamedQuery(name = "FbInvoice.findByType", query = "SELECT f FROM FbInvoice f WHERE f.type = :type")
    , @NamedQuery(name = "FbInvoice.findByNoDot", query = "SELECT f FROM FbInvoice f WHERE f.noDot = :noDot")
    , @NamedQuery(name = "FbInvoice.findByCustEmail", query = "SELECT f FROM FbInvoice f WHERE f.custEmail = :custEmail")
    , @NamedQuery(name = "FbInvoice.findByInDate", query = "SELECT f FROM FbInvoice f WHERE f.inDate = :inDate")
    , @NamedQuery(name = "FbInvoice.findByDueDate", query = "SELECT f FROM FbInvoice f WHERE f.dueDate = :dueDate")
    , @NamedQuery(name = "FbInvoice.findByActualBalance", query = "SELECT f FROM FbInvoice f WHERE f.actualBalance = :actualBalance")
    , @NamedQuery(name = "FbInvoice.findBySubTotal", query = "SELECT f FROM FbInvoice f WHERE f.subTotal = :subTotal")
    , @NamedQuery(name = "FbInvoice.findByTaxTotal", query = "SELECT f FROM FbInvoice f WHERE f.taxTotal = :taxTotal")
    , @NamedQuery(name = "FbInvoice.findByTotal", query = "SELECT f FROM FbInvoice f WHERE f.total = :total")
    , @NamedQuery(name = "FbInvoice.findByStatus", query = "SELECT f FROM FbInvoice f WHERE f.status = :status")
    , @NamedQuery(name = "FbInvoice.findByTerms", query = "SELECT f FROM FbInvoice f WHERE f.terms = :terms")
    , @NamedQuery(name = "FbInvoice.findByTrackNum", query = "SELECT f FROM FbInvoice f WHERE f.trackNum = :trackNum")
    , @NamedQuery(name = "FbInvoice.findByShipVia", query = "SELECT f FROM FbInvoice f WHERE f.shipVia = :shipVia")
    , @NamedQuery(name = "FbInvoice.findByShDate", query = "SELECT f FROM FbInvoice f WHERE f.shDate = :shDate")
    , @NamedQuery(name = "FbInvoice.findByShCost", query = "SELECT f FROM FbInvoice f WHERE f.shCost = :shCost")
    , @NamedQuery(name = "FbInvoice.findByAttachment", query = "SELECT f FROM FbInvoice f WHERE f.attachment = :attachment")
    , @NamedQuery(name = "FbInvoice.findByFechaCreacion", query = "SELECT f FROM FbInvoice f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbInvoice implements Serializable {

    @Size(max = 50)
    @Column(name = "PAY_METHOD")
    private String payMethod;
    @Size(max = 50)
    @Column(name = "PAY_REFERENCE_NO")
    private String payReferenceNo;
    @OneToMany(mappedBy = "idPayment",fetch = FetchType.LAZY)
    private List<FbPaymentDetail> fbPaymentDetailList;
    @OneToMany(mappedBy = "idInvoice",fetch = FetchType.LAZY)
    private List<FbPaymentDetail> fbPaymentDetailList1;

    @Size(max = 50)
    @Column(name = "ES_ACCBY")
    private String esAccby;
    @Size(max = 50)
    @Column(name = "ES_ACCDATE")
    private String esAccdate;

    @Column(name = "SHCOST_TAX_AMOUNT")
    private BigDecimal shcostTaxAmount;
    @Size(max = 100)
    @Column(name = "SHCOST_TAX_NAME")
    private String shcostTaxName;
    @JoinColumn(name = "ID_SHCOST_TAX", referencedColumnName = "ID_TAX")
    @ManyToOne
    private FbTax idShcostTax;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_INVOICE")
    private BigDecimal idInvoice;
    @Size(max = 50)
    @Column(name = "TYPE")
    private String type;
    @Size(max = 50)
    @Column(name = "NO_DOT")
    private String noDot;
    @Size(max = 100)
    @Column(name = "CUST_EMAIL")
    private String custEmail;
    @Column(name = "IN_DATE")
    private String inDate;
    @Column(name = "DUE_DATE")
    private String dueDate;
    @Column(name = "ACTUAL_BALANCE")
    private BigDecimal actualBalance;
    @Column(name = "SUB_TOTAL")
    private BigDecimal subTotal;
    @Column(name = "TAX_TOTAL")
    private BigDecimal taxTotal;
    @Column(name = "TOTAL")
    private BigDecimal total;
    @Size(max = 100)
    @Column(name = "STATUS")
    private String status;
    @Lob
    @Column(name = "BI_ADDRESS")
    private String biAddress;
    @Lob
    @Column(name = "SH_ADDRESS")
    private String shAddress;
    @Size(max = 50)
    @Column(name = "TERMS")
    private String terms;
    @Size(max = 50)
    @Column(name = "TRACK_NUM")
    private String trackNum;
    @Size(max = 50)
    @Column(name = "SHIP_VIA")
    private String shipVia;
    @Column(name = "SH_DATE")
    private String shDate;
    @Column(name = "SH_COST")
    private BigDecimal shCost;
    @Lob
    @Column(name = "MESSAGE_INVOICE")
    private String messageInvoice;
    @Lob
    @Column(name = "MESSAGE_STMNT")
    private String messageStmnt;
    @Size(max = 100)
    @Column(name = "ATTACHMENT")
    private String attachment;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_CIA", referencedColumnName = "ID_CIA")
    @ManyToOne
    private FbCompania idCia;
    @JoinColumn(name = "ID_CUST", referencedColumnName = "ID_CUST")
    @ManyToOne
    private FbCustomer idCust;
    @OneToMany(mappedBy = "idInvoice", fetch = FetchType.LAZY)
    private List<FbInvoiceDetail> fbInvoiceDetailList;
    @OneToMany(mappedBy = "idInvoice", fetch = FetchType.LAZY)
    private List<FbInvoiceTaxes> fbInvoiceTaxesList;
    
    
    
    @Transient
    private boolean checkbox;

    public FbInvoice() {
        this.checkbox = false;
    }

    public FbInvoice(BigDecimal idInvoice) {
        this.idInvoice = idInvoice;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public BigDecimal getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(BigDecimal idInvoice) {
        this.idInvoice = idInvoice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNoDot() {
        return noDot;
    }

    public void setNoDot(String noDot) {
        this.noDot = noDot;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(BigDecimal actualBalance) {
        this.actualBalance = actualBalance;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(BigDecimal taxTotal) {
        this.taxTotal = taxTotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBiAddress() {
        return biAddress;
    }

    public void setBiAddress(String biAddress) {
        this.biAddress = biAddress;
    }

    public String getShAddress() {
        return shAddress;
    }

    public void setShAddress(String shAddress) {
        this.shAddress = shAddress;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getTrackNum() {
        return trackNum;
    }

    public void setTrackNum(String trackNum) {
        this.trackNum = trackNum;
    }

    public String getShipVia() {
        return shipVia;
    }

    public void setShipVia(String shipVia) {
        this.shipVia = shipVia;
    }

    public String getShDate() {
        return shDate;
    }

    public void setShDate(String shDate) {
        this.shDate = shDate;
    }

    public BigDecimal getShCost() {
        return shCost;
    }

    public void setShCost(BigDecimal shCost) {
        this.shCost = shCost;
    }

    public String getMessageInvoice() {
        return messageInvoice;
    }

    public void setMessageInvoice(String messageInvoice) {
        this.messageInvoice = messageInvoice;
    }

    public String getMessageStmnt() {
        return messageStmnt;
    }

    public void setMessageStmnt(String messageStmnt) {
        this.messageStmnt = messageStmnt;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public FbCompania getIdCia() {
        return idCia;
    }

    public void setIdCia(FbCompania idCia) {
        this.idCia = idCia;
    }

    public FbCustomer getIdCust() {
        return idCust;
    }

    public void setIdCust(FbCustomer idCust) {
        this.idCust = idCust;
    }

    @XmlTransient
    public List<FbInvoiceDetail> getFbInvoiceDetailList() {
        return fbInvoiceDetailList;
    }

    public void setFbInvoiceDetailList(List<FbInvoiceDetail> fbInvoiceDetailList) {
        this.fbInvoiceDetailList = fbInvoiceDetailList;
    }

    @XmlTransient
    public List<FbInvoiceTaxes> getFbInvoiceTaxesList() {
        return fbInvoiceTaxesList;
    }

    public void setFbInvoiceTaxesList(List<FbInvoiceTaxes> fbInvoiceTaxesList) {
        this.fbInvoiceTaxesList = fbInvoiceTaxesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInvoice != null ? idInvoice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbInvoice)) {
            return false;
        }
        FbInvoice other = (FbInvoice) object;
        if ((this.idInvoice == null && other.idInvoice != null) || (this.idInvoice != null && !this.idInvoice.equals(other.idInvoice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbInvoice[ idInvoice=" + idInvoice + " ]";
    }

    public BigDecimal getShcostTaxAmount() {
        return shcostTaxAmount;
    }

    public void setShcostTaxAmount(BigDecimal shcostTaxAmount) {
        this.shcostTaxAmount = shcostTaxAmount;
    }

    public String getShcostTaxName() {
        return shcostTaxName;
    }

    public void setShcostTaxName(String shcostTaxName) {
        this.shcostTaxName = shcostTaxName;
    }

    public FbTax getIdShcostTax() {
        return idShcostTax;
    }

    public void setIdShcostTax(FbTax idShcostTax) {
        this.idShcostTax = idShcostTax;
    }

    public String getEsAccby() {
        return esAccby;
    }

    public void setEsAccby(String esAccby) {
        this.esAccby = esAccby;
    }

    public String getEsAccdate() {
        return esAccdate;
    }

    public void setEsAccdate(String esAccdate) {
        this.esAccdate = esAccdate;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayReferenceNo() {
        return payReferenceNo;
    }

    public void setPayReferenceNo(String payReferenceNo) {
        this.payReferenceNo = payReferenceNo;
    }

    @XmlTransient
    public List<FbPaymentDetail> getFbPaymentDetailList() {
        return fbPaymentDetailList;
    }

    public void setFbPaymentDetailList(List<FbPaymentDetail> fbPaymentDetailList) {
        this.fbPaymentDetailList = fbPaymentDetailList;
    }

    @XmlTransient
    public List<FbPaymentDetail> getFbPaymentDetailList1() {
        return fbPaymentDetailList1;
    }

    public void setFbPaymentDetailList1(List<FbPaymentDetail> fbPaymentDetailList1) {
        this.fbPaymentDetailList1 = fbPaymentDetailList1;
    }
    
}
