import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase robusta encargada de manejar las operaciones de lectura, escritura,
 * actualización y eliminación (CRUD) en archivos físicos .CSV.
 */
public class GestorArchivosCSV {

    /**
     * Agrega una nueva línea a un archivo CSV específico.
     * @param nombreArchivo El nombre del archivo (ej. "clientes.csv").
     * @param lineaCSV La cadena de texto en formato CSV a guardar.
     */
    public void agregarRegistro(String nombreArchivo, String lineaCSV) {
        try (FileWriter fw = new FileWriter(nombreArchivo, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(lineaCSV);
            System.out.println("-> Registro guardado exitosamente en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    /**
     * Consulta y retorna todos los registros de un archivo.
     * @param nombreArchivo El nombre del archivo a leer.
     * @return Una lista de cadenas, donde cada cadena es una línea del archivo.
     */
    public List<String> leerTodos(String nombreArchivo) {
        List<String> lineas = new ArrayList<>();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) return lineas; // Retorna vacío si el archivo no existe

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return lineas;
    }

    /**
     * Consulta un registro específico utilizando su Llave (ID).
     * @param nombreArchivo El archivo donde buscar.
     * @param id La llave de la entidad a buscar.
     * @return La línea CSV si se encuentra, o null si no existe.
     */
    public String consultarPorId(String nombreArchivo, int id) {
        List<String> lineas = leerTodos(nombreArchivo);
        for (String linea : lineas) {
            String[] datos = linea.split(",");
            if (datos.length > 0 && Integer.parseInt(datos[0]) == id) {
                return linea;
            }
        }
        return null;
    }

    /**
     * Elimina un registro de un archivo basado en su ID.
     * @param nombreArchivo Archivo a modificar.
     * @param id Identificador del registro a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminarRegistro(String nombreArchivo, int id) {
        List<String> lineas = leerTodos(nombreArchivo);
        boolean encontrado = false;
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo, false))) {
            for (String linea : lineas) {
                String[] datos = linea.split(",");
                if (Integer.parseInt(datos[0]) == id) {
                    encontrado = true; // No lo escribimos de nuevo (lo eliminamos)
                } else {
                    pw.println(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al modificar el archivo: " + e.getMessage());
        }
        return encontrado;
    }

    /**
     * Edita un registro existente sobrescribiéndolo con nuevos datos.
     * @param nombreArchivo Archivo a modificar.
     * @param id ID del registro a editar.
     * @param nuevaLineaCSV Los nuevos datos en formato CSV.
     * @return true si se actualizó correctamente.
     */
    public boolean editarRegistro(String nombreArchivo, int id, String nuevaLineaCSV) {
        List<String> lineas = leerTodos(nombreArchivo);
        boolean encontrado = false;
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo, false))) {
            for (String linea : lineas) {
                String[] datos = linea.split(",");
                if (Integer.parseInt(datos[0]) == id) {
                    pw.println(nuevaLineaCSV); // Escribimos la línea actualizada
                    encontrado = true;
                } else {
                    pw.println(linea); // Mantenemos la línea original
                }
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar el archivo: " + e.getMessage());
        }
        return encontrado;
    }
}