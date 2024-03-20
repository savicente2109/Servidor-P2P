package cliente;
import java.io.IOException;
import concurrencia.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import mensajes.*;
import utils.Pelicula;

public class OyenteServidor extends Thread {

	private Socket sc;
	private Usuario usr;
	private ObjectOutputStream fout;
	private ObjectInputStream fin;
	private Cliente cli;
	private boolean escuchando = true;
	private List<Emisor> emisores;
	private List<Receptor> receptores;
	private Lock l;

	public OyenteServidor(Socket sc, Usuario usr, Cliente cli, Lock l) {
		this.l = l;
		this.sc = sc;
		this.usr = usr;
		this.cli = cli;
		this.emisores = new ArrayList<Emisor>();
		this.receptores = new ArrayList<Receptor>();
	}
	
	public void run() {
		try {
			
			fout = new ObjectOutputStream(sc.getOutputStream());
			fout.writeObject(new MensajeConexion(usr.getIP(), "localhost", usr.getId(), usr.getInfoCompartida()));
			fout.flush();
			
			fin = new ObjectInputStream(sc.getInputStream());
			
			while (escuchando) {
				
				Mensaje m = (Mensaje) fin.readObject();
				
				switch (m.getTipo()) {
				case M_CONF_CONEXION:
					mConfConexion((MensajeConfirmacionConexion) m);
					break;
					
				case M_CONF_LISTA_USR:
					mConfListaUsr((MensajeConfirmacionListaUsuarios) m);
					break;
			
				case M_EMITIR_FICHERO:
					mEmitirFichero((MensajeEmitirFichero) m);
					break;
		
				case M_PREPARADO_SC:
					mPreparadoSC((MensajePreparadoSC) m);
					break;
					
				case M_CONF_FICHERO_DESC:
					mConfFicheroDesc((MensajeConfirmacionFicheroDescargado) m);
					break;
					
				case M_CONF_CERRAR_CONEXION:
					mConfCerrarConexion();
					break;
					
				default:
					break;
				}
			}
			for (Emisor em : emisores)
				em.join();
			for (Receptor r : receptores)
				r.join();  // Esperamos a que terminen las descargas en curso.
			fin.close();
			fout.close();
		}
		catch (IOException e) {
		}
		catch (ClassNotFoundException e) {
		}
		catch (InterruptedException e) {
		}
		
	}
	
	public ObjectOutputStream getFout() {
		return fout;
	}
	
	public ObjectInputStream getFin() {
		return fin;
	}
	
	private void mConfConexion(MensajeConfirmacionConexion m) throws InterruptedException {
		if (m.getConectado()) {
			cli.actualizarUsr(m.getInfoUsr());
			l.takeLock(2);
			System.out.println("Se ha establecido la conexi�n con el servidor.");
			System.out.println("Hay " + m.getNumConectados() + " usuario(s) conectado(s).");
			l.releaseLock(2);
		}
		else {
			l.takeLock(2);
			System.out.println("Error de conexi�n. Es posible que este usuario ya est� conectado desde otro dispositivo.");
			l.releaseLock(2);
			escuchando = false;
		}
	}
	
	private void mConfListaUsr(MensajeConfirmacionListaUsuarios m) {
		l.takeLock(2);
		System.out.println(m.getInfo());
		l.releaseLock(2);
	}
	
	private void mEmitirFichero(MensajeEmitirFichero m) throws InterruptedException, IOException {
		// Acceso protegido por la implementación de lectores-escritores
		// de la clase ListaConcurrentePeliculas.
		Pelicula p = usr.buscarPelicula(m.getTitulo());
		
		Emisor e = new Emisor(p);
		emisores.add(e);
		e.start();
		
		fout.writeObject(new MensajePreparadoCS(usr.getIP(), "localhost", e.getPuerto(), m.getNomReceptor()));
		fout.flush();
	}
	
	private void mPreparadoSC(MensajePreparadoSC m) {
		Receptor r = new Receptor(m.getIpEmisor(), m.getPuertoEmisor(), usr, fout);
		receptores.add(r);
		r.start();
	}
	
	private void mConfFicheroDesc(MensajeConfirmacionFicheroDescargado m) {
		l.takeLock(2);
		System.out.println("Descarga finalizada.");
		System.out.println("Se ha añadido " + m.getTitulo() + " a tu lista de información compartida.");
		l.releaseLock(2);
	}
	
	private void mConfCerrarConexion() {
		escuchando = false;
	}
}
