/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.entity.Imagenes;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author vane
 */
@Stateless
public class ImagenesFacade extends AbstractFacade<Imagenes> implements ImagenesFacadeLocal {
    @PersistenceContext(unitName = "PSIG-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ImagenesFacade() {
        super(Imagenes.class);
    }
    
    @Override
    public List<Imagenes> findAllImg() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Imagenes.class));
        return getEntityManager().createQuery(cq).getResultList();                
    }
    
    //busco la imagen destacada de un imbuele para mostrar en la vista
    @Override
    public Imagenes findImgPrincipal(int gidinm){
        List<Imagenes> allimg = findAllImg();
        Imagenes imgreturn = new Imagenes();
        if(!allimg.isEmpty()){
            for(Imagenes img : allimg){
                int buscoid = img.getGidInm().getGidInm();
                if(buscoid == gidinm && img.getDestacada() == true){
                    imgreturn = img;
                }
            }
        }
        return imgreturn;
    }
    
}