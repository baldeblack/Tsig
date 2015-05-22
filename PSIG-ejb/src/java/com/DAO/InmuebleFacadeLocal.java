/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.entity.Inmueble;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vane
 */
@Local
public interface InmuebleFacadeLocal {

    void create(Inmueble inmueble);

    //void edit(Inmueble inmueble);

    void remove(Inmueble inmueble);

    Inmueble find(Object id);

    List<Inmueble> findAll();

    List<Inmueble> findRange(int[] range);

    int count();
    
    public int crearInmueble(Inmueble inm, String x, String y);
    
    public Inmueble findInmueble(int gidinmueble);
    public boolean editarInmueble(Inmueble inm);
    
}
