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
 * @author DELL
 */
@Entity
@Table(name = "FB_STATEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbStatement.findAll", query = "SELECT f FROM FbStatement f")
    , @NamedQuery(name = "FbStatement.findByIdStmt", query = "SELECT f FROM FbStatement f WHERE f.idStmt = :idStmt")
    , @NamedQuery(name = "FbStatement.findByType", query = "SELECT f FROM FbStatement f WHERE f.type = :type")
    , @NamedQuery(name = "FbStatement.findByNoDot", query = "SELECT f FROM FbStatement f WHERE f.noDot = :noDot")
    , @NamedQuery(name = "FbStatement.findByStmtDate", query = "SELECT f FROM FbStatement f WHERE f.stmtDate = :stmtDate")
    , @NamedQuery(name = "FbStatement.findByStartDate", query = "SELECT f FROM FbStatement f WHERE f.startDate = :startDate")
    , @NamedQuery(name = "FbStatement.findByEndDate", query = "SELECT f FROM FbStatement f WHERE f.endDate = :endDate")
    , @NamedQuery(name = "FbStatement.findByTotalAmount", query = "SELECT f FROM FbStatement f WHERE f.totalAmount = :totalAmount")
    , @NamedQuery(name = "FbStatement.findByTotalBalance", query = "SELECT f FROM FbStatement f WHERE f.totalBalance = :totalBalance")
    , @NamedQuery(name = "FbStatement.findByFechaCreacion", query = "SELECT f FROM FbStatement f WHERE f.fechaCreacion = :fechaCreacion")})
public class FbStatement implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_STMT")
    private BigDecimal idStmt;
    @Size(max = 10)
    @Column(name = "TYPE")
    private String type;
    @Size(max = 20)
    @Column(name = "NO_DOT")
    private String noDot;
    @Size(max = 15)
    @Column(name = "STMT_DATE")
    private String stmtDate;
    @Size(max = 15)
    @Column(name = "START_DATE")
    private String startDate;
    @Size(max = 15)
    @Column(name = "END_DATE")
    private String endDate;
    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;
    @Column(name = "TOTAL_BALANCE")
    private BigDecimal totalBalance;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_CIA", referencedColumnName = "ID_CIA")
    @ManyToOne
    private FbCompania idCia;
    @JoinColumn(name = "ID_CUST", referencedColumnName = "ID_CUST")
    @ManyToOne
    private FbCustomer idCust;
    @OneToMany(mappedBy = "idStmt")
    private List<FbStmtDetail> fbStmtDetailList;

    public FbStatement() {
    }

    public FbStatement(BigDecimal idStmt) {
        this.idStmt = idStmt;
    }

    public BigDecimal getIdStmt() {
        return idStmt;
    }

    public void setIdStmt(BigDecimal idStmt) {
        this.idStmt = idStmt;
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

    public String getStmtDate() {
        return stmtDate;
    }

    public void setStmtDate(String stmtDate) {
        this.stmtDate = stmtDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
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
    public List<FbStmtDetail> getFbStmtDetailList() {
        return fbStmtDetailList;
    }

    public void setFbStmtDetailList(List<FbStmtDetail> fbStmtDetailList) {
        this.fbStmtDetailList = fbStmtDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStmt != null ? idStmt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbStatement)) {
            return false;
        }
        FbStatement other = (FbStatement) object;
        if ((this.idStmt == null && other.idStmt != null) || (this.idStmt != null && !this.idStmt.equals(other.idStmt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbStatement[ idStmt=" + idStmt + " ]";
    }
    
}
