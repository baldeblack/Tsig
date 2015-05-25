/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.entity.Consulta;
import com.logica.ConsultaL;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author vane
 */
@Stateless
public class ConsultaFacade extends AbstractFacade<Consulta> implements ConsultaFacadeLocal {
    @PersistenceContext(unitName = "PSIG-ejbPU")
    private EntityManager em;

    private static final Logger logger = Logger.getLogger(ConsultaFacade.class.getName()); 
   
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConsultaFacade() {
        super(Consulta.class);
    }
    
    @Override
    public boolean crearConsulta(Consulta consulta){
        Consulta con = new Consulta(consulta.getNombre(),consulta.getApellido(),consulta.getTelefono(),consulta.getEmail(),consulta.getDescripcion(),consulta.getGidInm());
       
        logger.warn("Consulta = "+ con);
        
        em.persist(con);
        return true;
    }
    
    @Override
    public boolean editarConsulta(Consulta consulta){
        em.merge(consulta);        
        return true;
    } 
    
    public Consulta findSegunId(int idC){
        List<Consulta> consultas = findAll();
        
        
        
        
        try{
          for(int i=0;i< consultas.size();i++){
            Consulta con = (Consulta) consultas.get(i);
            if(idC == con.getIdConsulta()){
                 
                logger.warn("Consulta = "+ con.toString());
                return con;
            }
        } 
      }
      catch (Exception e){
           System.err.println("Error: " + e.getMessage());
      }        
        return null;
        
    }
    
    @Override
    public List<Consulta> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Consulta.class));
        
        return getEntityManager().createQuery(cq).getResultList();                
    }
    
}
