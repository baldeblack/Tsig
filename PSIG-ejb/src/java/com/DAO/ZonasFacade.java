/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.entity.Zonas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author vane
 */
@Stateless
public class ZonasFacade extends AbstractFacade<Zonas> implements ZonasFacadeLocal {
    @PersistenceContext(unitName = "PSIG-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ZonasFacade() {
        super(Zonas.class);
    }
    
    public boolean crearZona(Zonas zon){
        em.persist(zon);
        return true;
     }
    
    public boolean editarZona(Zonas zon){
        em.merge(zon);
        return true;
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

    @Override
    public boolean borrar(Zonas zon) {
        em.remove(zon);
        return true;
    }
}
