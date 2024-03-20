package mensajes;

public class MensajeConfirmacionListaUsuarios extends Mensaje {
	
	private String info;

	public MensajeConfirmacionListaUsuarios(String origen, String destino, String info) {
		super(TipoMensaje.M_CONF_LISTA_USR, origen, destino);
		this.info = info;
	}
	
	public String getInfo() {
		return info;
	}
}
