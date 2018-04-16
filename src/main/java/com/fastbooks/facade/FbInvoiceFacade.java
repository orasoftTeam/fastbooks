/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.facade;

import com.fastbooks.modelo.FbInvoice;
import com.fastbooks.modelo.FbInvoiceDetail;
import com.fastbooks.modelo.FbInvoiceTaxes;
import com.fastbooks.util.GlobalParameters;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.text.SimpleDateFormat;
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
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 *
 * @author DELL
 */
@Stateless
public class FbInvoiceFacade extends AbstractFacade<FbInvoice>{
    @PersistenceContext(unitName = "com_Fastbooks_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FbInvoiceFacade() {
        super(FbInvoice.class);
    }
    
    public List<FbInvoice> getInvoicesByIdCia(String idCia){
    List<FbInvoice> list = new ArrayList<>();
        try {
            String sql = "select * from fb_invoice where id_cia = ?'";
            Query q = em.createNativeQuery("select * from fb_invoice where id_cia="+idCia, FbInvoice.class);
            //q.setParameter(1, idCia);
            list = q.getResultList();
            
        } catch (Exception e) {
            System.out.println("com.fastbooks.facade.FbProductFacade.getProductsByIdCia()");
            e.printStackTrace();
        }
    
    return list;
    }
    /*
     PROCEDURE PR_ACT_INVOICE
 (pIdCia IN INT, pIdInvoice IN INT, pIdCust IN INT, pType IN VARCHAR2,pNo IN VARCHAR2, pEmail IN VARCHAR2,
    pInDate IN VARCHAR2,pDueDate IN VARCHAR2,pActBalance IN DECIMAL,pSubTotal IN DECIMAL,pTaxTotal IN DECIMAL,
     pTotal IN DECIMAL,pStatus IN VARCHAR2,pBiAdd IN VARCHAR2,pShAdd IN VARCHAR2,pTerms IN VARCHAR2,pTrackNum IN VARCHAR2,
      pShipVia IN VARCHAR2,pShDate IN VARCHAR2,pShCost IN DECIMAL,pMessageInvoice IN VARCHAR2,pAttachment IN VARCHAR2,
      pProdIds IN VARCHAR2,pQuants IN VARCHAR2,pIdTaxes IN VARCHAR2,pFromAmounts IN VARCHAR2,pTaxAmounts IN VARCHAR2,
        pTaxProdIds IN VARCHAR2, op IN VARCHAR2, res OUT VARCHAR2); 
    */
    
     public String actInvoice(FbInvoice in,String op){
    String res = "";
    String pProdsIds = "";
    String pQuants = "";
    String pIdTaxes = "";
    String pFromAmounts = "";
    String pTaxAmounts = "";
    String pTaxProdsIds = "";
    String temp = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Connection cn = em.unwrap(java.sql.Connection.class);
            CallableStatement cs = cn.prepareCall("{call HOLOGRAM.PROCS_FASTBOOKS.PR_ACT_INVOICE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, Integer.parseInt(in.getIdCia().getIdCia().toString()));
            cs.setInt(2, Integer.parseInt(in.getIdInvoice().toString()));
            cs.setInt(3, Integer.parseInt(in.getIdCust().getIdCust().toString()));
            cs.setString(4, in.getType());
            cs.setString(5, in.getNoDot());
            cs.setString(6, in.getCustEmail());
            if (in.getInDate() != null) {
               temp = sdf.format(in.getInDate()); 
            }
            
            cs.setString(7, temp );
            if (in.getDueDate() != null) {
             temp = sdf.format(in.getDueDate());   
            }
            
            cs.setString(8, temp );
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
              temp = sdf.format(in.getShDate());  
            }
            
            cs.setString(19, temp);
            cs.setDouble(20, Double.parseDouble(in.getShCost().toString()));
            cs.setString(21, in.getMessageInvoice());
            cs.setString(22, in.getAttachment());
            
            for (FbInvoiceDetail fbInvoiceDetail : in.getFbInvoiceDetailList()) {
                pProdsIds += fbInvoiceDetail.getIdProd().getIdProd().toString() + ",";
                pQuants += fbInvoiceDetail.getItemQuant().toString()+",";
            }
            
            for (FbInvoiceTaxes fbInvoiceDetail : in.getFbInvoiceTaxesList()) {
                pIdTaxes = fbInvoiceDetail.getIdTax().getIdTax().toString() + ",";
                pFromAmounts = fbInvoiceDetail.getFromAmount().toString()+ ",";
                pTaxAmounts = fbInvoiceDetail.getTaxAmount().toString()+ ",";
                pTaxProdsIds = fbInvoiceDetail.getIdProds()+ ",";
                
            }
            
            cs.setString(23, pProdsIds);
            cs.setString(24, pQuants);
            cs.setString(25, pIdTaxes);
            cs.setString(26, pFromAmounts);
            cs.setString(27, pTaxAmounts);
            cs.setString(28, pTaxProdsIds);
            cs.setString(29, op);
            cs.registerOutParameter(30, Types.VARCHAR);
            
            cs.execute();
            res = cs.getString(30);
            cs.close();
           
        } catch (Exception e) {
            res = e.toString();//"-2";
            e.printStackTrace();
        }
        System.out.println("Facade Resultado de operacion: " + res);
        return res;
    }
     
     
     public void generateInvoice(FbInvoice i){
            Connection cn = em.unwrap(java.sql.Connection.class);
            String dir = "view"+File.separator+"jasper"+File.separator+"report1.jrxml";
            GlobalParameters gp = new GlobalParameters();
            File file = new File(gp.getAppPath()+ File.separator + "pdf"+File.separator+"cia" + i.getIdCia().getIdCia().toString()+
                    File.separator);
            file.mkdirs();
            String destino= gp.getAppPath()+ File.separator + "pdf"+File.separator+"cia" + i.getIdCia().getIdCia().toString()+
                    File.separator + "IN"+i.getNoDot()+i.getIdCia().getNomCom()+".pdf";
            
            String pdfName = File.separator + "IN"+i.getNoDot()+i.getIdCia().getNomCom()+".pdf";
             Map parametersMap = new HashMap();
            parametersMap.put("idInvoice",i.getIdInvoice().toString() );
            parametersMap.put("logo",new File(gp.getAppPath() + File.separator + "logo" + File.separator + "6Ra1xoZ7457138.png"));
            try {
             ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                String realPath = ec.getRealPath("/");
                System.out.println(realPath + dir);
                JasperReport report = JasperCompileManager.compileReport(realPath + dir);
                
                
                JasperPrint print = JasperFillManager.fillReport(report,parametersMap, cn);
                JRExporter exporter = new JRPdfExporter();
                
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destino);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.exportReport();
                System.out.println("File Created: " + destino);
         } catch (Exception e) {
                System.out.println("com.fastbooks.facade.FbInvoiceFacade.generateInvoice()");
                e.printStackTrace();
         }
     }
    
}
