/**
 * Clase que representa a una Sucursal del centro de entretenimiento PuellaGame.
 * Contiene la estructura básica de datos para esta entidad.
 */
public class Sucursal {
    private int id;
    private String nombre;
    private String direccion;

    /**
     * Constructor para inicializar una Sucursal.
     * @param id Identificador único (llave) de la sucursal.
     * @param nombre Nombre de la sucursal.
     * @param direccion Dirección física de la sucursal.
     */
    public Sucursal(int id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    /**
     * Convierte los datos de la sucursal a una cadena en formato CSV.
     * @return Cadena con los datos separados por comas.
     */
    public String aCSV() {
        return id + "," + nombre + "," + direccion;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
}