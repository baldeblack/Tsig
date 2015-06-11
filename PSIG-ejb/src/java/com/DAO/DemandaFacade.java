/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.entity.Demanda;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author vane
 */
@Stateless
public class DemandaFacade extends AbstractFacade<Demanda> implements DemandaFacadeLocal {
    @PersistenceContext(unitName = "PSIG-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DemandaFacade() {
        super(Demanda.class);
    }
    
    @Override
    public boolean createdemanda(Demanda demanda){
        em.persist(demanda);        
        return true;
    }
    
    @Override
    public List<Demanda> findAlldemanda() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Demanda.class));
        return getEntityManager().createQuery(cq).getResultList();                
    }
    
    @Override
    public Demanda find(int id){
        List<Demanda> demandas = findAlldemanda();
        for(Demanda d : demandas){
            if(d.getIdDemanda()== id){
                return d;
            }
        }
        return null;
    }
    
}
