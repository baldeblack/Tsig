package Extras;

import java.sql.Connection;
import java.sql.SQLException;
 
public class OrigenDatos {
  
 static PoolConexiones pool = new PoolConexiones();
  
 //throws ClassNotFoundException, SQLException
 public static Connection getConnection() throws ClassNotFoundException {
  Connection connection = pool.getConnectionFromPool();
  return connection;
 }
  
 public static void returnConnection(Connection connection) {
  pool.returnConnectionToPool(connection);
 }

   
}

