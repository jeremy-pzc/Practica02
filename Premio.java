/**
 * Clase que representa un Premio que los clientes pueden canjear.
 */
public class Premio {
    private int id;
    private String nombre;
    private int puntosRequeridos;

    /**
     * Constructor para inicializar un Premio.
     * @param id Identificador único del premio.
     * @param nombre Nombre descriptivo del premio.
     * @param puntosRequeridos Cantidad de puntos necesarios para obtenerlo (Dato numérico).
     */
    public Premio(int id, String nombre, int puntosRequeridos) {
        this.id = id;
        this.nombre = nombre;
        this.puntosRequeridos = puntosRequeridos;
    }

    /**
     * Convierte los datos del premio a una cadena en formato CSV.
     * @return Cadena separada por comas.
     */
    public String aCSV() {
        return id + "," + nombre + "," + puntosRequeridos;
    }

    public int getId() { return id; }
}