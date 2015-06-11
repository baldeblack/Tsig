/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.entity.Demanda;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vane
 */
@Local
public interface DemandaFacadeLocal {

    void create(Demanda demanda);

    void edit(Demanda demanda);

    void remove(Demanda demanda);

    Demanda find(int id);

    List<Demanda> findAll();

    List<Demanda> findRange(int[] range);

    int count();
   
    public boolean createdemanda(Demanda demanda);
    
    public List<Demanda> findAlldemanda();
    
}
