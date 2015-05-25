/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Administrador;
import com.entity.Consulta;
import com.entity.Imagenes;
import com.entity.Inmueble;
import com.entity.Propietario;
import com.entity.Zonas;
import com.logica.InmuebleL;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.log4j.Logger;

/**
 *
 * @author vane
 */
@ManagedBean
@SessionScoped
public class Pruebas implements Serializable{  
    
   
    private static final Logger logger = Logger.getLogger(Pruebas.class.getName()); 
   
    private List<String> coordenadas;

    public List<String> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(List<String> coordenadas) {
        this.coordenadas = coordenadas;
    }
    
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
    private Collection<Imagenes> imagenesCollection;
    private Collection<Consulta> consultaCollection;
    private Object gidzona;
    private Object idPropietario;
    private Object idAdmin;
    private String x;
    private String y;
    
        
     @EJB
    private InmuebleL inmuebleL;
    private Inmueble inmueble = new Inmueble();
    private Zonas zon = new Zonas();
    private Administrador adm = new Administrador();
    private Propietario prop = new Propietario();
    
    
    
    public Pruebas(){
    }

    public String pruebo(){
      //  List<String> coordenadas = inmuebleL.findhabitaciones(habitaciones);
       coordenadas = inmuebleL.Filtro(banios,habitaciones,pisos,garage,jardin,proposito);
       logger.warn("Consulta  " + coordenadas.toString());
       return "exito";        
    }
   
                                    
    
    //Geter y Seter

    
    
    
    public Inmueble getInmueble(){
        return this.inmueble;
    }
    
    public void setInmueble(Inmueble inmueble){
        this.inmueble = inmueble;
    }
    
    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    } 

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
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

    @XmlTransient
    public Collection<Imagenes> getImagenesCollection() {
        return imagenesCollection;
    }

    public void setImagenesCollection(Collection<Imagenes> imagenesCollection) {
        this.imagenesCollection = imagenesCollection;
    }

    @XmlTransient
    public Collection<Consulta> getConsultaCollection() {
        return consultaCollection;
    }

    public void setConsultaCollection(Collection<Consulta> consultaCollection) {
        this.consultaCollection = consultaCollection;
    }

    public Zonas getGidzona() {
        return (Zonas) gidzona;
    }

    public void setGidzona(Zonas gidzona) {
        this.gidzona = gidzona;
    }

    public Propietario getIdPropietario() {
        return (Propietario) idPropietario;
    }

    public void setIdPropietario(Propietario idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Administrador getIdAdmin() {
        return (Administrador) idAdmin;
    }

    public void setIdAdmin(Administrador idAdmin) {
        this.idAdmin = idAdmin;
    }
}