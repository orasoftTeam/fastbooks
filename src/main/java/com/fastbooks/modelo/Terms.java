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
public class Terms {
    private @Getter @Setter String id;
    private @Getter @Setter String days;
    private @Getter @Setter String display;

    public Terms(String id, String days, String display) {
        this.id = id;
        this.days = days;
        this.display = display;
    }

    public Terms() {
    }
    
}
