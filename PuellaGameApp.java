import java.util.Scanner;

/**
 * Clase principal que contiene el menú de interacción y la lógica de interfaz de usuario
 * para el prototipo de base de datos de PuellaGame.
 */
public class PuellaGameApp {
    private static Scanner scanner = new Scanner(System.in);
    private static GestorArchivosCSV gestor = new GestorArchivosCSV();
    
    // Nombres de los archivos
    private static final String ARCHIVO_SUCURSALES = "sucursales.csv";
    private static final String ARCHIVO_PREMIOS = "premios.csv";
    private static final String ARCHIVO_CLIENTES = "clientes.csv";

    /**
     * Método de entrada principal del programa.
     * @param args Argumentos de consola.
     */
    public static void main(String[] args) {
        int opcion = 0;
        do {
            System.out.println("\n=== SISTEMA PUELLAGAME (PROTOTIPO CSV) ===");
            System.out.println("1. Gestionar Sucursales");
            System.out.println("2. Gestionar Premios");
            System.out.println("3. Gestionar Clientes");
            System.out.println("4. Salir");
            
            opcion = leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1: menuCRUD("Sucursales", ARCHIVO_SUCURSALES); break;
                case 2: menuCRUD("Premios", ARCHIVO_PREMIOS); break;
                case 3: menuCRUD("Clientes", ARCHIVO_CLIENTES); break;
                case 4: System.out.println("Saliendo del sistema..."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
        
        scanner.close();
    }

    /**
     * Muestra un submenú genérico para Agregar, Consultar, Editar o Eliminar.
     * @param entidad Nombre de la entidad (ej. "Clientes").
     * @param archivo Nombre del archivo CSV correspondiente.
     */
    private static void menuCRUD(String entidad, String archivo) {
        int opcion = 0;
        do {
            System.out.println("\n--- GESTIÓN DE " + entidad.toUpperCase() + " ---");
            System.out.println("1. Agregar");
            System.out.println("2. Consultar por Llave (ID)");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("5. Volver al menú principal");
            
            opcion = leerEntero("Opción: ");
            
            switch (opcion) {
                case 1: agregarEntidad(entidad, archivo); break;
                case 2: consultarEntidad(archivo); break;
                case 3: editarEntidad(entidad, archivo); break;
                case 4: eliminarEntidad(archivo); break;
                case 5: break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }

    /**
     * Lógica para agregar dinámicamente un registro según la entidad.
     */
    private static void agregarEntidad(String entidad, String archivo) {
        System.out.println("\n[AGREGAR " + entidad + "]");
        int id = leerEntero("Ingrese ID (Numérico): ");
        
        // Validar que la llave no exista ya
        if (gestor.consultarPorId(archivo, id) != null) {
            System.out.println("Error: Ya existe un registro con ese ID.");
            return;
        }

        System.out.print("Ingrese Nombre: ");
        String nombre = scanner.nextLine();

        String lineaCSV = "";
        if (entidad.equals("Sucursales")) {
            System.out.print("Ingrese Dirección: ");
            String direccion = scanner.nextLine();
            Sucursal s = new Sucursal(id, nombre, direccion);
            lineaCSV = s.aCSV();
        } else if (entidad.equals("Premios")) {
            int puntos = leerEntero("Ingrese Puntos Requeridos (Numérico): ");
            Premio p = new Premio(id, nombre, puntos);
            lineaCSV = p.aCSV();
        } else if (entidad.equals("Clientes")) {
            int puntos = leerEntero("Ingrese Puntos Acumulados (Numérico): ");
            Cliente c = new Cliente(id, nombre, puntos);
            lineaCSV = c.aCSV();
        }
        
        gestor.agregarRegistro(archivo, lineaCSV);
    }

    /**
     * Lógica para consultar un registro solicitando la llave.
     */
    private static void consultarEntidad(String archivo) {
        int id = leerEntero("\nIngrese la Llave (ID) a consultar: ");
        String resultado = gestor.consultarPorId(archivo, id);
        if (resultado != null) {
            System.out.println("-> Datos encontrados: " + resultado);
        } else {
            System.out.println("-> No se encontró ningún registro con el ID " + id);
        }
    }

    /**
     * Lógica para editar un registro existente.
     */
    private static void editarEntidad(String entidad, String archivo) {
        int id = leerEntero("\nIngrese la Llave (ID) del registro a editar: ");
        if (gestor.consultarPorId(archivo, id) == null) {
            System.out.println("-> No existe un registro con ese ID.");
            return;
        }
        
        System.out.println("Ingrese los nuevos datos:");
        System.out.print("Nuevo Nombre: ");
        String nombre = scanner.nextLine();
        
        String nuevaLinea = "";
        if (entidad.equals("Sucursales")) {
            System.out.print("Nueva Dirección: ");
            String direccion = scanner.nextLine();
            nuevaLinea = new Sucursal(id, nombre, direccion).aCSV();
        } else if (entidad.equals("Premios")) {
            int puntos = leerEntero("Nuevos Puntos Requeridos: ");
            nuevaLinea = new Premio(id, nombre, puntos).aCSV();
        } else if (entidad.equals("Clientes")) {
            int puntos = leerEntero("Nuevos Puntos Acumulados: ");
            nuevaLinea = new Cliente(id, nombre, puntos).aCSV();
        }

        if (gestor.editarRegistro(archivo, id, nuevaLinea)) {
            System.out.println("-> Registro actualizado exitosamente.");
        }
    }

    /**
     * Lógica para eliminar un registro.
     */
    private static void eliminarEntidad(String archivo) {
        int id = leerEntero("\nIngrese la Llave (ID) del registro a eliminar: ");
        if (gestor.eliminarRegistro(archivo, id)) {
            System.out.println("-> Registro eliminado exitosamente.");
        } else {
            System.out.println("-> No se encontró el registro para eliminar.");
        }
    }

    /**
     * Método robusto para leer números. Atrapa excepciones si el usuario
     * ingresa letras o caracteres especiales, cumpliendo con la validación solicitada.
     * 
     * @param mensaje Mensaje a mostrar al usuario.
     * @return El número entero validado.
     */
    private static int leerEntero(String mensaje) {
        int numero = 0;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print(mensaje);
                numero = Integer.parseInt(scanner.nextLine().trim());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("¡EXCEPCIÓN ATRAPADA! Error: El campo es estrictamente numérico. Intente de nuevo.");
            }
        }
        return numero;
    }
}