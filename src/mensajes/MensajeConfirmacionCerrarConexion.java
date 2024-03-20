package mensajes;

public class MensajeConfirmacionCerrarConexion extends Mensaje {

	public MensajeConfirmacionCerrarConexion(String origen, String destino) {
		super(TipoMensaje.M_CONF_CERRAR_CONEXION, origen, destino);
	}

}
