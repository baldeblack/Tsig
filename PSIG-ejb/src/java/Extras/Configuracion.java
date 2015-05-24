package Extras;

public class Configuracion {
  
 public String DB_USUARIO ;
  
 public String DB_PASSWORD ;
  
 public String DB_URL;
  
 public String DB_DRIVER;
  
 public Integer DB_MAX_CONEXIONES;
  
 public Configuracion(){
  init();
 }
  
 private static Configuracion configuration = new Configuracion();
  
 public static Configuracion getInstance(){ 
  return configuration;
 }
  
 private void init() {
  DB_USUARIO = "postgres";
  DB_PASSWORD = "123456";
  DB_URL = "jdbc:postgresql://localhost:5432/Geografica";
  DB_DRIVER = "org.postgresql.Driver";
  DB_MAX_CONEXIONES = 10;
 }
}

