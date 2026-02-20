package zona_fit.conexion;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public static Connection getConexion(){
        Connection conexion = null;
        var baseDatos = "zona_fit_db";
        var url = "jdbc:mysql://localhost:3306/" + baseDatos;
        var usuario = "root";
        var password = "admin";
        try{
            //Clase de conexión a la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Creamos la conexion con los parametros de url, usuario y contraseña
            conexion = DriverManager.getConnection(url, usuario, password);

        }catch(Exception e){
            System.out.println("Error al conectarnos a la base de datos: " + e.getMessage());
        }
        return conexion;

    }

}
