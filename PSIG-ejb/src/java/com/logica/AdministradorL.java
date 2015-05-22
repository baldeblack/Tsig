/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logica;

import com.DAO.AdministradorFacadeLocal;
import com.entity.Administrador;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author vane
 */
@Stateless
@LocalBean
public class AdministradorL {
    @EJB
    private AdministradorFacadeLocal admfacadelocal;
    
    public boolean crearAdministrador(String login,String clave,String nombre,String apellido,int cedula,int telefono,String email,boolean rol){
       
        Administrador adm = new Administrador();
        adm.setLogin(login);
        adm.setClave(clave);
        adm.setNombre(nombre);
        adm.setApellido(apellido);
        adm.setCedula(cedula);
        adm.setTelefono(telefono);
        adm.setEmail(email);
        adm.setRol(rol);
        return admfacadelocal.crearAdministrador(adm);        
    }
    /*
    public boolean editAdministrador(String login,String clave,String nombre,String apellido,int cedula,int telefono,String email,boolean rol){
        Administrador adm = findadm(login);  
        adm.setLogin(login);
        adm.setClave(clave);
        adm.setNombre(nombre);
        adm.setApellido(apellido);
        adm.setCedula(cedula);
        adm.setTelefono(telefono);
        adm.setEmail(email);
        adm.setRol(rol);
        return admfacadelocal.editAdministrador(adm);
    }
    
    public List<Administrador> listarAdministradores(){
        return admfacadelocal.findAll();
    }
    
    public Administrador findadm(String login){
        return admfacadelocal.findadm(login);
    }
 
    //Le paso el login y la clave y me devuelve true si la clave es correcta
    public boolean login(String login, String clave){
        return admfacadelocal.login(login, clave);
    }*/
    
    public boolean editAdministrador(Administrador admin){
        Administrador adm = findadm(admin.getLogin());  
        adm.setLogin(admin.getLogin());
        adm.setClave(admin.getClave());
        adm.setNombre(admin.getNombre());
        adm.setApellido(admin.getApellido());
        adm.setCedula(admin.getCedula());
        adm.setTelefono(admin.getTelefono());
        adm.setEmail(admin.getEmail());
        adm.setRol(admin.getRol());
        return admfacadelocal.editAdministrador(adm);
    }
    
    public List<Administrador> listarAdministradores(){
        return admfacadelocal.findAll();
    }
    
    public Administrador findadm(String login){
        return admfacadelocal.findadm(login);
    }
    
    
    //Le paso el login y la clave y me devuelve true si la clave es correcta
    public boolean login(String login, String clave){
        return admfacadelocal.login(login, clave);
    }
    
    
}
