/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logica;

import Extras.OrigenDatos;
import Extras.PoolConexiones;
import com.DAO.Conexion_geografica;
import com.DAO.DemandaFacadeLocal;
import com.DAO.ImagenesFacade;
import com.DAO.ImagenesFacadeLocal;
import com.DAO.InmuebleFacadeLocal;
import com.DAO.ZonasFacade;
import com.DAO.ZonasFacadeLocal;
import com.entity.Demanda;
import com.entity.Imagenes;
import com.entity.Inmueble;
import com.entity.Zonas;
import com.entity.Objeto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
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
    @EJB
    private ZonasFacadeLocal zonasFacade;
    @PersistenceContext(unitName = "PSIG-ejbPU")
    private EntityManager em; 
    
    @EJB
    private InmuebleFacadeLocal inmfacade;
    @EJB
    private ImagenesFacadeLocal imagenfacade;
     @EJB
    private ZonasFacadeLocal zonasfacade;
      @EJB
    private DemandaFacadeLocal demandafacade;
    
    static final Logger logger = Logger.getLogger(InmuebleL.class.getName()); 
    private Connection conexion;
    private Statement s;
    private boolean resultado;
    private Object zonaF;
    
    
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
                
                logger.warn("Valor id a insertar "+Idinm);
            }
            else{                                                               			
                Idinm = 1 ;
                 logger.warn("Valor id a insertar "+Idinm);
            }
            
            //seteo el id de inmueble
            inm.setGidInm(Idinm);
            try{
                int gidZona=inmfacade.zonaInmueble(x, y);
                logger.warn("GID ZONA A QUE PERTENECE =  "+Idinm);
                if (gidZona!=0){
                    Zonas zona=zonasFacade.buscarZona(gidZona);
                    if (zona!=null){
                        logger.warn("ZONA A QUE PERTENECE  con gid=  "+zona.getGidzona());
                        inm.setGidzona(zona);
                    }
                    else{
                        logger.warn("ZONA NULL");
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            //persisto el inmueble en la base relacional
            em.persist(inm);
            
             //conexion base geografica///
             try{   
            
                try{ 
                    
                    logger.warn("CONEXION A establecer");
                    conexion = OrigenDatos.getConnection();
                     if (conexion.isClosed()){
                        logger.warn("CONEXION cerrada");
                        PoolConexiones pool= new PoolConexiones() ;
                        conexion = pool.getConnectionFromPool();
                        logger.warn("CONEXION = "+ conexion.getSchema());
                     }
                }
                catch(ClassNotFoundException e){
                    e.printStackTrace();
                }
                
                try{
                    
                    s=conexion.createStatement();
                    String consultageo = "insert into inmueble(gid,nombre,descripcion,geom) values ("
                        + inm.getGidInm()+",'" 
                        + inm.getTitulo()+"','" 
                        + inm.getDescripcion()+"',  ST_SetSRID( ST_MakePoint("
                        + x 
                        + ","
                        + y 
                        + "),4326))";
                    
                    //ST_GeomFromText('POINT(-71.060316 48.432044)', 4326));
                    
                    logger.warn("CONSULTA  = "+ consultageo);
                    /*String consultageo = "insert into inmueble(gid,nombre,descripcion,geom) values ("
                        + inm.getGidInm()+",'" 
                        + inm.getTitulo()+"','" 
                        + inm.getDescripcion()+"', ST_GeomFromText('POINT("
                        + x 
                        + " "
                        + y 
                        + ")',4326))"; */
                    resultado = s.execute(consultageo);
                    conexion.close();
                    OrigenDatos.returnConnection(conexion);
                }
                 catch(SQLException e){
                    e.printStackTrace();
                }
        }  
        catch (Exception e) { 
            System.out.println("Error en la conexion");
            e.printStackTrace();			 
        }     
            
            
            
            
        /*try{   
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
        }     */      
            
            
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
    
    public List<Imagenes> findAllImgInm(){
        return imagenfacade.findAllImg();
    }
    
    public List<Imagenes> findImagenesInm(Object gidinmueble){
        List<Imagenes> all = imagenfacade.findAllImg();
        List<Imagenes> result = new ArrayList();
        
        for (Imagenes imagenes : all) {
            //Imagenes img = (Imagenes) all.get(i);
            int idInm = imagenes.getGidInm().getGidInm();
            if(gidinmueble.equals(idInm) ){
                result.add(imagenes);
            } 
        }
        /*for(int i=0;i< all.size()-1;i++){
            Imagenes img = (Imagenes) all.get(i);
            int idInm = img.getGidInm().getGidInm();
            if(gidinmueble.equals(idInm) ){
                result.add(img);
            } 
        } */          
        return result;
    }
    
     public List<Imagenes> findImagenDestacada(Object gidinmueble){
        List<Imagenes> all = imagenfacade.findAllImg();
        List<Imagenes> resultado = new ArrayList();
        
        for(int i=0;i< all.size()-1;i++){
            Imagenes img = (Imagenes) all.get(i);
            
            if(gidinmueble == img.getGidInm() && img.getDestacada() == true){
                resultado.add(img);
            } 
        }           
        return resultado;
    }
    
    public List<Inmueble> findhabitaciones(int cantidad,List<Inmueble> lista){       
         List<Inmueble> Allinm = AllInmueble();
         List<Inmueble> resultado = new ArrayList();
         if(cantidad != 0){
            if(!lista.isEmpty()){                 
                 for(Inmueble inm : lista){              
                     if(inm.getHabitaciones() == cantidad){
                          resultado.add(inm);
                     }                                    
                 }              
             }
            else{
                if(!Allinm.isEmpty()){             
                   for(Inmueble inm : Allinm){
                       if(inm.getHabitaciones()== cantidad){
                           resultado.add(inm);
                       }                                
                   }                          
               }   
            }                        
            return resultado;
         }
         else{
             return lista;
         }
     }
     
     public List<Inmueble> findbanio(int cantidad,List<Inmueble> lista){
          List<Inmueble> resultado = new ArrayList();
          List<Inmueble> Allinm = AllInmueble();
          if(cantidad != 0){
                if(!lista.isEmpty()){              
                    for(Inmueble inm : lista){              
                        if(inm.getBanios() == cantidad){
                             resultado.add(inm);
                        }                                    
                    }              
                }
                else{
                    if(!Allinm.isEmpty()){             
                      for(Inmueble inm : Allinm){                 
                          if(inm.getBanios() == cantidad){
                             resultado.add(inm);
                          }                                
                      }                          
                    }   
                }                           
               return resultado;
          }
          else{
              return lista;
          }
     }
     
     public List<Inmueble> findpisos(int cantidad,List<Inmueble> lista){
        List<Inmueble> resultado = new ArrayList();
        List<Inmueble> Allinm = AllInmueble();
        if(cantidad != 0){
            if(!lista.isEmpty()){              
                  for(Inmueble inm : lista){              
                      if(inm.getPisos() == cantidad){
                           resultado.add(inm);
                      }                                    
                  }              
            }
            else{
                  if(!Allinm.isEmpty()){             
                    for(Inmueble inm : Allinm){                 
                        if(inm.getPisos() == cantidad){
                           resultado.add(inm);
                        }                                
                    }                          
                  }   
            }                  
            return resultado;
        }
        else{
            return lista;
        }
     }
     
      public List<Inmueble> findgarage(boolean garage,List<Inmueble> lista){
        List<Inmueble> resultado = new ArrayList();
        List<Inmueble> Allinm = AllInmueble();
        if(garage == true){
            if(!lista.isEmpty()){              
                  for(Inmueble inm : lista){              
                      if(inm.getGarage() == true){
                           resultado.add(inm);
                      }                                    
                  }              
            }
            else{
                  if(!Allinm.isEmpty()){             
                    for(Inmueble inm : Allinm){                 
                        if(inm.getGarage() == true){
                           resultado.add(inm);
                        }                                
                    }                          
                  }   
            }                  
            return resultado;
        }
        else{
            return lista;
        }
     }
     
      public List<Inmueble> findjardin(boolean jardin,List<Inmueble> lista){
        List<Inmueble> resultado = new ArrayList();
        List<Inmueble> Allinm = AllInmueble();
        if(jardin == true){
            if(!lista.isEmpty()){              
                  for(Inmueble inm : lista){              
                      if(inm.getJardin() == true){
                           resultado.add(inm);
                      }                                    
                  }              
            }
            else{
                  if(!Allinm.isEmpty()){             
                    for(Inmueble inm : Allinm){                 
                        if(inm.getJardin() == true){
                           resultado.add(inm);
                        }                                
                    }                          
                  }   
            }                  
            return resultado;
        }
        else{
            return lista;
        }
     }
     
     public List<Inmueble> findpreciomax(Double preciomax,List<Inmueble> lista){
        List<Inmueble> resultado = new ArrayList();
        List<Inmueble> Allinm = AllInmueble();
        if(preciomax != 0){
            if(!lista.isEmpty()){              
                  for(Inmueble inm : lista){              
                      if(inm.getValormax() <= preciomax){
                           resultado.add(inm);
                      }                                    
                  }              
            }
            else{
                  if(!Allinm.isEmpty()){             
                    for(Inmueble inm : Allinm){                 
                        if(inm.getValormax() <= preciomax){
                           resultado.add(inm);
                        }                                
                    }                          
                  }   
            }                  
            return resultado;
        }
        else{
            return lista;
        }
     } 
      
     public List<Inmueble> findprecio(Double preciomax,Double preciomin,List<Inmueble> lista){
        List<Inmueble> resultado = new ArrayList();
        List<Inmueble> Allinm = AllInmueble();
        if(preciomax != 0){
            if(!lista.isEmpty()&& preciomax > preciomin){              
                  for(Inmueble inm : lista){              
                      if(inm.getValormax() <= preciomax && inm.getValormin() >= preciomin){
                           resultado.add(inm);
                      }                                    
                  }              
            }
            else{
                  if(!Allinm.isEmpty()&& preciomax > preciomin){             
                    for(Inmueble inm : Allinm){                 
                        if(inm.getValormax() <= preciomax && inm.getValormin() >= preciomin){
                           resultado.add(inm);
                        }                                
                    }                          
                  }   
            }                  
            return resultado;
        }
        else{
            return lista;
        }
     }  
      
    public List<Inmueble> findpreciomin(Double preciomin,List<Inmueble> lista){
        List<Inmueble> resultado = new ArrayList();
        List<Inmueble> Allinm = AllInmueble();
        if(preciomin != 0){
            if(!lista.isEmpty()){              
                  for(Inmueble inm : lista){              
                      if(inm.getValormax() >= preciomin){
                           resultado.add(inm);
                      }                                    
                  }              
            }
            else{
                  if(!Allinm.isEmpty()){             
                    for(Inmueble inm : Allinm){                 
                        if(inm.getValormax() >= preciomin){
                           resultado.add(inm);
                        }                                
                    }                          
                  }   
            }                  
            return resultado;
        }
        else{
            return lista;
        }
     }
     
     public List<Inmueble> findproposito(String proposito,List<Inmueble> lista){
        List<Inmueble> resultado = new ArrayList();
        List<Inmueble> Allinm = AllInmueble();
        if(proposito.equalsIgnoreCase("Vender") || proposito.equalsIgnoreCase("Alquilar")){
          if(!lista.isEmpty()){              
              for(Inmueble inm : lista){              
                  if(inm.getProposito().equalsIgnoreCase(proposito)){
                       resultado.add(inm);
                  }                                    
              }              
          }
          else{
              if(!Allinm.isEmpty()){             
                for(Inmueble inm : Allinm){                 
                    if(inm.getProposito().equalsIgnoreCase(proposito)){
                       resultado.add(inm);
                    }                                
                }                          
              }   
          }                  
          return resultado;
        }
        else{
            return lista; 
        }
     }                     
     
    public List<Inmueble> findInmRambla(int metros,List<Inmueble> lista){
        List<Inmueble> resultado = new ArrayList();
        List<Inmueble> Allinm = AllInmueble();
        if(metros !=0){
            int pp=0;
            Statement s = null;
            Connection conexion =  null;
            String resulttabla="";         
            try{
                conexion =  Conexion_geografica.getConnection();
                s = conexion.createStatement();
            }
            catch (SQLException   e){
                e.printStackTrace();
            }   
             if(!lista.isEmpty()){   
                for(Inmueble inm : lista){              
                    String consultageo ="SELECT distinct i.gid\n" +
                                "FROM inmueble i join borde_rambla r\n" +
                                "ON ST_Intersects(i.geom,ST_TRANSFORM(ST_BUFFER(r.geom,"+metros+",'endcap=round join=round'),4326)) and i.gid ="+inm.getGidInm();
                    try {
                        ResultSet result = s.executeQuery(consultageo);
                        while (result.next()) {               // Situar el cursor          
                            resulttabla = result.getString(1);                                                                  
                            int gid = Integer.parseInt(resulttabla);
                            resultado.add(inmfacade.findInmueble(gid));
                        }
                    } catch (SQLException ex) { }                

                } 
             }
             else{
                  if(!Allinm.isEmpty()){             
                    for(Inmueble inm : Allinm){                 
                        String consultageo ="SELECT distinct i.gid\n" +
                                "FROM inmueble i join borde_rambla r\n" +
                                "ON ST_Intersects(i.geom,ST_TRANSFORM(ST_BUFFER(r.geom,"+metros+",'endcap=round join=round'),4326)) and i.gid ="+inm.getGidInm();
                        try {
                        ResultSet result = s.executeQuery(consultageo);
                        while (result.next()) {               // Situar el cursor          
                            resulttabla = result.getString(1);                                                                  
                            int gid = Integer.parseInt(resulttabla);
                            resultado.add(inmfacade.findInmueble(gid));
                        }
                        } catch (SQLException ex) { }                                     
                    }                          
                  }   
             }            
             return resultado;
        }
        else{
            return lista;
        }
    } 
    
    public List<Objeto> findInmSupermercado(int metros,List<Inmueble> lista){
        List<Inmueble> resultado = new ArrayList();
        List<Inmueble> Allinm = AllInmueble();
        List<Objeto> objetos = new ArrayList();                    
        
        if(metros !=0){            
            Statement s = null;
            Connection conexion =  null;
            String resulttabla="";         
            try{
                conexion =  Conexion_geografica.getConnection();
                s = conexion.createStatement();
            }
            catch (SQLException   e){
                e.printStackTrace();
            }   
             if(!lista.isEmpty()){   
                for(Inmueble inm : lista){              
                    String consultageo ="SELECT ST_AsText(i.geom), c.gid,c.tipo, c.nbre,ST_AsText(ST_TRANSFORM(c.geom,4326))\n" +
                                "FROM inmueble i join comercios c\n" +
                                "ON ST_Intersects(i.geom,ST_TRANSFORM(ST_BUFFER(c.geom,"+metros+",'endcap=round join=round'),4326)) and i.gid ="+inm.getGidInm()+"and c.tipo = 'Supermercado'";
                    try {
                        ResultSet result = s.executeQuery(consultageo);
                        String coordeandasinm = "";
                        int verifico = 0;
                        while (result.next()) {// Situar el cursor     
                            Objeto objsupermercado = new Objeto();                                                                                  
                            verifico = 1; // lo utilizo para saber si ingreso al while. Significa que se obtivieron datos en la consutla sql                                                        
                            coordeandasinm = result.getString(1);
                            objsupermercado.setGid("super="+result.getString(2));
                            objsupermercado.setTipo(result.getString(3));
                            objsupermercado.setNombre(result.getString(4));                           
                            objsupermercado.setCoordenadas(CadenaString(result.getString(5)));
                            objetos.add(objsupermercado);                            
                        }
                        if(verifico == 1){
                            Objeto objinmueble = new Objeto();   
                            if(inm.getTipo() == 1){objinmueble.setTipo("casa");}
                            if(inm.getTipo() == 2){objinmueble.setTipo("apartamento");}                            
                            objinmueble.setCoordenadas(CadenaString(coordeandasinm));
                            objinmueble.setNombre(inm.getTitulo());
                            objinmueble.setGid(Integer.toString(inm.getGidInm()));
                            objetos.add(objinmueble);
                        }
                    } catch (SQLException ex) { }                

                } 
             }
             else{
                  if(!Allinm.isEmpty()){             
                    for(Inmueble inm : Allinm){                 
                        String consultageo ="SELECT ST_AsText(i.geom), c.gid,c.tipo, c.nbre,ST_AsText(ST_TRANSFORM(c.geom,4326))\n" +
                                "FROM inmueble i join comercios c\n" +
                                "ON ST_Intersects(i.geom,ST_TRANSFORM(ST_BUFFER(c.geom,"+metros+",'endcap=round join=round'),4326)) and i.gid ="+inm.getGidInm()+"and c.tipo = 'Supermercado'";
                        try {
                        ResultSet result = s.executeQuery(consultageo);
                        String coordeandasinm = "";
                        int verifico = 0;
                        while (result.next()) {               // Situar el cursor    
                            Objeto objsupermercado = new Objeto();                              
                            verifico = 1; // lo utilizo para saber si ingreso al while. Significa que se obtivieron datos en la consutla sql                            
                            coordeandasinm = result.getString(1);
                            objsupermercado.setGid("super="+result.getString(2));
                            objsupermercado.setTipo(result.getString(3));
                            objsupermercado.setNombre(result.getString(4));
                            objsupermercado.setCoordenadas(CadenaString(result.getString(5)));                            
                            objetos.add(objsupermercado);
                            
                        }
                        if(verifico == 1){
                            Objeto objinmueble = new Objeto();   
                            if(inm.getTipo() == 1){objinmueble.setTipo("casa");}
                            if(inm.getTipo() == 2){objinmueble.setTipo("apartamento");}
                            objinmueble.setCoordenadas(CadenaString(coordeandasinm));
                            objinmueble.setNombre(inm.getTitulo());
                            objinmueble.setGid(Integer.toString(inm.getGidInm()));
                            objetos.add(objinmueble);
                        }
                        } catch (SQLException ex) { }                                     
                    }                          
                  }   
             }
             //Quito los duplicados del arraylist
             return QuitarRepetidos(objetos);
             //return objetos;           
        }
        else{           
            return objetos;
        }                
    }
    
    public List<Objeto> findParadas(int metros,List<Objeto> lista){
        List<Inmueble> resultado = new ArrayList();
        List<Inmueble> Allinm = AllInmueble();
        List<Objeto> objetos = new ArrayList();  
        List<Objeto> supermercado = new ArrayList(); 
        
        if(metros !=0){            
            Statement s4 = null;
            Connection conexion4 =  null;
            String resulttabla="";         
            try{
                conexion4 =  Conexion_geografica.getConnection();
                s4 = conexion4.createStatement();
            }
            catch (SQLException   e){
                e.printStackTrace();
            }   
             if(!lista.isEmpty()){   
                for(Objeto obj : lista){
                    if(obj.getTipo() == "casa" || obj.getTipo()== "apartamento"){                    
                        String consultageo = "SELECT distinct(ST_AsText(i.geom), p.gid, p.cod_ubic_p,ST_AsText( ST_TRANSFORM(p.geom,4326))) FROM inmueble i join paradas p ON ST_Intersects(i.geom,ST_TRANSFORM(ST_BUFFER(p.geom,"+metros+",'endcap=round join=round'),4326)) and i.gid ="+obj.getGid();
                        try {
                            ResultSet result = s4.executeQuery(consultageo);
                            String tupla = "";
                            String coordeandasinm = "";

                            int verifico = 0;
                            while (result.next()) {// Situar el cursor     
                                Objeto omnibus = new Objeto();                                                                                  
                                verifico = 1; // lo utilizo para saber si ingreso al while. Significa que se obtivieron datos en la consutla sql                                                        
                                tupla = result.getString(1);
                                String[] palabrasSeparadas = tupla.split(",");                            
                                coordeandasinm = CadenaString(palabrasSeparadas[0]);

                                omnibus.setGid("bus="+palabrasSeparadas[2]);
                                omnibus.setTipo("parada");
                                omnibus.setNombre("");                           
                                omnibus.setCoordenadas(CadenaString(palabrasSeparadas[3]));
                                objetos.add(omnibus);                            
                            }
                            if(verifico == 1){                            
                                objetos.add(obj);
                            }
                        } catch (SQLException ex) { }                
                    }
                    if(obj.getTipo().equalsIgnoreCase("Supermercado")){
                        objetos.add(obj);
                    }
                }                 
             }
             else{
                  if(!Allinm.isEmpty()){             
                    for(Inmueble inm : Allinm){                 
                           String consultageo ="SELECT distinct(ST_AsText(i.geom), p.gid, p.cod_ubic_p,ST_AsText( ST_TRANSFORM(p.geom,4326)))\n" +
                                "FROM inmueble i join paradas p\n" +
                                "ON ST_Intersects(i.geom,ST_TRANSFORM(ST_BUFFER(p.geom,"+metros+",'endcap=round join=round'),4326)) and i.gid ="+inm.getGidInm();
                        try {
                        ResultSet result = s.executeQuery(consultageo);
                        String tupla = "";
                        String coordeandasinm = "";
                        int verifico = 0;
                        while (result.next()) {               // Situar el cursor    
                           Objeto omnibus = new Objeto();                                                                                  
                            verifico = 1; // lo utilizo para saber si ingreso al while. Significa que se obtivieron datos en la consutla sql                                                        
                            tupla = result.getString(1);
                            String[] palabrasSeparadas = tupla.split(",");                            
                            coordeandasinm = CadenaString(palabrasSeparadas[0]);
                            
                            omnibus.setGid("bus="+palabrasSeparadas[1]);
                            omnibus.setTipo("parada");
                            omnibus.setNombre("");                           
                            omnibus.setCoordenadas(CadenaString(palabrasSeparadas[2]));
                            objetos.add(omnibus);                            
                        }
                        if(verifico == 1){
                            Objeto objinmueble = new Objeto();   
                            if(inm.getTipo() == 1){objinmueble.setTipo("casa");}
                            if(inm.getTipo() == 2){objinmueble.setTipo("apartamento");}
                            objinmueble.setCoordenadas(CadenaString(coordeandasinm));
                            objinmueble.setNombre(inm.getTitulo());
                            objinmueble.setGid(Integer.toString(inm.getGidInm()));
                            objetos.add(objinmueble);
                        }
                        } catch (SQLException ex) { }                                     
                    }                          
                  }   
             }
             //Quito los duplicados del arraylist
             List<Objeto> objunico = QuitarRepetidos(objetos);
             
             if(!objetos.isEmpty()){
                
                    for(Objeto obj2 : objunico){
                        if(obj2.getTipo() == "parada"){
                            String[] pp = obj2.getGid().split("=");
                            String gid = pp[1];                            
                            String consultalinea = "SELECT distinct(desc_linea) FROM paradas WHERE cod_ubic_p = "+ gid;
                            try {
                                ResultSet result = s4.executeQuery(consultalinea);
                                obj2.setNombre("");
                                while (result.next()) {  
                                    obj2.setNombre(obj2.getNombre()+ ","+result.getString(1));                                    
                                }
                            } catch (SQLException ex) { }
                        }
                    }
             }
             return objunico;           
        }
        else{           
            return lista;
        } 
    }
    
    
    
    
    public String CadenaString (String cadena){
        //le quito el segundo parentesis
        String delimitadores= "\\)";
        String[] palabrasSeparadas = cadena.split(delimitadores);
        String primero = palabrasSeparadas[0];
        
        //le quito la palabra point y el primer parentesis
        String delimitadores2= "\\(";
        String[] palabrasSeparadas2 = primero.split(delimitadores2);
        String segundo = palabrasSeparadas2[1];               
        
        return segundo;    
    } 
     
    
     
    public List<Objeto> Filtro(int banios, int habitaciones, int pisos, boolean garage, boolean jardin, String proposito,int metroscosta, int metrossuper, int metrosparada,Double preciomin,Double preciomax){
        List<Inmueble> Allinm = AllInmueble();
        List<Inmueble> lista = new ArrayList();
        List<Inmueble> inicio = new ArrayList();
        //List<String> resultado = new ArrayList<String>();
        List<Objeto> objetos = new ArrayList();       
        List<Objeto> objetos2 = new ArrayList(); 
        String tipo="";
        
        Statement s = null;
        Connection conexion =  null;
        String resulttabla="";         
        try{
            conexion =  Conexion_geografica.getConnection();
            s = conexion.createStatement();
        }
        catch (SQLException   e){
            e.printStackTrace();
        }
        
        if(!Allinm.isEmpty()){
            
            lista = findproposito(proposito,inicio);  //vender o alquilar
                if(lista.isEmpty()){
                    return objetos;
                }
            if(preciomax != 0 && preciomin != 0 ){
                lista = findprecio(preciomax,preciomin,lista);  
            }
                if(preciomax != 0 && preciomin != 0 && lista.isEmpty()){
                    return objetos;
                }
            if(preciomax == 0 && preciomin != 0 ){
                lista =findpreciomin(preciomin,lista);
            }
                if(preciomax == 0 && preciomin != 0 && lista.isEmpty() ){
                    return objetos;
                }
            if(preciomax != 0 && preciomin == 0 ){
                lista =findpreciomax(preciomax,lista);
            }
                if(preciomax != 0 && preciomin == 0 && lista.isEmpty()){
                    return objetos;
                }
                
            lista = findhabitaciones(habitaciones,lista);  
                if(habitaciones != 0 && lista.isEmpty()){
                     return objetos;
                }
            lista = findbanio(banios,lista);
                if(banios != 0 && lista.isEmpty()){
                     return objetos;
                }
            lista = findpisos(pisos,lista);
                if(pisos != 0 && lista.isEmpty()){
                     return objetos;
                }
            lista = findgarage(garage,lista);
                if(garage == true && lista.isEmpty()){
                     return objetos;
                }
            lista = findjardin(jardin,lista);
                if(jardin == true && lista.isEmpty()){
                     return objetos;
                }
            lista = findInmRambla(metroscosta,lista);
                if(metroscosta != 0 && lista.isEmpty()){
                     return objetos;
                }
            objetos2 = findInmSupermercado(metrossuper,lista);
            
            if(!objetos2.isEmpty()){
                objetos = findParadas(metrosparada,objetos2);
            }
            else{                
                objetos = findParadas(metrosparada,convertirAobjeto(lista));
            }
            
            if(!objetos.isEmpty() ){
                return objetos;
            }                      
            else{
                for(Inmueble inm : lista){
                    Objeto obj = new Objeto(); 
                    String consultageo = "SELECT ST_AsText(geom) FROM inmueble WHERE  gid ="+inm.getGidInm();
                    try {
                        ResultSet result = s.executeQuery(consultageo);
                        while (result.next()) {               // Situar el cursor                    3 
                            resulttabla = result.getString(1);                            
                            //trato a la cedena entera para obtener solo las coordenadas con CadenaString                            
                            obj.setCoordenadas(CadenaString(resulttabla));
                            obj.setNombre(inm.getTitulo());
                            if(inm.getTipo() == 1){tipo ="casa";}
                            if(inm.getTipo() == 2){tipo = "apartamento";}
                            obj.setTipo(tipo);
                            obj.setGid(Integer.toString(inm.getGidInm()));                            
                        }                                                    
                    } catch (SQLException ex) {                    
                    }
                    objetos.add(obj);
                }  
                
                return objetos;
            }             
        }    
       // logger.warn("Resultado =  "+resultado.toString());
        return objetos;
    }  
    
    public List<Objeto> GetEstadoInm(){
        List<Inmueble> Allinm = AllInmueble();
        List<Inmueble> resultado = new ArrayList();
        //reviso si los inmuebles tiene el estado cerrao o cancelado, en ese caso no los agrego a la lista.
        for(Inmueble inm : Allinm){
            if(!inm.getEstado().equalsIgnoreCase("cerrada") || !inm.getEstado().equalsIgnoreCase("cancelada") ){
                resultado.add(inm);
            }
        }        
        List<Objeto> objetos = convertirAobjetoEstado(resultado);    
        return objetos;    
    }
     
    
    public int findInmueblegid(String x, String y){              
        int gid = 0;
        
        //creo el string para la consulta
        String coordenadas = "POINT(" + x + " " + y +")";
                
        Statement s = null;
        Connection conexion =  null;
        String resulttabla="";         
        try{
            conexion =  Conexion_geografica.getConnection();
            s = conexion.createStatement();
        }
        catch (SQLException   e){
            e.printStackTrace();
        }                          
        String consultageo = "SELECT gid FROM inmueble WHERE  geom = ST_GeographyFromText('"+coordenadas+"')";
            try {
                ResultSet result = s.executeQuery(consultageo);
                while (result.next()) {               // Situar el cursor          
                    resulttabla = result.getString(1);                                                  
                    gid = Integer.parseInt(resulttabla);                                                                          
                }
            
                conexion.close();
            } catch (SQLException ex) {                    
              }                                                                                                                                                  
        return gid;
    }
    
    
    public List<String> getInmueble(String x, String y){
        List<String> retorno = new ArrayList();
        if(!x.equals("") && !y.equals("")){
            //obtengo el gid del inmueble desde la coordenadas
            int gid = findInmueblegid(x,y);        
            //obtengo el inmueble desde el gid
            
            Inmueble inm = inmfacade.findInmueble(gid);

            if(inm.getGidInm()!=0){
                retorno.add(Integer.toString(gid));
                retorno.add(inm.getProposito());
                retorno.add(inm.getEstado());
                retorno.add(Integer.toString(inm.getTipo()));
                retorno.add(String.valueOf(inm.getValormax()));
                retorno.add(String.valueOf(inm.getValormin()));
                retorno.add(inm.getDireccion());
                retorno.add(Integer.toString(inm.getPadron()));
                retorno.add(inm.getIdPropietario().getNombre());
                retorno.add(Integer.toString(inm.getBanios()));
                retorno.add(Integer.toString(inm.getHabitaciones()));
                retorno.add(Boolean.toString(inm.getGarage()));
                retorno.add(Boolean.toString(inm.getJardin()));
                retorno.add(inm.getDescripcion());
                retorno.add(inm.getTitulo());
                retorno.add(Integer.toString(inm.getGidzona().getGidzona()));
                retorno.add(inm.getIdAdmin().getNombre());
                retorno.add(x+" "+y);
            }
        }
        return retorno;
    }
    
    //paso algunos datos para mostrar en la pantalla al hacer clicl
    public List<String> getInmueblebasico(String x, String y){
        List<String> retorno = new ArrayList();
        if(!x.equals("") && !y.equals("")){
            //obtengo el gid del inmueble desde la coordenadas
            int gid = findInmueblegid(x,y);        
            //obtengo el inmueble desde el gid
            
            Inmueble inm = inmfacade.findInmueble(gid);

            if(inm.getGidInm()!=0){
             
                //retorno.add(Integer.toString(gid));
                //retorno.add(inm.getProposito());
                retorno.add(inm.getEstado());
                retorno.add(Integer.toString(inm.getTipo())); //casa o apartamento
                retorno.add(String.valueOf(inm.getValormax()));
               //retorno.add(String.valueOf(inm.getValormin()));
                retorno.add(inm.getDireccion());
                //retorno.add(Integer.toString(inm.getPadron()));
                //retorno.add(inm.getIdPropietario().getNombre());
                //retorno.add(Integer.toString(inm.getBanios()));
                //retorno.add(Integer.toString(inm.getHabitaciones()));
                //retorno.add(Boolean.toString(inm.getGarage()));
                //retorno.add(Boolean.toString(inm.getJardin()));
                //retorno.add(inm.getDescripcion());
                retorno.add(inm.getTitulo());
                
                //retorno.add(Integer.toString(inm.getGidzona().getGidzona()));
                //retorno.add(inm.getIdAdmin().getNombre());
                //retorno.add(x+" "+y);
                
                //imagen destacada del inmueble (para mostrar en la portada)
                Imagenes img = imagenfacade.findImgPrincipal(gid);
               retorno.add(img.getRuta());
            }
        }
        return retorno;
    }
    
    public List<String> InmRambla(int metros){
        List<String> coordenadas = new ArrayList();
        Statement s = null;
        Connection conexion =  null;
        String resulttabla="";         
        try{
            conexion =  Conexion_geografica.getConnection();
            s = conexion.createStatement();
        }
        catch (SQLException   e){
            e.printStackTrace();
        }   
        String consultageo ="SELECT distinct ST_AsText(i.geom)\n" +
                            "FROM inmueble i join borde_rambla r\n" +
                            "ON ST_Intersects(i.geom,ST_TRANSFORM(ST_BUFFER(r.geom,"+metros+",'endcap=round join=round'),4326))";
        try {
            ResultSet result = s.executeQuery(consultageo);
            while (result.next()) {               // Situar el cursor          
                resulttabla = result.getString(1);                                                  
                String solocoordenadas = CadenaString(resulttabla);  
                coordenadas.add(solocoordenadas);
            }
            conexion.close();
        } 
        catch (SQLException ex) {                    
        }
        return coordenadas;
    }
    
    public List<Objeto> QuitarRepetidos(List<Objeto> lista){
        List<Objeto> listaLimpia = new ArrayList();
        Map<String, Objeto> mapObjetos = new HashMap<String, Objeto>(lista.size());
        int a = 0;
        for(Objeto o : lista) {
            mapObjetos.put(o.getGid(), o);
            a++;
        }
        for(Entry<String, Objeto> o : mapObjetos.entrySet()) {
            listaLimpia.add(o.getValue());        
        }
        return listaLimpia;
    }
    
    public List<Objeto> convertirAobjeto(List<Inmueble> lista){
        List<Objeto> objetos = new ArrayList();
        if(!lista.isEmpty()){
            for(Inmueble inm : lista){
                Objeto obj = new Objeto();
                obj.setGid(Integer.toString(inm.getGidInm()));
                if(inm.getTipo() == 1){obj.setTipo("casa");}
                if(inm.getTipo() == 2){obj.setTipo("apartamento");}
                obj.setNombre(inm.getTitulo());
                ////
                Statement s3 = null;
                Connection conexion3 =  null;
                String resulttabla="";  
                try{
                    conexion3 =  Conexion_geografica.getConnection();
                    s3 = conexion3.createStatement();
                }
                catch (SQLException   e){
                    e.printStackTrace();
                }
                String consultageo = "select ST_AsText(geom)from inmueble where gid="+inm.getGidInm();
                try {
                    ResultSet result = s3.executeQuery(consultageo);
                    while (result.next()) {               // Situar el cursor          
                        resulttabla = result.getString(1);                                                  
                        String solocoordenadas = CadenaString(resulttabla);
                        obj.setCoordenadas(solocoordenadas);
                     }
                    
                    //conexion.close();
                } 
                catch (SQLException ex) {}  
                objetos.add(obj);
            }            
        }
        
        return objetos;
    }
    
    public List<Objeto> convertirAobjetoEstado(List<Inmueble> lista){
        List<Objeto> objetos = new ArrayList();
        if(!lista.isEmpty()){
            for(Inmueble inm : lista){
                Objeto obj = new Objeto();
                obj.setGid(Integer.toString(inm.getGidInm()));
                if(inm.getTipo() == 1){obj.setTipo("casa");}
                if(inm.getTipo() == 2){obj.setTipo("apartamento");}
                obj.setNombre(inm.getEstado());
                ////
                Statement s4 = null;
                Connection conexion4 =  null;
                String resulttabla="";  
                try{
                    conexion4 =  Conexion_geografica.getConnection();
                    s4 = conexion4.createStatement();
                }
                catch (SQLException   e){
                    e.printStackTrace();
                }
                String consultageo = "select ST_AsText(geom)from inmueble where gid="+inm.getGidInm();
                try {
                    ResultSet result = s4.executeQuery(consultageo);
                    while (result.next()) {               // Situar el cursor          
                        resulttabla = result.getString(1);                                                  
                        String solocoordenadas = CadenaString(resulttabla);
                        obj.setCoordenadas(solocoordenadas);
                     }
                    
                    //conexion.close();
                } 
                catch (SQLException ex) {}  
                objetos.add(obj);
            }            
        }
        
        return objetos;
    }
    
    public int buscozona(String x, String y){
        int zona = inmfacade.zonaInmueble(x,y);
        
        return zona;
    }
    
     public void creardemandazona(int gidzona){
         Demanda demanda = new Demanda();
         Zonas zona  = zonasfacade.buscarZona(gidzona);
         demanda.setGidzona(zona);
         java.util.Date fecha = new Date();
         demanda.setFecha(fecha);
         
         demandafacade.createdemanda(demanda);
     }

    public String getDireccion(String x, String y) {
        
        String retorno="";
         try{   
            try { 
               logger.warn("CONEXION A establecer");
               conexion = OrigenDatos.getConnection();
                if (conexion.isClosed()){
                   logger.warn("CONEXION cerrada");
                   PoolConexiones pool= new PoolConexiones() ;
                   conexion = pool.getConnectionFromPool();
                   logger.warn("CONEXION = "+ conexion.getSchema());
                }
            }
            catch(ClassNotFoundException e){
                e.printStackTrace();
            }
            try{
                s = conexion.createStatement();
                String consulta = "select * from num_puertas z where ST_Intersects("
                        + "Geography(ST_Transform(z.geom,4326)),"
                        + "ST_GeographyFromText('SRID=4326;POINT("
                        + x 
                        + " "
                        + y 
                        + ")'))";
               
                logger.warn("Consulta ="+consulta);
                
                try {
                    ResultSet result = s.executeQuery(consulta);
                    while (result.next()) {               // Situar el cursor
                         logger.warn("Direccion "+ result.toString());
                        // result.getString(1);                                                                  
                        retorno= result.getString(4)+" "+ result.getString(4) ;   
                        
                    }
                } 
                catch (SQLException ex) { 
                    ex.printStackTrace();
                }                
                conexion.close();
                OrigenDatos.returnConnection(conexion);
                //resultado =true; 
               }
            catch(SQLException e){
                 e.printStackTrace();
            }
        } 
        catch (SQLException e) {
            logger.error("Error al consultar si hay interseccion");
            e.printStackTrace();
            resultado= false;
        }
        return retorno;
        

//To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean AgregarImagen(Imagenes img){
         return imagenfacade.crearImagen(img);
     }
}
