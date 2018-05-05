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
public class PaymentMethod {
    private @Getter @Setter String id;
    private @Getter @Setter String display;

    public PaymentMethod() {
    }

    public PaymentMethod(String id, String display) {
        this.id = id;
        this.display = display;
    }
    
    
}
