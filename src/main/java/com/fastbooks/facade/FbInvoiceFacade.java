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
import com.fastbooks.modelo.FbStatement;
import com.fastbooks.modelo.FbStmtDetail;
import com.fastbooks.util.GlobalParameters;
import com.fastbooks.util.PanelesVentas;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
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

    public List<FbInvoice> getInvoicesByIdCia(String idCia, int op) {
        List<FbInvoice> list = new ArrayList<>();
        try {
            String sql = "select * from fb_invoice i where id_cia=? and status != 'DEL' \n"
                    + "and to_date(i.in_date,'MM/dd/yyyy') >= sysdate-365 \n"
                    + "order by i.fecha_creacion desc";

            switch (op) {
                case 0:
                    sql = "select * from fb_invoice i where id_cia=? and status != 'DEL'\n"
                            + "and i.type = 'ES'\n"
                            + "and to_date(i.in_date,'MM/dd/yyyy') >= sysdate -365\n"
                            + "order by i.fecha_creacion desc";
                    break;
                case 1:
                    sql = "select * from fb_invoice i where id_cia=? and status != 'DEL'\n"
                            + "and i.type = 'SR'\n"
                            + "and to_date(i.in_date,'MM/dd/yyyy') >= sysdate -365\n"
                            + "order by i.fecha_creacion desc";
                    break;
                case 2:
                    sql = "select * from fb_invoice i where id_cia=? and i.status != 'DEL'\n"
                            + "and i.type = 'IN' and i.status = 'OV'\n"
                            + "and to_date(i.in_date,'MM/dd/yyyy') >= sysdate -365\n"
                            + "order by i.fecha_creacion desc";
                    break;
                case 3:
                    sql = "select * from fb_invoice i where id_cia=? and i.status != 'DEL'\n"
                            + "and i.type = 'IN' and i.status in ('OP','PA')\n"
                            + "and to_date(i.in_date,'MM/dd/yyyy') >= sysdate -365\n"
                            + "order by i.fecha_creacion desc";
                    break;
                case 4:
                    sql = "select * from fb_invoice i where id_cia=? and i.status != 'DEL'\n"
                            + "and i.type = 'IN' and i.status in ('PD','PA')\n"
                            + "and to_date(i.in_date,'MM/dd/yyyy') >= sysdate -30\n"
                            + "order by i.fecha_creacion desc";
                    break;
                default:

                    break;
            }

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

    public FbInvoice getInvoiceByIdInvoice(String idInvoice, String idCia) {
        List<FbInvoice> list = new ArrayList<>();
        try {

            String sql = "select * from fb_invoice where id_invoice=? and status != 'DEL' and id_cia =?";
            Query q = em.createNativeQuery(sql, FbInvoice.class);
            q.setParameter(1, idInvoice);
            q.setParameter(2, idCia);
            list = q.getResultList();
            for (FbInvoice fbInvoice : list) {
                em.refresh(fbInvoice);
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }

        return list.isEmpty() ? null : list.get(0);
    }

    public List<FbInvoice> getInvoicesByIdCust(String idCust, String idCia) {
        List<FbInvoice> list = new ArrayList<>();
        try {

            String sql = "select * from fb_invoice i where id_cust=? and id_cia = ? and status != 'DEL' order by i.fecha_creacion desc";
            Query q = em.createNativeQuery(sql, FbInvoice.class);
            q.setParameter(1, idCust);
            q.setParameter(2, idCia);
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
        String tempIn = "";
        String tempDue = "";
        String tempSh = "";
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
                tempIn = in.getInDate();
            }

            cs.setString(7, tempIn);
            if (in.getDueDate() != null) {
                tempDue = in.getDueDate();
            }

            cs.setString(8, tempDue);
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
                tempSh = in.getShDate();
            }

            cs.setString(19, tempSh);
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

    public String generateInvoice(FbInvoice i, String logo, JasperReport report, String type, String balance) {
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
                + File.separator + i.getType() + i.getNoDot() + ".pdf";

        String pdfName = File.separator + i.getType() + i.getNoDot() + ".pdf";
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

            res = "/pdf/" + "cia" + i.getIdCia().getIdCia().toString()
                    + "/" + i.getType() + i.getNoDot() + ".pdf";
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbInvoiceFacade.generateInvoice()");
            e.printStackTrace();
            res = e.toString() + " ::: " + e.getMessage();
        }

        return res;
    }

    public String generatePayment(FbInvoice i, String logo, JasperReport report, String amountCredited, String total) {
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
                + File.separator + "Payment" + i.getIdInvoice().toString() + ".pdf";

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

            res = "/pdf/" + "cia" + i.getIdCia().getIdCia().toString()
                    + "/Payment" + i.getIdInvoice().toString() + ".pdf";
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

    public PanelesVentas getPanelesInfo(String idCia) {
        PanelesVentas pv = null;
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            String sql = "select \n"
                    + "(select count(*) from fb_invoice nes where nes.id_cia = ? and nes.status !='DEL' and nes.type = 'ES' and TO_DATE(nes.in_date,'MM/dd/yyyy') >= sysdate -365) \"NO_ESTIMATE\" ,\n"
                    + "(select nvl(sum(tes.TOTAL),0)s from fb_invoice tes where tes.id_cia = ? and tes.status !='DEL' and tes.type = 'ES' and TO_DATE(tes.in_date,'MM/dd/yyyy') >= sysdate -365) \"TOTAL_ESTIMATE\" ,\n"
                    + "(select count(*) from fb_invoice nsr where nsr.id_cia = ? and nsr.status !='DEL' and nsr.type = 'SR' and TO_DATE(nsr.in_date,'MM/dd/yyyy') >= sysdate -365) \"NO_UNBILLED\",\n"
                    + "(select nvl(sum(tsr.TOTAL),0) from fb_invoice tsr where tsr.id_cia = ? and tsr.status !='DEL' and tsr.type = 'SR' and TO_DATE(tsr.in_date,'MM/dd/yyyy') >= sysdate -365) \"TOTAL_UNBILLED\" ,\n"
                    + "(select count(*) from fb_invoice nov where nov.id_cia = ? and nov.status !='DEL' and nov.type = 'IN' and TO_DATE(nov.in_date,'MM/dd/yyyy') >= sysdate -365 and nov.status = 'OV') \"NO_OVERDUE\",\n"
                    + "(select nvl(sum(tov.ACTUAL_BALANCE),0) from fb_invoice tov where tov.id_cia = ? and tov.status !='DEL' and tov.type = 'IN' and TO_DATE(tov.in_date,'MM/dd/yyyy') >= sysdate -365 and tov.status = 'OV') \"TOTAL_OVERDUE\" ,\n"
                    + "(select count(*) from fb_invoice nop where nop.id_cia = ? and nop.status !='DEL' and nop.type = 'IN' and TO_DATE(nop.in_date,'MM/dd/yyyy') >= sysdate -365 and nop.status in ('OP','PA')) \"NO_OPEN\",\n"
                    + "(select nvl(sum(top.ACTUAL_BALANCE),0) from fb_invoice top where top.id_cia = ? and top.status !='DEL' and top.type = 'IN' and TO_DATE(top.in_date,'MM/dd/yyyy') >= sysdate -365 and top.status in ('OP','PA')) \"TOTAL_OPEN\",\n"
                    + "(select count(*) from fb_invoice npa where npa.id_cia = ? and npa.status !='DEL' and npa.type = 'IN' and TO_DATE(npa.in_date,'MM/dd/yyyy') >= sysdate -30 and npa.status in ('PD','PA')) \"NO_PAID\",\n"
                    + "(select nvl(sum(tpa.TOTAL  - tpa.ACTUAL_BALANCE),0) from fb_invoice tpa where tpa.id_cia = ? and tpa.status !='DEL' and tpa.type = 'IN' and TO_DATE(tpa.in_date,'MM/dd/yyyy') >= sysdate -30 and tpa.status in ('PD','PA')) \"TOTAL_PAID\" \n"
                    + "from dual";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, idCia);
            ps.setString(2, idCia);
            ps.setString(3, idCia);
            ps.setString(4, idCia);
            ps.setString(5, idCia);
            ps.setString(6, idCia);
            ps.setString(7, idCia);
            ps.setString(8, idCia);
            ps.setString(9, idCia);
            ps.setString(10, idCia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pv = new PanelesVentas();
                pv.setNoEstimates(rs.getInt("NO_ESTIMATE"));
                pv.setTotalEstimates(new BigDecimal(rs.getDouble("TOTAL_ESTIMATE")).setScale(2, BigDecimal.ROUND_HALF_UP));
                pv.setNoUnbilled(rs.getInt("NO_UNBILLED"));
                pv.setTotalUnbilled(new BigDecimal(rs.getDouble("TOTAL_UNBILLED")).setScale(2, BigDecimal.ROUND_HALF_UP));
                pv.setNoOverdue(rs.getInt("NO_OVERDUE"));
                pv.setTotalOverdue(new BigDecimal(rs.getDouble("TOTAL_OVERDUE")).setScale(2, BigDecimal.ROUND_HALF_UP));
                pv.setNoOpen(rs.getInt("NO_OPEN"));
                pv.setTotalOpen(new BigDecimal(rs.getDouble("TOTAL_OPEN")).setScale(2, BigDecimal.ROUND_HALF_UP));
                pv.setNoPaid(rs.getInt("NO_PAID"));
                pv.setTotalPaid(new BigDecimal(rs.getDouble("TOTAL_PAID")).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbInvoiceFacade.getPanelesInfo()");
            e.printStackTrace();
        }
        return pv;
    }

    public List<FbInvoice> getTransactionsForStmt(String idCia, String idCust, String sDate, String eDate, String op) {
        List<FbInvoice> tranList = new ArrayList<>();
        String sql = "";
        try {
            switch (op) {
                case "BF":
                    sql = "select *\n"
                            + "from fb_invoice i\n"
                            + "where i.id_cia = ?\n"
                            + "and i.id_cust = ?\n"
                            + "and i.type in ('IN','PA','SR') and i.status != 'DEL'  \n"
                            + "and to_date(i.in_date,'MM/dd/yyyy')\n"
                            + "between to_date('" + sDate + "','MM/dd/yyyy')\n"
                            + "and to_date('" + eDate + "','MM/dd/yyyy')\n"
                            + "order by i.fecha_creacion asc";
                    break;
                case "OI":
                    sql = "select *\n"
                            + "from fb_invoice i\n"
                            + "where i.id_cia = ?\n"
                            + "and i.id_cust = ?\n"
                            + "and i.type = 'IN'\n"
                            + "and i.status in ('OP','PA') and i.status != 'DEL'  \n"
                            + "order by i.fecha_creacion asc";
                    break;

                case "TS":
                    sql = "select *\n"
                            + "from fb_invoice i\n"
                            + "where i.id_cia = ?\n"
                            + "and i.id_cust = ?\n"
                            + "and i.type in ('IN','SR')\n"
                            + "and to_date(i.in_date,'MM/dd/yyyy')  \n"
                            + "between to_date('" + sDate + "','MM/dd/yyyy')\n"
                            + "and to_date('" + eDate + "','MM/dd/yyyy')  and i.status != 'DEL' \n"
                            + "order by i.fecha_creacion asc";

                    break;
            }

            Query q = em.createNativeQuery(sql, FbInvoice.class);
            q.setParameter(1, idCia);
            q.setParameter(2, idCust);
            tranList = q.getResultList();

        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbInvoiceFacade.getTransactionsForStmt()");
            e.printStackTrace();
        }

        return tranList;
    }

    public String getPreBalance(String idCia, String idCust, String pDate) {
        String res = "0";
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            String sql = "SELECT PROCS_FASTBOOKS.FN_CALC_PREDATE_BALANCE(" + idCia + "," + idCust + ",'" + pDate + "') pre_balance from dual";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                res = rs.getString("pre_balance");
            }
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbInvoiceFacade.getPreBalance()");
            e.printStackTrace();
        }

        return res;
    }

    public String actStatement(FbStatement stmt, String op) {
        String res = "-2";
        String pTranDates = "";
        String pDescrips = "";
        String pAmounts = "";
        String pBalances = "";
        String pIdTrans = "";
        String idInserted = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        try {

            for (FbStmtDetail d : stmt.getFbStmtDetailList()) {
                pTranDates += sdf.format(d.getTranDate()) + ",";
                pDescrips += d.getDescripcion() + ",";
                if (d.getAmount() != null) {
                    pAmounts += String.format("%.2f", d.getAmount()) + ";";
                } else {
                    pAmounts += String.format("%.2f", BigDecimal.ZERO) + ";";
                }

                pBalances += String.format("%.2f", d.getBalance()) + ";";
                if (d.getIdTran() != null) {
                    pIdTrans += d.getIdTran().getIdInvoice().toString() + ",";
                } else {
                    pIdTrans += "0,";
                }

            }

            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs = cn.prepareCall("{call FASTBOOKS.PROCS_FASTBOOKS.PR_ACT_STATEMENT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

            /*
            (pIdCia IN INT, pIdStatement IN INT, pIdCust IN INT, pType IN VARCHAR2, pStmtDate IN VARCHAR2, pStartDate IN VARCHAR2,
                                pEndDate IN VARCHAR2, pTotalAmount IN DECIMAL, pTotalBalance IN DECIMAL, pTranDates IN VARCHAR2, pDescrips IN VARCHAR2,
                                    pAmounts IN VARCHAR2, pBalances IN VARCHAR2, pIdTrans IN VARCHAR2, op IN VARCHAR2, res OUT VARCHAR2, idInserted OUT VARCHAR2)
            
             */
            cs.setInt(1, Integer.parseInt(stmt.getIdCia().getIdCia().toString()));
            cs.setInt(3, Integer.parseInt(stmt.getIdCust().getIdCust().toString()));
            cs.setInt(2, Integer.parseInt(stmt.getIdStmt().toString()));
            cs.setString(4, stmt.getType());
            cs.setString(5, stmt.getStmtDate());
            cs.setString(6, stmt.getStartDate());
            cs.setString(7, stmt.getEndDate());
            cs.setDouble(8, Double.parseDouble(stmt.getTotalAmount().toString()));
            cs.setDouble(9, Double.parseDouble(stmt.getTotalBalance().toString()));
            cs.setString(10, pTranDates);
            cs.setString(11, pDescrips);
            cs.setString(12, pAmounts);
            cs.setString(13, pBalances);
            cs.setString(14, pIdTrans);
            cs.setString(15, op);
            cs.registerOutParameter(16, Types.VARCHAR);
            cs.registerOutParameter(17, Types.VARCHAR);
            cs.execute();
            res = cs.getString(16);
            idInserted = cs.getString(17);
            cs.close();
            System.out.println("res: " + res);
        } catch (NumberFormatException | SQLException e) {
            res = "-2";
            System.out.println("com.fastbooks.facade.FbInvoiceFacade.actStatement()");
            e.printStackTrace();
        }

        return res.equals("0") ? idInserted : "-2";
    }

    public String generateStmt(FbStatement st, String logo, JasperReport report, String balance) {
        String res = "";
        //em.getEntityManagerFactory().getCache().evictAll();
        Connection cn = em.unwrap(java.sql.Connection.class);
        String dir = "view" + File.separator + "jasper" + File.separator + "statement.jrxml";
        GlobalParameters gp = new GlobalParameters();
        File file = new File(gp.getAppPath() + File.separator + "pdf" + File.separator + "cia" + st.getIdCia().getIdCia().toString()
                + File.separator);
        /*String[] entries = file.list();
        for (String s : entries) {
            File currentFile = new File(file.getPath(),s);
            currentFile.delete();
        }*/
        file.mkdirs();
        String fileName = st.getType() + st.getIdStmt().toString()+ this.randomAlphaNumeric(10)+ ".pdf";
        String destino = gp.getAppPath() + File.separator + "pdf" + File.separator + "cia" + st.getIdCia().getIdCia().toString()
                + File.separator + fileName;

        //String pdfName = File.separator + i.getType() + i.getNoDot() + ".pdf";
        Map parametersMap = new HashMap();
        parametersMap.put("ID_STMT", st.getIdStmt().toString());
        File logoFile = new File(gp.getAppPath() + logo);
        if (!logoFile.exists()) {
            logoFile = null;
        }
        parametersMap.put("LOGO", logoFile);

        parametersMap.put("BALANCE", balance);

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

            res = "/pdf/" + "cia" + st.getIdCia().getIdCia().toString()
                    + "/" + fileName;
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbInvoiceFacade.generateInvoice()");
            e.printStackTrace();
            res = e.toString() + " ::: " + e.getMessage();
        }

        return res;
    }
    
    
        public List<FbInvoice> applyFilter(String idCia, String type, String status, String sh, String from, String to, String idCust) {
        List<FbInvoice> list = new ArrayList<>();
        try {
        String query = "SELECT * FROM FB_INVOICE WHERE ID_CIA = " + idCia + " ";
        
        if (!type.equals("0")) {
            query += "AND TYPE = '" + type + "' ";
        }
        
        if (!status.equals("0")) {
            query += "AND STATUS = '" + status + "' ";
        }
        
        if (!sh.isEmpty()) {
            query += "AND SHIP_VIA = '" + sh + "' ";
        }

        
        
            query += " AND to_date(IN_DATE,'MM/dd/yyyy') BETWEEN to_date('"+from+"','MM/dd/yyyy') AND to_date('"+to+"','MM/dd/yyyy') ";
        
        
        
        if (!idCust.equals("0")) {
            query += " AND ID_CUST =  " + idCust;
        }
        query += " and status != 'DEL' order by fecha_creacion desc";
        
        //System.out.println("com.fastbooks.managedbeans.InvoiceController.applyFilter()");
         Query q = em.createNativeQuery(query, FbInvoice.class);
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
        
        public List<FbStatement> getStmtFilter(String idCia,  String from, String to, String idCust){
        List<FbStatement> list = new ArrayList<>();
            try {
                String sql = "SELECT * FROM FB_STATEMENT WHERE ID_CIA = " + idCia + " AND to_date(STMT_DATE,'MM/dd/yyyy') BETWEEN to_date('"+from+"','MM/dd/yyyy') AND to_date('"+to+"','MM/dd/yyyy') ";
                if (!idCust.equals("0")) {
                    sql += " AND ID_CUST = " + idCust + " ";
                }
                sql += " ORDER BY FECHA_CREACION DESC";
                Query q = em.createNativeQuery(sql, FbStatement.class);
                list = q.getResultList();
            } catch (Exception e) {
                System.out.println("com.fastbooks.facade.FbInvoiceFacade.getStmtFilter()");
                e.printStackTrace();
            }
        
        
        
        return list;
        }
    
    
    

    /* 
    Generate random names
     */
    private final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public  String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

}
