package clases;
import java.sql.*;  //importantisimo escribir esto a mano. NO DEJAR QUE LO HAGA NETBIN

public class Conexion {
    //Conexion Local. 
    
    public static Connection conectar(){
        try{
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/bd_ds", "root", "");
            return cn;
        } catch(SQLException e){
            System.out.println("Error en conexion local " + e);
        }
        return (null); //esto se pone para el error
    }
}
