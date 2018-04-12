/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbGlCatalogoFacade;
import com.fastbooks.modelo.FbGlCatalogo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author dell
 */
@Named(value = "accountControler")
@ViewScoped
public class AccountControler implements Serializable{

    /**
     * Creates a new instance of AccountControler
     */
    private @Getter @Setter boolean active = false;
    private @Getter @Setter List<FbGlCatalogo> catList = new ArrayList<>();
    
    
    @EJB
    FbGlCatalogoFacade fbGlCatalogoFacade;
    
    @Inject
    UserData userData;
    
    public AccountControler() {
    }
    
    public String welcome(){
    
        return " ";
        
    }
    
    public void init(){
    catList = this.fbGlCatalogoFacade.getCatalogosByCia("1");
    //catList = this.fbGlCatalogoFacade.getCatalogosByCia(this.userData.getCurrentCia().getIdCia().toString());
    }
    
    
}
