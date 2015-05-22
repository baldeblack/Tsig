/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logica;

import com.DAO.PropietarioFacadeLocal;
import com.entity.Propietario;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Gaston
 */
@Stateless
@LocalBean
public class PropietarioL {
    
    @EJB
    private PropietarioFacadeLocal propFacadeLocal;

    public boolean crearPropietario(String nombre,String apellido,int telefono, String email, int ci){
       
        Propietario prop = new Propietario();
        prop.setNombre(nombre);
        prop.setApellido(apellido);
        prop.setTelefono(telefono);
        prop.setEmail(email);
        prop.setCi(ci);
  
        return propFacadeLocal.crearPropietario(prop);        
    }
    
     
    public boolean editPropietario(Propietario prop){
        Propietario pro = findProp(prop.getCi());
        pro.setNombre(prop.getNombre());
        pro.setApellido(prop.getApellido());
        pro.setTelefono(prop.getTelefono());
        pro.setEmail(prop.getEmail());
        pro.setCi(prop.getCi());

        return propFacadeLocal.editarPropietario(pro);
    }
    
    public List<Propietario> listarPropietarios(){
        return propFacadeLocal.findAll();
    }
    
    public Propietario findProp(int ci){
        return propFacadeLocal.findPropietario(ci);
    }
    
    public Propietario buscarPropietario(Object id){
        return propFacadeLocal.find(id);
    }
    public void borrarPropietario(Propietario prop){
        propFacadeLocal.borrar(prop);
    }
    
    
}
