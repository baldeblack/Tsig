/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import Extras.OrigenDatos;
import Extras.PoolConexiones;
import com.entity.Inmueble;
import com.entity.Zonas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.*;
import org.apache.log4j.Logger;
/**
 *
 * @author vane
 */
@Stateless
public class InmuebleFacade extends AbstractFacade<Inmueble> implements InmuebleFacadeLocal {
    @PersistenceContext(unitName = "PSIG-ejbPU")
    private EntityManager em;   
    private Connection conexion;
    private Statement s;
    private boolean resultado;
    
    private static final Logger logger = Logger.getLogger(InmuebleFacade.class.getName()); 
    
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InmuebleFacade() {
        super(Inmueble.class);
    }
    
    @Override
    public int crearInmueble(Inmueble inm, String x, String y){
            //obtengo el gid mas alto en la base de datos
            String consulta = "SELECT MAX(i.gidInm) FROM Inmueble as i";            
            Query query = em.createQuery(consulta);
            Object pp = query.getSingleResult(); 
            int Idinm;
            //reviso si la tabla esta bacia
            if(pp != null){
                Idinm = 1 + pp.hashCode();
            }
            else{                                                               			
                Idinm = 1 ;
            }
             //seteo el id de inmueble
            inm.setGidInm(Idinm);
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
                    String consultageo = "insert into inmueblegeo(gid,nombre,descripcion,geom) values ("
                        + Idinm+",'" 
                        + inm.getTitulo()+"','" 
                        + inm.getDescripcion()+"', ST_GeomFromText('POINT("
                        + x 
                        + " "
                        + y 
                        + ")',32721))"; 
                
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
            return inm.getGidInm();                                                              
     }
    
    
    @Override
    public boolean editarInmueble(Inmueble inm){
        em.merge(inm);
        return true;
    }
    
    @Override
    public List<Inmueble> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Inmueble.class));
        return getEntityManager().createQuery(cq).getResultList();                
    }
    
    @Override
    public Inmueble findInmueble(int gidinmueble){
        List<Inmueble> todosinm = findAll();                     
        
        for(Inmueble inm : todosinm){            
            if(gidinmueble == inm.getGidInm()){
                //busco si tiene el mismo login y si es asi lo retorno
                return inm;
            } 
        }   
        return null;
    }

    @Override
    public int zonaInmueble(String x, String y) {
    int gid=0;
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
                String consulta = "select * from zonas z where ST_Intersects("
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
                        String resulttabla = result.getString(1);                                                                  
                        gid = Integer.parseInt(resulttabla);
                        
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
        return gid;
    }
        
}
