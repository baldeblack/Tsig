/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Administrador;
import com.entity.Inmueble;
import com.entity.Propietario;
import com.logica.AdministradorL;
import com.logica.InmuebleL;
import com.logica.PropietarioL;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

public class AltaInmueble {
    
    private String proposito;
    private String estado;
    private Integer tipo;
    private Double valormin;
    private Double valormax;
    private String direccion;
    private Integer padron;
    private Integer banios;
    private Integer habitaciones;
    private Integer pisos;
    private Boolean garage;
    private Boolean jardin;
    private String descripcion;
    private String titulo;
    private Propietario propietario;
    private List<SelectItem> propietarioSeleccionado;
    
    private FacesMessage facesMessage;

    /*
    private Zona gidzona;
    
    private Administrador idAdmin;
    */
    
    
    @EJB
    private InmuebleL inmuebleL;
    private AdministradorL AdminL;
    private Inmueble inmueble;
    private PropietarioL propL;
    
   
    
    public AltaInmueble() {
        inmueble = new Inmueble();
    }
   
    public String crearInmueble(){
        
        HttpServletRequest prueba = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        Administrador admin = AdminL.findadm(prueba.getSession().getAttribute("nick").toString());
        
        Inmueble inm = new Inmueble();
        inm.setProposito(proposito);
        inm.setEstado(estado);
        inm.setTipo(tipo);
        inm.setValormax(valormax);
        inm.setValormin(valormin);
        inm.setDireccion(direccion);
        inm.setPadron(padron);
        inm.setBanios(banios);
        inm.setHabitaciones(habitaciones);
        inm.setPisos(pisos);
        inm.setGarage(garage);
        inm.setJardin(jardin);
        inm.setDescripcion(descripcion);
        inm.setTitulo(titulo);
        
        inm.setIdAdmin(admin);
        
        
        /*
        if(inmuebleL.crearInmueble(inm))
        {
            //facesMessage= new FacesMessage(FacesMessage.SEVERITY_INFO,"El usuario se creo correctamente",null);
            this.inmueble = new Inmueble();
            return "ListarInmuebles";
        }
        else
        {
            
            facesMessage= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al crear usuario",null);
            FacesContext.getCurrentInstance().addMessage(null,facesMessage);
            return "AltaInmueble";
        }*/
        return "";
  
    }
    
    
    public List<Inmueble> listarInmueble(){
        return inmuebleL.AllInmueble();
    }
    
    
    public List<SelectItem> getPropietarioSeleccionado(){
        
        if(this.propietarioSeleccionado == null){
            propietarioSeleccionado = new ArrayList<SelectItem>();
            propL = new PropietarioL();
            List<Propietario> listaProp = propL.listarPropietarios();
            if(listaProp!=null && !listaProp.isEmpty()){
                SelectItem item;
                for (Propietario p : listaProp) {
                    item = new SelectItem(p, p.getNombre());
                    this.propietarioSeleccionado.add(item);
                }
                
            }
            
            
        }
        
        return propietarioSeleccionado;
    }
    
    
    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Double getValormin() {
        return valormin;
    }

    public void setValormin(Double valormin) {
        this.valormin = valormin;
    }

    public Double getValormax() {
        return valormax;
    }

    public void setValormax(Double valormax) {
        this.valormax = valormax;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getPadron() {
        return padron;
    }

    public void setPadron(Integer padron) {
        this.padron = padron;
    }

    public Integer getBanios() {
        return banios;
    }

    public void setBanios(Integer banios) {
        this.banios = banios;
    }

    public Integer getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(Integer habitaciones) {
        this.habitaciones = habitaciones;
    }

    public Integer getPisos() {
        return pisos;
    }

    public void setPisos(Integer pisos) {
        this.pisos = pisos;
    }

    public Boolean getGarage() {
        return garage;
    }

    public void setGarage(Boolean garage) {
        this.garage = garage;
    }

    public Boolean getJardin() {
        return jardin;
    }

    public void setJardin(Boolean jardin) {
        this.jardin = jardin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }        
    

    
}
