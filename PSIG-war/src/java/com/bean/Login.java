/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.logica.AdministradorL;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Gaston
 */
@ManagedBean(name="loginBean")
@SessionScoped
public class Login{

    private String password;
    private String nick;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext facesContext;
    private FacesMessage facesMessage;

    @EJB
    private AdministradorL AdmL;
    
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
        

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Login ()
    { 
        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)facesContext.getExternalContext().getRequest();
    }
    
    public String getUsuarioSession()
    {
        HttpServletRequest prueba = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        
        return  prueba.getSession().getAttribute("nick") +" - "+ prueba.getSession().getAttribute("nombre");
    }
    
    public String checkLogin(){
        
            System.err.println("Entre checklogin");
            boolean check = AdmL.login(nick, password);
            System.err.println("entro:"+check);
            HttpServletRequest prueba = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if(check)
            {
                
                prueba.getSession().setAttribute("nick", nick);
                prueba.getSession().setAttribute("nombre", AdmL.findadm(nick).getNombre());

                return "admin";
            }
            else
            {
                facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario o contraseña invalido",null);
                FacesContext.getCurrentInstance().addMessage(null,facesMessage);
                return "login";
            }

            /*{
                System.err.println("Entre if login");
                httpServletRequest.getSession().setAttribute("nick", nick);
                httpServletRequest.getSession().setAttribute("nombre", AdmL.findadm(nick).getNombre());
                facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO,"Acceso correcto",null);
                facesContext.addMessage(null, facesMessage);
                return "admin";
            }
            else
            {   System.err.println("Entre else login");
                facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario o contraseña invalido",null);
                facesContext.addMessage(null, facesMessage);
                return null;
            }*/
    }
    
    
   /* public void validarLogin()
    {
        AdmL = new AdministradorL();

        if (AdmL.login(nick, password) ){

            System.err.println("Entre if login");
            httpServletRequest.getSession().setAttribute("nick", nick);
            //httpServletRequest.getSession().setAttribute("nombre", AdmL.findadm(nick).getNombre());
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO,"Acceso correcto",null);
            facesContext.addMessage(null, facesMessage);
            try {
                facesContext.getExternalContext().redirect("admin");
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
                System.err.println("Entre else login");
                facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario o contraseña invalido",null);
                facesContext.addMessage(null, facesMessage);
            try {
                facesContext.getExternalContext().redirect("login");
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }*/

    
}
