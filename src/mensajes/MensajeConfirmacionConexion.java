package mensajes;

import java.util.ArrayList;

import utils.Pelicula;

public class MensajeConfirmacionConexion extends Mensaje {

	private static final long serialVersionUID = 1L;
	private boolean conectado;
	private int numConectados;
	private ArrayList<Pelicula> infoUsr;

	public MensajeConfirmacionConexion(String origen, String destino, boolean conectado, int numConectados, ArrayList<Pelicula> infoUsr) {
		super(TipoMensaje.M_CONF_CONEXION, origen, destino);
		this.conectado = conectado;
		this.numConectados = numConectados;
		this.infoUsr = infoUsr;
	}
	
	public boolean getConectado() {
		return conectado;
	}
	
	public int getNumConectados() {
		return numConectados;
	}
	
	public ArrayList<Pelicula> getInfoUsr() {
		return infoUsr;
	}

}
