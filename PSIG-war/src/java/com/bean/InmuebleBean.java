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
import com.logica.AdministradorL;
import com.logica.InmuebleL;
import com.logica.PropietarioL;
import java.io.Serializable;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlTransient;



/**
 *
 * @author vane
 */
@ManagedBean
@SessionScoped
public class InmuebleBean implements Serializable{  
    @EJB
    private PropietarioL propietarioL;
    
    private static final Logger logger = Logger.getLogger(InmuebleBean.class.getName()); 
    
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
    private Propietario propietario;
    //private Propietario propietario;
    private Object idAdmin;
    private String x;
    private String y;
    private List<SelectItem> selectOneItemPropietario;
    private int idProp;

        
     @EJB
    private InmuebleL inmuebleL;
    private AdministradorL admL;
    private Inmueble inmueble = new Inmueble();
    private Zonas zon = new Zonas();
    private Administrador adm = new Administrador();
    private Propietario prop = new Propietario();
    
    
    public InmuebleBean(){
    }


    public String altaInmueble(){                                    
        
        logger.warn("Valores x e y");
        
        /* ---- OBTENER ADMIN DE LA SESION TODAVIA ME FALTA ALGO OBTENGO EL NICK PERO ME DA ERROR AL BUSCAR ---*/
        //HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        //String nick_admin = session.getAttribute("nick").toString();
        //Administrador ad = admL.findadm(nick_admin);
       
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        
       
        
        
        String valor_x = ec.getRequestParameterMap().get("form:punto_select_x");
	//setX(x);
        String valor_y = ec.getRequestParameterMap().get("form:punto_select_y");
	//setX(y);
        
        
        
                
        if(x!=null){
            logger.warn("Valor x "+ x);
            logger.warn("Valor y "+ y);
        }
        
        if (valor_x==null){
        logger.warn("Valor x nulo");
        }
        else { 
                logger.warn("Valor x " + valor_x);
                logger.warn("Valor y "+  valor_y);
             }
        zon.setGidzona(1);
        zon.setNombre("Palermo");
        adm.setIdAdmin(1);

        
        prop.setIdPropietario(idProp);
       
        inmueble.setBanios(banios);
        inmueble.setDescripcion("Prueba");
        inmueble.setDireccion(descripcion);
        inmueble.setEstado(estado);
        inmueble.setGarage(true);
        inmueble.setGidzona(zon);
        inmueble.setHabitaciones(3);       
        inmueble.setIdAdmin(adm);
        inmueble.setIdPropietario(prop);
        inmueble.setJardin(true);
        inmueble.setPadron(2345);
        inmueble.setPisos(2);
        inmueble.setProposito(proposito);
        inmueble.setTipo(tipo);
        inmueble.setValormax(123.88);
        inmueble.setValormin(100.22);
        inmueble.setTitulo(titulo);
        
        logger.warn("valores x "+ x + "valor y "+ y);
        inmuebleL.crearInmueble(inmueble, x, y);
        return "index";
    }
    
    public List<Inmueble> listarInmuebles(){
        return inmuebleL.AllInmueble();
    }
    
    public String detalleInmueble(Inmueble inm){
        this.inmueble = inm;
        return "DetalleInmueble";
    }
    
    public List<String> findInmueble(){
        
        
        return null;
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

    
    public Propietario getPropietario() {
        return (Propietario) propietario;
    }

    public void setPropietario(Propietario prop) {
        this.propietario = prop;
    }

    public Administrador getIdAdmin() {
        return (Administrador) idAdmin;
    }

    public void setIdAdmin(Administrador idAdmin) {
        this.idAdmin = idAdmin;
    }

    public List<SelectItem> getSelectOneItemPropietario() {
        this.selectOneItemPropietario = new ArrayList<SelectItem>();
        List<Propietario> listaProp = propietarioL.listarPropietarios();
        for (Propietario propI : listaProp) {
            SelectItem selectItem = new SelectItem(propI.getIdPropietario(),propI.getNombre()+' '+propI.getApellido());
            this.selectOneItemPropietario.add(selectItem);
        }
        return selectOneItemPropietario;
    }

    public void setSelectOneItemPropietario(List<SelectItem> selectOneItemPropietario) {
        this.selectOneItemPropietario = selectOneItemPropietario;
    }

    public int getIdProp() {
        return idProp;
    }

    public void setIdProp(int idProp) {
        this.idProp = idProp;
    }

    
}