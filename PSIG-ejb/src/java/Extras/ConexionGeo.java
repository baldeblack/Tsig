package Extras;
import java.sql.*;

public class ConexionGeo {
    private static ConexionGeo conexion=null;
    private static String url;    
    private static String username;
    private static String pass;
    private static Connection conect=null ;
    
    private ConexionGeo(){}
    
    public static ConexionGeo getInstance() {
        if(conexion == null) {
            conexion = new ConexionGeo();
        }
        return conexion;
    }
     
	
    private void establecer_conexion(){
	if(conect !=null){            
            url = "jdbc:postgresql://localhost:5432/Geografica";
            try{
                loadDriver();
               loadConnection();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
    }
    
    public static Connection  loadDriver(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.out.println("Failed to load the driver: " + e);
        }
        return conect;
    
    }
    
     private static void loadConnection() {
        try {
            conect =DriverManager.getConnection(url, "postgres", "123456");
        } catch(SQLException e) {
            System.out.println("Failed to connect to the database: " + url + e);
        }
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

    public static Connection getConexion() {
		if (conect == null)
                {
                        loadDriver();
                        loadConnection();
                }       
		return conect;
    }

	
    public static void cerrarConexion(Connection conect){
		try {
                        conect.close();
                        conect=null;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
	
}
