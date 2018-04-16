/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.util;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author dell
 */
public class GlobalParameters {
    private @Getter @Setter String appPath = /*"/u01/app/oracle/fastbooks"  "C:\\Users\\luis\\Pictures\\fastbooks"*/System.getProperty("user.dir") ; 
    
    
}
