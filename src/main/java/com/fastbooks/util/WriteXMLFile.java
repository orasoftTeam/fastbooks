/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.util;
import com.fastbooks.modelo.FbInvoice;
import com.fastbooks.modelo.FbInvoiceDetail;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.eclipse.persistence.internal.oxm.schema.model.Element;
import org.w3c.dom.Document;
/**
 *
 * @author DELL
 */
public class WriteXMLFile {
    
    GlobalParameters gb = new GlobalParameters();
    
    public void crearXML(List<FbInvoice> list) {
        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            org.w3c.dom.Element root = doc.createElement("FbInvoices");
            doc.appendChild(root);
            

            for (FbInvoice in : list) {
                org.w3c.dom.Element invoice = doc.createElement("FbInvoice");
                root.appendChild(invoice);
                
                org.w3c.dom.Element idInvoice = doc.createElement("idInvoice");
                idInvoice.appendChild(doc.createTextNode(String.valueOf(in.getIdInvoice())));
                invoice.appendChild(idInvoice);
                
                org.w3c.dom.Element company = doc.createElement("FbCompany");
                
                org.w3c.dom.Element companyId = doc.createElement("idCia");
                companyId.appendChild(doc.createTextNode(String.valueOf(in.getIdCia().getIdCia().toString())));
                company.appendChild(companyId);
                
                org.w3c.dom.Element companyName = doc.createElement("nomCom");
                companyName.appendChild(doc.createTextNode(String.valueOf(in.getIdCia().getNomCom())));
                company.appendChild(companyName);
                
                org.w3c.dom.Element companyLegalName = doc.createElement("nomLeg");
                companyLegalName.appendChild(doc.createTextNode(String.valueOf(in.getIdCia().getNomLeg())));
                company.appendChild(companyLegalName);
                
                org.w3c.dom.Element giro = doc.createElement("giro");
                giro.appendChild(doc.createTextNode(String.valueOf(in.getIdCia().getGiro())));
                company.appendChild(giro);
                
                org.w3c.dom.Element logo = doc.createElement("logo");
                logo.appendChild(doc.createTextNode(String.valueOf(in.getIdCia().getLogo())));
                company.appendChild(logo);
                
                org.w3c.dom.Element email = doc.createElement("ComEmail");
                email.appendChild(doc.createTextNode(String.valueOf(in.getIdCia().getEmail())));
                company.appendChild(email);
                
                org.w3c.dom.Element website = doc.createElement("comWebsite");
                website.appendChild(doc.createTextNode(String.valueOf(in.getIdCia().getWebsite())));
                company.appendChild(website);
                
                org.w3c.dom.Element telefono = doc.createElement("comTel");
                telefono.appendChild(doc.createTextNode(String.valueOf(in.getIdCia().getTelefono())));
                company.appendChild(telefono);
                
                invoice.appendChild(company);
                
                org.w3c.dom.Element customer = doc.createElement("FbCustomer");
                
                org.w3c.dom.Element idCust = doc.createElement("idCust");
                idCust.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getIdCust())));
                customer.appendChild(idCust);
                
                org.w3c.dom.Element title = doc.createElement("title");
                title.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getTitle())));
                customer.appendChild(title);
                
                org.w3c.dom.Element firstName = doc.createElement("firstName");
                firstName.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getFirstname())));
                customer.appendChild(firstName);
                
                org.w3c.dom.Element middleName = doc.createElement("middleName");
                middleName.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getMiddlename())));
                customer.appendChild(middleName);
                
                org.w3c.dom.Element lastName = doc.createElement("lastName");
                lastName.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getLastname())));
                customer.appendChild(lastName);
                
                org.w3c.dom.Element suffix = doc.createElement("suffix");
                suffix.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getSuffixx())));
                customer.appendChild(suffix);
                
                org.w3c.dom.Element custEmail = doc.createElement("custEmail");
                custEmail.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getEmail())));
                customer.appendChild(custEmail);
                
                org.w3c.dom.Element custCompany = doc.createElement("custCompany");
                custCompany.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getCompany())));
                customer.appendChild(custCompany);
                
                org.w3c.dom.Element custPhone = doc.createElement("custPhone");
                custPhone.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getPhone())));
                customer.appendChild(custPhone);
                
                org.w3c.dom.Element custMobile = doc.createElement("custMobile");
                custMobile.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getMobile())));
                customer.appendChild(custMobile);
                
                org.w3c.dom.Element custFax = doc.createElement("custFax");
                custFax.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getFax())));
                customer.appendChild(custFax);
                
                org.w3c.dom.Element displayName = doc.createElement("displayName");
                displayName.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getDisplayName())));
                customer.appendChild(displayName);
                
                org.w3c.dom.Element custWebsite = doc.createElement("custWebsite");
                custWebsite.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getWebside())));
                customer.appendChild(custWebsite);
                
                org.w3c.dom.Element custNote = doc.createElement("custNote");
                custNote.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getNote())));
                customer.appendChild(custNote);
                
                org.w3c.dom.Element custAttach = doc.createElement("custAttach");
                custAttach.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getAtachment())));
                customer.appendChild(custAttach);
                
                org.w3c.dom.Element custStatus = doc.createElement("custStatus");
                custStatus.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getStatus())));
                customer.appendChild(custStatus);
                
                org.w3c.dom.Element custStreet= doc.createElement("custStreet");
                custStreet.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getStreet())));
                customer.appendChild(custStreet);
                
                org.w3c.dom.Element custCity= doc.createElement("custCity");
                custCity.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getCity())));
                customer.appendChild(custCity);
                
                org.w3c.dom.Element custEstate= doc.createElement("custEstate");
                custEstate.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getEstate())));
                customer.appendChild(custEstate);
                
                org.w3c.dom.Element custPostal= doc.createElement("custPostal");
                custPostal.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getPostalCode())));
                customer.appendChild(custPostal);
                
                org.w3c.dom.Element custCountry= doc.createElement("custCountry");
                custCountry.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getCountry())));
                customer.appendChild(custCountry);
                
                org.w3c.dom.Element custStreetS= doc.createElement("custStreetS");
                custStreetS.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getStreetS())));
                customer.appendChild(custStreetS);
                
                org.w3c.dom.Element custCityS= doc.createElement("custCityS");
                custCityS.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getCityS())));
                customer.appendChild(custCityS);
                
                org.w3c.dom.Element custEstateS= doc.createElement("custEstateS");
                custEstateS.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getEstateS())));
                customer.appendChild(custEstateS);
                
                org.w3c.dom.Element custPostalS= doc.createElement("custPostalS");
                custPostalS.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getPostalCodeS())));
                customer.appendChild(custPostalS);
                
                org.w3c.dom.Element custCountryS= doc.createElement("custCountryS");
                custCountryS.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getCountryS())));
                customer.appendChild(custCountryS);
                
                org.w3c.dom.Element custBalance = doc.createElement("custBalance");
                custBalance.appendChild(doc.createTextNode(String.valueOf(in.getIdCust().getBalance())));
                customer.appendChild(custBalance);
                
                invoice.appendChild(customer);
                
                
                org.w3c.dom.Element type = doc.createElement("type");
                type.appendChild(doc.createTextNode(String.valueOf(in.getType())));
                invoice.appendChild(type);
                
                org.w3c.dom.Element noDot = doc.createElement("noDot");
                noDot.appendChild(doc.createTextNode(String.valueOf(in.getNoDot())));
                invoice.appendChild(noDot);
                
                org.w3c.dom.Element invoiceCustEmail = doc.createElement("invoiceCustEmail");
                invoiceCustEmail.appendChild(doc.createTextNode(String.valueOf(in.getCustEmail())));
                invoice.appendChild(invoiceCustEmail);
                
                org.w3c.dom.Element actualBalance = doc.createElement("actualBalance");
                actualBalance.appendChild(doc.createTextNode(String.valueOf(in.getActualBalance())));
                invoice.appendChild(actualBalance);
                
                org.w3c.dom.Element subTotal = doc.createElement("subTotal");
                subTotal.appendChild(doc.createTextNode(String.valueOf(in.getSubTotal())));
                invoice.appendChild(subTotal);
                
                org.w3c.dom.Element taxTotal = doc.createElement("taxTotal");
                taxTotal.appendChild(doc.createTextNode(String.valueOf(in.getTaxTotal())));
                invoice.appendChild(taxTotal);
                
                org.w3c.dom.Element total = doc.createElement("total");
                total.appendChild(doc.createTextNode(String.valueOf(in.getTotal())));
                invoice.appendChild(total);
                
                org.w3c.dom.Element status = doc.createElement("status");
                status.appendChild(doc.createTextNode(String.valueOf(in.getStatus())));
                invoice.appendChild(status);
                
                org.w3c.dom.Element biAddress = doc.createElement("biAddress");
                biAddress.appendChild(doc.createTextNode(String.valueOf(in.getBiAddress())));
                invoice.appendChild(biAddress);
                
                org.w3c.dom.Element shAddress = doc.createElement("shAddress");
                shAddress.appendChild(doc.createTextNode(String.valueOf(in.getShAddress())));
                invoice.appendChild(shAddress);
                
                
                org.w3c.dom.Element terms = doc.createElement("terms");
                terms.appendChild(doc.createTextNode(String.valueOf(in.getShAddress())));
                invoice.appendChild(terms);
                
                org.w3c.dom.Element trackNum = doc.createElement("trackNum");
                trackNum.appendChild(doc.createTextNode(String.valueOf(in.getTrackNum())));
                invoice.appendChild(trackNum);
                
                org.w3c.dom.Element shipVia = doc.createElement("shipVia");
                shipVia.appendChild(doc.createTextNode(String.valueOf(in.getShipVia())));
                invoice.appendChild(shipVia);
                
                org.w3c.dom.Element shCost = doc.createElement("shCost");
                shCost.appendChild(doc.createTextNode(String.valueOf(in.getShCost())));
                invoice.appendChild(shCost);
                
                org.w3c.dom.Element inDate = doc.createElement("inDate");
                inDate.appendChild(doc.createTextNode(String.valueOf(in.getInDate())));
                invoice.appendChild(inDate);
                
                org.w3c.dom.Element dueDate = doc.createElement("dueDate");
                dueDate .appendChild(doc.createTextNode(String.valueOf(in.getDueDate() )));
                invoice.appendChild(dueDate );
                
                org.w3c.dom.Element shDate = doc.createElement("shDate");
                shDate .appendChild(doc.createTextNode(String.valueOf(in.getShDate() )));
                invoice.appendChild(shDate );
                
                org.w3c.dom.Element shTaxAmount = doc.createElement("shTaxAmount");
                shTaxAmount .appendChild(doc.createTextNode(String.valueOf(in.getShcostTaxAmount() )));
                invoice.appendChild(shTaxAmount );
                
                org.w3c.dom.Element shTaxName= doc.createElement("shTaxName");
                shTaxName.appendChild(doc.createTextNode(String.valueOf(in.getShcostTaxName() )));
                invoice.appendChild(shTaxName );
                
                org.w3c.dom.Element esAccBy = doc.createElement("esAccBy");
                esAccBy.appendChild(doc.createTextNode(String.valueOf(in.getEsAccby() )));
                invoice.appendChild(esAccBy);
                
                org.w3c.dom.Element esAccDate = doc.createElement("esAccDate");
                esAccDate.appendChild(doc.createTextNode(String.valueOf(in.getEsAccdate() )));
                invoice.appendChild(esAccDate );
                
                
                org.w3c.dom.Element fbDetails = doc.createElement("FbInvoiceDetails");
                
                for (FbInvoiceDetail det : in.getFbInvoiceDetailList()) {
                    org.w3c.dom.Element fbDetail = doc.createElement("FbInvoiceDetail");
                    
                    org.w3c.dom.Element idIn= doc.createElement("idIn");
                    idIn.appendChild(doc.createTextNode(String.valueOf(det.getIdInvoice() )));
                    fbDetail.appendChild(idIn);
                    
                    
                    org.w3c.dom.Element idProd = doc.createElement("idProd");
                    idProd.appendChild(doc.createTextNode(String.valueOf(det.getIdProd().getIdProd())));
                    fbDetail.appendChild(idProd);
                    
                    org.w3c.dom.Element prodName = doc.createElement("prodName");
                    prodName.appendChild(doc.createTextNode(String.valueOf(det.getItemName() )));
                    fbDetail.appendChild(prodName);
                    
                    org.w3c.dom.Element sku = doc.createElement("sku");
                    sku.appendChild(doc.createTextNode(String.valueOf(det.getItemSku() )));
                    fbDetail.appendChild(sku);
                    
                    org.w3c.dom.Element desc = doc.createElement("desc");
                    desc.appendChild(doc.createTextNode(String.valueOf(det.getItemDesc() )));
                    fbDetail.appendChild(desc);
                    
                    org.w3c.dom.Element quant = doc.createElement("quant");
                    quant.appendChild(doc.createTextNode(String.valueOf(det.getItemQuant() )));
                    fbDetail.appendChild(quant);
                    
                    org.w3c.dom.Element rate = doc.createElement("rate");
                    rate.appendChild(doc.createTextNode(String.valueOf(det.getItemRate() )));
                    fbDetail.appendChild(rate);
                    
                    fbDetails.appendChild(fbDetail);
                }
                
                
                
                
                invoice.appendChild(fbDetails);
                
               /* org.w3c.dom.Element nomdepto = doc.createElement("nomdepto");
                nomdepto.appendChild(doc.createTextNode(String.valueOf(dg.getNomdepto())));
                Details.appendChild(nomdepto);

                org.w3c.dom.Element nompartido = doc.createElement("nompartido");
                nompartido.appendChild(doc.createTextNode(String.valueOf(dg.getNompartido())));
                Details.appendChild(nompartido);*/
            }

            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();

            aTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            aTransformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
              
            DOMSource source = new DOMSource(doc);
            try {
                FileWriter file = new FileWriter(gb.getAppPath() + File.separator + "FbInvoices.xml");
                StreamResult result = new StreamResult(file);
                aTransformer.transform(source, result);
                
            } catch (Exception e) {
                System.out.println("com.votoseguro.util.WriteXMLFile.crearXML()");
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            System.out.println("com.votoseguro.util.WriteXMLFile.crearXML()");
            e.printStackTrace();
        }

    }
}
