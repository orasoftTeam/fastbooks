/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DELL
 */
public class PanelesVentas {
    private @Getter
    @Setter
    int noEstimates = 0;
    private @Getter
    @Setter
    int noUnbilled = 0;
    private @Getter
    @Setter
    int noOverdue = 0;
    private @Getter
    @Setter
    int noOpen = 0;
    private @Getter
    @Setter
    int noPaid = 0;
    
    private @Getter
    @Setter
    int panelFlag = 0;

    private @Getter
    @Setter
    BigDecimal totalEstimates = new BigDecimal(BigInteger.ZERO);
    private @Getter
    @Setter
    BigDecimal totalUnbilled = new BigDecimal(BigInteger.ZERO);
    private @Getter
    @Setter
    BigDecimal totalOverdue = new BigDecimal(BigInteger.ZERO);
    private @Getter
    @Setter
    BigDecimal totalOpen = new BigDecimal(BigInteger.ZERO);
    private @Getter
    @Setter
    BigDecimal totalPaid = new BigDecimal(BigInteger.ZERO);

    public PanelesVentas() {
    }
    
    
}
