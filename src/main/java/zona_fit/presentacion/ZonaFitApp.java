package zona_fit.presentacion;

import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IClienteDAO;
import zona_fit.dominio.Cliente;

import java.util.Scanner;

public class ZonaFitApp {
    public static void main(String[] args) {
        zonaFitApp();
    }
    private static void zonaFitApp(){
        var salir = false;
        var consola = new Scanner(System.in);
        //Creamos objeto de la clase cliente dao
        IClienteDAO clienteDao = new ClienteDAO();
        while(!salir){
            try{
                var opcion = mostrarmenu(consola);
                salir = ejecutarOpciones(consola, opcion, clienteDao);

            }catch(Exception e){
                System.out.println("Error al ejecutar opciones: " + e.getMessage());

            }
            System.out.println();
        }
    }
    private static int mostrarmenu(Scanner consola){
        System.out.println("""
                ***Zona fit gym
                1. Listar clientes
                2. buscar cliente
                3. Agregar cliente
                4. Modificar cliente
                5. Eliminar cliente
                6. Salir
                Elije una opcion:\s""");
        return Integer.parseInt(consola.nextLine());

    }
    private static boolean ejecutarOpciones(Scanner consola, int opcion, IClienteDAO clienteDAO){
        var salir = false;
        switch (opcion){
            case 1 -> {
                System.out.println("---Listado de clientes---");
                var clientes = clienteDAO.ListarClientes();
                clientes.forEach(System.out::println);
            }
            case 2 -> {
                System.out.println("Proporcione el id que del cliente que quiere buscar: ");
                var id = Integer.parseInt(consola.nextLine());
                var clienteBuscado = new Cliente(id);
                var encontrado = clienteDAO.buscarClientePorId(clienteBuscado);
                if(encontrado){
                    System.out.println("Datos del cliente encontrado: " + clienteBuscado);
                }
                else{
                    System.out.println("Cliente no encontrado: " + clienteBuscado);
                }
            }
            case 3 ->{
                System.out.println("Ingrese el nombre del cliente que quiere agregar: ");
                String nombre = consola.nextLine();
                System.out.println("Ingrese el apellido del cliente que quiere agregar: ");
                String apellido = consola.nextLine();
                System.out.println("Ingrese el valor de membresía del cliente que quiere agregar: ");
                int membresia = Integer.parseInt(consola.nextLine());
                var clienteAgregado = new Cliente(nombre, apellido, membresia);
                var agregado = clienteDAO.agregarCliente(clienteAgregado);
                if(agregado){
                    System.out.println("Nuevo cliente agregado a la base de datos: " + clienteAgregado);

                }
                else{
                    System.out.println("El cliente no pudo ser agregado exitosamente: " + clienteAgregado);
                }
            }
            case 4 ->{
                System.out.println("Ingrese el número de id del cliente que quiere modificar: ");
                int id = Integer.parseInt(consola.nextLine());
                System.out.println("Ingrese el nuevo nombre del cliente que quiere modificar: ");
                String nombre = consola.nextLine();
                System.out.println("Ingrese el apellido nuevo del cliente que quiere modificar: ");
                String apellido = consola.nextLine();
                System.out.println("Ingrese el valor de membresia nuevo del cliente que quiere modificar: ");
                int membresia = Integer.parseInt(consola.nextLine());
                var clienteModificado = new Cliente(id, nombre, apellido, membresia);
                var modificado = clienteDAO.modificarCliente(clienteModificado);
                if(modificado){
                    System.out.println("Cliente modificado exitosamente: " + clienteModificado);
                }
                else{
                    System.out.println("El cliente no pudo ser modificado: " + clienteModificado);
                }
            }
            case 5->{
                System.out.println("Ingrese el id del cliente que desea eliminar: ");
                int id = Integer.parseInt(consola.nextLine());
                var clienteEliminado = new Cliente(id);
                var eliminado = clienteDAO.eliminarCliente(clienteEliminado);
                if(eliminado){
                    System.out.println("Cliente eliminado exitosamente: " + clienteEliminado);

                }
                else{
                    System.out.println("El cliente no pudo ser eliminado exitosamente: " + clienteEliminado);
                }
            }
            case 6->{
                salir = true;
            }
        }
        return salir;
    }
}
