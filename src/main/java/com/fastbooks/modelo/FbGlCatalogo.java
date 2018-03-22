/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
 * @author dell
 */
@Entity
@Table(name = "FB_GL_CATALOGO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FbGlCatalogo.findAll", query = "SELECT f FROM FbGlCatalogo f"),
    @NamedQuery(name = "FbGlCatalogo.findByIdCia", query = "SELECT f FROM FbGlCatalogo f WHERE f.fbGlCatalogoPK.idCia = :idCia"),
    @NamedQuery(name = "FbGlCatalogo.findByConNumcta", query = "SELECT f FROM FbGlCatalogo f WHERE f.fbGlCatalogoPK.conNumcta = :conNumcta"),
    @NamedQuery(name = "FbGlCatalogo.findByConDescri", query = "SELECT f FROM FbGlCatalogo f WHERE f.conDescri = :conDescri"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive01", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive01 = :conNive01"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive02", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive02 = :conNive02"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive03", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive03 = :conNive03"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive04", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive04 = :conNive04"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive05", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive05 = :conNive05"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive06", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive06 = :conNive06"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive07", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive07 = :conNive07"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive08", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive08 = :conNive08"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive09", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive09 = :conNive09"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive10", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive10 = :conNive10"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive11", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive11 = :conNive11"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive12", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive12 = :conNive12"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive13", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive13 = :conNive13"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive14", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive14 = :conNive14"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive15", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive15 = :conNive15"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive16", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive16 = :conNive16"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive17", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive17 = :conNive17"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive18", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive18 = :conNive18"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive19", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive19 = :conNive19"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive20", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive20 = :conNive20"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive21", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive21 = :conNive21"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive22", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive22 = :conNive22"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive23", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive23 = :conNive23"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive24", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive24 = :conNive24"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive25", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive25 = :conNive25"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive26", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive26 = :conNive26"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive27", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive27 = :conNive27"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive28", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive28 = :conNive28"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive29", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive29 = :conNive29"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive30", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive30 = :conNive30"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive31", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive31 = :conNive31"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive32", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive32 = :conNive32"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive33", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive33 = :conNive33"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive34", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive34 = :conNive34"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive35", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive35 = :conNive35"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive36", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive36 = :conNive36"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive37", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive37 = :conNive37"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive38", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive38 = :conNive38"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive39", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive39 = :conNive39"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive40", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive40 = :conNive40"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive41", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive41 = :conNive41"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive42", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive42 = :conNive42"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive43", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive43 = :conNive43"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive44", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive44 = :conNive44"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive45", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive45 = :conNive45"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive46", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive46 = :conNive46"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive47", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive47 = :conNive47"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive48", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive48 = :conNive48"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive49", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive49 = :conNive49"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive50", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive50 = :conNive50"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive51", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive51 = :conNive51"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive52", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive52 = :conNive52"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive53", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive53 = :conNive53"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive54", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive54 = :conNive54"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive55", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive55 = :conNive55"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive56", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive56 = :conNive56"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive57", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive57 = :conNive57"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive58", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive58 = :conNive58"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive59", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive59 = :conNive59"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive60", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive60 = :conNive60"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive61", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive61 = :conNive61"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive62", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive62 = :conNive62"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive63", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive63 = :conNive63"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive64", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive64 = :conNive64"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive65", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive65 = :conNive65"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive66", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive66 = :conNive66"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive67", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive67 = :conNive67"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive68", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive68 = :conNive68"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive69", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive69 = :conNive69"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive70", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive70 = :conNive70"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive71", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive71 = :conNive71"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive72", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive72 = :conNive72"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive73", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive73 = :conNive73"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive74", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive74 = :conNive74"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive75", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive75 = :conNive75"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive76", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive76 = :conNive76"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive77", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive77 = :conNive77"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive78", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive78 = :conNive78"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive79", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive79 = :conNive79"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive80", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive80 = :conNive80"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive81", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive81 = :conNive81"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive82", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive82 = :conNive82"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive83", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive83 = :conNive83"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive84", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive84 = :conNive84"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive85", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive85 = :conNive85"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive86", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive86 = :conNive86"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive87", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive87 = :conNive87"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive88", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive88 = :conNive88"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive89", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive89 = :conNive89"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive90", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive90 = :conNive90"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive91", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive91 = :conNive91"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive92", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive92 = :conNive92"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive93", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive93 = :conNive93"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive94", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive94 = :conNive94"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive95", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive95 = :conNive95"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive96", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive96 = :conNive96"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive97", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive97 = :conNive97"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive98", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive98 = :conNive98"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive99", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive99 = :conNive99"),
    @NamedQuery(name = "FbGlCatalogo.findByConNive100", query = "SELECT f FROM FbGlCatalogo f WHERE f.conNive100 = :conNive100"),
    @NamedQuery(name = "FbGlCatalogo.findByConCarmes", query = "SELECT f FROM FbGlCatalogo f WHERE f.conCarmes = :conCarmes"),
    @NamedQuery(name = "FbGlCatalogo.findByConAbomes", query = "SELECT f FROM FbGlCatalogo f WHERE f.conAbomes = :conAbomes"),
    @NamedQuery(name = "FbGlCatalogo.findByConCarano", query = "SELECT f FROM FbGlCatalogo f WHERE f.conCarano = :conCarano"),
    @NamedQuery(name = "FbGlCatalogo.findByConAboano", query = "SELECT f FROM FbGlCatalogo f WHERE f.conAboano = :conAboano"),
    @NamedQuery(name = "FbGlCatalogo.findByConOperab", query = "SELECT f FROM FbGlCatalogo f WHERE f.conOperab = :conOperab"),
    @NamedQuery(name = "FbGlCatalogo.findByConTipcta", query = "SELECT f FROM FbGlCatalogo f WHERE f.conTipcta = :conTipcta"),
    @NamedQuery(name = "FbGlCatalogo.findByConTipsal", query = "SELECT f FROM FbGlCatalogo f WHERE f.conTipsal = :conTipsal"),
    @NamedQuery(name = "FbGlCatalogo.findByConFechac", query = "SELECT f FROM FbGlCatalogo f WHERE f.conFechac = :conFechac"),
    @NamedQuery(name = "FbGlCatalogo.findByConTransit", query = "SELECT f FROM FbGlCatalogo f WHERE f.conTransit = :conTransit"),
    @NamedQuery(name = "FbGlCatalogo.findByDigMayor", query = "SELECT f FROM FbGlCatalogo f WHERE f.digMayor = :digMayor"),
    @NamedQuery(name = "FbGlCatalogo.findByCorrelativo", query = "SELECT f FROM FbGlCatalogo f WHERE f.correlativo = :correlativo"),
    @NamedQuery(name = "FbGlCatalogo.findByIndRet", query = "SELECT f FROM FbGlCatalogo f WHERE f.indRet = :indRet"),
    @NamedQuery(name = "FbGlCatalogo.findByEmail", query = "SELECT f FROM FbGlCatalogo f WHERE f.email = :email")})
