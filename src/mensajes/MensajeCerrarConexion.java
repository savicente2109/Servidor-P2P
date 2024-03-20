package mensajes;

public class MensajeCerrarConexion extends Mensaje {

	private static final long serialVersionUID = 1L;

	public MensajeCerrarConexion(String origen, String destino) {
		super(TipoMensaje.M_CERRAR_CONEXION, origen, destino);
		// TODO Auto-generated constructor stub
	}

}
