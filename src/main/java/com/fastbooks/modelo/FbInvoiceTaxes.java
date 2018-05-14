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
 * @author Luis Eduardo Valdez
 */
@Entity
@Table(name = "FB_INVOICE_TAXES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbInvoiceTaxes.findAll", query = "SELECT f FROM FbInvoiceTaxes f")
    , @NamedQuery(name = "FbInvoiceTaxes.findByIdInvoiceTax", query = "SELECT f FROM FbInvoiceTaxes f WHERE f.idInvoiceTax = :idInvoiceTax")
    , @NamedQuery(name = "FbInvoiceTaxes.findByIdProds", query = "SELECT f FROM FbInvoiceTaxes f WHERE f.idProds = :idProds")
    , @NamedQuery(name = "FbInvoiceTaxes.findByFechaCreacion", query = "SELECT f FROM FbInvoiceTaxes f WHERE f.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "FbInvoiceTaxes.findByFromAmount", query = "SELECT f FROM FbInvoiceTaxes f WHERE f.fromAmount = :fromAmount")
    , @NamedQuery(name = "FbInvoiceTaxes.findByTaxAmount", query = "SELECT f FROM FbInvoiceTaxes f WHERE f.taxAmount = :taxAmount")})
public class FbInvoiceTaxes implements Serializable {

    @Size(max = 50)
    @Column(name = "FROM_AMOUNT")
    private BigDecimal fromAmount;
    @Size(max = 50)
    @Column(name = "TAX_AMOUNT")
    private BigDecimal taxAmount;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_INVOICE_TAX")
    private BigDecimal idInvoiceTax;
    @Size(max = 100)
    @Column(name = "ID_PRODS")
    private String idProds;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_INVOICE", referencedColumnName = "ID_INVOICE")
    @ManyToOne
    private FbInvoice idInvoice;
    @JoinColumn(name = "ID_TAX", referencedColumnName = "ID_TAX")
    @ManyToOne
    private FbTax idTax;

    public FbInvoiceTaxes() {
    }

    public FbInvoiceTaxes(BigDecimal idInvoiceTax) {
        this.idInvoiceTax = idInvoiceTax;
    }

    public BigDecimal getIdInvoiceTax() {
        return idInvoiceTax;
    }

    public void setIdInvoiceTax(BigDecimal idInvoiceTax) {
        this.idInvoiceTax = idInvoiceTax;
    }

    public String getIdProds() {
        return idProds;
    }

    public void setIdProds(String idProds) {
        this.idProds = idProds;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public FbInvoice getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(FbInvoice idInvoice) {
        this.idInvoice = idInvoice;
    }

    public FbTax getIdTax() {
        return idTax;
    }

    public void setIdTax(FbTax idTax) {
        this.idTax = idTax;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInvoiceTax != null ? idInvoiceTax.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbInvoiceTaxes)) {
            return false;
        }
        FbInvoiceTaxes other = (FbInvoiceTaxes) object;
        if ((this.idInvoiceTax == null && other.idInvoiceTax != null) || (this.idInvoiceTax != null && !this.idInvoiceTax.equals(other.idInvoiceTax))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbInvoiceTaxes[ idInvoiceTax=" + idInvoiceTax + " ]";
    }

 
    
}
