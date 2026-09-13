/**
 * Clase que representa a un Cliente de PuellaGame.
 */
public class Cliente {
    private int id;
    private String nombre;
    private int puntosAcumulados;

    /**
     * Constructor para inicializar un Cliente.
     * @param id Identificador único del cliente.
     * @param nombre Nombre completo del cliente.
     * @param puntosAcumulados Puntos que el cliente tiene actualmente (Dato numérico).
     */
    public Cliente(int id, String nombre, int puntosAcumulados) {
        this.id = id;
        this.nombre = nombre;
        this.puntosAcumulados = puntosAcumulados;
    }

    /**
     * Convierte los datos del cliente a una cadena en formato CSV.
     * @return Cadena separada por comas.
     */
    public String aCSV() {
        return id + "," + nombre + "," + puntosAcumulados;
    }

    public int getId() { return id; }
}