package mensajes;
public class MensajeListaUsuarios extends Mensaje {

	private static final long serialVersionUID = 1L;

	public MensajeListaUsuarios(String origen, String destino) {
		super(TipoMensaje.M_LISTA_USR, origen, destino);
	}

}
