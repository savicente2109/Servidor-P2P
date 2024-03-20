package mensajes;

public class MensajeFicheroDescargado extends Mensaje {

	private static final long serialVersionUID = 1L;
	String titulo;
	
	public MensajeFicheroDescargado(String origen, String destino, String titulo) {
		super(TipoMensaje.M_FICHERO_DESC, origen, destino);
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}
}
