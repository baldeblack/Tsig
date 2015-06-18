package com.logica;

import Extras.JsonConverter;
import Extras.OrigenDatos;
import Extras.PoolConexiones;
import com.DAO.Conexion_BaseRelacional;
import com.DAO.Conexion_geografica;
import com.DAO.DemandaFacadeLocal;
import com.DAO.ZonasFacadeLocal;
import com.entity.Demanda;
import com.entity.Zonas;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
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
     @EJB
    private DemandaFacadeLocal demandafacade;
    
    
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
    
     public void creardemandazona(int gidzona){
         Demanda demanda = new Demanda();
         Zonas zona  = zonasfacade.buscarZona(gidzona);
         demanda.setGidzona(zona);
         java.util.Date fecha = new Date();
         demanda.setFecha(fecha);
         
         demandafacade.createdemanda(demanda);
     }
     
     public List<String> demandaporzona(String fechainicio, String fechafin){        
        List<Zonas> allzonas = new ArrayList();
        List<String> reporte = new ArrayList();
        List<Demanda> demandasbusqueda = new ArrayList();
                
        Statement s2 = null;        
        Connection conexion2 =  null;     
        Statement s3 = null;        
        Connection conexion3 =  null; 
        int giddemanda;
        int gidzona;
        try{
            conexion2 =  Conexion_BaseRelacional.getConnection();
            s2 = conexion2.createStatement();
            
            String consultageo ="select * from demanda where fecha >= '"+fechainicio +"' and fecha <='"+fechafin+"'";
            ResultSet result = s2.executeQuery(consultageo);
            while(result.next()) 
                {               // Situar el cursor                          
                giddemanda = Integer.parseInt(result.getString(1));        
                Demanda d = demandafacade.find(giddemanda);                
                //obtengo una lista de todas las demandas que hay entre las fechas solicitadas
                demandasbusqueda.add(d);
                }
            conexion3 =  Conexion_BaseRelacional.getConnection();
            s3 = conexion3.createStatement();
            String consulta ="select distinct gidzona from demanda where fecha >= '"+fechainicio +"' and fecha <='"+fechafin+"'";
            ResultSet result2 = s2.executeQuery(consulta);
            while(result2.next()){
                gidzona = Integer.parseInt(result2.getString(1)); 
                Zonas zon = zonasfacade.buscarZona(gidzona);
                allzonas.add(zon);
            }
            
            reporte = crecimientozonas(allzonas, demandasbusqueda);
        }
        catch (SQLException   e){
            e.printStackTrace();
        }                       
        
        
        return reporte;
    }
     
     
    public List<String> crecimientozonas(List<Zonas> zonas, List<Demanda> demandas){
        List<String> resultadofinal = new ArrayList();
        //List<Zonas> zonas = zonasfacade.allZona();
        //List<Demanda> demandas = demandafacade.findAlldemanda();
        if(!zonas.isEmpty()&& !demandas.isEmpty()){
        
            int cantididaz = zonas.size();
            int promedio = 100/cantididaz;
            int cotasuperior = promedio + 5;
            int cotainferior = promedio - 5;

            int totalvisitas = demandas.size();

            List<String> vistasporzonas = new ArrayList();
            List<String> porcentajezonas = new ArrayList();


            for(Zonas z : zonas){
                int cantidadvisitas = 0;
                for(Demanda d : demandas){
                    if(d.getGidzona().getGidzona() == z.getGidzona()){
                        cantidadvisitas++;
                    }                
                }
                String resultado = Integer.toString(z.getGidzona()) + "," + cantidadvisitas+","+z.getNombre();
                vistasporzonas.add(resultado);
            }

            for(String s : vistasporzonas){
                String[] palabrasSeparadas = s.split(",");
                int visitas =  Integer.parseInt(palabrasSeparadas[1]);
                int porcentaje = (visitas * 100)/totalvisitas;
                String resultado2 = palabrasSeparadas[0] + "," + Integer.toString(porcentaje)+","+ palabrasSeparadas[2];

                porcentajezonas.add(resultado2);

            }

            for(String r : porcentajezonas){
                String[] palabrasSeparadas = r.split(",");
                int porcejate = Integer.parseInt(palabrasSeparadas[1]);

                if(porcejate > cotasuperior){
                    String alta = palabrasSeparadas[0]+","+palabrasSeparadas[2]+","+palabrasSeparadas[1]+",ALTA:";
                    resultadofinal.add(alta);
                }
                if(porcejate <= cotasuperior && porcejate >= cotainferior){
                    String media = palabrasSeparadas[0]+","+palabrasSeparadas[2]+","+palabrasSeparadas[1]+",MEDIA:";
                    resultadofinal.add(media);
                }
                if(porcejate < cotainferior){
                    String baja = palabrasSeparadas[0]+","+palabrasSeparadas[2]+","+palabrasSeparadas[1]+",BAJA:";
                    resultadofinal.add(baja);
                }            
            }
            return resultadofinal;
        }
        return resultadofinal;
    }
}