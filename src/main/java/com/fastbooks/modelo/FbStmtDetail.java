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
@Table(name = "FB_STMT_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbStmtDetail.findAll", query = "SELECT f FROM FbStmtDetail f")
    , @NamedQuery(name = "FbStmtDetail.findByIdDetail", query = "SELECT f FROM FbStmtDetail f WHERE f.idDetail = :idDetail")
    , @NamedQuery(name = "FbStmtDetail.findByTranDate", query = "SELECT f FROM FbStmtDetail f WHERE f.tranDate = :tranDate")
    , @NamedQuery(name = "FbStmtDetail.findByDescripcion", query = "SELECT f FROM FbStmtDetail f WHERE f.descripcion = :descripcion")
    , @NamedQuery(name = "FbStmtDetail.findByAmount", query = "SELECT f FROM FbStmtDetail f WHERE f.amount = :amount")
    , @NamedQuery(name = "FbStmtDetail.findByBalance", query = "SELECT f FROM FbStmtDetail f WHERE f.balance = :balance")
    , @NamedQuery(name = "FbStmtDetail.findByFechaCreacion", query = "SELECT f FROM FbStmtDetail f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbStmtDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DETAIL")
    private BigDecimal idDetail;
    @Column(name = "TRAN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranDate;
    @Size(max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "BALANCE")
    private BigDecimal balance;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_TRAN", referencedColumnName = "ID_INVOICE")
    @ManyToOne
    private FbInvoice idTran;
    @JoinColumn(name = "ID_STMT", referencedColumnName = "ID_STMT")
    @ManyToOne
    private FbStatement idStmt;

    public FbStmtDetail() {
    }

    public FbStmtDetail(BigDecimal idDetail) {
        this.idDetail = idDetail;
    }

    public BigDecimal getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(BigDecimal idDetail) {
        this.idDetail = idDetail;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public FbInvoice getIdTran() {
        return idTran;
    }

    public void setIdTran(FbInvoice idTran) {
        this.idTran = idTran;
    }

    public FbStatement getIdStmt() {
        return idStmt;
    }

    public void setIdStmt(FbStatement idStmt) {
        this.idStmt = idStmt;
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
        if (!(object instanceof FbStmtDetail)) {
            return false;
        }
        FbStmtDetail other = (FbStmtDetail) object;
        if ((this.idDetail == null && other.idDetail != null) || (this.idDetail != null && !this.idDetail.equals(other.idDetail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbStmtDetail[ idDetail=" + idDetail + " ]";
    }
    
}
