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
import com.logica.ZonaL;
import java.io.Serializable;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import javax.validation.constraints.Digits;
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
    
    private String url;
    private FacesMessage facesMessage;
    private String proposito;
    private String estado;
    private int tipo;
    //@Digits(integer=9, fraction=0)
    private Double valormin;
    //@Digits(integer=9, fraction=0)
    private Double valormax;
    private String direccion;
    private int padron;
     //@Digits(integer=2, fraction=0)
    private int banios;
     //@Digits(integer=2, fraction=0)
    private int habitaciones;
     //@Digits(integer=2, fraction=0)
    private int pisos;
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
    private int gidInm;
    
    //private String barrioInm;
    //private List<SelectItem> selectOneItemBarrio;
        
     @EJB
    private InmuebleL inmuebleL;
    private AdministradorL admL;
    private Inmueble inmueble = new Inmueble();
    private Zonas zon = new Zonas();
    private Administrador adm = new Administrador();
    private Propietario prop = new Propietario();
    private ZonaL zonaL;
    
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
        inmueble.setDescripcion(descripcion);
        inmueble.setDireccion(direccion);
        inmueble.setEstado(estado);
        inmueble.setGarage(garage);
        inmueble.setGidzona(zon);
        inmueble.setHabitaciones(habitaciones);       
        inmueble.setIdAdmin(adm);
        inmueble.setIdPropietario(prop);
        inmueble.setJardin(jardin);
        inmueble.setPadron(padron);
        inmueble.setPisos(pisos);
        inmueble.setProposito(proposito);
        inmueble.setTipo(tipo);
        inmueble.setValormax(valormax);
        inmueble.setValormin(0.0);
        inmueble.setTitulo(titulo);
        
        logger.warn("valores x "+ x + "valor y "+ y);
        inmuebleL.crearInmueble(inmueble, x, y);
        return "index";
    }
    
    
    public void cargar_direccion(){
    
       try {
        this.direccion=inmuebleL.getDireccion(x,y);
       }
        catch(Exception e)
        {}
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
    
    public String editarInmueble(Inmueble inm){
        this.inmueble = inm;
        return "EditarInmueble";
    }
    
    public String editarInmueble(){
        Inmueble inm = new Inmueble();
        inm.setGidInm(gidInm);
        inm.setBanios(banios);
        inm.setDescripcion(descripcion);
        inm.setDireccion(descripcion);
        inm.setEstado(estado);
        inm.setGarage(garage);
        //inm.setGidzona(zon);
        inm.setHabitaciones(habitaciones);       
        //inm.setIdAdmin(adm);
        //inm.setIdPropietario(prop);
        inm.setJardin(jardin);
        inm.setPadron(padron);
        inm.setPisos(pisos);
        inm.setProposito(proposito);
        inm.setTipo(tipo);
        inm.setValormax(valormax);
        inm.setValormin(0.0);
        inm.setTitulo(titulo);
        inmuebleL.editarInmueble(inm);
        
        return "ListaInmueble";
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

    public int getGidInm() {
        return gidInm;
    }

    public void setGidInm(int gidInm) {
        this.gidInm = gidInm;
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

    public void createdemanda(){
        int gidzona = inmuebleL.buscozona(x,y);        
        inmuebleL.creardemandazona(gidzona);
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String uploadFile(Inmueble inm){
        this.inmueble = inm;
        return "UploadFile";
    }
    
    public String agregarImagen(){
        
        Imagenes img = new Imagenes();
        FacesContext contexto = FacesContext.getCurrentInstance();
        List<Imagenes> imges = inmuebleL.findImagenesInm(inmueble.getGidInm());
        
        if(imges == null || imges.isEmpty() ){
            img.setDestacada(true);
        }
        else{
            img.setDestacada(false);
        }
        
        img.setGidInm(inmueble);
        img.setRuta(url);
        
        if(inmuebleL.AgregarImagen(img)){
            
            facesMessage= new FacesMessage(FacesMessage.SEVERITY_INFO,"La imagen se creo correctamente",null);
            contexto.addMessage(null, facesMessage);  
        }
        else{
            facesMessage= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al crear la imagen",null);
            contexto.addMessage(null, facesMessage);
            
        }

        return "UploadFile";
    }
    
    public String listarImagenesInm(Inmueble inm){
        this.inmueble = inm;
        return "ListarImagenesInm";
    }
    
    public String datalleInm(Inmueble inm){
        this.inmueble = inm;  
        //zonaL.creardemandazona(inm.getGidzona().getGidzona());
        return "DetalleInmueble";
    }
    
    public List<Imagenes> listarImagenesInmL(){
        List<Imagenes> imagenesL = inmuebleL.findImagenesInm(inmueble.getGidInm());
        return imagenesL;
    }
    
    public List<Imagenes> listarAllImagenesInmL(){
        return inmuebleL.findAllImgInm();
    }
    
    /*public List<String> obtBarrios(){
        return inmuebleL.getallbarrios();
    }
    
    public SelectItem[] obtenerBarrios(){
        List<String> barrios = inmuebleL.getallbarrios();
        SelectItem[] items = new SelectItem[barrios.size()];
        int i = 0;
        for(String g: barrios) {
          items[i++] = new SelectItem(g, g);
        }
        return items;
  
        //return inmuebleL.getallbarrios();
    }
    
    public List<SelectItem> getSelectOneItemBarrio() {
        this.selectOneItemBarrio = new ArrayList<SelectItem>();
        List<String> listaBarros = inmuebleL.getallbarrios();
        for (String barrio : listaBarros) {
            SelectItem selectItem = new SelectItem(barrio,barrio);
            this.selectOneItemBarrio.add(selectItem);
        }
        return selectOneItemBarrio;
    }

    public void setSelectOneItemBarrio(List<SelectItem> selectOneItemBarrio) {
        this.selectOneItemBarrio = selectOneItemBarrio;
    }*/
    
    
    //cambio para gaston
}
