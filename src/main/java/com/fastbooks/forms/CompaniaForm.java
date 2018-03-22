/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.forms;

/**
 *
 * @author dell
 */
public class CompaniaForm {
    private String id_cia;
    private String nom_com;
    private String nom_leg;
    private String giro;
    private String telefono;
    private String logo;
    private String per_nat;
    private String estado;
    private String email;
    private String website;
    private String perfil;

    public CompaniaForm(String id_cia, String nom_com, String nom_leg, String giro, String telefono, String logo, String per_nat, String estado, String email, String website, String perfil) {
        this.id_cia = id_cia;
        this.nom_com = nom_com;
        this.nom_leg = nom_leg;
        this.giro = giro;
        this.telefono = telefono;
        this.logo = logo;
        this.per_nat = per_nat;
        this.estado = estado;
        this.email = email;
        this.website = website;
        this.perfil = perfil;
    }

    public CompaniaForm() {
    }

    public String getId_cia() {
        return id_cia;
    }

    public void setId_cia(String id_cia) {
        this.id_cia = id_cia;
    }

    public String getNom_com() {
        return nom_com;
    }

    public void setNom_com(String nom_com) {
        this.nom_com = nom_com;
    }

    public String getNom_leg() {
        return nom_leg;
    }

    public void setNom_leg(String nom_leg) {
        this.nom_leg = nom_leg;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPer_nat() {
        return per_nat;
    }

    public void setPer_nat(String per_nat) {
        this.per_nat = per_nat;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return "CompaniaForm{" + "id_cia=" + id_cia + ", nom_com=" + nom_com + ", nom_leg=" + nom_leg + ", giro=" + giro + ", telefono=" + telefono + ", logo=" + logo + ", per_nat=" + per_nat + ", estado=" + estado + ", email=" + email + ", website=" + website + ", perfil=" + perfil + '}';
    }
    
    
    
}
