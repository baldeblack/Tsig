/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


/**
 *
 * @author Gaston
 */
@ManagedBean(name = "altazonaBean")
@RequestScoped
public class AltaZona {

    // gid
    private int id_zona;
    private String nombre_zona;
    private String descripcion_zona;
    private String demanda;
    // geom
    private String zona_puntos;
    
    //private final FacesContext facesContext;
    //private FacesMessage facesMessage;
    
    //------------------------------------------    
    // GET - SET ATRIBUTOS
    public void setNombre_Zona(String nombreZona) {
		this.nombre_zona = nombreZona;
	}

	public String getNombre_Zona() {
		return nombre_zona;
	}
        
    public String getDescripcion_Zona() {
		return descripcion_zona;
	}

	public void setDescripcion_Zona(String descripcion) {
		this.descripcion_zona = descripcion;
	}    
        
    public String getDemanda() {
		return demanda;
	}

	public void setDemanda(String demanda) {
		this.demanda = demanda;
	}    
        
    public String getZona_puntos() {
		return zona_puntos;
	}

	public void setZona_puntos(String puntos) {
		this.zona_puntos = puntos;
	}    
        
    public void setId_zona(int idZona) {
		this.id_zona = idZona;
	}
	
	public int getId_zona() {
		return id_zona;
	}    
        
    //------------------------------------------------    
    public void addMessage(String summary, Severity tipoMsg ) {
        FacesMessage message = new FacesMessage(tipoMsg, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
