package Extras;

import com.entity.Inmueble;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Test_conexion {
 
	public static void main(String[] argv) {
 
		System.out.println ("Prueba de conexion geografica");
                Statement s=null;
                Connection conexion =  null;
                Boolean resultado= null;
		try {
                        Inmueble inm= new Inmueble(); 
                        //Conexion_geografica conexion= new Conexion_geografica();
                       
                        try{
                            conexion =  Conexion_geografica.getConnection();
                            s=conexion.createStatement();
                        }
                        catch (SQLException   e){
                            e.printStackTrace();
                        }
                        
                         inm.setGidInm(2222);
                         inm.setTitulo("titulo");
                         inm.setDescripcion("descripcion");
                         
                        /* if (conexion.isClosed()){
                           System.out.println("Conexion cerrada");
                         }*/
                         
                         if(!(conexion.isClosed())){                                
                            try{
                                //s =conexion.createStatement();
                                
                                
                                String consultageo = " INSERT INTO public.inmueble (gid, nombre, descripcion, geom) VALUES (555,'sss', 'sss',( ST_GeomFromText('POINT(-71.064544 42.28787)',32721)))";                      
                                 resultado = s.execute(consultageo);
                            }catch(Exception e){
                                
                                e.printStackTrace();
                            }}
                         
                         
                         
                         
                         
                         //System.out.println(resultado.toString());
                         conexion.close();
                         //conexion.
                         System.out.println("Conexion cerrada");

		} 
                catch (Exception e) {
 
			System.out.println("Error en la conexion");
			e.printStackTrace();
			return;
 
		}
 
        }
}