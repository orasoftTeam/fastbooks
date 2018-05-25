/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DELL
 */
@Named(value = "payController")
@ViewScoped
public class PayController implements Serializable{

    private @Getter @Setter String title;
    
    
    /**
     * Creates a new instance of PayController
     */
    public PayController() {
    }
    
    @PostConstruct
    public void init(){
        this.title = "Payment";
        System.out.println("com.fastbooks.managedbeans.PayController.init()");
    }
    
}
