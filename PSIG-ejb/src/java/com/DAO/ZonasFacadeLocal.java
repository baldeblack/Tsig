/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.entity.Zonas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vane
 */
@Local
public interface ZonasFacadeLocal {

    boolean crearZona(Zonas zon);
    
    boolean editarZona(Zonas zon);
    
    List<Zonas> allZona();

    Zonas buscarZona(int gidzona);
    
    boolean borrar(Zonas zona);
    
}
