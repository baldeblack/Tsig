package com.bean;

import com.entity.Zonas;
import com.logica.ZonaL;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.apache.log4j.Logger;


@ManagedBean
@SessionScoped
public class ZonaBean implements Serializable{  

    //Log
    private static final Logger logger = Logger.getLogger(ZonaBean.class.getName()); 
    
    // gid
    private int id_zona;
    private String nombre_zona;
    private String descripcion_zona;
    // geom
    private String zona_puntos;
    
     @EJB
    private ZonaL zonaL;
    private Zonas zona = new Zonas();
    private Severity tipo_msj;
    private FacesMessage mensaje;
    private FacesContext contexto;
    private FacesMessage facesMessage;
  
    public ZonaBean(){
    }

    public int getId_zona( ) {    
        return id_zona;
    }

    public void setId_zona(int id_zona) {
        this.id_zona = id_zona;
    }

    public String getNombre_zona() {
        return nombre_zona;
    }

    public void setNombre_zona(String nombre_zona) {
        this.nombre_zona = nombre_zona;
    }

    public String getDescripcion_zona() {
        return descripcion_zona;
    }

    public void setDescripcion_zona(String descripcion_zona) {
        this.descripcion_zona = descripcion_zona;
    }
    //------------------------------------------------
    public String getZona_puntos() {
        return zona_puntos;
    }
    //private final FacesContext facesContext;
    //private FacesMessage facesMessage;
    //------------------------------------------    
    // GET - SET ATRIBUTOS
    public void setZona_puntos(String zona_puntos) {
        this.zona_puntos = zona_puntos;
    }
    //------------------------------------------------
    public void addMessage(String summary, Severity tipo_msj) {
        FacesMessage message = new FacesMessage(tipo_msj, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    //-------------------------------------------------------------------------
    public String altaZona()  throws ValidatorException{   
        String retorno="";
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        logger.warn("Coordenadas"+zona_puntos);
       
        try{
            if (zona_puntos.equals("[]")){
                contexto = FacesContext.getCurrentInstance();
                facesMessage= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe marcar la zona a registrar",null);
                contexto.addMessage(null, facesMessage);
                retorno= "AltaZona";
            }
            else{
                String coordenadas= zonaL.ConvertirCoordenadas(zona_puntos);
                if (zonaL.interseccionZonas(coordenadas)){
                    contexto = FacesContext.getCurrentInstance();
                    facesMessage= new FacesMessage(FacesMessage.SEVERITY_ERROR,"La zona no puede coincidir con una zona existente",null);
                    contexto.addMessage(null, facesMessage);
                    retorno= "AltaZona";
                }
                else{
                    //logger.warn("Valores x e y");
                    zona.setNombre(nombre_zona);
                    logger.warn("Zona puntos" +coordenadas.toString());
                    zonaL.crearZona(zona, coordenadas);
                    
                    mensaje =new FacesMessage("Se agrego correctamente la Zona");	
                    mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
                    retorno= "index";
                    //throw new ValidatorException(mensaje);
               }		
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return retorno;
    }
}
