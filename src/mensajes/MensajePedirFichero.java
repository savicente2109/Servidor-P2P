package mensajes;
public class MensajePedirFichero extends Mensaje {
	
	private String nombreUsr;
	private String titulo;

	public MensajePedirFichero(String origen, String destino, String nombreUsr, String titulo) {
		super(TipoMensaje.M_PEDIR_FICHERO, origen, destino);
		this.nombreUsr = nombreUsr;
		this.titulo = titulo;		
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public String getNombreUsr() {
		return nombreUsr;
	}
}