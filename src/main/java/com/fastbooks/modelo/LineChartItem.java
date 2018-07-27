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
public class LineChartItem {

    private @Getter
    @Setter
    String label;
    private @Getter
    @Setter
    Double value;

    public LineChartItem(String label, Double value) {
        this.label = label;
        this.value = value;
    }

    public LineChartItem() {
    }

}
