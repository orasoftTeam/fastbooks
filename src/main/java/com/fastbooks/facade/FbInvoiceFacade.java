/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.modelo.FbInvoice;
import com.fastbooks.modelo.FbInvoiceDetail;
import com.fastbooks.modelo.FbInvoiceTaxes;
import com.fastbooks.modelo.FbPaymentDetail;
import com.fastbooks.util.GlobalParameters;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author DELL
 */
@Stateless
public class FbInvoiceFacade extends AbstractFacade<FbInvoice> {

    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    //FbCustomerFacade cFacade;
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FbInvoiceFacade() {
        super(FbInvoice.class);
    }

    public List<FbInvoice> getInvoicesByIdCia(String idCia) {
        List<FbInvoice> list = new ArrayList<>();
        try {

            String sql = "select * from fb_invoice i where id_cia=? and status != 'DEL' order by to_number(i.NO_DOT),to_date(i.in_date,'MM/dd/yyyy') desc";
            Query q = em.createNativeQuery(sql, FbInvoice.class);
            q.setParameter(1, idCia);
            list = q.getResultList();
            for (FbInvoice fbInvoice : list) {
                em.refresh(fbInvoice);
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }

        return list;
    }
    
    public FbInvoice getInvoiceByIdInvoice(String idInvoice) {
        List<FbInvoice> list = new ArrayList<>();
        try {

            String sql = "select * from fb_invoice where id_invoice=? and status != 'DEL'";
            Query q = em.createNativeQuery(sql, FbInvoice.class);
            q.setParameter(1, idInvoice);
            list = q.getResultList();
            for (FbInvoice fbInvoice : list) {
                em.refresh(fbInvoice);
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }

        return list.get(0);
    }    
    
    public List<FbInvoice> getInvoicesByIdCust(String idCust) {
        List<FbInvoice> list = new ArrayList<>();
        try {

            String sql = "select * from fb_invoice i where id_cust=? and status != 'DEL' order by to_number(i.NO_DOT),to_date(i.in_date,'MM/dd/yyyy') desc";
            Query q = em.createNativeQuery(sql, FbInvoice.class);
            q.setParameter(1, idCust);
            list = q.getResultList();
            for (FbInvoice fbInvoice : list) {
                em.refresh(fbInvoice);
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }

        return list;
    }    
    
    

 /*   public List<FbInvoice> getInvoicesByIdCiaNonJpa(String idCia) throws SQLException, ClassNotFoundException {
        List<FbInvoice> list = new ArrayList<>();

        //Connection cn = em.unwrap(java.sql.Connection.class);
        Connection cn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            cn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.41:1551:dbprod1", "fastbooks", "fastbooks1979");
            String sql = "select * from fb_invoice i where id_cia=? order by to_date(i.in_date,'MM/dd/yyyy') desc";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idCia));
            ResultSet rs = ps.executeQuery();
            int c = 0;
            while (rs.next()) {
                FbInvoice in = new FbInvoice();
                in.setIdInvoice(new BigDecimal(rs.getInt("ID_INVOICE")));
                in.setType(rs.getString("TYPE"));
                in.setStatus(rs.getString("STATUS"));
                in.setInDate(rs.getString("IN_DATE"));
                in.setNoDot(rs.getString("NO_DOT"));
                in.setDueDate(rs.getString("DUE_DATE"));
                in.setTerms(rs.getString("TERMS"));
                in.setTotal(new BigDecimal(rs.getDouble("TOTAL")));
                in.setShCost(new BigDecimal(rs.getDouble("SH_COST")));
                in.setSubTotal(new BigDecimal(rs.getDouble("SUB_TOTAL")));
                in.setTaxTotal(new BigDecimal(rs.getDouble("TAX_TOTAL")));
                in.setActualBalance(new BigDecimal(rs.getDouble("ACTUAL_BALANCE")));
                in.setCustEmail(rs.getString("CUST_EMAIL"));
                in.setBiAddress(rs.getString("BI_ADDRESS"));
                in.setShAddress(rs.getString("SH_ADDRESS"));
                in.setMessageInvoice(rs.getString("MESSAGE_INVOICE"));
                in.setEsAccby(rs.getString("ES_ACCBY"));
                in.setEsAccdate(rs.getString("ES_ACCDATE"));
                in.setShDate(rs.getString("SH_DATE"));
                in.setShcostTaxAmount(rs.getBigDecimal("SHCOST_TAX_AMOUNT"));
                in.setShipVia(rs.getString("SHIP_VIA"));
                in.setShcostTaxName(rs.getString("SHCOST_TAX_NAME"));
                in.setPayMethod(rs.getString("PAY_METHOD"));
                in.setPayReferenceNo(rs.getString("PAY_REFERENCE_NO"));
                in.setTrackNum(rs.getString("TRACK_NUM"));
                //in.setIdCust(this.cFacade.find(rs.getBigDecimal("ID_CUST")));
                list.add(in);
            }

        } catch (NumberFormatException | SQLException e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        } finally {
            if (cn != null) {
                cn.close();
            }
        }

        return list;
    }*/

    public List<FbInvoice> getInvoicesForPayment(String idCia, String idCust) {
        List<FbInvoice> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM FB_INVOICE i WHERE ID_CIA = ? AND ID_CUST = ? AND STATUS IN('OV','PA','OP') AND TYPE = 'IN' ORDER BY TO_NUMBER(i.NO_DOT) ASC";
            Query q = em.createNativeQuery(sql, FbInvoice.class);
            q.setParameter(1, idCia);
            q.setParameter(2, idCust);
            list = q.getResultList();
            //em.flush();
            
            for (FbInvoice fbInvoice : list) {
                em.refresh(fbInvoice);
            }

        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getInvoicesForPayment()");
            e.printStackTrace();
        }

        return list;
    }

    public List<FbInvoice> getInvoicesByIdCiaFilter(String sql) {
        List<FbInvoice> list = new ArrayList<>();
        try {
            //String sql = "select * from fb_invoice where id_cia = ?'";
            Query q = em.createNativeQuery(sql, FbInvoice.class);
            //q.setParameter(1, idCia);
            list = q.getResultList();

        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }

        return list;
    }

    public String actPayment(FbInvoice in, String op) {
        String res = "def";
        String pIdInvoices = "";
        String pPayAmounts = "";
        /*
    PROCEDURE PR_ACT_PAYMENT(pIdCia IN INT,pIdPayment IN INT,pIdCust IN INT, pReferenceNo IN VARCHAR2,pEmail IN VARCHAR2,
                            pPayDate IN VARCHAR2,pPaymentTotal IN DECIMAL,pMethod IN VARCHAR2,pIdInvoices IN VARCHAR2,
                                pPayAmounts IN VARCHAR2,op IN VARCHAR2,res OUT VARCHAR2);
         */
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs = cn.prepareCall("{call FASTBOOKS.PROCS_FASTBOOKS.PR_ACT_PAYMENT(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, Integer.parseInt(in.getIdCia().getIdCia().toString()));
            cs.setInt(2, Integer.parseInt(in.getIdInvoice().toString()));
            cs.setInt(3, Integer.parseInt(in.getIdCust().getIdCust().toString()));
            cs.setString(4, in.getPayReferenceNo());
            cs.setString(5, in.getCustEmail());
            cs.setString(6, in.getInDate());
            cs.setDouble(7, in.getTotal().doubleValue());
            cs.setString(8, in.getPayMethod());

            for (FbPaymentDetail pd : in.getFbPaymentDetailList()) {
                pIdInvoices += pd.getIdInvoice().getIdInvoice().toString() + ",";
                pPayAmounts += String.format("%.2f", pd.getPayment()) + ";";
            }

            cs.setString(9, pIdInvoices);
            cs.setString(10, pPayAmounts);
            cs.setString(11, in.getMessageInvoice());
            cs.setString(12, op);
            cs.registerOutParameter(13, Types.VARCHAR);
            cs.registerOutParameter(14, Types.VARCHAR);
            cs.execute();
            res = cs.getString(13);
            // res = pIdInvoices+" :: "+pPayAmounts;
            cs.close();

        } catch (Exception e) {
            res = e.getMessage();
            System.out.println("com.fastbooks.facade.FbInvoiceFacade.actPayment()");
            e.printStackTrace();
        }
        System.out.println("res: " + res);
        return res;
    }
    
    public String actPaymentWithReturnId(FbInvoice in, String op) {
        String res = "def";
        String pIdInvoices = "";
        String pPayAmounts = "";
        /*
    PROCEDURE PR_ACT_PAYMENT(pIdCia IN INT,pIdPayment IN INT,pIdCust IN INT, pReferenceNo IN VARCHAR2,pEmail IN VARCHAR2,
                            pPayDate IN VARCHAR2,pPaymentTotal IN DECIMAL,pMethod IN VARCHAR2,pIdInvoices IN VARCHAR2,
                                pPayAmounts IN VARCHAR2,op IN VARCHAR2,res OUT VARCHAR2);
         */
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs = cn.prepareCall("{call FASTBOOKS.PROCS_FASTBOOKS.PR_ACT_PAYMENT(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, Integer.parseInt(in.getIdCia().getIdCia().toString()));
            cs.setInt(2, Integer.parseInt(in.getIdInvoice().toString()));
            cs.setInt(3, Integer.parseInt(in.getIdCust().getIdCust().toString()));
            cs.setString(4, in.getPayReferenceNo());
            cs.setString(5, in.getCustEmail());
            cs.setString(6, in.getInDate());
            cs.setDouble(7, in.getTotal().doubleValue());
            cs.setString(8, in.getPayMethod());

            for (FbPaymentDetail pd : in.getFbPaymentDetailList()) {
                pIdInvoices += pd.getIdInvoice().getIdInvoice().toString() + ",";
                pPayAmounts += String.format("%.2f", pd.getPayment()) + ";";
            }

            cs.setString(9, pIdInvoices);
            cs.setString(10, pPayAmounts);
            cs.setString(11, in.getMessageInvoice());
            cs.setString(12, op);
            cs.registerOutParameter(13, Types.VARCHAR);
            cs.registerOutParameter(14, Types.VARCHAR);
            cs.execute();
            res = cs.getString(14);
            // res = pIdInvoices+" :: "+pPayAmounts;
            cs.close();

        } catch (Exception e) {
            res = e.getMessage();
            System.out.println("com.fastbooks.facade.FbInvoiceFacade.actPayment()");
            e.printStackTrace();
        }
        System.out.println("res: " + res);
        return res;
    }    
    

    public String actInvoice(FbInvoice in, String op) {
        String res = "";
        String pProdsIds = "";
        String pQuants = "";
        String pIdTaxes = "";
        String pFromAmounts = "";
        String pTaxAmounts = "";
        String pTaxProdsIds = "";
        String pItemTaxes = "";
        String temp = null;
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs = cn.prepareCall("{call FASTBOOKS.PROCS_FASTBOOKS.PR_ACT_INVOICE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, Integer.parseInt(in.getIdCia().getIdCia().toString()));
            cs.setInt(2, Integer.parseInt(in.getIdInvoice().toString()));
            cs.setInt(3, Integer.parseInt(in.getIdCust().getIdCust().toString()));
            cs.setString(4, in.getType());
            cs.setString(5, in.getNoDot());
            cs.setString(6, in.getCustEmail());
            if (in.getInDate() != null) {
                temp = in.getInDate();
            }

            cs.setString(7, temp);
            if (in.getDueDate() != null) {
                temp = in.getDueDate();
            }

            cs.setString(8, temp);
            cs.setDouble(9, Double.parseDouble(in.getActualBalance().toString()));
            cs.setDouble(10, Double.parseDouble(in.getSubTotal().toString()));
            cs.setDouble(11, Double.parseDouble(in.getTaxTotal().toString()));
            cs.setDouble(12, Double.parseDouble(in.getTotal().toString()));
            cs.setString(13, in.getStatus());
            cs.setString(14, in.getBiAddress());
            cs.setString(15, in.getShAddress());
            cs.setString(16, in.getTerms());
            cs.setString(17, in.getTrackNum());
            cs.setString(18, in.getShipVia());
            if (in.getShDate() != null) {
                temp = in.getShDate();
            }

            cs.setString(19, temp);
            cs.setDouble(20, Double.parseDouble(in.getShCost().toString()));
            cs.setString(21, in.getMessageInvoice());
            cs.setString(22, in.getAttachment());

            for (FbInvoiceDetail fbInvoiceDetail : in.getFbInvoiceDetailList()) {
                pProdsIds += fbInvoiceDetail.getIdProd().getIdProd().toString() + ",";
                pQuants += fbInvoiceDetail.getItemQuant().toString() + ",";
                for (FbInvoiceTaxes fbInvoiceTax : in.getFbInvoiceTaxesList()) {
                    if (fbInvoiceTax.getIdTax().getIdTax().toString().equals(fbInvoiceDetail.getItemTax())) {
                        fbInvoiceDetail.setItemTax(fbInvoiceTax.getIdTax().getName());
                    }
                }
                pItemTaxes += fbInvoiceDetail.getItemTax() + ",";
            }

            for (FbInvoiceTaxes fbInvoiceDetail : in.getFbInvoiceTaxesList()) {
                pIdTaxes += fbInvoiceDetail.getIdTax().getIdTax().toString() + ",";
                pFromAmounts += fbInvoiceDetail.getFromAmount().toString() + ",";
                pTaxAmounts += fbInvoiceDetail.getTaxAmount().toString() + ",";
                pTaxProdsIds += fbInvoiceDetail.getIdProds() + ",";

            }

            cs.setString(23, pProdsIds);
            cs.setString(24, pQuants);
            cs.setString(25, pIdTaxes);
            cs.setString(26, pFromAmounts);
            cs.setString(27, pTaxAmounts);
            cs.setString(28, pTaxProdsIds);
            cs.setString(29, pItemTaxes);
            Integer idtax = 0;
            if (in.getIdShcostTax() != null) {
                idtax = Integer.parseInt(in.getIdShcostTax().getIdTax().toString());
            }

            cs.setInt(30, idtax);
            Double shtaxamount = Double.parseDouble(in.getShcostTaxAmount().toString());

            cs.setDouble(31, shtaxamount);

            cs.setString(32, in.getShcostTaxName());

            cs.setString(33, in.getEsAccby());
            cs.setString(34, in.getEsAccdate());
            cs.setString(35, op);
            cs.registerOutParameter(36, Types.VARCHAR);
            cs.registerOutParameter(37, Types.VARCHAR);
            cs.execute();
            res = cs.getString(36);
            String resId = cs.getString(37);
            System.out.println("Id de invoice recien insertada: " + resId);
            cs.close();

        } catch (Exception e) {
            res = e.toString();//"-2";
            e.printStackTrace();
        }
        System.out.println("Facade Resultado de operacion: " + res);
        return res;
    }
    
    
    public String actInvoiceWithReturnId(FbInvoice in, String op) {
        String res = "";
        String pProdsIds = "";
        String pQuants = "";
        String pIdTaxes = "";
        String pFromAmounts = "";
        String pTaxAmounts = "";
        String pTaxProdsIds = "";
        String pItemTaxes = "";
        String temp = null;
        String resId = "-1";
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs = cn.prepareCall("{call FASTBOOKS.PROCS_FASTBOOKS.PR_ACT_INVOICE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, Integer.parseInt(in.getIdCia().getIdCia().toString()));
            cs.setInt(2, Integer.parseInt(in.getIdInvoice().toString()));
            cs.setInt(3, Integer.parseInt(in.getIdCust().getIdCust().toString()));
            cs.setString(4, in.getType());
            cs.setString(5, in.getNoDot());
            cs.setString(6, in.getCustEmail());
            if (in.getInDate() != null) {
                temp = in.getInDate();
            }

            cs.setString(7, temp);
            if (in.getDueDate() != null) {
                temp = in.getDueDate();
            }

            cs.setString(8, temp);
            cs.setDouble(9, Double.parseDouble(in.getActualBalance().toString()));
            cs.setDouble(10, Double.parseDouble(in.getSubTotal().toString()));
            cs.setDouble(11, Double.parseDouble(in.getTaxTotal().toString()));
            cs.setDouble(12, Double.parseDouble(in.getTotal().toString()));
            cs.setString(13, in.getStatus());
            cs.setString(14, in.getBiAddress());
            cs.setString(15, in.getShAddress());
            cs.setString(16, in.getTerms());
            cs.setString(17, in.getTrackNum());
            cs.setString(18, in.getShipVia());
            if (in.getShDate() != null) {
                temp = in.getShDate();
            }

            cs.setString(19, temp);
            cs.setDouble(20, Double.parseDouble(in.getShCost().toString()));
            cs.setString(21, in.getMessageInvoice());
            cs.setString(22, in.getAttachment());

            for (FbInvoiceDetail fbInvoiceDetail : in.getFbInvoiceDetailList()) {
                pProdsIds += fbInvoiceDetail.getIdProd().getIdProd().toString() + ",";
                pQuants += fbInvoiceDetail.getItemQuant().toString() + ",";
                for (FbInvoiceTaxes fbInvoiceTax : in.getFbInvoiceTaxesList()) {
                    if (fbInvoiceTax.getIdTax().getIdTax().toString().equals(fbInvoiceDetail.getItemTax())) {
                        fbInvoiceDetail.setItemTax(fbInvoiceTax.getIdTax().getName());
                    }
                }
                pItemTaxes += fbInvoiceDetail.getItemTax() + ",";
            }

            for (FbInvoiceTaxes fbInvoiceDetail : in.getFbInvoiceTaxesList()) {
                pIdTaxes += fbInvoiceDetail.getIdTax().getIdTax().toString() + ",";
                pFromAmounts += fbInvoiceDetail.getFromAmount().toString() + ",";
                pTaxAmounts += fbInvoiceDetail.getTaxAmount().toString() + ",";
                pTaxProdsIds += fbInvoiceDetail.getIdProds() + ",";

            }

            cs.setString(23, pProdsIds);
            cs.setString(24, pQuants);
            cs.setString(25, pIdTaxes);
            cs.setString(26, pFromAmounts);
            cs.setString(27, pTaxAmounts);
            cs.setString(28, pTaxProdsIds);
            cs.setString(29, pItemTaxes);
            Integer idtax = 0;
            if (in.getIdShcostTax() != null) {
                idtax = Integer.parseInt(in.getIdShcostTax().getIdTax().toString());
            }

            cs.setInt(30, idtax);
            Double shtaxamount = Double.parseDouble(in.getShcostTaxAmount().toString());

            cs.setDouble(31, shtaxamount);

            cs.setString(32, in.getShcostTaxName());

            cs.setString(33, in.getEsAccby());
            cs.setString(34, in.getEsAccdate());
            cs.setString(35, op);
            cs.registerOutParameter(36, Types.VARCHAR);
            cs.registerOutParameter(37, Types.VARCHAR);
            cs.execute();
            res = cs.getString(36);
            resId = cs.getString(37);
            System.out.println("Id de invoice recien insertada: " + resId);
            cs.close();

        } catch (Exception e) {
            res = e.toString();//"-2";
            resId = "-2";
            e.printStackTrace();
        }
        System.out.println("Facade Resultado de operacion: " + res);
        return resId;
    }    

    public String generateInvoice(FbInvoice i, String logo, JasperReport report, String type,String balance) {
        String res = "";
        em.getEntityManagerFactory().getCache().evictAll();
        Connection cn = em.unwrap(java.sql.Connection.class);
        String dir = "view" + File.separator + "jasper" + File.separator + "report1.jrxml";
        GlobalParameters gp = new GlobalParameters();
        File file = new File(gp.getAppPath() + File.separator + "pdf" + File.separator + "cia" + i.getIdCia().getIdCia().toString()
                + File.separator);
        /*String[] entries = file.list();
        for (String s : entries) {
            File currentFile = new File(file.getPath(),s);
            currentFile.delete();
        }*/
        file.mkdirs();

        String destino = gp.getAppPath() + File.separator + "pdf" + File.separator + "cia" + i.getIdCia().getIdCia().toString()
                + File.separator + "IN" + i.getNoDot() + i.getIdCia().getNomCom() + ".pdf";

        String pdfName = File.separator + "IN" + i.getNoDot() + i.getIdCia().getNomCom() + ".pdf";
        Map parametersMap = new HashMap();
        parametersMap.put("ID_INVOICE", i.getIdInvoice().toString());
        File logoFile = new File(gp.getAppPath() + logo);
        if (!logoFile.exists()) {
            logoFile = null;
        }
        parametersMap.put("LOGO", logoFile);

        parametersMap.put("TYPE", type);
        parametersMap.put("BALANCE_DUE", balance);
        
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String realPath = ec.getRealPath("/");
            System.out.println(realPath + dir);
            //JasperReport report = JasperCompileManager.compileReport(realPath + dir);

            File dest = new File(destino);
            if (dest.exists()) {
                dest.delete();
            }
            JasperPrint print = JasperFillManager.fillReport(report, parametersMap, cn);
            JRExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destino);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.exportReport();
            System.out.println("File Created: " + destino);
            // i.setMessageStmnt("/pdf/"+"cia" + i.getIdCia().getIdCia().toString()+
            //       "/IN"+i.getNoDot()+i.getIdCia().getNomCom()+".pdf");
            // this.edit(i);
            //  res = i.getMessageStmnt();
            res = "/pdf/" + "cia" + i.getIdCia().getIdCia().toString()
                    + "/IN" + i.getNoDot() + i.getIdCia().getNomCom() + ".pdf";
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbInvoiceFacade.generateInvoice()");
            e.printStackTrace();
            res = e.toString() + " ::: " + e.getMessage();
        }

        return res;
    }
    
