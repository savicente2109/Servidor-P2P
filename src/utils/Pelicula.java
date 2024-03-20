package utils;
import java.io.Serializable;

public class Pelicula implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public String titulo;

	public Pelicula(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}
}
