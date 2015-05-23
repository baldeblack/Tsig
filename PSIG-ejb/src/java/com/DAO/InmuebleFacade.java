/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;


import Extras.ConexionGeo;
import com.entity.Inmueble;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.*;
/**
 *
 * @author vane
 */
@Stateless
public class InmuebleFacade extends AbstractFacade<Inmueble> implements InmuebleFacadeLocal {
    @PersistenceContext(unitName = "PSIG-ejbPU")
    private EntityManager em;   
    
    
    
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
            Statement s=null;
            Connection conexion =  null;
            Boolean resultado= null;
        try{   
            try{
                conexion =  ConexionGeo.getConexion();
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
                ConexionGeo.cerrarConexion(conexion);
            }
            catch (SQLException   e){
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
        
        for(int i=0;i< todosinm.size()-1;i++){
            Inmueble inm = (Inmueble) todosinm.get(i);
            if(gidinmueble == inm.getGidInm()){
                //busco si tiene el mismo login y si es asi lo retorno
                return inm;
            } 
        }   
        return null;
    }
        
}
