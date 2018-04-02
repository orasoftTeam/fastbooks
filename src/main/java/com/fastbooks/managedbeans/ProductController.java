/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.util.ValidationBean;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author dell
 */
@Named(value = "productController")
@ViewScoped
public class ProductController implements Serializable{

    
    
    @EJB
    ValidationBean vb;
    /**
     * Creates a new instance of ProductController
     */
    public ProductController() {
    }
    
    public void goCat(){
    vb.redirecionar("/view/sales/categories.xhtml");
    }
    
    public void goProd(){
    vb.redirecionar("/view/sales/producsNServices.xhtml");
    }
    
}
