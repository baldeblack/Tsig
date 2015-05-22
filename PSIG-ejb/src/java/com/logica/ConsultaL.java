/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logica;

import com.DAO.ConsultaFacadeLocal;
import com.entity.Consulta;
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
public class ConsultaL {

    @EJB
    private ConsultaFacadeLocal confacadelocal;
    
    public boolean crearConsulta(Consulta consulta){
        Consulta con = new Consulta();
        con.setNombre(consulta.getNombre());
        con.setApellido(consulta.getApellido());
        con.setTelefono(consulta.getTelefono());
        con.setEmail(consulta.getEmail());
        con.setDescripcion(consulta.getDescripcion());
        return confacadelocal.crearConsulta(con);
    }
    
    public boolean editarConsulta(Consulta consulta){
        Consulta con = findSegunId(consulta.getIdConsulta());
        con.setNombre(consulta.getNombre());
        con.setApellido(consulta.getApellido());
        con.setTelefono(consulta.getTelefono());
        con.setEmail(consulta.getEmail());
        con.setDescripcion(consulta.getDescripcion());
        return confacadelocal.editarConsulta(con);
    }
    
    public List<Consulta> listarConsultas(){
        return confacadelocal.findAll();
    }
    
    public Consulta findSegunId(int idC){
        return confacadelocal.findSegunId(idC);
    }
    
}
