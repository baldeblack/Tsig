/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author vane
 */
public class Conexion_BaseRelacional {
     private static Conexion_BaseRelacional conexion;
	private static String direccion;
	private static String usuario;
	private static String password;
	private static Connection psqlCon;
	
	Conexion_BaseRelacional(){
		try{
			Class.forName("org.postgresql.Driver");
                }
                catch (ClassNotFoundException e) {
                        System.out.println("No se encuentra el driver Postgres");
			e.printStackTrace();
			return;
                }
                try {
                        direccion = "jdbc:postgresql://127.0.0.1:5432/PSIG";
			usuario = "postgres";
			password = "123456";
			psqlCon = DriverManager.getConnection(direccion,usuario,password);
                } 
                catch (SQLException e) {
                        System.out.println("Fallo la conexion");
			e.printStackTrace();
			return;
 
		}
	}
        
        

	public static void setDireccion(String myUrl) {
		direccion = myUrl;
	}

	public static void setUsuario(String usu) {
		usuario = usu;
	}

	public static void setPassword(String pass) {
		password = pass;
	}

	public static Conexion_BaseRelacional getConexion() {
		if (conexion == null)
			conexion = new Conexion_BaseRelacional();
		return conexion;
	}

	public static Connection getConnection() {
		if (conexion == null)
			conexion = new Conexion_BaseRelacional();
		return psqlCon;
	}
	
	public void cerrarConexion(){
		try {
			if (!psqlCon.isClosed()){
				psqlCon.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
