/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.forms;

import com.fastbooks.modelo.FbCompania;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dell
 */
public class UserForm {
    private String idUsuario;
    private String idCia;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String profile;
    private List<CompaniaForm> cias = new ArrayList<>();

    public UserForm(String idUsuario, String email, String password) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.password = password;
    }

    public UserForm() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdCia() {
        return idCia;
    }

    public void setIdCia(String idCia) {
        this.idCia = idCia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public List<CompaniaForm> getCias() {
        return cias;
    }

    public void setCias(List<CompaniaForm> cias) {
        this.cias = cias;
    }

    @Override
    public String toString() {
        return "UserForm{" + "idUsuario=" + idUsuario + ", idCia=" + idCia + ", email=" + email + ", password=" + password + ", profile=" + profile + ", cias=" + cias + '}';
    }
    
    
    
    
}
