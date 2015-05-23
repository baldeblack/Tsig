/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logica;

import com.DAO.Conexion_geografica;
import com.DAO.ImagenesFacadeLocal;
import com.DAO.InmuebleFacadeLocal;
import com.entity.Imagenes;
import com.entity.Inmueble;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.apache.log4j.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vane
 */
@Stateless
//@LocalBean
public class InmuebleL {
    @PersistenceContext(unitName = "PSIG-ejbPU")
    private EntityManager em; 
    
    @EJB
    private InmuebleFacadeLocal inmfacade;
    private ImagenesFacadeLocal imagenfacade;
    
    
    static final Logger logger = Logger.getLogger(InmuebleL.class.getName()); 
    
    
   /* public boolean crearInmueble(Inmueble inm, String x, String y){        
        inmfacade.crearInmueble(inm,x,y);
        return true;
    }
    */
     public int crearInmueble(Inmueble inm, String x, String y){
  
                      
            //obtengo el gid mas alto en la base de datos
            String consulta = "SELECT MAX(i.gidInm) FROM Inmueble as i";            
            Query query = em.createQuery(consulta);
            Object pp = query.getSingleResult(); 
            
            pp = (Integer)pp;
            int Idinm;
            //reviso si la tabla esta bacia
            if(pp != null){
                Idinm = 1 + pp.hashCode();
                
                logger.warn("Valor id  "+Idinm);
            }
            else{                                                               			
                Idinm = 1 ;
                 logger.warn("Valor id  "+Idinm);
            }
            
            //seteo el id de inmueble
            inm.setGidInm(Idinm);
            
            //persisto el inmueble en la base relacional
            em.persist(inm);
            
             //conexion base geografica///
            Statement s = null;
            Connection conexion =  null;
            Boolean resultado= null;
        try{   
            try{
                conexion =  Conexion_geografica.getConnection();
                s = conexion.createStatement();
            }
            catch (SQLException   e){
                e.printStackTrace();
            }
           
            if(!(conexion.isClosed())){                                
                try{                                                                                                
                System.out.println(" x "+x + "y" + y );
                String consultageo = "insert into inmueble(gid,nombre,descripcion,geom) values ("
                        + inm.getGidInm()+",'" 
                        + inm.getTitulo()+"','" 
                        + inm.getDescripcion()+"', ST_GeomFromText('POINT("
                        + x 
                        + " "
                        + y 
                        + ")',32721))"; 
                resultado = s.execute(consultageo);
                }catch(Exception e){                                
                   e.printStackTrace();
                }
                conexion.close();
            }
        }  
        catch (Exception e) { 
            System.out.println("Error en la conexion");
            e.printStackTrace();			 
        }              
            return inm.getGidInm();                                                              
     }
    
    
    public boolean editarInmueble(Inmueble inm){
        Inmueble inmueble = inmfacade.findInmueble(inm.getGidInm());
        
        inmueble.setBanios(inm.getBanios());
        inmueble.setDescripcion(inm.getDescripcion());
        inmueble.setDireccion(inm.getDireccion());
        inmueble.setEstado(inm.getEstado());
        inmueble.setGarage(inm.getGarage());
        inmueble.setGidzona(inm.getGidzona());
        inmueble.setHabitaciones(inm.getHabitaciones());
        inmueble.setIdAdmin(inm.getIdAdmin());
        inmueble.setIdPropietario(inm.getIdPropietario());
        inmueble.setJardin(inm.getJardin());
        inmueble.setPadron(inm.getPadron());
        inmueble.setPisos(inm.getPisos());
        inmueble.setProposito(inm.getProposito());
        inmueble.setTipo(inm.getTipo());
        inmueble.setValormax(inm.getValormax());
        inmueble.setValormin(inm.getValormin());
        inmueble.setTitulo(inm.getTitulo());
                        
        return inmfacade.editarInmueble(inmueble);
    }
    
    
    public List<Inmueble> AllInmueble(){                
        return inmfacade.findAll();
    }
    
    
    public List<Imagenes> findImagenesInm(Object gidinmueble){
        List<Imagenes> all = imagenfacade.findAllImg();
        List<Imagenes> resultado = null;
        
        for(int i=0;i< all.size()-1;i++){
            Imagenes img = (Imagenes) all.get(i);
            
            if(gidinmueble == img.getGidInm()){
                resultado.add(img);
            } 
        }           
        return resultado;
    }
    
     public List<Imagenes> findImagenDestacada(Object gidinmueble){
        List<Imagenes> all = imagenfacade.findAllImg();
        List<Imagenes> resultado = null;
        
        for(int i=0;i< all.size()-1;i++){
            Imagenes img = (Imagenes) all.get(i);
            
            if(gidinmueble == img.getGidInm() && img.getDestacada() == true){
                resultado.add(img);
            } 
        }           
        return resultado;
    }
    
}
