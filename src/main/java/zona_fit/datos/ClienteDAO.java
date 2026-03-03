package zona_fit.datos;

import zona_fit.conexion.Conexion;
import zona_fit.dominio.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IClienteDAO{
    @Override
    public List<Cliente> ListarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        //nos permite preparar la sentencia sql que vamos a mandar a la base de datos
        PreparedStatement ps;
        //nos permite recibir la info de la consulta que realizamos
        ResultSet rs;
        Connection con = Conexion.getConexion();
        //sentencia sql
        var sql = "SELECT * FROM cliente ORDER BY id";
        try{
            //Nos conectamos a la base de datos y le pasamos la sentencia sql
            ps = con.prepareStatement(sql);
            //ejecutamos la sentencia sql y se almacena en rs
            rs = ps.executeQuery();
            while(rs.next()){
                //Creamos un objeto cliente para meter los valores de la bd ahi
                var cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }
        }catch(Exception e){
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        finally {

            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        return clientes;
    }

    @Override
    public boolean buscarClientePorId(Cliente cliente) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = Conexion.getConexion();
        var sql = "SELECT * FROM cliente WHERE id = ?";
        try{
            //Nos conectamos a la base de datos y le pasamos la sentencia sql
            ps = con.prepareStatement(sql);
            //Llenamos el parametro pendiente
            ps.setInt(1,cliente.getId());
            //ejecutamos la sentencia sql y se almacena en rs
            rs = ps.executeQuery();
            if(rs.next()){
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                return true;


            }


        }catch(Exception e){
            System.out.println("Error al recuperar cliente por id: " + e.getMessage());
        }
        finally {

            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = Conexion.getConexion();
        //Los parametros son en orden, 1, 2 y 3
        String sql = "INSERT INTO cliente(nombre, apellido, membresia) " + "VALUES(?, ?, ?)";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.execute();
            return true;

        }catch(Exception e){
            System.out.println("Error al agregar cliente: " + e.getMessage());
        }
        finally {
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar conexion: "+ e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = Conexion.getConexion();
        var sql = "UPDATE cliente SET nombre=?, apellido=?, membresia=? " + " WHERE id = ?";
        try{
            //Pasamos los parámetros para la sentencia sql
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.setInt(4, cliente.getId());
            ps.execute();
            return true;
        }catch(Exception e){
            System.out.println("Error al modificar el cliente: " + e.getMessage());
        }
        finally{
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = Conexion.getConexion();
        var sql = "DELETE FROM cliente WHERE id = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            ps.execute();
            return true;
        }catch(Exception e){
            System.out.println("Error eliminando cliente");
        }
        finally{
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error cerrando la conexión");
            }
        }
        return false;

    }


}
