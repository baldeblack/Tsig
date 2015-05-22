/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Gaston
 */
@ManagedBean(name = "sesionBean")
@RequestScoped
public class Sesion {

    private String usuario;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext facesContext;
    private FacesMessage facesMessage;
    
    public Sesion() {
        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest=(HttpServletRequest)facesContext.getExternalContext().getRequest();
        
        if (httpServletRequest.getSession().getAttribute("nick")!=null) 
        {
            usuario = httpServletRequest.getSession().getAttribute("nick").toString();
        }
    }
    
    public String cerrarSesion()
    {
        httpServletRequest.getSession().removeAttribute("nick");
        return "index";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    
}
