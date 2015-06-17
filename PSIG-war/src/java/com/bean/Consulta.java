/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import Extras.EnvioMail;
import com.entity.Inmueble;
import com.logica.InmuebleL;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Gaston
 */
@ManagedBean(name = "consultaBean")
@RequestScoped
public class Consulta {
    private int gidInm;
    private String mail;
    private String nombre;
    private String telefono;
    private String asunto;
    private String mensaje;
    
    @EJB
    private InmuebleL inmuebleL;
    private Inmueble inmueble = new Inmueble();
    
    
    public Consulta() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getGidInm() {
        return gidInm;
    }

    public void setGidInm(int gidInm) {
        this.gidInm = gidInm;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String enviarCorreo()
    {
        
        Inmueble inm = inmuebleL.obtenerInmueblePorId(inmueble.getGidInm());
        String msj = mail+nombre+telefono+asunto+mensaje;
        //EnvioMail mail = new EnvioMail();
        return "Consulta";
    }
    
    public void consultarInm(ActionEvent event){
        //Inmueble inm = inmuebleL.obtenerInmueblePorId(gidInm);
        String buttonId = event.getComponent().getId();
        //this.inmueble = inm;    
    }
    public String outcome(){
	return "Consulta";
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }
    
    
}
