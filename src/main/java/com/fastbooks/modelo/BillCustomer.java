/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.modelo;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DELL
 */
public class BillCustomer {
    @Getter @Setter String id;
    @Getter @Setter String name;
    @Getter @Setter String display;

    public BillCustomer(String id, String name, String display) {
        this.id = id;
        this.name = name;
        this.display = display;
    }

    public BillCustomer() {
    }
    
}
