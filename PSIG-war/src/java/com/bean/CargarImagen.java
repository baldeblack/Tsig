/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.ejb.LocalBean;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.Part;

/**
 *
 * @author nacho
 */
@ManagedBean(name = "cargar")
@LocalBean
public class CargarImagen {

   private Part file;
   private HttpServletRequest servletRequest;
 

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    public String subir(){
        
        String path=servletRequest.getSession().getServletContext().getRealPath("css");
        path=path.substring(0,path.indexOf("\\build"));
        path=path+"\\web\\resources\\css\\";
        
        try{
            InputStream in = file.getInputStream();
            byte[] data= new byte[in.available()];
            in.read(data);
            FileOutputStream out = new FileOutputStream(new File (path+"demo.jpg"));
            out.write(data);
            in.close();
            out.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "exito";
    }
}
