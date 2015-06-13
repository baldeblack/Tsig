/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Consulta;
import com.entity.Imagenes;
import com.entity.Inmueble;
import com.entity.Objeto;
import com.logica.InmuebleL;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author vane
 */
@ManagedBean
@SessionScoped
public class Filtro implements Serializable{
    private String proposito;
    private String estado;
    private int tipo;
    private Double valormin;
    private Double valormax;
    private String direccion;
    private int padron;
    private int banios;
    private int habitaciones;
    private int pisos;
    private Boolean garage;
    private Boolean jardin;
    private String descripcion;
    private String titulo;
    private Collection<Imagenes> imagenesCollection;
    private Collection<Consulta> consultaCollection;
    private Object gidzona;
    private Object idPropietario;
    private Object idAdmin;   
    private int metros;  
    private int metrosparada;
    private Double preciomin;
    private Double preciomax;
    private String barrio;
    private int tipoinm;
    private int metrossuper;  
    private String resultado = "";
    private String estados = "";    
    
    
    @EJB
    private InmuebleL inmuebleL;
    private Inmueble inmueble = new Inmueble();
    
    public Filtro() {               
        
    }
    
    public String GetFiltrado(){
        resultado = "";
        //String retorno="ResultadoBusqueda";
        String retorno="index";
        
         //String retorno="index";
        List<Objeto> objetos = inmuebleL.Filtro(banios,habitaciones,pisos,garage,jardin,proposito,metros,metrossuper,metrosparada,preciomin,preciomax,barrio,tipoinm);
        String result = "";                
        for(Objeto obj : objetos){
            String cadena = obj.getCoordenadas();
            String delimitadores = " ";
            String[] xyseparados = cadena.split(delimitadores);
            String x= xyseparados[0];
            String y= xyseparados[1];
            result = result +obj.getTipo()+ "," +x+ "," +y+ "," +obj.getNombre()+ "," +obj.getGid()+ ":";
        }   
        resultado = result;
        return retorno;
    }

    public String GetEstadosinm(){
        estados = "";
        String retorno="EstadoInmuebles";
        
        List<Objeto> objetos = inmuebleL.GetEstadoInm();
        String result = "";
        for(Objeto obj : objetos){
            String cadena = obj.getCoordenadas();
            String delimitadores = " ";
            String[] xyseparados = cadena.split(delimitadores);
            String x= xyseparados[0];
            String y= xyseparados[1];
            result = result +obj.getTipo()+ "," +x+ "," +y+ "," +obj.getNombre()+ "," +obj.getGid()+ ":";
        }   
        estados = result;
        return retorno;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public int getTipoinm() {
        return tipoinm;
    }

    public void setTipoinm(int tipoinm) {
        this.tipoinm = tipoinm;
    }
    
    
    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getEstados() {
        return estados;
    }

    public void setEstados(String estados) {
        this.estados = estados;
    }
    
    public Double getValormin() {
        return valormin;
    }

    public void setValormin(Double valormin) {
        this.valormin = valormin;
    }
    
    public int getMetrosparada() {
        return metrosparada;
    }

    public void setMetrosparada(int metrosparada) {
        this.metrosparada = metrosparada;
    }

    public Double getPreciomin() {
        return preciomin;
    }

    public void setPreciomin(Double preciomin) {
        this.preciomin = preciomin;
    }

    public Double getPreciomax() {
        return preciomax;
    }

    public void setPreciomax(Double preciomax) {
        this.preciomax = preciomax;
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

    public int getPadron() {
        return padron;
    }

    public void setPadron(int padron) {
        this.padron = padron;
    }

    public int getBanios() {
        return banios;
    }

    public void setBanios(int banios) {
        this.banios = banios;
    }

    public int getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }

    public int getPisos() {
        return pisos;
    }

    public void setPisos(int pisos) {
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

    public Collection<Imagenes> getImagenesCollection() {
        return imagenesCollection;
    }

    public void setImagenesCollection(Collection<Imagenes> imagenesCollection) {
        this.imagenesCollection = imagenesCollection;
    }

    public Collection<Consulta> getConsultaCollection() {
        return consultaCollection;
    }

    public void setConsultaCollection(Collection<Consulta> consultaCollection) {
        this.consultaCollection = consultaCollection;
    }

    public Object getGidzona() {
        return gidzona;
    }

    public void setGidzona(Object gidzona) {
        this.gidzona = gidzona;
    }

    public Object getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Object idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Object getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Object idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getMetros() {
        return metros;
    }

    public void setMetros(int metros) {
        this.metros = metros;
    }

    public int getMetrossuper() {
        return metrossuper;
    }

    public void setMetrossuper(int metrossuper) {
        this.metrossuper = metrossuper;
    }

   

    public InmuebleL getInmuebleL() {
        return inmuebleL;
    }

    public void setInmuebleL(InmuebleL inmuebleL) {
        this.inmuebleL = inmuebleL;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }
   
    
    
    
    
}
