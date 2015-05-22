package Extras;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

        
        
        
public class ConexionSQL {
	
	private static ConexionSQL conexion;
	private static String url;
	private static String username;
	private static String pass;
	private static Connection psqlCon;
	
         public ConexionSQL() {}
        
        public Connection getConexionSQL() {
        
            try {
                Class.forName("org.postgresql.Driver").newInstance();
                url = "jdbc:postgresql://localhost:5432/PSIG";
			username = "postgres";
			pass = "123456";
			psqlCon = DriverManager.getConnection(url,username,pass);
                return psqlCon;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
					
             return psqlCon;
        }

	public static void setUrl(String myUrl) {
		url = myUrl;
	}

	public static void setUsername(String myUser) {
		username = myUser;
	}

	public static void setPassword(String myPass) {
		pass = myPass;
	}

	public static ConexionSQL getConexion() {
		if (conexion == null)
			conexion = new ConexionSQL();                
		return conexion;
	}

	public static Connection getConnection() {
		if (conexion == null)
			conexion = new ConexionSQL();
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