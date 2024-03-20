package mensajes;

import java.io.Serializable;

public abstract class Mensaje implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public TipoMensaje tipo;
	public String origen;
	public String destino;
	
	public Mensaje(TipoMensaje tipo, String origen, String destino) {
		this.tipo = tipo;
		this.origen = origen;
		this.destino = destino;
	}
	
	public TipoMensaje getTipo() {
		return this.tipo;
	}
	
	public String getOrigen() {
		return this.origen;
	}
	
	public String getDestino() {
		return this.destino;
	}
}
