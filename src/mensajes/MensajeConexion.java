package mensajes;

import java.util.ArrayList;

import utils.Pelicula;

public class MensajeConexion extends Mensaje {
	
	private static final long serialVersionUID = 1L;
	String nombre;
	ArrayList<Pelicula> peliculasNuevas;

	public MensajeConexion(String origen, String destino, String nombre, ArrayList<Pelicula> peliculasNuevas) {
		super(TipoMensaje.M_CONEXION, origen, destino);
		this.nombre = nombre;
		this.peliculasNuevas = peliculasNuevas;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public ArrayList<Pelicula> getPeliculasNuevas() {
		return peliculasNuevas;
	}

}
