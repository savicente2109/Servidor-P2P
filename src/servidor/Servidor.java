package servidor;
import concurrencia.TablaConcurrenteCanales;
import concurrencia.TablaConcurrenteUsuarios;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {
	
	private ServerSocket ss;
	private TablaConcurrenteUsuarios tUsr;
	private TablaConcurrenteCanales tCanales;

	public static void main(String args[]) {
		
		try {
			Servidor servidor = new Servidor();
			servidor.inicializar();
			servidor.funcionar();	
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void inicializar() throws IOException {
		System.out.println("Se ha creado el servidor");
		ss = new ServerSocket(999);
		tUsr = new TablaConcurrenteUsuarios();
		tCanales = new TablaConcurrenteCanales();
	}
	
	private void funcionar() throws IOException {
		while(true) {
			Socket sc = ss.accept();
			System.out.println("Se ha creado el Socket.");
			OyenteCliente oc = new OyenteCliente(sc, tUsr, tCanales);
			oc.start();
		}
	}
}
