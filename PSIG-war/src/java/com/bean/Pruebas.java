package com.bean;

import com.entity.Administrador;
import com.entity.Consulta;
import com.entity.Imagenes;
import com.entity.Inmueble;
import com.entity.Propietario;
import com.entity.Zonas;
import com.logica.InmuebleL;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.log4j.Logger;

/**
 *
 * @author vane
 */
@ManagedBean
@SessionScoped
public class Pruebas implements Serializable{ 
    
    // Logging
    private static final Logger logger = Logger.getLogger(Pruebas.class.getName()); 
    
    // Atributos inmueble
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
   
    private int metros;
    
    private List<String> coordenadas;
    
    // Datos basicos inmueble
    private String x;
    private String y;
    
    private String x_nuevo;
    private String y_nuevo;
    private FacesMessage mensaje;
    private FacesContext contexto;
    private FacesMessage facesMessage;
    
    
 
    
    private List<String> inmueble_basico;
    private List<String> inmueble_completo;
    
    private String nombre;
    
    @EJB
    private InmuebleL inmuebleL;
    private Inmueble inmueble = new Inmueble();
    private final Zonas zon = new Zonas();
    private final Administrador adm = new Administrador();
    private final Propietario prop = new Propietario();
    private String coordenadas_String;
    
    
    public Pruebas(){
       
    }
    
    //Metodos
    public String pruebo(){
       String retorno="ResultadoBusqueda";
        try{
            coordenadas = inmuebleL.Filtro(banios,habitaciones,pisos,garage,jardin,proposito,metros);       
            
           
            if (coordenadas!=null){
                
                 coordenadas_String= coordenadas.toString();
                logger.warn("Consulta Pruebo 1   " + coordenadas_String);
                 retorno= "ResultadoBusqueda";        
            }
            else{
                facesMessage= new FacesMessage(FacesMessage.SEVERITY_ERROR,"No hay resultados para la busqueda",null);
                contexto.addMessage(null, facesMessage);
                retorno= "Pruebas";
            
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return retorno;   
    }

    public String getCoordenadas_String() {
        return coordenadas_String;
    }

    public void setCoordenadas_String(String coordenadas_String) {
        this.coordenadas_String = coordenadas_String;
    }
    //********************************************  
    public String consulta2(){
        String retorno="ResultadoBusqueda";
        try{
            x_nuevo= "-56.1729062389646";
            y_nuevo="-34.8927886510942";
            logger.warn("Consulta 2" + x_nuevo + " "+ y_nuevo);
            //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            //x_nuevo= ec.getRequestParameterMap().get("formulario:x_nuevo");
            //y_nuevo = ec.getRequestParameterMap().get("formulario:y_nuevo");
            inmueble_completo = inmuebleL.getInmueble(x_nuevo,y_nuevo);
            //le pasas las coordenadas y te busca el inmueble, te devuelve los datos en una lista de string
            if (inmueble_completo!=null){
                logger.warn("Consulta Pruebo 2  " + inmueble_completo.toString());
                retorno="DetalleInmueble";
            }
            else{
                facesMessage= new FacesMessage(FacesMessage.SEVERITY_ERROR,"No hay detalle del inmueble",null);
                contexto.addMessage(null, facesMessage);
                retorno= "ResultadoBusqueda";
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return retorno;   
    }
    
   
    //******************************************** 
    public String pruebo3(){
        String retorno=null;
        try{
            inmueble_basico = inmuebleL.getInmueblebasico(x,y);//le pasas las coordenadas y te busca el inmueble, te devuelve los datos en una lista de string
            //logger.warn("Consulta  " + resultado.toString());
            retorno="DetalleInmueble";
        }
        catch(Exception e){
        }
        return retorno;   
    }
    /*List<String> coordenadas = inmuebleL.findhabitaciones(habitaciones);               
       //List<Inmueble> lista = new ArrayList();
       //List<String> rambla = inmuebleL.InmRambla(metros); //Le pasas los metros y te busca los inmuebles que esten a menos de esa distancia de la rambla
       //List<String> resultado = inmuebleL.getInmueble(x,y);//le pasas las coordenadas y te busca el inmueble, te devuelve los datos en una lista de string
       //List<Inmueble>inmpp = inmuebleL.findInmRambla(metros,lista);
     }*/
    /*
     public String pruebo(){
        String retorno=null;
         try{
-            coordenadas = inmuebleL.Filtro(banios,habitaciones,pisos,garage,jardin,proposito,metros);       
+            List<String> img = inmuebleL.getInmueblebasico(x, y);
+            //coordenadas = inmuebleL.Filtro(banios,habitaciones,pisos,garage,jardin,proposito,metros);       
             logger.warn("Consulta  " + coordenadas.toString());
             return "exito";        
         }
    */
    
//Geter y Seter  
    
     public String getX_nuevo() {
        return x_nuevo;
    }

    public void setX_nuevo(String x_nuevo) {
        this.x_nuevo = x_nuevo;
    }

    public String getY_nuevo() {
        return y_nuevo;
    }

    public void setY_nuevo(String y_nuevo) {
        this.y_nuevo = y_nuevo;
    }
    
    public List<String> getInmueble_basico() {
        return inmueble_basico;
    }

    public void setInmueble_basico(List<String> inmueble_basico) {
        this.inmueble_basico = inmueble_basico;
    }
    
    public List<String> getCoordenadas() {
        return coordenadas;
    }
    
   public void setCoordenadas(List<String> coordenadas) {
        this.coordenadas = coordenadas;
    }
    
    public List<String> getInmueble_completo() {
        return inmueble_completo;
    }

    public void setInmueble_completo(List<String> inmueble_completo) {
        this.inmueble_completo = inmueble_completo;
    }
    
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
    public int getMetros() {
        return metros;
    }

    public void setMetros(int metros) {
        this.metros = metros;
    }
    
     public void addMessage(String summary, FacesMessage.Severity tipo_msj) {
        FacesMessage message = new FacesMessage(tipo_msj, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    public void setIdAdmin(Object idAdmin) {
        this.idAdmin = idAdmin;
    }

    public FacesMessage getMensaje() {
        return mensaje;
    }

    public void setMensaje(FacesMessage mensaje) {
        this.mensaje = mensaje;
    }

    public FacesContext getContexto() {
        return contexto;
    }

    public void setContexto(FacesContext contexto) {
        this.contexto = contexto;
    }

    public FacesMessage getFacesMessage() {
        return facesMessage;
    }

    public void setFacesMessage(FacesMessage facesMessage) {
        this.facesMessage = facesMessage;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public InmuebleL getInmuebleL() {
        return inmuebleL;
    }

    public void setInmuebleL(InmuebleL inmuebleL) {
        this.inmuebleL = inmuebleL;
    }
     
     
}