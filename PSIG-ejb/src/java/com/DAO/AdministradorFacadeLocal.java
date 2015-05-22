/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.entity.Administrador;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vane
 */
@Local
public interface AdministradorFacadeLocal {

    void create(Administrador administrador);

    void edit(Administrador administrador);

    void remove(Administrador administrador);

    Administrador find(Object id);

    List<Administrador> findAll();

    List<Administrador> findRange(int[] range);

    int count();
    
    boolean crearAdministrador(Administrador adm);
    
    public Administrador findadm(String login);
    
    boolean editAdministrador(Administrador adm);
    
    boolean login(String login, String clave);
}
