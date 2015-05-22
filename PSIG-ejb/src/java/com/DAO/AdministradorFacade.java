/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.entity.Administrador;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author vane
 */
@Stateless
public class AdministradorFacade extends AbstractFacade<Administrador> implements AdministradorFacadeLocal {
    @PersistenceContext(unitName = "PSIG-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdministradorFacade() {
        super(Administrador.class);
    }
    
    @Override
    public boolean crearAdministrador(Administrador adm){
        Administrador admin = new Administrador(adm.getLogin(),adm.getClave(),adm.getNombre(),adm.getApellido(),adm.getCedula(),adm.getTelefono(),adm.getEmail(),adm.getRol());
        em.persist(admin);
        return true;
     }
    
    @Override
    public boolean editAdministrador(Administrador adm){
        em.merge(adm);        
        return true;
    }
    
    @Override
    public Administrador findadm(String login){
        List<Administrador> todosadm = findAll();
        
        for(int i=0;i< todosadm.size();i++){
            Administrador adm = (Administrador) todosadm.get(i);
            if(login.equals(adm.getLogin())){  //busco si tiene el mismo login y si es asi lo retorno                
                 return adm;
            }
        }       
        return null;
    }
    
    @Override
    public List<Administrador> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Administrador.class));
        return getEntityManager().createQuery(cq).getResultList();                
    }
    
    
    
    @Override
    public boolean login(String login, String clave){
        Administrador adm = findadm(login);  //busco el usuario        
        
        if(adm != null){        //chequeo si encontro un usuario con el dato login ingresado
             if(adm.getClave().equals(clave))
             {
                 return true;
             }          
        }             
        return false;
    }
    
    
}
