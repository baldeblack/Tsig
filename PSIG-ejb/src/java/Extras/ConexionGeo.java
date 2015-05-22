

package Extras;
import java.sql.*;



/**
 *
 * @author vane
 */
public class ConexionGeo {
    private static ConexionGeo conexion = null;
    private static String url;    
    private static String username;
    private static String pass;
    private static Connection psqlCon = null;
	
    private void establecer_conexion(){
	if(psqlCon !=null){            
            url = "jdbc:postgresql://localhost:5432/Geografica";
            try{
                Class.forName("org.postgresql.Driver");
                psqlCon = DriverManager.getConnection(url, "postgres", "123456");
            }catch(Exception e){
            }
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

    public static ConexionGeo getConexion() {
		if (conexion == null)
                {
                    conexion = new ConexionGeo();
                }       
		return conexion;
    }

    public static Connection getConnection()  {
	if (conexion == null){
            conexion = new ConexionGeo();
            
        }
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