public class FbGlCatalogo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FbGlCatalogoPK fbGlCatalogoPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "CON_DESCRI")
    private String conDescri;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE01")
    private String conNive01;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE02")
    private String conNive02;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE03")
    private String conNive03;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE04")
    private String conNive04;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE05")
    private String conNive05;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE06")
    private String conNive06;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE07")
    private String conNive07;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE08")
    private String conNive08;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE09")
    private String conNive09;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE10")
    private String conNive10;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE11")
    private String conNive11;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE12")
    private String conNive12;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE13")
    private String conNive13;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE14")
    private String conNive14;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE15")
    private String conNive15;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE16")
    private String conNive16;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE17")
    private String conNive17;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE18")
    private String conNive18;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE19")
    private String conNive19;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE20")
    private String conNive20;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE21")
    private String conNive21;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE22")
    private String conNive22;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE23")
    private String conNive23;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE24")
    private String conNive24;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE25")
    private String conNive25;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE26")
    private String conNive26;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE27")
    private String conNive27;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE28")
    private String conNive28;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE29")
    private String conNive29;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE30")
    private String conNive30;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE31")
    private String conNive31;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE32")
    private String conNive32;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE33")
    private String conNive33;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE34")
    private String conNive34;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE35")
    private String conNive35;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE36")
    private String conNive36;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE37")
    private String conNive37;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE38")
    private String conNive38;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE39")
    private String conNive39;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE40")
    private String conNive40;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE41")
    private String conNive41;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE42")
    private String conNive42;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE43")
    private String conNive43;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE44")
    private String conNive44;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE45")
    private String conNive45;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE46")
    private String conNive46;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE47")
    private String conNive47;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE48")
    private String conNive48;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE49")
    private String conNive49;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE50")
    private String conNive50;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE51")
    private String conNive51;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE52")
    private String conNive52;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE53")
    private String conNive53;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE54")
    private String conNive54;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE55")
    private String conNive55;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE56")
    private String conNive56;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE57")
    private String conNive57;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE58")
    private String conNive58;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE59")
    private String conNive59;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE60")
    private String conNive60;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE61")
    private String conNive61;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE62")
    private String conNive62;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE63")
    private String conNive63;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE64")
    private String conNive64;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE65")
    private String conNive65;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE66")
    private String conNive66;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE67")
    private String conNive67;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE68")
    private String conNive68;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE69")
    private String conNive69;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE70")
    private String conNive70;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE71")
    private String conNive71;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE72")
    private String conNive72;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE73")
    private String conNive73;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE74")
    private String conNive74;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE75")
    private String conNive75;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE76")
    private String conNive76;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE77")
    private String conNive77;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE78")
    private String conNive78;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE79")
    private String conNive79;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE80")
    private String conNive80;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE81")
    private String conNive81;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE82")
    private String conNive82;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE83")
    private String conNive83;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE84")
    private String conNive84;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE85")
    private String conNive85;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE86")
    private String conNive86;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE87")
    private String conNive87;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE88")
    private String conNive88;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE89")
    private String conNive89;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE90")
    private String conNive90;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE91")
    private String conNive91;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE92")
    private String conNive92;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE93")
    private String conNive93;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE94")
    private String conNive94;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE95")
    private String conNive95;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE96")
    private String conNive96;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE97")
    private String conNive97;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE98")
    private String conNive98;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE99")
    private String conNive99;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_NIVE100")
    private String conNive100;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "CON_CARMES")
    private BigDecimal conCarmes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CON_ABOMES")
    private BigDecimal conAbomes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CON_CARANO")
    private BigDecimal conCarano;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CON_ABOANO")
    private BigDecimal conAboano;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_OPERAB")
    private String conOperab;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_TIPCTA")
    private String conTipcta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CON_TIPSAL")
    private String conTipsal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CON_FECHAC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date conFechac;
    @Size(max = 1)
    @Column(name = "CON_TRANSIT")
    private String conTransit;
    @Size(max = 10)
    @Column(name = "DIG_MAYOR")
    private String digMayor;
    @Column(name = "CORRELATIVO")
    private BigInteger correlativo;
    @Size(max = 1)
    @Column(name = "IND_RET")
    private String indRet;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;

    public FbGlCatalogo() {
    }

    public FbGlCatalogo(FbGlCatalogoPK fbGlCatalogoPK) {
        this.fbGlCatalogoPK = fbGlCatalogoPK;
    }

    public FbGlCatalogo(FbGlCatalogoPK fbGlCatalogoPK, String conDescri, String conNive01, String conNive02, String conNive03, String conNive04, String conNive05, String conNive06, String conNive07, String conNive08, String conNive09, String conNive10, String conNive11, String conNive12, String conNive13, String conNive14, String conNive15, String conNive16, String conNive17, String conNive18, String conNive19, String conNive20, String conNive21, String conNive22, String conNive23, String conNive24, String conNive25, String conNive26, String conNive27, String conNive28, String conNive29, String conNive30, String conNive31, String conNive32, String conNive33, String conNive34, String conNive35, String conNive36, String conNive37, String conNive38, String conNive39, String conNive40, String conNive41, String conNive42, String conNive43, String conNive44, String conNive45, String conNive46, String conNive47, String conNive48, String conNive49, String conNive50, String conNive51, String conNive52, String conNive53, String conNive54, String conNive55, String conNive56, String conNive57, String conNive58, String conNive59, String conNive60, String conNive61, String conNive62, String conNive63, String conNive64, String conNive65, String conNive66, String conNive67, String conNive68, String conNive69, String conNive70, String conNive71, String conNive72, String conNive73, String conNive74, String conNive75, String conNive76, String conNive77, String conNive78, String conNive79, String conNive80, String conNive81, String conNive82, String conNive83, String conNive84, String conNive85, String conNive86, String conNive87, String conNive88, String conNive89, String conNive90, String conNive91, String conNive92, String conNive93, String conNive94, String conNive95, String conNive96, String conNive97, String conNive98, String conNive99, String conNive100, BigDecimal conCarmes, BigDecimal conAbomes, BigDecimal conCarano, BigDecimal conAboano, String conOperab, String conTipcta, String conTipsal, Date conFechac) {
        this.fbGlCatalogoPK = fbGlCatalogoPK;
        this.conDescri = conDescri;
        this.conNive01 = conNive01;
        this.conNive02 = conNive02;
        this.conNive03 = conNive03;
        this.conNive04 = conNive04;
        this.conNive05 = conNive05;
        this.conNive06 = conNive06;
        this.conNive07 = conNive07;
        this.conNive08 = conNive08;
        this.conNive09 = conNive09;
        this.conNive10 = conNive10;
        this.conNive11 = conNive11;
        this.conNive12 = conNive12;
        this.conNive13 = conNive13;
        this.conNive14 = conNive14;
        this.conNive15 = conNive15;
        this.conNive16 = conNive16;
        this.conNive17 = conNive17;
        this.conNive18 = conNive18;
        this.conNive19 = conNive19;
        this.conNive20 = conNive20;
        this.conNive21 = conNive21;
        this.conNive22 = conNive22;
        this.conNive23 = conNive23;
        this.conNive24 = conNive24;
        this.conNive25 = conNive25;
        this.conNive26 = conNive26;
        this.conNive27 = conNive27;
        this.conNive28 = conNive28;
        this.conNive29 = conNive29;
        this.conNive30 = conNive30;
        this.conNive31 = conNive31;
        this.conNive32 = conNive32;
        this.conNive33 = conNive33;
        this.conNive34 = conNive34;
        this.conNive35 = conNive35;
        this.conNive36 = conNive36;
        this.conNive37 = conNive37;
        this.conNive38 = conNive38;
        this.conNive39 = conNive39;
        this.conNive40 = conNive40;
        this.conNive41 = conNive41;
        this.conNive42 = conNive42;
        this.conNive43 = conNive43;
        this.conNive44 = conNive44;
        this.conNive45 = conNive45;
        this.conNive46 = conNive46;
        this.conNive47 = conNive47;
        this.conNive48 = conNive48;
        this.conNive49 = conNive49;
        this.conNive50 = conNive50;
        this.conNive51 = conNive51;
        this.conNive52 = conNive52;
        this.conNive53 = conNive53;
        this.conNive54 = conNive54;
        this.conNive55 = conNive55;
        this.conNive56 = conNive56;
        this.conNive57 = conNive57;
        this.conNive58 = conNive58;
        this.conNive59 = conNive59;
        this.conNive60 = conNive60;
        this.conNive61 = conNive61;
        this.conNive62 = conNive62;
        this.conNive63 = conNive63;
        this.conNive64 = conNive64;
        this.conNive65 = conNive65;
        this.conNive66 = conNive66;
        this.conNive67 = conNive67;
        this.conNive68 = conNive68;
        this.conNive69 = conNive69;
        this.conNive70 = conNive70;
        this.conNive71 = conNive71;
        this.conNive72 = conNive72;
        this.conNive73 = conNive73;
        this.conNive74 = conNive74;
        this.conNive75 = conNive75;
        this.conNive76 = conNive76;
        this.conNive77 = conNive77;
        this.conNive78 = conNive78;
        this.conNive79 = conNive79;
        this.conNive80 = conNive80;
        this.conNive81 = conNive81;
        this.conNive82 = conNive82;
        this.conNive83 = conNive83;
        this.conNive84 = conNive84;
        this.conNive85 = conNive85;
        this.conNive86 = conNive86;
        this.conNive87 = conNive87;
        this.conNive88 = conNive88;
        this.conNive89 = conNive89;
        this.conNive90 = conNive90;
        this.conNive91 = conNive91;
        this.conNive92 = conNive92;
        this.conNive93 = conNive93;
        this.conNive94 = conNive94;
        this.conNive95 = conNive95;
        this.conNive96 = conNive96;
        this.conNive97 = conNive97;
        this.conNive98 = conNive98;
        this.conNive99 = conNive99;
        this.conNive100 = conNive100;
        this.conCarmes = conCarmes;
        this.conAbomes = conAbomes;
        this.conCarano = conCarano;
        this.conAboano = conAboano;
        this.conOperab = conOperab;
        this.conTipcta = conTipcta;
        this.conTipsal = conTipsal;
        this.conFechac = conFechac;
    }

    public FbGlCatalogo(BigInteger idCia, String conNumcta) {
        this.fbGlCatalogoPK = new FbGlCatalogoPK(idCia, conNumcta);
    }

    public FbGlCatalogoPK getFbGlCatalogoPK() {
        return fbGlCatalogoPK;
    }

    public void setFbGlCatalogoPK(FbGlCatalogoPK fbGlCatalogoPK) {
        this.fbGlCatalogoPK = fbGlCatalogoPK;
    }

    public String getConDescri() {
        return conDescri;
    }

    public void setConDescri(String conDescri) {
        this.conDescri = conDescri;
    }

    public String getConNive01() {
        return conNive01;
    }

    public void setConNive01(String conNive01) {
        this.conNive01 = conNive01;
    }

    public String getConNive02() {
        return conNive02;
    }

    public void setConNive02(String conNive02) {
        this.conNive02 = conNive02;
    }

    public String getConNive03() {
        return conNive03;
    }

    public void setConNive03(String conNive03) {
        this.conNive03 = conNive03;
    }

    public String getConNive04() {
        return conNive04;
    }

    public void setConNive04(String conNive04) {
        this.conNive04 = conNive04;
    }

    public String getConNive05() {
        return conNive05;
    }

    public void setConNive05(String conNive05) {
        this.conNive05 = conNive05;
    }

    public String getConNive06() {
        return conNive06;
    }

    public void setConNive06(String conNive06) {
        this.conNive06 = conNive06;
    }

    public String getConNive07() {
        return conNive07;
    }

    public void setConNive07(String conNive07) {
        this.conNive07 = conNive07;
    }

    public String getConNive08() {
        return conNive08;
    }

    public void setConNive08(String conNive08) {
        this.conNive08 = conNive08;
    }

    public String getConNive09() {
        return conNive09;
    }

    public void setConNive09(String conNive09) {
        this.conNive09 = conNive09;
    }

    public String getConNive10() {
        return conNive10;
    }

    public void setConNive10(String conNive10) {
        this.conNive10 = conNive10;
    }

    public String getConNive11() {
        return conNive11;
    }

    public void setConNive11(String conNive11) {
        this.conNive11 = conNive11;
    }

    public String getConNive12() {
        return conNive12;
    }

    public void setConNive12(String conNive12) {
        this.conNive12 = conNive12;
    }

    public String getConNive13() {
        return conNive13;
    }

    public void setConNive13(String conNive13) {
        this.conNive13 = conNive13;
    }

    public String getConNive14() {
        return conNive14;
    }

    public void setConNive14(String conNive14) {
        this.conNive14 = conNive14;
    }

    public String getConNive15() {
        return conNive15;
    }

    public void setConNive15(String conNive15) {
        this.conNive15 = conNive15;
    }

    public String getConNive16() {
        return conNive16;
    }

    public void setConNive16(String conNive16) {
        this.conNive16 = conNive16;
    }

    public String getConNive17() {
        return conNive17;
    }

    public void setConNive17(String conNive17) {
        this.conNive17 = conNive17;
    }

    public String getConNive18() {
        return conNive18;
    }

    public void setConNive18(String conNive18) {
        this.conNive18 = conNive18;
    }

    public String getConNive19() {
        return conNive19;
    }

    public void setConNive19(String conNive19) {
        this.conNive19 = conNive19;
    }

    public String getConNive20() {
        return conNive20;
    }

    public void setConNive20(String conNive20) {
        this.conNive20 = conNive20;
    }

    public String getConNive21() {
        return conNive21;
    }

    public void setConNive21(String conNive21) {
        this.conNive21 = conNive21;
    }

    public String getConNive22() {
        return conNive22;
    }

    public void setConNive22(String conNive22) {
        this.conNive22 = conNive22;
    }

    public String getConNive23() {
        return conNive23;
    }

    public void setConNive23(String conNive23) {
        this.conNive23 = conNive23;
    }

    public String getConNive24() {
        return conNive24;
    }

    public void setConNive24(String conNive24) {
        this.conNive24 = conNive24;
    }

    public String getConNive25() {
        return conNive25;
    }

    public void setConNive25(String conNive25) {
        this.conNive25 = conNive25;
    }

    public String getConNive26() {
        return conNive26;
    }

    public void setConNive26(String conNive26) {
        this.conNive26 = conNive26;
    }

    public String getConNive27() {
        return conNive27;
    }

    public void setConNive27(String conNive27) {
        this.conNive27 = conNive27;
    }

    public String getConNive28() {
        return conNive28;
    }

    public void setConNive28(String conNive28) {
        this.conNive28 = conNive28;
    }

    public String getConNive29() {
        return conNive29;
    }

    public void setConNive29(String conNive29) {
        this.conNive29 = conNive29;
    }

    public String getConNive30() {
        return conNive30;
    }

    public void setConNive30(String conNive30) {
        this.conNive30 = conNive30;
    }

    public String getConNive31() {
        return conNive31;
    }

    public void setConNive31(String conNive31) {
        this.conNive31 = conNive31;
    }

    public String getConNive32() {
        return conNive32;
    }

    public void setConNive32(String conNive32) {
        this.conNive32 = conNive32;
    }

    public String getConNive33() {
        return conNive33;
    }

    public void setConNive33(String conNive33) {
        this.conNive33 = conNive33;
    }

    public String getConNive34() {
        return conNive34;
    }

    public void setConNive34(String conNive34) {
        this.conNive34 = conNive34;
    }

    public String getConNive35() {
        return conNive35;
    }

    public void setConNive35(String conNive35) {
        this.conNive35 = conNive35;
    }

    public String getConNive36() {
        return conNive36;
    }

    public void setConNive36(String conNive36) {
        this.conNive36 = conNive36;
    }

    public String getConNive37() {
        return conNive37;
    }

    public void setConNive37(String conNive37) {
        this.conNive37 = conNive37;
    }

    public String getConNive38() {
        return conNive38;
    }

    public void setConNive38(String conNive38) {
        this.conNive38 = conNive38;
    }

    public String getConNive39() {
        return conNive39;
    }

    public void setConNive39(String conNive39) {
        this.conNive39 = conNive39;
    }

    public String getConNive40() {
        return conNive40;
    }

    public void setConNive40(String conNive40) {
        this.conNive40 = conNive40;
    }

    public String getConNive41() {
        return conNive41;
    }

    public void setConNive41(String conNive41) {
        this.conNive41 = conNive41;
    }

    public String getConNive42() {
        return conNive42;
    }

    public void setConNive42(String conNive42) {
        this.conNive42 = conNive42;
    }

    public String getConNive43() {
        return conNive43;
    }

    public void setConNive43(String conNive43) {
        this.conNive43 = conNive43;
    }

    public String getConNive44() {
        return conNive44;
    }

    public void setConNive44(String conNive44) {
        this.conNive44 = conNive44;
    }

    public String getConNive45() {
        return conNive45;
    }

    public void setConNive45(String conNive45) {
        this.conNive45 = conNive45;
    }

    public String getConNive46() {
        return conNive46;
    }

    public void setConNive46(String conNive46) {
        this.conNive46 = conNive46;
    }

    public String getConNive47() {
        return conNive47;
    }

    public void setConNive47(String conNive47) {
        this.conNive47 = conNive47;
    }

    public String getConNive48() {
        return conNive48;
    }

    public void setConNive48(String conNive48) {
        this.conNive48 = conNive48;
    }

    public String getConNive49() {
        return conNive49;
    }

    public void setConNive49(String conNive49) {
        this.conNive49 = conNive49;
    }

    public String getConNive50() {
        return conNive50;
    }

    public void setConNive50(String conNive50) {
        this.conNive50 = conNive50;
    }

    public String getConNive51() {
        return conNive51;
    }

    public void setConNive51(String conNive51) {
        this.conNive51 = conNive51;
    }

    public String getConNive52() {
        return conNive52;
    }

    public void setConNive52(String conNive52) {
        this.conNive52 = conNive52;
    }

    public String getConNive53() {
        return conNive53;
    }

    public void setConNive53(String conNive53) {
        this.conNive53 = conNive53;
    }

    public String getConNive54() {
        return conNive54;
    }

    public void setConNive54(String conNive54) {
        this.conNive54 = conNive54;
    }

    public String getConNive55() {
        return conNive55;
    }

    public void setConNive55(String conNive55) {
        this.conNive55 = conNive55;
    }

    public String getConNive56() {
        return conNive56;
    }

    public void setConNive56(String conNive56) {
        this.conNive56 = conNive56;
    }

    public String getConNive57() {
        return conNive57;
    }

    public void setConNive57(String conNive57) {
        this.conNive57 = conNive57;
    }

    public String getConNive58() {
        return conNive58;
    }

    public void setConNive58(String conNive58) {
        this.conNive58 = conNive58;
    }

    public String getConNive59() {
        return conNive59;
    }

    public void setConNive59(String conNive59) {
        this.conNive59 = conNive59;
    }

    public String getConNive60() {
        return conNive60;
    }

    public void setConNive60(String conNive60) {
        this.conNive60 = conNive60;
    }

    public String getConNive61() {
        return conNive61;
    }

    public void setConNive61(String conNive61) {
        this.conNive61 = conNive61;
    }

    public String getConNive62() {
        return conNive62;
    }

    public void setConNive62(String conNive62) {
        this.conNive62 = conNive62;
    }

    public String getConNive63() {
        return conNive63;
    }

    public void setConNive63(String conNive63) {
        this.conNive63 = conNive63;
    }

    public String getConNive64() {
        return conNive64;
    }

    public void setConNive64(String conNive64) {
        this.conNive64 = conNive64;
    }

    public String getConNive65() {
        return conNive65;
    }

    public void setConNive65(String conNive65) {
        this.conNive65 = conNive65;
    }

    public String getConNive66() {
        return conNive66;
    }

    public void setConNive66(String conNive66) {
        this.conNive66 = conNive66;
    }

    public String getConNive67() {
        return conNive67;
    }

    public void setConNive67(String conNive67) {
        this.conNive67 = conNive67;
    }

    public String getConNive68() {
        return conNive68;
    }

    public void setConNive68(String conNive68) {
        this.conNive68 = conNive68;
    }

    public String getConNive69() {
        return conNive69;
    }

    public void setConNive69(String conNive69) {
        this.conNive69 = conNive69;
    }

    public String getConNive70() {
        return conNive70;
    }

    public void setConNive70(String conNive70) {
        this.conNive70 = conNive70;
    }

    public String getConNive71() {
        return conNive71;
    }

    public void setConNive71(String conNive71) {
        this.conNive71 = conNive71;
    }

    public String getConNive72() {
        return conNive72;
    }

    public void setConNive72(String conNive72) {
        this.conNive72 = conNive72;
    }

    public String getConNive73() {
        return conNive73;
    }

    public void setConNive73(String conNive73) {
        this.conNive73 = conNive73;
    }

    public String getConNive74() {
        return conNive74;
    }

    public void setConNive74(String conNive74) {
        this.conNive74 = conNive74;
    }

    public String getConNive75() {
        return conNive75;
    }

    public void setConNive75(String conNive75) {
        this.conNive75 = conNive75;
    }

    public String getConNive76() {
        return conNive76;
    }

    public void setConNive76(String conNive76) {
        this.conNive76 = conNive76;
    }

    public String getConNive77() {
        return conNive77;
    }

    public void setConNive77(String conNive77) {
        this.conNive77 = conNive77;
    }

    public String getConNive78() {
        return conNive78;
    }

    public void setConNive78(String conNive78) {
        this.conNive78 = conNive78;
    }

    public String getConNive79() {
        return conNive79;
    }

    public void setConNive79(String conNive79) {
        this.conNive79 = conNive79;
    }

    public String getConNive80() {
        return conNive80;
    }

    public void setConNive80(String conNive80) {
        this.conNive80 = conNive80;
    }

    public String getConNive81() {
        return conNive81;
    }

    public void setConNive81(String conNive81) {
        this.conNive81 = conNive81;
    }

    public String getConNive82() {
        return conNive82;
    }

    public void setConNive82(String conNive82) {
        this.conNive82 = conNive82;
    }

    public String getConNive83() {
        return conNive83;
    }

    public void setConNive83(String conNive83) {
        this.conNive83 = conNive83;
    }

    public String getConNive84() {
        return conNive84;
    }

    public void setConNive84(String conNive84) {
        this.conNive84 = conNive84;
    }

    public String getConNive85() {
        return conNive85;
    }

    public void setConNive85(String conNive85) {
        this.conNive85 = conNive85;
    }

    public String getConNive86() {
        return conNive86;
    }

    public void setConNive86(String conNive86) {
        this.conNive86 = conNive86;
    }

    public String getConNive87() {
        return conNive87;
    }

    public void setConNive87(String conNive87) {
        this.conNive87 = conNive87;
    }

    public String getConNive88() {
        return conNive88;
    }

    public void setConNive88(String conNive88) {
        this.conNive88 = conNive88;
    }

    public String getConNive89() {
        return conNive89;
    }

    public void setConNive89(String conNive89) {
        this.conNive89 = conNive89;
    }

    public String getConNive90() {
        return conNive90;
    }

    public void setConNive90(String conNive90) {
        this.conNive90 = conNive90;
    }

    public String getConNive91() {
        return conNive91;
    }

    public void setConNive91(String conNive91) {
        this.conNive91 = conNive91;
    }

    public String getConNive92() {
        return conNive92;
    }

    public void setConNive92(String conNive92) {
        this.conNive92 = conNive92;
    }

    public String getConNive93() {
        return conNive93;
    }

    public void setConNive93(String conNive93) {
        this.conNive93 = conNive93;
    }

    public String getConNive94() {
        return conNive94;
    }

    public void setConNive94(String conNive94) {
        this.conNive94 = conNive94;
    }

    public String getConNive95() {
        return conNive95;
    }

    public void setConNive95(String conNive95) {
        this.conNive95 = conNive95;
    }

    public String getConNive96() {
        return conNive96;
    }

    public void setConNive96(String conNive96) {
        this.conNive96 = conNive96;
    }

    public String getConNive97() {
        return conNive97;
    }

    public void setConNive97(String conNive97) {
        this.conNive97 = conNive97;
    }

    public String getConNive98() {
        return conNive98;
    }

    public void setConNive98(String conNive98) {
        this.conNive98 = conNive98;
    }

    public String getConNive99() {
        return conNive99;
    }

    public void setConNive99(String conNive99) {
        this.conNive99 = conNive99;
    }

    public String getConNive100() {
        return conNive100;
    }

    public void setConNive100(String conNive100) {
        this.conNive100 = conNive100;
    }

    public BigDecimal getConCarmes() {
        return conCarmes;
    }

    public void setConCarmes(BigDecimal conCarmes) {
        this.conCarmes = conCarmes;
    }

    public BigDecimal getConAbomes() {
        return conAbomes;
    }

    public void setConAbomes(BigDecimal conAbomes) {
        this.conAbomes = conAbomes;
    }

    public BigDecimal getConCarano() {
        return conCarano;
    }

    public void setConCarano(BigDecimal conCarano) {
        this.conCarano = conCarano;
    }

    public BigDecimal getConAboano() {
        return conAboano;
    }

    public void setConAboano(BigDecimal conAboano) {
        this.conAboano = conAboano;
    }

    public String getConOperab() {
        return conOperab;
    }

    public void setConOperab(String conOperab) {
        this.conOperab = conOperab;
    }

    public String getConTipcta() {
        return conTipcta;
    }

    public void setConTipcta(String conTipcta) {
        this.conTipcta = conTipcta;
    }

    public String getConTipsal() {
        return conTipsal;
    }

    public void setConTipsal(String conTipsal) {
        this.conTipsal = conTipsal;
    }

    public Date getConFechac() {
        return conFechac;
    }

    public void setConFechac(Date conFechac) {
        this.conFechac = conFechac;
    }

    public String getConTransit() {
        return conTransit;
    }

    public void setConTransit(String conTransit) {
        this.conTransit = conTransit;
    }

    public String getDigMayor() {
        return digMayor;
    }

    public void setDigMayor(String digMayor) {
        this.digMayor = digMayor;
    }

    public BigInteger getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(BigInteger correlativo) {
        this.correlativo = correlativo;
    }

    public String getIndRet() {
        return indRet;
    }

    public void setIndRet(String indRet) {
        this.indRet = indRet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fbGlCatalogoPK != null ? fbGlCatalogoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbGlCatalogo)) {
            return false;
        }
        FbGlCatalogo other = (FbGlCatalogo) object;
        if ((this.fbGlCatalogoPK == null && other.fbGlCatalogoPK != null) || (this.fbGlCatalogoPK != null && !this.fbGlCatalogoPK.equals(other.fbGlCatalogoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fastbooks.modelo.FbGlCatalogo[ fbGlCatalogoPK=" + fbGlCatalogoPK + " ]";
    }
    
}
