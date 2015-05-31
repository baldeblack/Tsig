/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;


import Extras.OrigenDatos;
import Extras.PoolConexiones;
import com.entity.Zonas;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

@Stateless
public class ZonasFacade extends AbstractFacade<Zonas> implements ZonasFacadeLocal {
    
    @PersistenceContext(unitName = "PSIG-ejbPU")
    private EntityManager em;

    private Connection conexion;
    static final Logger logger = Logger.getLogger(ZonasFacade.class.getName()); 
    private Statement s;
    private boolean resultado;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ZonasFacade() {
        super(Zonas.class);
    }
    
    @Override
    public List<Zonas> allZona() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Zonas.class));
        return getEntityManager().createQuery(cq).getResultList();                
    }
    
    public Zonas buscarZona(int gidzona){
        List<Zonas> list_zona = findAll();                     
        
        for(int i=0;i< list_zona.size()-1;i++){
            Zonas zon = (Zonas) list_zona.get(i);
            if(gidzona == zon.getGidzona()){
                //busco si tiene el mismo login y si es asi lo retorno
                return zon;
            } 
        }   
        return null;
    }
    
     public boolean editarZona(Zonas zon){
        em.merge(zon);
        return true;
    }
    
    

    @Override
    public boolean borrar(Zonas zon) {
        em.remove(zon);
        return true;
    }

    public int crearZona(Zonas zon,String coordenadas ){
        int retorno=0;
        String consulta = "SELECT MAX(i.gidzona) FROM Zonas as i";            
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
                s=conexion.createStatement();
                String consultageo = "insert into public.zonas(gid,nombre_zona,geom) values ("
                    + zon.getGidzona()+",'" 
                    + zon.getNombre()+"'," 
                    + "(SELECT ST_GeomFromText('MULTIPOLYGON("
                    + coordenadas
                    + ")',4326)))";
                    //   "ST_SetSRID( ST_MakePolygon("
                 //ST_GeomFromText('POINT(-71.060316 48.432044)', 4326));

                 logger.warn("CONSULTA  = "+ consultageo);

                 resultado = s.execute(consultageo);
                 conexion.close();
                 OrigenDatos.returnConnection(conexion);
                 
                 retorno=zon.getGidzona();
             }
              catch(SQLException e){
                 e.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return retorno;
     }
    
   
    @Override
    public boolean interseccionZonas(String coordenadas) {
        Boolean resultado=true;
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
                String query = "select * from zonas z where ST_Intersects(Geography(ST_Transform(z.geom,4326)),(SELECT ST_GeomFromText('MULTIPOLYGON("+coordenadas+")',4326)))";
                logger.warn("Consulta ="+query);
                resultado = s.executeQuery(query).next();
                conexion.close();    
                OrigenDatos.returnConnection(conexion);
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
        return resultado;
    }
}
