package mensajes;

public class MensajePreparadoSC extends Mensaje {
	
	private static final long serialVersionUID = 1L;
	private String ipEmisor;
	private int puertoEmisor;

	public MensajePreparadoSC(String origen, String destino, String ipEmisor, int puertoEmisor) {
		super(TipoMensaje.M_PREPARADO_SC, origen, destino);
		this.ipEmisor = ipEmisor;
		this.puertoEmisor = puertoEmisor;
	}

	public String getIpEmisor() {
		return ipEmisor;
	}
	
	public int getPuertoEmisor() {
		return puertoEmisor;
	}
}