    public String generatePayment(FbInvoice i, String logo, JasperReport report, String amountCredited,String total) {
        String res = "";
        em.getEntityManagerFactory().getCache().evictAll();
        Connection cn = em.unwrap(java.sql.Connection.class);
        String dir = "view" + File.separator + "jasper" + File.separator + "payment.jrxml";
        GlobalParameters gp = new GlobalParameters();
        File file = new File(gp.getAppPath() + File.separator + "pdf" + File.separator + "cia" + i.getIdCia().getIdCia().toString()
                + File.separator);
        /*String[] entries = file.list();
        for (String s : entries) {
            File currentFile = new File(file.getPath(),s);
            currentFile.delete();
        }*/
        file.mkdirs();

        String destino = gp.getAppPath() + File.separator + "pdf" + File.separator + "cia" + i.getIdCia().getIdCia().toString()
                + File.separator + "Payment" + i.getIdInvoice().toString() + i.getIdCia().getNomCom() + ".pdf";

        //String pdfName = File.separator + "IN" + i.getNoDot() + i.getIdCia().getNomCom() + ".pdf";
        Map parametersMap = new HashMap();
        parametersMap.put("ID_INVOICE", i.getIdInvoice().toString());
        File logoFile = new File(gp.getAppPath() + logo);
        if (!logoFile.exists()) {
            logoFile = null;
        }
        parametersMap.put("LOGO", logoFile);
        parametersMap.put("AMOUNT_CREDITED", amountCredited);
        parametersMap.put("TOTAL", total);
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String realPath = ec.getRealPath("/");
            System.out.println(realPath + dir);
            //JasperReport report = JasperCompileManager.compileReport(realPath + dir);

            File dest = new File(destino);
            if (dest.exists()) {
                dest.delete();
            }
            JasperPrint print = JasperFillManager.fillReport(report, parametersMap, cn);
            JRExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destino);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.exportReport();
            System.out.println("File Created: " + destino);
            // i.setMessageStmnt("/pdf/"+"cia" + i.getIdCia().getIdCia().toString()+
            //       "/IN"+i.getNoDot()+i.getIdCia().getNomCom()+".pdf");
            // this.edit(i);
            //  res = i.getMessageStmnt();
            res = "/pdf/" + "cia" + i.getIdCia().getIdCia().toString()
                    + "/Payment" + i.getIdInvoice().toString() + i.getIdCia().getNomCom() + ".pdf";
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbInvoiceFacade.generatePayment()");
            e.printStackTrace();
            res = e.toString() + " ::: " + e.getMessage();
        }

        return res;
    }    

    public JasperReport getCompiledFile(String fileName, HttpServletRequest request) throws JRException, IOException {
        // Create temporary folder to store jasper report as you should not write a resource into your program
        // distribution
        String tempFolderPath = System.getProperty("java.io.tmpdir") + File.separator + "jasperReport";
        File tempFolder = new File(tempFolderPath);
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }
        String jasperFilePath = tempFolderPath + File.separator + fileName + ".jasper";
        File reportFile = new File(jasperFilePath);
        // If compiled file is not found, then compile XML template
        // if (!reportFile.exists()) {
        reportFile.delete();
        InputStream jRXmlStream = request.getSession().getServletContext().getResourceAsStream("/view/jasper/" + fileName + ".jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(jRXmlStream);
        JasperCompileManager.compileReportToFile(jasperDesign, jasperFilePath);
        // }
        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
        return jasperReport;
    }

    public String printTransactions(String idInvoices, String logo, JasperReport report, String idCia) {
        String res = "";
        try {

            Connection cn = em.unwrap(java.sql.Connection.class);
            String dir = "view" + File.separator + "jasper" + File.separator + "multipleInvoice.jrxml";
            GlobalParameters gp = new GlobalParameters();
            File file = new File(gp.getAppPath() + File.separator + "pdf" + File.separator + "cia" + idCia
                    + File.separator);
            file.mkdirs();
            String destino = gp.getAppPath() + File.separator + "pdf" + File.separator + "cia" + idCia
                    + File.separator + "INVOICES.pdf";
            Map parametersMap = new HashMap();
            parametersMap.put("idInvoices", idInvoices);
            File logoFile = new File(gp.getAppPath() + logo);
            if (!logoFile.exists()) {
                logoFile = null;
            }
            parametersMap.put("logo", logoFile);

            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String realPath = ec.getRealPath("/");
            System.out.println(realPath + dir);

            File dest = new File(destino);
            if (dest.exists()) {
                dest.delete();
            }
            JasperPrint print = JasperFillManager.fillReport(report, parametersMap, cn);
            JRExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destino);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.exportReport();
            System.out.println("File Created: " + destino);
            res = "/pdf/" + "cia" + idCia
                    + "/INVOICES.pdf";
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbInvoiceFacade.printTransactions()");
            e.printStackTrace();
            res = e.toString() + " ::: " + e.getMessage();
        }
        return res;
    }

    public String packingSlip(String idInvoices, String logo, JasperReport report, String idCia) {
        String res = "";
        try {

            Connection cn = em.unwrap(java.sql.Connection.class);
            String dir = "view" + File.separator + "jasper" + File.separator + "packingSlip.jrxml";
            GlobalParameters gp = new GlobalParameters();
            File file = new File(gp.getAppPath() + File.separator + "pdf" + File.separator + "cia" + idCia
                    + File.separator);
            file.mkdirs();
            String destino = gp.getAppPath() + File.separator + "pdf" + File.separator + "cia" + idCia
                    + File.separator + "packingSlip.pdf";
            Map parametersMap = new HashMap();
            parametersMap.put("idInvoices", idInvoices);
            File logoFile = new File(gp.getAppPath() + logo);
            if (!logoFile.exists()) {
                logoFile = null;
            }
            parametersMap.put("logo", logoFile);

            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String realPath = ec.getRealPath("/");
            System.out.println(realPath + dir);

            File dest = new File(destino);
            if (dest.exists()) {
                dest.delete();
            }
            JasperPrint print = JasperFillManager.fillReport(report, parametersMap, cn);
            JRExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destino);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.exportReport();
            System.out.println("File Created: " + destino);
            res = "/pdf/" + "cia" + idCia
                    + "/packingSlip.pdf";
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbInvoiceFacade.printTransactions()");
            e.printStackTrace();
            res = e.toString() + " ::: " + e.getMessage();
        }
        return res;
    }

    public List<FbInvoiceDetail> getInvoiceDetailsByIdInvoice(String idInvoice) {
        List<FbInvoiceDetail> list = new ArrayList<>();
        try {

            String sql = "select * from fb_invoice_detail where id_invoice = ? order by id_detail asc";
            Query q = em.createNativeQuery(sql, FbInvoiceDetail.class);
            q.setParameter(1, idInvoice);
            list = q.getResultList();
            for (FbInvoiceDetail fbInvoice : list) {
                em.refresh(fbInvoice);
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }

        return list;
    }

    public List<FbInvoiceTaxes> getInvoiceTaxesByIdInvoice(String idInvoice) {
        List<FbInvoiceTaxes> list = new ArrayList<>();
        try {

            String sql = "select * from fb_invoice_taxes where id_invoice = ? order by ID_INVOICE_TAX asc";
            Query q = em.createNativeQuery(sql, FbInvoiceTaxes.class);
            q.setParameter(1, idInvoice);
            list = q.getResultList();
            for (FbInvoiceTaxes fbInvoice : list) {
                em.refresh(fbInvoice);
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }

        return list;
    }

    public List<FbPaymentDetail> getPaymentDetailsByIdPayment(String idPayment) {
        List<FbPaymentDetail> list = new ArrayList<>();
        try {

            String sql = "select * from fb_payment_detail where id_payment = ? order by id_detail asc";
            Query q = em.createNativeQuery(sql, FbPaymentDetail.class);
            q.setParameter(1, idPayment);
            list = q.getResultList();
            for (FbPaymentDetail fbInvoice : list) {
                em.refresh(fbInvoice);
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }

        return list;
    }
    
    public List<FbPaymentDetail> getPaymentDetailsByIdInvoice(String idInvoice) {
        List<FbPaymentDetail> list = new ArrayList<>();
        try {

            String sql = "select * from fb_payment_detail where id_invoice = ? order by id_detail asc";
            Query q = em.createNativeQuery(sql, FbPaymentDetail.class);
            q.setParameter(1, idInvoice);
            list = q.getResultList();
            for (FbPaymentDetail fbInvoice : list) {
                em.refresh(fbInvoice);
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }

        return list;
    }    

}
