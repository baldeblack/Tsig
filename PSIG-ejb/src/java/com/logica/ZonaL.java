/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logica;

import com.DAO.ZonasFacadeLocal;
import com.entity.Zonas;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author vane
 */
@Stateless
@LocalBean
public class ZonaL {
    @EJB
    private ZonasFacadeLocal zonfacadelocal;
    /*
    public boolean crearZona(String gid,String nombre,String descripcion,String demanda,String puntos_zona){
       
        Zonas zon = new Zonas();
        zon.setNombre(nombre);
        zon.setDemandaCollection(demanda);
        
        return zonfacadelocal.crearZona(zon);        
    }
    
    public boolean editZona (String gid,String nombre,String descripcion,String demanda,String puntos_zona){
        Zona zon = new Zona();
        zon.setNombre_Zona(nombre);
        zon.setDescripcion_Zona(descripcion);
        zon.setDemanda(demanda);
        
        return zonfacadelocal.editarZona(zon);     
    }
    
    public Zona findzona(int gid){
        return zonfacadelocal.buscarZona(gid);
    }*/
    
}
