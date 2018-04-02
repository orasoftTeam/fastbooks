/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.modelo;

import java.util.Locale;

/**
 *
 * @author Dell
 */
public class Country {
    
    private String name ;
    private String language;
    private String lanInitials;
    private Locale locale;

    public Country() {
    }

    public Country(String name, String language, String lanInitials, Locale locale) {
        this.name = name;
        this.language = language;
        this.lanInitials = lanInitials;
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanInitials() {
        return lanInitials;
    }

    public void setLanInitials(String lanInitials) {
        this.lanInitials = lanInitials;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
        
    
    
}
