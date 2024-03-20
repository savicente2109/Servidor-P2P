package mensajes;

public class MensajeEmitirFichero extends Mensaje {

	private static final long serialVersionUID = 1L;
	private String titulo;
	private String nomReceptor;
	
	public MensajeEmitirFichero(String origen, String destino, String titulo, String nomReceptor) {
		super(TipoMensaje.M_EMITIR_FICHERO, origen, destino);
		this.titulo = titulo;
		this.nomReceptor = nomReceptor;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public String getNomReceptor() {
		return nomReceptor;
	}
}
