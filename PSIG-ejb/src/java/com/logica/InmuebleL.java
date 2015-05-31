/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logica;

import Extras.OrigenDatos;
import Extras.PoolConexiones;
import com.DAO.Conexion_geografica;
import com.DAO.ImagenesFacade;
import com.DAO.ImagenesFacadeLocal;
import com.DAO.InmuebleFacadeLocal;
import com.DAO.ZonasFacade;
import com.DAO.ZonasFacadeLocal;
import com.entity.Imagenes;
import com.entity.Inmueble;
import com.entity.Zonas;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
     
     public List<Inmueble> findproposito(String proposito,List<Inmueble> lista){
        List<Inmueble> resultado = new ArrayList();
        List<Inmueble> Allinm = AllInmueble();
        if(proposito.equals("vender") || proposito.equals("alquilar")){
          if(!lista.isEmpty()){              
              for(Inmueble inm : lista){              
                  if(inm.getProposito().equals(proposito)){
                       resultado.add(inm);
                  }                                    
              }              
          }
          else{
              if(!Allinm.isEmpty()){             
                for(Inmueble inm : Allinm){                 
                    if(inm.getProposito().equals(proposito)){
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
     
    
     
    public List<String> Filtro(int banios, int habitaciones, int pisos, boolean garage, boolean jardin, String proposito,int metros){
        List<Inmueble> Allinm = AllInmueble();
        List<Inmueble> lista = new ArrayList();
        List<Inmueble> inicio = new ArrayList();
        List<String> resultado = new ArrayList<String>();
        
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
            
            lista = findproposito(proposito,inicio);
            lista = findhabitaciones(habitaciones,lista);
            lista = findbanio(banios,lista);
            lista = findpisos(pisos,lista);
            lista = findgarage(garage,lista);
            lista = findjardin(jardin,lista);
            lista = findInmRambla(metros,lista);
            for(Inmueble inm : lista){                                  
                String consultageo = "SELECT ST_AsText(geom) FROM inmueble WHERE  gid ="+inm.getGidInm();
                try {
                    ResultSet result = s.executeQuery(consultageo);
                    while (result.next()) {               // Situar el cursor                    3 
                        resulttabla = result.getString(1);                            
                        //trato a la cedena entera para obtener solo las coordenadas
                        String cadena = CadenaString(resulttabla);                            
                        resultado.add(cadena);                            
                    }
                } catch (SQLException ex) {                    
                }                                                                                            
             }             
        }    
        logger.warn("Resultado =  "+resultado.toString());
        return resultado;
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
    
}
