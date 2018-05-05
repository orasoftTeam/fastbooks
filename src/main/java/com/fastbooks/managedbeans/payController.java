/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DELL
 */
@Named(value = "payController")
@ViewScoped
public class payController implements Serializable{

    private @Getter
    @Setter
    String idCust = "0";
    
    public payController() {
    }
    
    
    public void test(){
    idCust = "1";
    }
    
}
