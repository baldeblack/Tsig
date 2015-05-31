package com.logica;

import Extras.JsonConverter;
import Extras.OrigenDatos;
import Extras.PoolConexiones;
import com.DAO.ZonasFacadeLocal;
import com.entity.Zonas;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
//@LocalBean
public class ZonaL {
    @PersistenceContext(unitName = "PSIG-ejbPU")
    private EntityManager em; 
    
    @EJB
    private ZonasFacadeLocal zonasfacade;
    
    
    static final Logger logger = Logger.getLogger(ZonaL.class.getName()); 
    private Connection conexion;
    private Statement s;
    private boolean resultado;
    
    public int crearZona(Zonas zon, String coordenadas){
        //obtengo el gid mas alto en la base de datos
        int retorno =0;
        try{
            retorno=zonasfacade.crearZona(zon,coordenadas);
      /*  String consulta = "SELECT MAX(i.gidzona) FROM Zonas as i";            
        Query query = em.createQuery(consulta);
        Object pp = query.getSingleResult(); 

        pp = (Integer)pp;
        int Idzon;
        //reviso si la tabla esta bacia
        if(pp != null){
            Idzon = 1 + pp.hashCode();
            logger.warn("Valor id a insertar "+Idzon);
        }
        else{                                                               			
            Idzon = 1 ;
             logger.warn("Valor id a insertar "+Idzon);
        }
        //seteo el id de zona
        zon.setGidzona(Idzon);
        //persisto el zona en la base relacional
        em.persist(zon);
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
                String consultageo = "insert into zona(gid,nombre,geom) values ("
                    + zon.getGidzona()+",'" 
                    + zon.getNombre()+"','" 
                    + "',  ST_SetSRID( ST_MakePoint("
                    + "),4326))";

                 //ST_GeomFromText('POINT(-71.060316 48.432044)', 4326));

                 logger.warn("CONSULTA  = "+ consultageo);
                 
                 resultado = s.execute(consultageo);
                 conexion.close();
                 OrigenDatos.returnConnection(conexion);
             }
              catch(SQLException e){
                 e.printStackTrace();
             }*/
     }  
     catch (Exception e) { 
         System.out.println("Error en la conexion");
         e.printStackTrace();			 
     }     

         return retorno;                                                              
  }    

    public boolean interseccionZonas(String coordenadas) {
        boolean retorno=true;
        
        try{
            logger.warn("Coordenadas  "+coordenadas);
            retorno= zonasfacade.interseccionZonas(coordenadas);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return retorno;
    }
    
    
     public String ConvertirCoordenadas(String coordenadas)
       {
           String puntos=""; 
           try{
                puntos=coordenadas;
                puntos = puntos.replaceAll(Pattern.quote("[[\""), "((");
		puntos = puntos.replaceAll("\"]]", "))");
		puntos = puntos.replaceAll("\",\"", ",");
		puntos = puntos.replaceAll(Pattern.quote("\"],[\""), ")),((");
            }
            catch(Exception e){
                e.printStackTrace();
            }
                   
           return puntos;
       }
    
}