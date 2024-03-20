package mensajes;

public class MensajeConfirmacionFicheroDescargado extends Mensaje {

	private static final long serialVersionUID = 1L;
	String titulo;
	
	public MensajeConfirmacionFicheroDescargado(String origen, String destino, String titulo) {
		super(TipoMensaje.M_CONF_FICHERO_DESC, origen, destino);
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}
}
