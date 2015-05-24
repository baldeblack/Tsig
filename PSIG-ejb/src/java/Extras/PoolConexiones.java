package Extras;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PoolConexiones {
  
 List<Connection> availableConnections = new ArrayList<Connection>();
 
 public PoolConexiones()
 {
  initializeConnectionPool();
 }
 
 private void initializeConnectionPool()
 {
  while(!checkIfConnectionPoolIsFull())
  {
   availableConnections.add(createNewConnectionForPool());
  }
 }
 
 private synchronized boolean checkIfConnectionPoolIsFull()
 {
  final int MAX_POOL_SIZE = Configuracion.getInstance().DB_MAX_CONEXIONES;
 
  if(availableConnections.size() < MAX_POOL_SIZE)
  {
   return false;
  }
 
  return true;
 }
 
 //Creating a conexion
 private Connection createNewConnectionForPool()
 {
  Configuracion config = Configuracion.getInstance();
  try {
   Class.forName(config.DB_DRIVER);
   Connection conexion = (Connection) DriverManager.getConnection(
     config.DB_URL, config.DB_USUARIO, config.DB_PASSWORD);
   return conexion;
  } catch (ClassNotFoundException e) {
   e.printStackTrace();
  } catch (SQLException e) {
   e.printStackTrace();
  }
  return null;
   
 }
 
 public synchronized Connection getConnectionFromPool()
 {
  Connection conexion = null;
  if(availableConnections.size() > 0)
  {
   conexion = (Connection) availableConnections.get(0);
   availableConnections.remove(0);
  }
  return conexion;
 }
 
 public synchronized void returnConnectionToPool(Connection conexion)
 {
  availableConnections.add(conexion);
 }
}
 