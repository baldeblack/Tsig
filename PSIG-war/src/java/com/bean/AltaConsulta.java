/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Consulta;
import com.entity.Inmueble;
import com.logica.ConsultaL;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Gaston
 */
@ManagedBean
@RequestScoped
public class AltaConsulta {

    private String nombre;
    private String apellido;
    private int telefono;
    private String email;
    private String descripcion;
    private FacesMessage facesMessage;
    private Inmueble inmueble;
    
    @EJB
    private ConsultaL ConL;
    private Consulta con;
    
    public AltaConsulta() {
    }
    
    public String crearConsulta(){
        Consulta consu = new Consulta();
        consu.setNombre(nombre);
        consu.setApellido(apellido);
        consu.setTelefono(telefono);
        consu.setEmail(email);
        consu.setDescripcion(descripcion);
        
        if(ConL.crearConsulta(consu))
        {
            //facesMessage= new FacesMessage(FacesMessage.SEVERITY_INFO,"El usuario se creo correctamente",null);
            this.con = new Consulta();
            return "ListaConsulta";
        }
        else
        {
            
            facesMessage= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al crear consulta",null);
            FacesContext.getCurrentInstance().addMessage(null,facesMessage);
            return "AltaConsulta";
        }
  
    }
    
    public List<Consulta> listaConsulta(){
        return ConL.listarConsultas();
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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Consulta getCon() {
        return con;
    }

    public void setCon(Consulta con) {
        this.con = con;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }
    
    
    
}
