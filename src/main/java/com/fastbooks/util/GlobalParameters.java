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
    private @Getter @Setter String appPath = System.getProperty("user.dir");/*"C:\\Users\\luis\\Pictures\\fastbooks"  "/u01/app/oracle/fastbooks"*/ ;  
    private @Getter @Setter String email = "fastbooks20@gmail.com";
    private @Getter @Setter String pass = "orasoft123";
    
    
}
