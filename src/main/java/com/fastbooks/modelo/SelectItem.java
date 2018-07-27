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
public class SelectItem {
    private @Getter @Setter String id;
    private @Getter @Setter String label;
    private @Getter @Setter String value;
    private @Getter @Setter boolean bool;

    public SelectItem() {
    }

    public SelectItem(String id, String label, String value, boolean bool) {
        this.id = id;
        this.label = label;
        this.value = value;
        this.bool = bool;
    }
    
    public SelectItem(String label, String value, boolean bool) {
        this.id = id;
        this.label = label;
        this.value = value;
        this.bool = bool;
    }
    
    public SelectItem(String label,boolean bool) {
        this.id = id;
        this.label = label;
        this.value = value;
        this.bool = bool;
    }
    
}
