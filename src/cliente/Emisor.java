package cliente;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import concurrencia.Monitor;
import mensajes.MensajeConexion;
import utils.Pelicula;

public class Emisor extends Thread {
	
	private int puerto;
	private ServerSocket ss;
	private Socket s;
	private ObjectOutputStream fout;
	private Pelicula p;
	
	public Emisor(Pelicula p) throws IOException {
		ss = new ServerSocket(0);
		puerto = ss.getLocalPort();
		System.out.println("EMISOR EN PUERTO: " + puerto);
		this.p = p;
	}
	
	public void run() {
		try {
			s = ss.accept();
			fout = new ObjectOutputStream(s.getOutputStream());
			fout.writeObject(p);
			fout.flush();
			s.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPuerto() {
		return puerto;
	}
}
