
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Administrador;
import com.logica.AdministradorL;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


/**
 *
 * @author vane
 */
@ManagedBean
@RequestScoped
public class AltaAdministrador {
    
    private String login;
    private String clave;
    private String nombre;
    private String apellido;
    private int cedula;
    private int telefono;
    private String email;
    private boolean rol;
    private final FacesContext facesContext;
    private FacesMessage facesMessage;
    
    @EJB
    private AdministradorL AdmL;
    private Administrador adm;
    
    public AltaAdministrador(){
        adm = new Administrador();
        facesContext = FacesContext.getCurrentInstance();
    }

    public String crearAdministrador(){
        if(AdmL.crearAdministrador(login, clave, nombre, apellido, cedula, telefono, email, rol))
        {
            //facesMessage= new FacesMessage(FacesMessage.SEVERITY_INFO,"El usuario se creo correctamente",null);
            this.adm = new Administrador();
            return "ListarAdministradores";
        }
        else
        {
            
            facesMessage= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al crear usuario",null);
            FacesContext.getCurrentInstance().addMessage(null,facesMessage);
            return "AltaAdministrador";
        }
  
    }
    
    public List<Administrador> listarAdministradores(){
        return AdmL.listarAdministradores();
    }
    
   /* public boolean loginadm(){
        return AdmL.login(adm.getLogin(),adm.getClave());    
        //return AdmL.login("vanecas","1");    
    }*/
    
    public String editAdministrador(Administrador admin){
        this.adm = admin;
        return "EditarAdministrador";
    }
    
    public String editAdministrador(){
        
        if(AdmL.editAdministrador(adm))
        {
            facesMessage= new FacesMessage(FacesMessage.SEVERITY_INFO,"El usuario se edito correctamente",null);
            return "ListarAdministradores";
        }
        else
        {
            facesMessage= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al editar usuario",null);
            FacesContext.getCurrentInstance().addMessage(null,facesMessage);
            return "EditarAdministrador";
        }
    }
        
    
     public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getRol() {
        return rol;
    }

    public void setRol(Boolean rol) {
        this.rol = rol;
    }
    
    public Administrador getAdm() {
        return adm;
    }

    public void setAdm(Administrador adm) {
        this.adm = adm;
    }
}
