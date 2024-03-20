package mensajes;

public class MensajePreparadoCS extends Mensaje {
	
	private static final long serialVersionUID = 1L;
	private int puerto;
	private String nomReceptor;

	public MensajePreparadoCS(String origen, String destino, int puerto, String nomReceptor) {
		super(TipoMensaje.M_PREPARADO_CS, origen, destino);
		this.puerto = puerto;
		this.nomReceptor = nomReceptor;
	}
	
	public int getPuerto() {
		return puerto;
	}
	
	public String getNomReceptor() {
		return nomReceptor;
	}

}
